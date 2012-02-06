/**
 * @file CodeGenerator.java
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

package paql.src.PAQLCompiler.PAQLCodeGenerator;

import paql.lib.Pair.Pair;
import paql.lib.Compiler.CodeGenerator.CodeGenerator;
import paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure.PAQLSemanticStructure;
import paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure.ElementInformation.ElementInformation;
import paql.lib.Compiler.CodeGenerator.OutputType.SourceFile.SourceFile;

import java.util.*;

public class PAQLCodeGenerator
implements CodeGenerator<PAQLSemanticStructure>
{
    private final String header;
    private void makeLibraries(List<SourceFile> sourceCode)
    {
        SourceFile containerLibrary =
            new SourceFile
            (
            "PAQLContainerLibrary.h",
            header
            +"#include <boost/fusion/container/map.hpp>\n"
            +"#include <boost/fusion/include/map.hpp>\n"
            +"#include <boost/fusion/container/vector.hpp>\n"
            +"#include <boost/fusion/include/vector.hpp>\n"
            +"#include <boost/fusion/sequence/intrinsic/at_key.hpp>\n"
            +"#include <boost/fusion/include/at_key.hpp>\n"
            +"#include <boost/fusion/sequence/intrinsic/at_c.hpp>\n"
            +"#include <boost/fusion/include/at_c.hpp>\n"
            +"#include <boost/fusion/support/pair.hpp>\n"
            +"#include <boost/fusion/include/pair.hpp>\n"
            +"#include <boost/ref.hpp>\n"
            +"#include <map>\n"
            +"#include <list>\n"
            +"#include <string>\n"
            +"#include <algorithm>\n\n"

            +"#ifndef __PAQL_CONTAINER_LIBRARY_H__\n"
            +"#define __PAQL_CONTAINER_LIBRARY_H__\n\n"

            +"template <typename KeyType, int i> class MetaKey{};\n\n"

            +"template <typename KeyType> class FusionVectorComparator\n"
            +"{\n"
            +   "\tpublic:\n"
            +       "\t\tbool operator()(const KeyType& a, const KeyType& b) const\n"
            +       "\t\t{\n"
            +           "\t\t\treturn boost::fusion::at_c<0>(a) < boost::fusion::at_c<0>(b);\n"
            +       "\t\t}\n"
            +"};\n\n"

            +"template <typename T> class ContentComparator\n"
            +"{\n"
            +   "\tprivate:\n"
            +       "\t\tboost::reference_wrapper<T> first;"
            +   "\tpublic:\n"
            +       "\t\tContentComparator(boost::reference_wrapper<T> firstToSet)\n"
            +       "\t\t: first(firstToSet){}\n"
            +       "\t\tbool operator()(boost::reference_wrapper<T> elementToCheck) const\n"
            +       "\t\t{\n"
            +           "\t\t\treturn\n"
            +               "\t\t\t\t((typename boost::unwrap_reference<T>::type)elementToCheck)\n"
            +               "\t\t\t\t==\n"
            +               "\t\t\t\t((typename boost::unwrap_reference<T>::type)first);\n"
            +       "\t\t}\n"
            +"};\n\n"
            +"#endif"
            );
        sourceCode.add(containerLibrary);
    }
    private void serializeElements
    (
        HashMap<String, ElementInformation> elementsDataStructure,
        List<SourceFile> sourceCode
    )
    {
        Set<String> keySet = elementsDataStructure.keySet();
        Collection<ElementInformation> elementInformation = elementsDataStructure.values();
        Iterator<String> key = keySet.iterator();
        Iterator<ElementInformation> information = elementInformation.iterator();
        while(key.hasNext() && information.hasNext())
        {
            String sourceString = new String(header + "#include <string>\nusing namespace std;\n\n" );
            String currentKey = key.next();
            ElementInformation currentInformation = information.next();
            String className = new String(currentKey + "Element");
            sourceString +=
                "#ifndef __" + currentKey.toUpperCase() + "_ELEMENT_H__\n"
                +"#define __" + currentKey.toUpperCase() + "_ELEMENT_H__\n\n"

                +"struct " + className + "\n"
                +"{\n";
            Iterator< Pair<String, String> > variable =
                currentInformation.variableInformation.iterator();
            while(variable.hasNext())
            {
                Pair<String, String> currentVariable = variable.next();
                sourceString += "\t" + currentVariable.getFirst() + " " + currentVariable.getSecond() + ";\n";
            }
            sourceString += "\tbool operator==(const "+ className + "& elementToCompare) const\n\t{\n\treturn\n";
            variable = currentInformation.variableInformation.iterator();
            while(variable.hasNext())
            {
                Pair<String, String> currentVariable = variable.next();
                sourceString +=
                    "\t\t\t(elementToCompare."
                    + currentVariable.getSecond()
                    +
                    (
                        ((currentVariable.getFirst()).compareTo("string") == 0)?
                            ".compare("+currentVariable.getSecond()+") == 0) &&\n"
                            :
                            " == " + currentVariable.getSecond()+ ") &&\n"
                    );
            }
            sourceString += "\t\t\t(true);\n\t}\n};\n\n#endif";
            sourceCode.add(new SourceFile(className + ".h", sourceString));
        }
    }
    public PAQLCodeGenerator()
    {
        header = new String
        (
            "/**\n"
            + "* @author Cosimo Sacco <cosimosacco@gmail.com>\n"
            + "*\n"
            + "* @section LICENSE\n"
            + "*\n"
            + "* This program is free software: you can redistribute it and/or modify\n"
            + "* it under the terms of the GNU General Public License as published by\n"
            + "* the Free Software Foundation, either version 3 of the License, or\n"
            + "* (at your option) any later version.\n"
            + "*\n"
            + "* This program is distributed in the hope that it will be useful,\n"
            + "* but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
            + "* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n"
            + "* GNU General Public License for more details.\n"
            + "*\n"
            + "* You should have received a copy of the GNU General Public License\n"
            + "* along with this program.  If not, see <http://www.gnu.org/licenses/>.\n"
            + "**/\n\n"
        );
    }
    public List<SourceFile> transform(PAQLSemanticStructure semanticStructure)
    {
        List<SourceFile> sourceCode = new LinkedList<SourceFile>();
        makeLibraries(sourceCode);
        serializeElements(semanticStructure.elementsDataStructure, sourceCode);
        //serializeContainers(semanticStructure.containerDataStructure, sourceCode);
        //serializeQueries(semanticStructure.queryDataStructure, sourceCode);
        return sourceCode;
    }
}
