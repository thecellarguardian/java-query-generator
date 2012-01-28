/**
 * @file PAQLTokenizer.java
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

package paql.src.PAQLCompiler.PAQLTokenizer;

import paql.lib.Compiler.LexicalAnalyzer.Tokenizer.Tokenizer;
import paql.lib.Compiler.LexicalAnalyzer.Scanner.OutputType.Word.Word;
import paql.lib.Compiler.LexicalAnalyzer.Tokenizer.OutputType.Token.Token;
import paql.src.PAQLCompiler.PAQLTokenizer.PAQLTokenClass.PAQLTokenClass;

import java.util.*;
import java.io.StreamTokenizer;

public class PAQLTokenizer implements Tokenizer<Integer, PAQLTokenClass>
{
    public List< Token<PAQLTokenClass> > transform(List< Word<Integer> > words)
    {
        List< Token<PAQLTokenClass> > tokenList =
            new LinkedList< Token<PAQLTokenClass> >();
        for(Word<Integer> word : words)
        {
            switch((word.getWordType()).intValue())
            {
                case StreamTokenizer.TT_NUMBER:
                    tokenList.add
                    (
                        new Token<PAQLTokenClass>
                        (
                            PAQLTokenClass.NUMERIC_CONSTANT,
                            word.getWord()
                        )
                    );
                    break;
                case '\'':
                    tokenList.add
                    (
                        new Token<PAQLTokenClass>
                        (
                            PAQLTokenClass.CHAR_CONSTANT,
                            word.getWord()
                        )
                    );
                    break;
                case '"':
                    tokenList.add
                    (
                        new Token<PAQLTokenClass>
                        (
                            PAQLTokenClass.STRING_CONSTANT,
                            word.getWord()
                        )
                    );
                    break;
                case StreamTokenizer.TT_WORD:
                    {
                        String stringRepresentation = word.getWord();
                        PAQLTokenClass tokenType;
                        if(stringRepresentation.compareTo("element") == 0) tokenType = PAQLTokenClass.ELEMENT_KEYWORD;
                        else if(stringRepresentation.compareTo("container") == 0) tokenType = PAQLTokenClass.CONTAINER_KEYWORD;
                        else if(stringRepresentation.compareTo("query") == 0) tokenType = PAQLTokenClass.QUERY_KEYWORD;
                        else if(stringRepresentation.compareTo("key") == 0) tokenType = PAQLTokenClass.KEY_KEYWORD;
                        else tokenType = PAQLTokenClass.IDENTIFIER;
                        tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                tokenType,
                                word.getWord()
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
                                word.getWord()
                            )
                        );
                    break;
                case 125:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.RIGHT_CURLY_BRACKET,
                                word.getWord()
                            )
                        );
                    break;
                case 40:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.LEFT_PARENTHESIS,
                                word.getWord()
                            )
                        );
                    break;
                case 41:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.RIGHT_PARENTHESIS,
                                word.getWord()
                            )
                        );
                    break;
                case 60:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.LEFT_ANGULAR_BRACKET,
                                word.getWord()
                            )
                        );
                    break;
                case 62:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.RIGHT_ANGULAR_BRACKET,
                                word.getWord()
                            )
                        );
                    break;
                case 59:
                    tokenList.add
                        (
                            new Token<PAQLTokenClass>
                            (
                                PAQLTokenClass.SEMICOLON,
                                word.getWord()
                            )
                        );
                    break;
            }
        }
        return tokenList;
    }
}
