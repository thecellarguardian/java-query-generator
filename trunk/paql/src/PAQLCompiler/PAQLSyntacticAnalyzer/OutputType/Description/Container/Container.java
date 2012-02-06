/**
 * @file Container.java
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

package paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.OutputType.Description.Container;

import paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree.ParseTree;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.PAQLParseTreeClass.PAQLParseTreeClass;

import java.util.LinkedList;

public class Container extends ParseTree<PAQLParseTreeClass>
{
    private String elementClassName;
    private String containerClassName;
    public void setElementClassName(String name){elementClassName = name;}
    public String getElementClassName(){return elementClassName;}
    public void setContainerClassName(String name){containerClassName = name;}
    public String getContainerClassName(){return containerClassName;}
    public Container()
    {
        super(PAQLParseTreeClass.CONTAINER);
    }
    public String toString(int level)
    {
        String treeRepresentation = new String(parseTreeMetaType + "\n");
        String[] members = new String[]{elementClassName, containerClassName};
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
