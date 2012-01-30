/**
 * @file Token.java
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

package paql.lib.Compiler.LexicalAnalyzer.OutputType.Token;

public class Token<TokenClass>
{
    private TokenClass metaType;
    private int lineNumber = -1;
    public Token(TokenClass metaTypeToSet){metaType = metaTypeToSet;}
    public Token(TokenClass metaTypeToSet, int lineNumberToSet)
    {
        metaType = metaTypeToSet;
        lineNumber = lineNumberToSet;
    }
    public TokenClass getMetaType(){return metaType;}
    public int getLineNumber(){return lineNumber;}
    public String toString(){return lineNumber + ": " + metaType.toString();}
}
