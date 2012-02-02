/**
 * @file VariableDeclaration.java
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

package paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Element.VariableDeclaration;

import paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree.ParseTree;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.PAQLParseTreeClass.PAQLParseTreeClass;

import java.util.LinkedList;

public class VariableDeclaration extends ParseTree<PAQLParseTreeClass>
{
    private String type;
    private String identifier;
    public void setVariableType(String typeToSet){type = typeToSet;}
    public void setVariableIdentifier(String identifierToSet)
    {
        identifier = identifierToSet;
    }
    public VariableDeclaration()
    {
        super(PAQLParseTreeClass.VARIABLE_DECLARATION);
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
