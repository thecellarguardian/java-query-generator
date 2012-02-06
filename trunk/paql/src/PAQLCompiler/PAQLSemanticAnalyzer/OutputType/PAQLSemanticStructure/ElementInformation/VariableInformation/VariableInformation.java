/**
 * @file VariableInformation.java
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

package paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure.ElementInformation.VariableInformation;

public class VariableInformation
{
    public final String variableType;
    public final String variableName;
    VariableInformation(String variableTypeToSet, String variableNameToSet)
    {
        variableType = variableTypeToSet;
        variableName = variableNameToSet;
    }
    public boolean equals(Object other)
    {
        if(other instanceof VariableInformation)
        {
            VariableInformation otherPair = (VariableInformation)other;
            return
                (
                    (
                        this.variableType == otherPair.variableType
                        ||
                        (
                            this.variableType != null
                            &&
                            otherPair.variableType != null
                            &&
                            this.variableType.equals(otherPair.variableType)
                        )
                    )
                    &&
                    (
                        this.variableName == otherPair.variableName
                        ||
                        (
                            this.variableName != null
                            &&
                            otherPair.variableName != null
                            &&
                            this.variableName.equals(otherPair.variableName)
                        )
                    )
                );
        }
        return false;
    }
    public int hashCode()
    {
        int hashFirst = variableType != null ? variableType.hashCode() : 0;
        int hashSecond = variableName != null ? variableName.hashCode() : 0;
        return (hashFirst + hashSecond)*hashSecond + hashFirst;
    }
}

