/**
 * @file PAQLLexicalAnalyzer.java
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

package paql.src.PAQLCompiler.PAQLLexicalAnalyzer;

import paql.lib.Compiler.LexicalAnalyzer.LexicalAnalyzer;
import paql.lib.Compiler.LexicalAnalyzer.OutputType.Token.Token;
import paql.lib.Compiler.LexicalAnalyzer.OutputType.Token.EvaluableToken.EvaluableToken;
import paql.src.PAQLCompiler.PAQLLexicalAnalyzer.PAQLTokenClass.PAQLTokenClass;

import java.io.StreamTokenizer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

public class PAQLLexicalAnalyzer implements LexicalAnalyzer<PAQLTokenClass>
{
    public List< Token<PAQLTokenClass> > transform(InputStream sourceCode)
    {
        List< Token<PAQLTokenClass> > tokenList =
            new LinkedList< Token<PAQLTokenClass> >();
        StreamTokenizer streamTokenizer =
            new StreamTokenizer
            (
                new BufferedReader
                (
                    new InputStreamReader
                    (
                        sourceCode
                    )
                )
            );
        streamTokenizer.slashSlashComments(true);
        streamTokenizer.slashStarComments(true);
        streamTokenizer.wordChars('_', '_');
        streamTokenizer.ordinaryChars(40, 47);
        streamTokenizer.ordinaryChars(59, 62);
        streamTokenizer.ordinaryChars(123, 125);
        streamTokenizer.quoteChar('"');
        streamTokenizer.quoteChar('\'');
        int wordType = StreamTokenizer.TT_EOF + 1;
        while (wordType != StreamTokenizer.TT_EOF)
        {
            try
            {
                wordType = streamTokenizer.nextToken();
            }
            catch(IOException exception)
            {
                System.out.println("exception: " + exception.getMessage());
                exception.printStackTrace();
                return null;
            }
            switch(wordType)
            {
                case StreamTokenizer.TT_NUMBER:
                    tokenList.add
                    (new EvaluableToken<PAQLTokenClass, Double>
                        (
                            PAQLTokenClass.NUMERIC_CONSTANT,
                            streamTokenizer.lineno(),
                            streamTokenizer.nval
                        )
                    );
                    break;
                case StreamTokenizer.TT_WORD:
                    {
                        PAQLTokenClass tokenType;
                        if(streamTokenizer.sval.compareTo("element") == 0)
                            tokenType = PAQLTokenClass.ELEMENT_KEYWORD;
                        else if(streamTokenizer.sval.compareTo("container") == 0)
                            tokenType = PAQLTokenClass.CONTAINER_KEYWORD;
                        else if(streamTokenizer.sval.compareTo("query") == 0)
                            tokenType = PAQLTokenClass.QUERY_KEYWORD;
                        else if(streamTokenizer.sval.compareTo("key") == 0)
                            tokenType = PAQLTokenClass.KEY_KEYWORD;
                        else tokenType = PAQLTokenClass.IDENTIFIER;
                        tokenList.add
                        (
                            (tokenType == PAQLTokenClass.IDENTIFIER)?
                                new EvaluableToken<PAQLTokenClass, String>
                                (
                                    tokenType,
                                    streamTokenizer.lineno(),
                                    streamTokenizer.sval
                                )
                                :
                                new Token<PAQLTokenClass>
                                (
                                    tokenType,
                                    streamTokenizer.lineno()
                                )
                        );
                    }
                    break;
                case '"':
                    tokenList.add
                    (
                        new EvaluableToken<PAQLTokenClass, String>
                        (
                            PAQLTokenClass.STRING_CONSTANT,
                            streamTokenizer.lineno(),
                            streamTokenizer.sval
                        )
                    );
                    break;
                case '\'':
                    {
                        if(streamTokenizer.sval.length() != 1)
                        {
                            throw new RuntimeException
                                (
                                    "Lexical error:"
                                    +
                                    "\n\tline:"
                                    +
                                    streamTokenizer.lineno()
                                    +
                                    "\n\terror: char constant expected"
                                );
                        }
                        tokenList.add
                        (
                            new EvaluableToken<PAQLTokenClass, Character>
                            (
                                PAQLTokenClass.CHAR_CONSTANT,
                                streamTokenizer.lineno(),
                                streamTokenizer.sval.charAt(0)
                            )
                        );
                    }
                    break;
                case 123:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.LEFT_CURLY_BRACKET,
                                streamTokenizer.lineno()
                            )
                        );
                    break;
                case 125:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.RIGHT_CURLY_BRACKET,
                                streamTokenizer.lineno()
                            )
                        );
                    break;
                case 40:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.LEFT_PARENTHESIS,
                                streamTokenizer.lineno()
                            )
                        );
                    break;
                case 41:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.RIGHT_PARENTHESIS,
                                streamTokenizer.lineno()
                            )
                        );
                    break;
                case 60:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.LEFT_ANGULAR_BRACKET,
                                streamTokenizer.lineno()
                            )
                        );
                    break;
                case 62:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.RIGHT_ANGULAR_BRACKET,
                                streamTokenizer.lineno()
                            )
                        );
                    break;
                case 59:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.SEMICOLON,
                                streamTokenizer.lineno()
                            )
                        );
                    break;
                case StreamTokenizer.TT_EOL:
                    break;
                case StreamTokenizer.TT_EOF:
                    break;
                default:
                    {
                        throw new RuntimeException
                            (
                                "Lexical error:"
                                    +
                                    "\n\tline:"
                                    +
                                    streamTokenizer.lineno()
                                    +
                                    "\n\terror: unknown token"
                            );
                    }
            }
        }
        return tokenList;
    }
}

