/**
 * @file ParseTree.java
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

package paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree;

import java.util.*;

public abstract class ParseTree<ParseTreeClass>
{
    protected ParseTreeClass parseTreeMetaType;
    protected List< ParseTree<ParseTreeClass> > childrenNodes =
        new LinkedList< ParseTree<ParseTreeClass> >();
    public ParseTree(ParseTreeClass parseTreeMetaTypeToSet)
    {
        parseTreeMetaType = parseTreeMetaTypeToSet;
    }
    public void add(ParseTree<ParseTreeClass> subTree)
    {
        childrenNodes.add(subTree);
    }
    ParseTreeClass getMetaType(){return parseTreeMetaType;}
    public String toString(int level)
    {
        String treeRepresentation = new String(parseTreeMetaType + "\n");
        Iterator< ParseTree<ParseTreeClass> > i = childrenNodes.iterator();
        while(i.hasNext())
        {
            for(int j = 0; j <= level; j++)
            {
                treeRepresentation += "\t";
            }
            treeRepresentation += (i.next()).toString(level + 1);
        }
        return treeRepresentation;
    }
}
