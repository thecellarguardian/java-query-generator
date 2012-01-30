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

import paql.lib.DesignPatterns.Visitor.Visitor;
import paql.lib.Compiler.SyntacticAnalyzer.SyntacticAnalyzer;
import paql.lib.Compiler.LexicalAnalyzer.OutputType.Token.Token;
import paql.lib.Compiler.LexicalAnalyzer.OutputType.Token.EvaluableToken.EvaluableToken;
import paql.src.PAQLCompiler.PAQLTokenizer.PAQLTokenClass.PAQLTokenClass;
import paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree.ParseTree;

import java.util.*;

public class PAQLSyntacticAnalyzer
implements SyntacticAnalyzer<PAQLTokenClass>
{
    private ParseTree parseDescription
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
    private ParseTree parseConstruct throws ParsingException
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        ParseTree construct;
        if(expect(PAQLTokenClass.ELEMENT_KEYWORD, tokenIterator))
        {
            construct = parseElement(tokenIterator);
        }
        else if(expect(PAQLTokenClass.CONTAINER_KEYWORD, tokenIterator))
        {
            construct = parseContainer(tokenIterator);
        }
        else if(expect(PAQLTokenClass.QUERY_KEYWORD, tokenIterator))
        {
            construct = parseQuery(tokenIterator);
        }
        return construct;
    }
    private ParseTree parseElement throws ParsingException
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        ParseTree element = new Element();
        tokenIterator.next();
        if(expect(PAQLTokenClass.IDENTIFIER, tokenIterator))
        {
            element.setIdentifier(tokenIterator);
            tokenIterator.next();
        }
        else
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                streamTokenizer.lineno()
                +
                "\n\terror: identifier expected"
            );
        }
        element.add(parseDeclarationBlock(tokenIterator));
        return element;
    }
    private ParseTree parseDeclarationBlock
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        DeclarationBlock declarationBlock = new DeclarationBlock();
        if(!expect(PAQLTokenClass.LEFT_CURLY_BRACKET, tokenIterator))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                streamTokenizer.lineno()
                +
                "\n\terror: '{' expected"
            );
        }
        tokenIterator.next();
        while
        (
            tokenIterator.hasNext()
            &&
            (!expect(PAQLTokenClass.RIGHT_CURLY_BRACKET, tokenIterator))
        )
        {
            declarationBlock.add(parseDeclaration(tokenIterator));
        }
        if(!expect(PAQLTokenClass.RIGHT_CURLY_BRACKET, tokenIterator))
        {
            throw new RuntimeException
            (
                "Syntactic error:"
                +
                "\n\tline:"
                +
                streamTokenizer.lineno()
                +
                "\n\terror: '}' expected"
            );
        }
        return declarationBlock;
    }
    private ParseTree parseDeclaration
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        ParseTree declaration;
        if(expect(PAQLTokenClass.KEY_KEYWORD, tokenIterator))
        {
            declaration = parseKeyDeclaration(tokenIterator);
        }
        else if(expect(PAQLTokenClass.IDENTIFIER, tokenIterator))
        {
            declaration = parseVariableDeclaration(tokenIterator);
        }
        else
        {
            throw new RuntimeError
            {
                "Syntactic error:"
                +
                "\n\tline:"
                +
                streamTokenizer.lineno()
                +
                "\n\terror: declaration expected"
            }
        }
        return construct;
    }
    private ParseTree parseKeyDeclaration
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        KeyDeclaration keyDeclaration = new KeyDeclaration();
        tokenIterator.next();
        keyDeclaration.add(parseVariableDeclaration(tokenIterator));
        return keyDeclaration;
    }
    private ParseTree parseVariableDeclaration
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        VariableDeclaration variableDeclaration = new VariableDeclaration();
        PAQLTokenClass[] sequence =
            new PAQLTokenClass[]
            {
                PAQLTokenClass.IDENTIFIER,
                PAQLTokenClass.IDENTIFIER,
                PAQLTokenClass.SEMICOLON
            };
        String[] missingItem = new String[]{"type", "identifier", ";"};
        for(int i = 0; i < 3; i++)
        {
            if(!expect(sequence[i], tokenIterator))
            {
                throw new RuntimeError
                {
                    "Syntactic error:"
                    +
                    "\n\tline:"
                    +
                    streamTokenizer.lineno()
                    +
                    "\n\terror: "
                    +
                    missingItem[i]
                    +
                    " expected"
                }
            }
            if(i < 2)
            {
                variableDeclaration.add
                (
                    (
                        (EvaluableToken<PAQLTokenClass,String>)tokenIterator
                    ).getValue()
                );
            }
            tokenIterator.next();
        }
        return variableDeclaration;
    }
    private ParseTree parseContainer
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        Container container = new Container();
        PAQLTokenClass[] sequence =
            new PAQLTokenClass[]
            {
                PAQLTokenClass.LEFT_ANGULAR_BRACKET,
                PAQLTokenClass.IDENTIFIER,
                PAQLTokenClass.RIGHT_ANGULAR_BRACKET,
                PAQLTokenClass.IDENTIFIER,
                PAQLTokenClass.SEMICOLON
            };
        String[] missingItem = new String[]{"<", "identifier", ">", "identifier", ";"};
        tokenIterator.next();
        for(int i = 0; i < 5; i++)
        {
            if(!expect(sequence[i], tokenIterator))
            {
                throw new RuntimeError
                {
                    "Syntactic error:"
                    +
                    "\n\tline:"
                    +
                    streamTokenizer.lineno()
                    +
                    "\n\terror: "
                    +
                    missingItem[i]
                    +
                    " expected"
                }
            }
            if(i == 1 || i == 3)
            {
                container.add
                (
                    (
                        (EvaluableToken<PAQLTokenClass,String>)tokenIterator
                    ).getValue()
                );
            }
            tokenIterator.next();
        }
        return container;
    }
    private ParseTree parseQuery
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        Query query = new Query();
        PAQLTokenClass[] sequence =
            new PAQLTokenClass[]
            {
                PAQLTokenClass.LEFT_ANGULAR_BRACKET,
                PAQLTokenClass.IDENTIFIER,
                PAQLTokenClass.RIGHT_ANGULAR_BRACKET,
                PAQLTokenClass.IDENTIFIER,
                PAQLTokenClass.LEFT_PARENTHESIS,
                PAQLTokenClass.IDENTIFIER,
                PAQLTokenClass.RIGHT_PARENTHESIS,
                PAQLTokenClass.SEMICOLON
            };
        String[] missingItem =
            new String[]
            {
                "<",
                "identifier",
                ">",
                "identifier",
                "(",
                "identifier",
                ")",
                ";"
            };
        tokenIterator.next();
        for(int i = 0; i < 8; i++)
        {
            if(!expect(sequence[i], tokenIterator))
            {
                throw new RuntimeError
                {
                    "Syntactic error:"
                    +
                    "\n\tline:"
                    +
                    streamTokenizer.lineno()
                    +
                    "\n\terror: "
                    +
                    missingItem[i]
                    +
                    " expected"
                }
            }
            if(i == 1 || i == 3 || i == 5)
            {
                query.add
                (
                    (
                        (EvaluableToken<PAQLTokenClass,String>)tokenIterator
                    ).getValue()
                );
            }
            tokenIterator.next();
        }
        return query;
    }
    public ParseTree transform(List< Token<PAQLTokenClass> > tokenList)
    {
        try
        {
            ParseTree parseTree = parseDescription(tokenList.iterator());
        }
        catch(ParsingException parsingException)
        {
            catch(IOException exception)
            System.out.println("Parser error: " + parsingException.getMessage());
            return null;
        }
        return parseTree;
    }
}
