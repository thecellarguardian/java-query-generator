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

import paql.lib.MetaType.MetaType;

import java.util.*;

public abstract class ParseTree<ParseTreeClass> extends MetaType<ParseTreeClass>
{
    protected List< ParseTree<ParseTreeClass> > childrenNodes =
        new LinkedList< ParseTree<ParseTreeClass> >();
    public ParseTree(ParseTreeClass metaTypeToSet)
    {
        super(metaTypeToSet);
    }
    public void add(ParseTree<ParseTreeClass> subTree)
    {
        childrenNodes.add(subTree);
    }
    public void semanticCheck(ParseTreeClass metaTypeToCheck, String where)
    {
        if(metaType != metaTypeToCheck)
        {
            throw new RuntimeException
            (
                "Semantic error: in "
                +
                where
                +": "
                +
                metaTypeToCheck
                +
                " expected, found "
                +
                metaType
            );
        }
    }
    public boolean checkMetaType(ParseTreeClass metaTypeToCheck)
    {
        return metaType == metaTypeToCheck;
    }
    public String toString(int level)
    {
        String treeRepresentation = new String(metaType + "\n");
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
    public ListIterator< ParseTree<ParseTreeClass> > iterator(){return childrenNodes.listIterator();}
}
