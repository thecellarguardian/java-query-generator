/**
 * @file SyntacticAnalyzer.java
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

package paql.lib.Compiler.SyntacticAnalyzer;

import paql.lib.System.System;
import paql.lib.Compiler.LexicalAnalyzer.OutputType.Token.Token;
import paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree.ParseTree;
import paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree.TerminalParseTree.TerminalParseTree;

import java.util.*;
import java.lang.Class;

public abstract class SyntacticAnalyzer<TokenClass, ParseTreeClass>
implements System< List< Token<TokenClass> >, ParseTree<ParseTreeClass> >
{
    protected Token<TokenClass> checkedNext
    (
        Iterator< Token<TokenClass> > tokenIterator
    )
    {
        if(tokenIterator.hasNext()) return tokenIterator.next();
        else throw new RuntimeException("Unexpected token list ending.");
    }
    protected boolean expect
    (
        TokenClass expectedMetaType,
        Token<TokenClass> tokenToCheck
    )
    {
        return (tokenToCheck.getMetaType() == expectedMetaType);
    }
    protected < TerminalParseTreeType extends TerminalParseTree<ParseTreeClass, TokenClass> >
    ParseTree<ParseTreeClass> parseTerminal
    (
        Iterator< Token<TokenClass> > tokenIterator,
        TerminalParseTreeType parseTree
    )
    {
        for(int i = 0; i < parseTree.terminalSequence.length; i++)
        {
            int retrievePointIndex = 0;
            Token<TokenClass> probe = checkedNext(tokenIterator);
            if(!expect(parseTree.terminalSequence[i], probe))
            {
                throw new RuntimeException
                (
                    "Syntactic error:"
                    +
                    "\n\tline:"
                    +
                    probe.getLineNumber()
                    +
                    "\n\terror: "
                    +
                    parseTree.terminalSequence[i]
                    +
                    " expected"
                );
            }
            if(i == parseTree.retrievePoints[retrievePointIndex])
            {
                parseTree.retrieveAdapter[retrievePointIndex].retrieve(probe);
                retrievePointIndex++;
            }
        }
        return parseTree;
    }
}
