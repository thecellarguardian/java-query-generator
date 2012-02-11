/**
 * @file PAQLSemanticAnalyzer.java
 * @author Cosimo Sacco <cosimosacco@gmail.com>
 *
 * @section LICENSE
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/

package paql.src.PAQLCompiler.PAQLSemanticAnalyzer;

import paql.lib.Pair.Pair;
import paql.lib.Triple.Triple;
import paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree.ParseTree;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.PAQLParseTreeClass.PAQLParseTreeClass;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Description;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Element.Element;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Element.DeclarationBlock.DeclarationBlock;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Element.VariableDeclaration.VariableDeclaration;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Element.VariableDeclarationBlock.VariableDeclarationBlock;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Element.KeyDeclaration.KeyDeclaration;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Container.Container;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Query.Query;
import paql.lib.Compiler.SemanticAnalyzer.SemanticAnalyzer;
import paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure.PAQLSemanticStructure;
import paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure.ElementInformation.ElementInformation;

import java.util.*;

public class PAQLSemanticAnalyzer
implements SemanticAnalyzer<PAQLParseTreeClass, PAQLSemanticStructure>
{
    private void extractDescriptionInformation
    (
        ParseTree<PAQLParseTreeClass> parseTree,
        PAQLSemanticStructure semanticStructure
    )
    {
        parseTree.semanticCheck(PAQLParseTreeClass.DESCRIPTION, "source file");
        ListIterator< ParseTree<PAQLParseTreeClass> > i = parseTree.iterator();
        while(i.hasNext())
        {
            ParseTree<PAQLParseTreeClass> child = i.next();
            if(child.checkMetaType(PAQLParseTreeClass.ELEMENT))
            {
                extractElementInformation(child, semanticStructure);
            }
            else if(child.checkMetaType(PAQLParseTreeClass.CONTAINER))
            {
                extractContainerInformation(child, semanticStructure);
            }
            else if(child.checkMetaType(PAQLParseTreeClass.QUERY))
            {
                extractQueryInformation(child, semanticStructure);
            }
            else
            {
                throw new RuntimeException
                (
                    "Semantic error: PAQL construct expected, nothing found"
                );
            }
        }
    }
    @SuppressWarnings({"unchecked"}) private void extractElementInformation
    (
        ParseTree<PAQLParseTreeClass> parseTree,
        PAQLSemanticStructure semanticStructure
    )
    {
        parseTree.semanticCheck(PAQLParseTreeClass.ELEMENT, "description");
        String elementType = ((Element)parseTree).getIdentifier();
        if(semanticStructure.elementsDataStructure.containsKey(elementType))
        {
            throw new RuntimeException
            (
                "Semantic error: multiple definition of " + elementType
            );
        }
        semanticStructure.elementsDataStructure.put
        (
            elementType,
            new ElementInformation()
        );
        if(!(parseTree.iterator()).hasNext())
        {
            throw new RuntimeException
            (
                "Semantic error: no declaration block found for " + elementType
            );
        }
        ParseTree<PAQLParseTreeClass> declarationBlock = (parseTree.iterator()).next();
        declarationBlock.semanticCheck(PAQLParseTreeClass.DECLARATION_BLOCK, elementType);
        ListIterator< ParseTree<PAQLParseTreeClass> > i = declarationBlock.iterator();
        Integer keyIndex = new Integer(0);
        while(i.hasNext())
        {
            ParseTree<PAQLParseTreeClass> child = i.next();
            if(child.checkMetaType(PAQLParseTreeClass.KEY_DECLARATION))
            {
                extractKeyDeclarationInformation(child, semanticStructure, elementType, keyIndex);
                keyIndex++;
            }
            else if(child.checkMetaType(PAQLParseTreeClass.VARIABLE_DECLARATION_BLOCK))
            {
                extractVariableDeclarationBlockInformation(child, semanticStructure, elementType);
            }
            else
            {
                throw new RuntimeException
                (
                    "Semantic error: in " +
                    elementType +
                    ": key or variable declaration block expected, "
                    + child.getMetaType() +
                    "found"
                );
            }
        }
    }
    @SuppressWarnings({"unchecked"}) private void extractKeyDeclarationInformation
    (
        ParseTree<PAQLParseTreeClass> parseTree,
        PAQLSemanticStructure semanticStructure,
        String elementType,
        Integer keyIndex
    )
    {
        parseTree.semanticCheck(PAQLParseTreeClass.KEY_DECLARATION, elementType);
        if
        (
            (
                (semanticStructure.elementsDataStructure.get(elementType))
                .keyInformation
            ).containsKey(keyIndex)
        )
        {
            throw new RuntimeException
            (
                "Semantic error: in " +
                elementType +
                ": multiple definition of key " +
                keyIndex
            );
        }
        ((semanticStructure.elementsDataStructure.get(elementType)).keyInformation).put(keyIndex, new LinkedHashSet< Pair<String, String> >());
        if(!(parseTree.iterator()).hasNext())
        {
            throw new RuntimeException
            (
                "Semantic error: in "+ elementType + ": no key declaration block found for key " + keyIndex
            );
        }
        ParseTree<PAQLParseTreeClass> variableDeclarationBlock = (parseTree.iterator()).next();
        variableDeclarationBlock.semanticCheck(PAQLParseTreeClass.VARIABLE_DECLARATION_BLOCK, elementType + ": in key declaration " + keyIndex + ":");
        ListIterator< ParseTree<PAQLParseTreeClass> > i = variableDeclarationBlock.iterator();
        while(i.hasNext())
        {
            ParseTree<PAQLParseTreeClass> child = i.next();
            if(child.checkMetaType(PAQLParseTreeClass.VARIABLE_DECLARATION))
            {
                extractKeyVariableDeclarationInformation(child, semanticStructure, elementType, keyIndex);
            }
            else
            {
                throw new RuntimeException
                (
                    "Semantic error: in " +
                    elementType +
                    ": key " +
                    keyIndex +
                    " declaration: variable declaration expected, "
                    + child.getMetaType() +
                    "found"
                );
            }
        }
    }
    @SuppressWarnings("unchecked") private void extractKeyVariableDeclarationInformation
    (
        ParseTree<PAQLParseTreeClass> parseTree,
        PAQLSemanticStructure semanticStructure,
        String elementType,
        Integer keyIndex
    )
    {
        parseTree.semanticCheck(PAQLParseTreeClass.VARIABLE_DECLARATION, elementType + ": in key " + keyIndex + " declaration: ");
        Pair<String, String> variableDeclaration =
            new Pair<String, String>
            (
                ((VariableDeclaration)parseTree).getVariableType(),
                ((VariableDeclaration)parseTree).getVariableIdentifier()
            );
        if
        (
            ((semanticStructure.elementsDataStructure.get(elementType)).keyInformation.get(keyIndex)).contains(variableDeclaration)
        )
        {
            throw new RuntimeException
            (
                "Semantic error: in " +
                elementType +
                ": key " +
                keyIndex +
                " declaration: multiple declaration of variable " +
                variableDeclaration
            );
        }
        ((semanticStructure.elementsDataStructure.get(elementType)).keyInformation.get(keyIndex)).add(variableDeclaration);
        if(!(semanticStructure.elementsDataStructure.get(elementType)).variableInformation.contains(variableDeclaration))
        {
            (semanticStructure.elementsDataStructure.get(elementType)).variableInformation.add(variableDeclaration);
        }
    }
    @SuppressWarnings("unchecked") private void extractVariableDeclarationBlockInformation
    (
        ParseTree<PAQLParseTreeClass> parseTree,
        PAQLSemanticStructure semanticStructure,
        String elementType
    )
    {
        parseTree.semanticCheck(PAQLParseTreeClass.VARIABLE_DECLARATION_BLOCK, elementType);
        ListIterator< ParseTree<PAQLParseTreeClass> > i = parseTree.iterator();
        while(i.hasNext())
        {
            ParseTree<PAQLParseTreeClass> child = i.next();
            if(child.checkMetaType(PAQLParseTreeClass.VARIABLE_DECLARATION))
            {
                extractVariableDeclarationInformation(child, semanticStructure, elementType);
            }
            else
            {
                throw new RuntimeException
                (
                    "Semantic error: in " +
                    elementType +
                    ": variable declaration expected, "
                    + child.getMetaType() +
                    "found"
                );
            }
        }
    }
    @SuppressWarnings("unchecked") private void extractVariableDeclarationInformation
    (
        ParseTree<PAQLParseTreeClass> parseTree,
        PAQLSemanticStructure semanticStructure,
        String elementType
    )
    {
        parseTree.semanticCheck(PAQLParseTreeClass.VARIABLE_DECLARATION, elementType);
        Pair<String, String> variableDeclaration =
            new Pair<String, String>
            (
                ((VariableDeclaration)parseTree).getVariableType(),
                ((VariableDeclaration)parseTree).getVariableIdentifier()
            );
        if
        (
            (semanticStructure.elementsDataStructure.get(elementType)).variableInformation.contains(variableDeclaration)
        )
        {
            throw new RuntimeException
            (
                "Semantic error: in " +
                elementType +
                " declaration: multiple declaration of variable " +
                variableDeclaration
            );
        }
        (semanticStructure.elementsDataStructure.get(elementType)).variableInformation.add(variableDeclaration);
    }
    @SuppressWarnings("unchecked") private void  extractContainerInformation
    (
        ParseTree<PAQLParseTreeClass> parseTree,
        PAQLSemanticStructure semanticStructure
    )
    {
        parseTree.semanticCheck(PAQLParseTreeClass.CONTAINER, "description");
        Pair<String, String> containerInformation =
            new Pair<String, String>
            (
                ((Container)parseTree).getContainerClassName(),
                ((Container)parseTree).getElementClassName()
            );
        if(!semanticStructure.elementsDataStructure.containsKey(containerInformation.getSecond()))
        {
            throw new RuntimeException
            (
                "Semantic error: " +
                "unknown element type: \"" +
                containerInformation.getSecond() +
                "\""

            );
        }
        if(semanticStructure.containerDataStructure.contains(containerInformation))
        {
            throw new RuntimeException
            (
                "Semantic error: " +
                "multiple declaration of container " +
                containerInformation.getFirst() +
                "<" + containerInformation.getSecond() + ">"

            );
        }
        semanticStructure.containerDataStructure.add(containerInformation);
    }
    @SuppressWarnings("unchecked") private void  extractQueryInformation
    (
        ParseTree<PAQLParseTreeClass> parseTree,
        PAQLSemanticStructure semanticStructure
    )
    {
        parseTree.semanticCheck(PAQLParseTreeClass.QUERY, "description");
        Triple<String, String, String> queryInformation =
            new Triple<String, String, String>
            (
                ((Query)parseTree).getQueryClassName(),
                ((Query)parseTree).getContainerClassName(),
                ((Query)parseTree).getElementClassName()
            );
        if(!semanticStructure.elementsDataStructure.containsKey(queryInformation.getThird()))
        {
            throw new RuntimeException
            (
                "Semantic error: " +
                "unknown element type: \"" +
                queryInformation.getThird() +
                "\""

            );
        }
        if(!semanticStructure.containerDataStructure.contains(new Pair<String, String>(queryInformation.getSecond(), queryInformation.getThird())))
        {
            throw new RuntimeException
            (
                "Semantic error: " +
                "unknown container type: " +
                queryInformation.getSecond() +
                "<"+
                queryInformation.getThird() +
                ">"

            );
        }
        if(semanticStructure.queryDataStructure.contains(queryInformation))
        {
            throw new RuntimeException
            (
                "Semantic error: " +
                "multiple declaration of query" +
                "<" + queryInformation.getThird() + "> "
                + queryInformation.getFirst() + "(" + queryInformation.getThird() + ")"

            );
        }
        semanticStructure.queryDataStructure.add(queryInformation);
    }
    public PAQLSemanticStructure transform(ParseTree<PAQLParseTreeClass> parseTree)
    {
        PAQLSemanticStructure semanticStructure = new PAQLSemanticStructure();
        extractDescriptionInformation(parseTree, semanticStructure);
        return semanticStructure;
    }
}

