/**
 * @file PAQLSyntacticAnalyzer.java
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

package paql.src.PAQLCompiler.PAQLSyntacticAnalyzer;

import paql.lib.Compiler.SyntacticAnalyzer.SyntacticAnalyzer;
import paql.lib.Compiler.LexicalAnalyzer.OutputType.Token.Token;
import paql.lib.Compiler.LexicalAnalyzer.OutputType.Token.EvaluableToken.EvaluableToken;
import paql.src.PAQLCompiler.PAQLLexicalAnalyzer.PAQLTokenClass.PAQLTokenClass;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.PAQLParseTreeClass.PAQLParseTreeClass;
import paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree.ParseTree;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Description;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Element.Element;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Element.DeclarationBlock.DeclarationBlock;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Element.VariableDeclaration.VariableDeclaration;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Element.KeyDeclaration.KeyDeclaration;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Container.Container;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Query.Query;

import java.util.*;

public class PAQLSyntacticAnalyzer
extends SyntacticAnalyzer<PAQLTokenClass, PAQLParseTreeClass>
{
    private ParseTree<PAQLParseTreeClass> parseDescription
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        System.out.println("Parsing description");
        Description description = new Description();
        while(tokenIterator.hasNext())
        {
            description.add(parseConstruct(tokenIterator));
        }
        return description;
    }
    private ParseTree<PAQLParseTreeClass> parseConstruct
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        System.out.println("\tParsing construct");
        ParseTree<PAQLParseTreeClass> construct = null;
        Token<PAQLTokenClass> constructKeyword = checkedNext(tokenIterator);
        if(expect(PAQLTokenClass.ELEMENT_KEYWORD, constructKeyword))
        {
            construct = parseElement(tokenIterator);
        }
        else if(expect(PAQLTokenClass.CONTAINER_KEYWORD, constructKeyword))
        {
            construct= parseContainer(tokenIterator);
        }
        else if(expect(PAQLTokenClass.QUERY_KEYWORD, constructKeyword))
        {
            construct = parseQuery(tokenIterator);
        }
        else
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                constructKeyword.getLineNumber()
                +
                "\n\tunknown contruct"
            );
        }
        return construct;
    }
    private ParseTree<PAQLParseTreeClass> parseElement
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        System.out.println("\t\tParsing element");
        Element element = new Element();
        Token<PAQLTokenClass> elementIdentifier = checkedNext(tokenIterator);
        if(expect(PAQLTokenClass.IDENTIFIER, elementIdentifier))
        {
            element.setIdentifier(elementIdentifier);
        }
        else
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                elementIdentifier.getLineNumber()
                +
                "\n\terror: identifier expected"
            );
        }
        element.add(parseDeclarationBlock(tokenIterator));
        return element;
    }
    private ParseTree<PAQLParseTreeClass> parseDeclarationBlock
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        System.out.println("\t\t\tParsing declaration block");
        DeclarationBlock declarationBlock = new DeclarationBlock();
        Token<PAQLTokenClass> leftCurlyBracket = checkedNext(tokenIterator);
        if(!expect(PAQLTokenClass.LEFT_CURLY_BRACKET, leftCurlyBracket))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                leftCurlyBracket.getLineNumber()
                +
                "\n\terror: '{' expected"
            );
        }
        ListIterator< Token<PAQLTokenClass> > blockEndProbe = (ListIterator< Token<PAQLTokenClass> >)tokenIterator;
        while
        (
            (!expect(PAQLTokenClass.RIGHT_CURLY_BRACKET, checkedNext(blockEndProbe)))
        )
        {
            blockEndProbe.previous();
            declarationBlock.add(parseDeclaration(blockEndProbe));
        }
        return declarationBlock;
    }
    private ParseTree<PAQLParseTreeClass> parseDeclaration
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        ParseTree<PAQLParseTreeClass> declaration;
        ListIterator< Token<PAQLTokenClass> > declarationProbe = (ListIterator< Token<PAQLTokenClass> >)tokenIterator;
        Token<PAQLTokenClass> declarationDiscriminant = checkedNext(declarationProbe);
        declarationProbe.previous();
        if(expect(PAQLTokenClass.KEY_KEYWORD, declarationDiscriminant))
        {
            declaration = parseKeyDeclaration(declarationProbe);
        }
        else if(expect(PAQLTokenClass.IDENTIFIER, declarationDiscriminant))
        {
            declaration = parseVariableDeclaration(declarationProbe);
        }
        else
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                declarationDiscriminant.getLineNumber()
                +
                "\n\terror: declaration expected"
            );
        }
        return declaration;
    }
    private ParseTree<PAQLParseTreeClass> parseKeyDeclaration
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        System.out.println("\t\t\t\tParsing key declaration");
        KeyDeclaration keyDeclaration = new KeyDeclaration();
        checkedNext(tokenIterator);
        keyDeclaration.add(parseVariableDeclaration(tokenIterator));
        return keyDeclaration;
    }
    /**
     * Why SuppressWarnings("unchecked")?
     * Java requires type-checking (through instanceof) before downcasting.
     * But, because of Java Generics type erasure, no type-check can be performed
     * on generic types. However, since Token type is evalued through its metaType
     * value, the downcast is safe and thus can be performed. The compiler
     * still warns, but in this case those "unchecked" warnings can be safely turned off.
     **/
    @SuppressWarnings("unchecked") private ParseTree<PAQLParseTreeClass> parseVariableDeclaration
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        System.out.println("\t\t\t\tParsing variable declaration");
        VariableDeclaration variableDeclaration = new VariableDeclaration();
        Token<PAQLTokenClass> type = checkedNext(tokenIterator);
        if(expect(PAQLTokenClass.IDENTIFIER, type))
        {
            variableDeclaration.setVariableType(((EvaluableToken<PAQLTokenClass, String>)type).getValue());
        }
        else
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                type.getLineNumber()
                +
                "\n\terror: type expected"
            );
        }
        Token<PAQLTokenClass> identifier = checkedNext(tokenIterator);
        if(expect(PAQLTokenClass.IDENTIFIER, identifier))
        {
            variableDeclaration.setVariableIdentifier(((EvaluableToken<PAQLTokenClass, String>)identifier).getValue());
        }
        else
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                identifier.getLineNumber()
                +
                "\n\terror: identifier expected"
            );
        }
        Token<PAQLTokenClass> semicolon = checkedNext(tokenIterator);
        if(!expect(PAQLTokenClass.SEMICOLON, semicolon))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                identifier.getLineNumber()
                +
                "\n\terror: \";\" expected"
            );
        }
        return variableDeclaration;
    }
    /**
     * Why SuppressWarnings("unchecked")?
     * Java requires type-checking (through instanceof) before downcasting.
     * But, because of Java Generics type erasure, no type-check can be performed
     * on generic types. However, since Token type is evalued through its metaType
     * value, the downcast is safe and thus can be performed. The compiler
     * still warns, but in this case those "unchecked" warnings can be safely turned off.
     **/
    @SuppressWarnings("unchecked") private ParseTree<PAQLParseTreeClass> parseContainer
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        System.out.println("\t\tParsing container");
        Container container = new Container();
        Token<PAQLTokenClass> leftAngularBracket = checkedNext(tokenIterator);
        if(!expect(PAQLTokenClass.LEFT_ANGULAR_BRACKET, leftAngularBracket))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                leftAngularBracket.getLineNumber()
                +
                "\n\terror: \"<\" expected"
            );
        }
        Token<PAQLTokenClass> elementIdentifier = checkedNext(tokenIterator);
        if(expect(PAQLTokenClass.IDENTIFIER, elementIdentifier))
        {
            container.setElementClassName(((EvaluableToken<PAQLTokenClass, String>)elementIdentifier).getValue());
        }
        else
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                elementIdentifier.getLineNumber()
                +
                "\n\terror: identifier expected"
            );
        }
        Token<PAQLTokenClass> rightAngularBracket = checkedNext(tokenIterator);
        if(!expect(PAQLTokenClass.RIGHT_ANGULAR_BRACKET, rightAngularBracket))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                rightAngularBracket.getLineNumber()
                +
                "\n\terror: \">\" expected"
            );
        }
        Token<PAQLTokenClass> containerIdentifier = checkedNext(tokenIterator);
        if(expect(PAQLTokenClass.IDENTIFIER, containerIdentifier))
        {
            container.setContainerClassName(((EvaluableToken<PAQLTokenClass, String>)containerIdentifier).getValue());
        }
        else
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                containerIdentifier.getLineNumber()
                +
                "\n\terror: identifier expected"
            );
        }
        Token<PAQLTokenClass> semicolon = checkedNext(tokenIterator);
        if(!expect(PAQLTokenClass.SEMICOLON, semicolon))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                semicolon.getLineNumber()
                +
                "\n\terror: \";\" expected"
            );
        }
        return container;
    }
    @SuppressWarnings("unchecked") private ParseTree<PAQLParseTreeClass> parseQuery
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        System.out.println("\t\tParsing query");
        Query query = new Query();
        Token<PAQLTokenClass> leftAngularBracket = checkedNext(tokenIterator);
        if(!expect(PAQLTokenClass.LEFT_ANGULAR_BRACKET, leftAngularBracket))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                leftAngularBracket.getLineNumber()
                +
                "\n\terror: \"<\" expected"
            );
        }
        Token<PAQLTokenClass> element = checkedNext(tokenIterator);
        if(expect(PAQLTokenClass.IDENTIFIER, element))
        {
            query.setElementClassName(((EvaluableToken<PAQLTokenClass, String>)element).getValue());
        }
        else
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                element.getLineNumber()
                +
                "\n\terror: identifier expected"
            );
        }
        Token<PAQLTokenClass> rightAngularBracket = checkedNext(tokenIterator);
        if(!expect(PAQLTokenClass.RIGHT_ANGULAR_BRACKET, rightAngularBracket))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                rightAngularBracket.getLineNumber()
                +
                "\n\terror: \">\" expected"
            );
        }
        Token<PAQLTokenClass> queryClass = checkedNext(tokenIterator);
        if(expect(PAQLTokenClass.IDENTIFIER, queryClass))
        {
            query.setQueryClassName(((EvaluableToken<PAQLTokenClass, String>)queryClass).getValue());
        }
        else
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                queryClass.getLineNumber()
                +
                "\n\terror: identifier expected"
            );
        }
        Token<PAQLTokenClass> leftParenthesis = checkedNext(tokenIterator);
        if(!expect(PAQLTokenClass.LEFT_PARENTHESIS, leftParenthesis))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                leftParenthesis.getLineNumber()
                +
                "\n\terror: \"(\" expected"
            );
        }
        Token<PAQLTokenClass> containerIdentifier = checkedNext(tokenIterator);
        if(expect(PAQLTokenClass.IDENTIFIER, containerIdentifier))
        {
            query.setContainerClassName(((EvaluableToken<PAQLTokenClass, String>)containerIdentifier).getValue());
        }
        else
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                containerIdentifier.getLineNumber()
                +
                "\n\terror: identifier expected"
            );
        }
        Token<PAQLTokenClass> rightParenthesis = checkedNext(tokenIterator);
        if(!expect(PAQLTokenClass.RIGHT_PARENTHESIS, rightParenthesis))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                rightParenthesis.getLineNumber()
                +
                "\n\terror: \")\" expected"
            );
        }
        Token<PAQLTokenClass> semicolon = checkedNext(tokenIterator);
        if(!expect(PAQLTokenClass.SEMICOLON, semicolon))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                semicolon.getLineNumber()
                +
                "\n\terror: \";\" expected"
            );
        }
        return query;
    }
    public ParseTree<PAQLParseTreeClass> transform(List< Token<PAQLTokenClass> > tokenList)
    {
        return parseDescription(tokenList.iterator());
    }
}
