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
        ParseTree<PAQLParseTreeClass> construct = null;
        Token<PAQLTokenClass> constructKeyword = checkedNext(tokenIterator);
        if(expect(PAQLTokenClass.ELEMENT_KEYWORD, constructKeyword))
        {
            construct = parseElement(tokenIterator);
        }
        else if(expect(PAQLTokenClass.CONTAINER_KEYWORD, constructKeyword))
        {
            construct= parseTerminal(tokenIterator, new Container());
        }
        else if(expect(PAQLTokenClass.QUERY_KEYWORD, constructKeyword))
        {
            construct = parseTerminal(tokenIterator, new Query());
        }
        return construct;
    }
    private ParseTree<PAQLParseTreeClass> parseElement
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
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
        Iterator< Token<PAQLTokenClass> > rightCurlyBracketProbe = tokenIterator;
        while
        (
            (!expect(PAQLTokenClass.RIGHT_CURLY_BRACKET, checkedNext(rightCurlyBracketProbe)))
        )
        {
            declarationBlock.add(parseDeclaration(tokenIterator));
            rightCurlyBracketProbe = tokenIterator;
        }
        return declarationBlock;
    }
    private ParseTree<PAQLParseTreeClass> parseDeclaration
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        ParseTree<PAQLParseTreeClass> declaration;
        Iterator< Token<PAQLTokenClass> > declarationProbe = tokenIterator;
        Token<PAQLTokenClass> declarationDiscriminant = checkedNext(declarationProbe);
        if(expect(PAQLTokenClass.KEY_KEYWORD, declarationDiscriminant))
        {
            declaration = parseKeyDeclaration(tokenIterator);
        }
        else if(expect(PAQLTokenClass.IDENTIFIER, declarationDiscriminant))
        {
            declaration = parseTerminal(tokenIterator, new VariableDeclaration());
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
        KeyDeclaration keyDeclaration = new KeyDeclaration();
        checkedNext(tokenIterator);
        keyDeclaration.add(parseTerminal(tokenIterator, new VariableDeclaration()));
        return keyDeclaration;
    }
    public ParseTree<PAQLParseTreeClass> transform(List< Token<PAQLTokenClass> > tokenList)
    {
        return parseDescription(tokenList.iterator());
    }
}
