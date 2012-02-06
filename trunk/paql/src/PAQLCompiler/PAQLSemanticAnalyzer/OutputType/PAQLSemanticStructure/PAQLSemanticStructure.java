/**
 * @file PAQLSemanticStructure.java
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

package paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure;

import paql.lib.Pair.Pair;
import paql.lib.Triple.Triple;
import paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure.ElementInformation.ElementInformation;
//import paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure.ContainerInformation.ContainerInformation;
//import paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure.QueryInformation.QueryInformation;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class PAQLSemanticStructure
{
    public final HashMap<String, ElementInformation> elementsDataStructure;
    public final LinkedHashSet< Pair<String, String> > containerDataStructure; //Pair: containedType, containerType
    public final LinkedHashSet< Triple<String, String, String> > queryDataStructure;
    public PAQLSemanticStructure()
    {
        elementsDataStructure = new HashMap<String, ElementInformation>();
        containerDataStructure = new LinkedHashSet< Pair<String, String> >();
        queryDataStructure = new LinkedHashSet< Triple<String, String, String> >();
    }
}
