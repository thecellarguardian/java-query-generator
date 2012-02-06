/**
 * @file Compiler.java
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

package paql.lib.Compiler;

import paql.lib.System.System;
import paql.lib.Compiler.LexicalAnalyzer.LexicalAnalyzer;
import paql.lib.Compiler.SyntacticAnalyzer.SyntacticAnalyzer;
import paql.lib.Compiler.CodeGenerator.CodeGenerator;
import paql.lib.Compiler.CodeGenerator.OutputType.SourceFile.SourceFile;


class Compiler implements System< InputStream, List<SourceFile> >
{
    private LexicalAnalyzer lexicalAnalyzer;
    private SyntacticAnalyzer syntacticAnalyzer;
    private CodeGenerator codeGenerator;
    Compiler
    (
        LexicalAnalyzer lexicalAnalyzerToSet,
        SyntacticAnalyzer syntacticAnalyzerToSet,
        CodeGenerator codeGeneratorToSet
    )
    {
        lexicalAnalyzer = lexicalAnalyzerToSet;
        syntacticAnalyzer = syntacticAnalyzerToSet;
        codeGenerator = codeGeneratorToSet;
    }
    public List<SourceFile> transform(InputStream input)
    {
        return
            codeGenerator.transform
            (
                syntacticAnalyzer.transform
                (
                    lexicalAnalyzer.transform
                    (
                        input
                    )
                )
            );
    }
}
