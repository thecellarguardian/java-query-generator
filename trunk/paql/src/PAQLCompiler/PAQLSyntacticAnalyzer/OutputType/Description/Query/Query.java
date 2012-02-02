/**
 * @file Query.java
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

package paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Query;

import paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree.TerminalParseTree.TerminalParseTree;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.PAQLParseTreeClass.PAQLParseTreeClass;
import paql.src.PAQLCompiler.PAQLLexicalAnalyzer.PAQLTokenClass.PAQLTokenClass;

import java.util.LinkedList;

public class Query extends TerminalParseTree<PAQLParseTreeClass, PAQLTokenClass>
{
    private String elementClassName;
    private String queryClassName;
    private String containerClassName;
    private void setElementClassName(String name){elementClassName = name;}
    private void setQueryClassName(String name){queryClassName = name;}
    private void setContainerClassName(String name){containerClassName = name;}
    public Query()
    {
        super(PAQLParseTreeClass.QUERY);
        terminalSequence =
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
        retrievePoints = new Integer[]{1, 3, 5};
        retrieveAdapter =
            new RetrieveAction[]
            {
                new RetrieveAction()
                {
                    public void retrieve(Token<PAQLTokenClass> token)
                    {
                        setElementClassName
                        (
                            (
                                (EvaluableToken<PAQLTokenClass, String>)token
                            ).getValue()
                        );
                    }
                },
                new RetrieveAction()
                {
                    public void retrieve(Token<PAQLTokenClass> token)
                    {
                        setQueryClassName
                        (
                            (
                                (EvaluableToken<PAQLTokenClass, String>)token
                            ).getValue()
                        );
                    }
                },
                new RetrieveAction()
                {
                    public void retrieve(Token<PAQLTokenClass> token)
                    {
                        setContainerClassName
                        (
                            (
                                (EvaluableToken<PAQLTokenClass, String>)token
                            ).getValue()
                        );
                    }
                }
            };
    }
    public String toString(int level)
    {
        String treeRepresentation = new String(parseTreeMetaType + "\n");
        String[] members = new String[]{type, identifier};
        for(String s : members)
        {
            for(int i = 0; i <= level; i++)
            {
                treeRepresentation += "\t";
            }
            treeRepresentation += s + "\n";
        }
        return treeRepresentation;
    }
}
