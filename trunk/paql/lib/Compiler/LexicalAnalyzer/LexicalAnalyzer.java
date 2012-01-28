/**
 * @file LexicalAnalyzer.java
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

package paql.lib.Compiler.LexicalAnalyzer;

import paql.lib.System.System;
import paql.lib.Compiler.LexicalAnalyzer.Scanner.Scanner;
import paql.lib.Compiler.LexicalAnalyzer.Tokenizer.Tokenizer;
import paql.lib.Compiler.LexicalAnalyzer.Tokenizer.OutputType.Token.Token;

import java.io.InputStream;
import java.util.*;

public class LexicalAnalyzer<WordClass, TokenClass>
implements System< InputStream, List< Token<TokenClass> > >
{
    private Scanner<WordClass> scanner;
    private Tokenizer<WordClass, TokenClass> tokenizer;
    public LexicalAnalyzer
    (
        Scanner<WordClass> scannerToSet,
        Tokenizer<WordClass, TokenClass> tokenizerToSet
    )
    {
        scanner = scannerToSet;
        tokenizer = tokenizerToSet;
    }
    public List< Token<TokenClass> > transform(InputStream input)
    {
        return tokenizer.transform(scanner.transform(input));
    }
}
