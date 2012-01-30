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
    private ParseTree parseDescription throws ParsingException
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        Description description = new Description();
        while(tokenIterator.hasNext())
        {
            description.addConstructs(parseConstruct(tokenIterator));
        }
        return description;
    }
    private ParseTree parseConstruct throws ParsingException
    (
        Iterator< Token<PAQLTokenClass> > tokenIterator
    )
    {
        ParseTree construct;
        if(expect(new Token<PAQLTokenClass>(PAQLTokenClass.ELEMENT_KEYWORD), tokenIterator))
        {
            construct = parseElement(tokenIterator);
        }
        else if(expect(new Token<PAQLTokenClass>(PAQLTokenClass.CONTAINER_KEYWORD), tokenIterator))
        {
            construct = parseContainer(tokenIterator);
        }
        else if(expect(new Token<PAQLTokenClass>(PAQLTokenClass.QUERY_KEYWORD), tokenIterator))
        {
            construct = parseQUERY(tokenIterator);
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
        if(expect(new Token<PAQLTokenClass>(PAQLTokenClass.IDENTIFIER), tokenIterator))
        {
            element.setIdentifier(tokenIterator);
        }
        else ...
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
