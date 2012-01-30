/**
 * @file Description.java
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

package paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description;

import paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree.ParseTree;
import paql.lib.Compiler.LexicalAnalyzer.Tokenizer.OutputType.Token.Token;
import paql.src.PAQLCompiler.PAQLTokenizer.PAQLTokenClass.PAQLTokenClass;

import java.util.LinkedList;

public class Description extends ParseTree
{
    private List<ParseTree> constructs = new LinkedList<ParseTree>;
    public void add(ParseTree constructToAdd)
    {
        constructs.add(constructToAdd);
    }
}
