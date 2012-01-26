/**
 * @file PAQLScanner.java
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

package paql.src.PAQLCompilationStep.PAQLScanner;
import java.io.StreamTokenizer;
import java.io.InputStream;
import java.io.IOException;
import java.util.*;
import paql.lib.CompilationStep.Scanner.Scanner;
import paql.lib.CompilationStep.Utilities.Word.Word;

public class PAQLScanner implements Scanner<Integer>
{
    public List< Word<Integer> > transform(InputStream sourceCode)
    {
        List< Word<Integer> > wordList = new LinkedList< Word<Integer> >();
        StreamTokenizer streamTokenizer = new StreamTokenizer(sourceCode);
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
                    wordList.add(new Word<Integer>(Double.toString(streamTokenizer.nval), StreamTokenizer.TT_NUMBER));
                    break;
                case StreamTokenizer.TT_WORD:
                    wordList.add(new Word<Integer>(streamTokenizer.sval, StreamTokenizer.TT_WORD));
                    break;
                case '"':
                    wordList.add(new Word<Integer>(streamTokenizer.sval, new Integer((int)('"'))));
                    break;
                case '\'':
                    wordList.add(new Word<Integer>(streamTokenizer.sval, new Integer((int)('\''))));
                    break;
                case StreamTokenizer.TT_EOL:
                    break;
                case StreamTokenizer.TT_EOF:
                    break;
                default:
                    wordList.add(new Word<Integer>((new Character((char)streamTokenizer.ttype)).toString(), streamTokenizer.ttype));
                    break;
            }
        }
        return wordList;
    }
}
