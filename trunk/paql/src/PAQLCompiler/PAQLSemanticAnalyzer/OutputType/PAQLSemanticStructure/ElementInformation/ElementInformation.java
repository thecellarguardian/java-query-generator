/**
 * @file ElementInformation.java
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

package paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure.ElementInformation;

import paql.lib.Pair.Pair;

import java.util.LinkedList;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.*;

public class ElementInformation
{
    public final HashMap<Integer, LinkedHashSet< Pair<String, String> > > keyInformation; //int is the key index
    public final LinkedHashSet< Pair<String, String> > variableInformation;
    public ElementInformation()
    {
        keyInformation = new HashMap<Integer, LinkedHashSet< Pair<String, String> > >(); //Pair: type and variable name
        variableInformation = new LinkedHashSet< Pair<String, String> >();
    }
    public String toString()
    {
        String composedString = "\n[KEYS]:";
        Set<Integer> keys = keyInformation.keySet();
        Collection<LinkedHashSet< Pair<String, String> > > info = keyInformation.values();
        Iterator<Integer> k = keys.iterator();
        Iterator<LinkedHashSet< Pair<String, String> > > i = info.iterator();
        while(k.hasNext() && i.hasNext())
        {
           composedString += "(" + k.next()+ "," + i.next() + ")";
        }
        composedString += "\n[VARS]:";
        Iterator<Pair<String, String> > j = variableInformation.iterator();
        while(j.hasNext()) composedString += j.next();
        return composedString;
    }
}

