/**
 * @file SourceFile.java
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

package paql.lib.Compiler.CodeGenerator.OutputType.SourceFile;

import java.io.*;

public class SourceFile
{
    private String fileName;
    private String sourceCode;
    public SourceFile(String fileNameToSet, String sourceCodeToSet)
    {
        fileName = fileNameToSet;
        sourceCode = sourceCodeToSet;
    }
    public SourceFile(String fileNameToSet, BufferedReader sourceCodeSource)
    {
        fileName = fileNameToSet;
        sourceCode = new String();
        try
        {
            while(sourceCodeSource.ready())
            {
                sourceCode += sourceCodeSource.readLine();
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException("Cannot read " + fileName);
        }
    }
    public void writeSourceFile()
    {
        try
        {
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(sourceCode);
            out.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException("Cannot write " + fileName);
        }
    }
}
