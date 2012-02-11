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
import paql.lib.Triple.Triple;
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

            +"namespace paql\n{\n"

            +"\ttemplate <typename KeyType, int i> class MetaKey{};\n"

            +"\ttemplate <typename KeyType> class FusionVectorComparator\n"
            +"\t{\n"
            +   "\t\tpublic:\n"
            +       "\t\t\tbool operator()(const KeyType& a, const KeyType& b) const\n"
            +       "\t\t\t{\n"
            +           "\t\t\t\treturn boost::fusion::at_c<0>(a) < boost::fusion::at_c<0>(b);\n"
            +       "\t\t\t}\n"
            +"\t};\n"

            +"\ttemplate <typename T> class ContentComparator\n"
            +"\t{\n"
            +   "\t\tprivate:\n"
            +       "\t\t\tboost::reference_wrapper<T> first;"
            +   "\t\tpublic:\n"
            +       "\t\t\tContentComparator(boost::reference_wrapper<T> firstToSet)\n"
            +       "\t\t\t: first(firstToSet){}\n"
            +       "\t\t\tbool operator()(boost::reference_wrapper<T> elementToCheck) const\n"
            +       "\t\t\t{\n"
            +           "\t\t\t\treturn\n"
            +               "\t\t\t\t\t((typename boost::unwrap_reference<T>::type)elementToCheck)\n"
            +               "\t\t\t\t\t==\n"
            +               "\t\t\t\t\t((typename boost::unwrap_reference<T>::type)first);\n"
            +       "\t\t\t}\n"
            +"\t};\n"
            +"}\n\n"
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
            String className = new String(currentKey);
            sourceString +=
                "#ifndef __" + currentKey.toUpperCase() + "_ELEMENT_H__\n"
                +"#define __" + currentKey.toUpperCase() + "_ELEMENT_H__\n\n"

                +"namespace paql\n{\n\tnamespace element\n\t{\n"

                +"\t\tstruct " + className + "\n"
                +"\t\t{\n";
            Iterator< Pair<String, String> > variable =
                currentInformation.variableInformation.iterator();
            while(variable.hasNext())
            {
                Pair<String, String> currentVariable = variable.next();
                sourceString += "\t\t\t" + currentVariable.getFirst() + " " + currentVariable.getSecond() + ";\n";
            }
            sourceString += "\t\t\tbool operator==(const "+ className + "& elementToCompare) const\n\t\t\t{\n\t\t\t\treturn\n";
            variable = currentInformation.variableInformation.iterator();
            while(variable.hasNext())
            {
                Pair<String, String> currentVariable = variable.next();
                sourceString +=
                    "\t\t\t\t\t(elementToCompare."
                    + currentVariable.getSecond()
                    +
                    (
                        ((currentVariable.getFirst()).compareTo("string") == 0)?
                            ".compare("+currentVariable.getSecond()+") == 0) &&\n"
                            :
                            " == " + currentVariable.getSecond()+ ") &&\n"
                    );
            }
            sourceString += "\t\t\t\t\t(true);\n\t\t\t}\n\t\t};\n\t}\n}\n\n#endif";
            sourceCode.add(new SourceFile(className + ".h", sourceString));
        }
    }
    private void serializeContainers
    (
        LinkedHashSet< Pair<String, String> > containerDataStructure,
        HashMap<String, ElementInformation> elementsDataStructure,
        List<SourceFile> sourceCode
    )
    {
        Iterator< Pair<String, String> > container = containerDataStructure.iterator();
        while(container.hasNext())
        {
            Pair<String, String> containerInformation = container.next();
            String containerType = containerInformation.getFirst();
            String containedType = containerInformation.getSecond();
            String containerClass = containedType + containerType;
            String sourceString =
                new String
                (
                    header
                    + "#include \"" + containedType + ".h\"\n"
                    + "#include \"PAQLContainerLibrary.h\"\n"
                    + "#include <string>\n"
                    + "#include <stdexcept>\n"
                    + "using namespace std;\n\n"
                );
            sourceString +=
                "#ifndef __" + containerClass.toUpperCase() + "_" + containedType.toUpperCase() + "_CONTAINER_H__\n"
                +"#define __" + containerClass.toUpperCase() + "_" + containedType.toUpperCase() + "_CONTAINER_H__\n\n"

                +"namespace paql\n{\n\tnamespace container\n\t{\n"
                +"\t\tclass " + containerClass + " :\n"
                +"\t\tpublic std::list< boost::reference_wrapper<paql::element::" + containedType + "> >,\n"
                +"\t\tpublic virtual boost::fusion::map\n"
                +"\t\t<\n";
            ElementInformation containedElementInformation = elementsDataStructure.get(containedType);
            Set<Integer> keySet = containedElementInformation.keyInformation.keySet();
            Iterator<Integer> currentKeyIterator = keySet.iterator();
            List<String> getMethods = new LinkedList<String>();
            List<String> insertSections = new LinkedList<String>();
            List<String> removeSections = new LinkedList<String>();
            while(currentKeyIterator.hasNext())
            {
                String metaEntry = new String("\t\t\tboost::fusion::pair\n\t\t\t<\n");
                String metaKey = new String("MetaKey<");
                Integer currentKey = currentKeyIterator.next();
                Iterator< Pair<String, String> > currentKeyVariableIterator = containedElementInformation.keyInformation.get(currentKey).iterator();
                String fusionKey = "boost::fusion::vector<";
                String getParameters = new String();
                String getActualParameters = new String();
                String elementKeyMembers = new String();
                Integer keyArity = new Integer(0);
                while(currentKeyVariableIterator.hasNext())
                {
                    Pair<String, String> currentKeyVariable = currentKeyVariableIterator.next();
                    fusionKey += currentKeyVariable.getFirst();
                    fusionKey += ((currentKeyVariableIterator.hasNext())? (", ") : (">"));
                    elementKeyMembers += "element." + currentKeyVariable.getSecond() + ((currentKeyVariableIterator.hasNext())? (", ") : (""));
                    getParameters += currentKeyVariable.getFirst() + " a" + keyArity + ((currentKeyVariableIterator.hasNext())? (", ") : (""));
                    getActualParameters += "a" + keyArity + ((currentKeyVariableIterator.hasNext())? (", ") : (""));
                    keyArity++;
                }
                metaKey += fusionKey;
                metaKey += ", " + currentKey.toString() + ">";
                metaEntry += "\t\t\t\t" + metaKey + ",\n";
                String metaValue = "\t\t\t\tstd::map\n\t\t\t\t<\n\t\t\t\t\t" + fusionKey + ",\n\t\t\t\t\tboost::reference_wrapper<" + "paql::element::"
                + containedType + ">,\n\t\t\t\t\tpaql::FusionVectorComparator< " + fusionKey + " >\n\t\t\t\t>";
                metaEntry += metaValue + "\n\t\t\t>";
                sourceString += metaEntry + ((currentKeyIterator.hasNext())? ",\n" : "\n");
                getMethods.add
                (
                    new String
                    (
                        "\t\t\ttemplate<int keyIndex> paql::element::" + containedType + "& get(" + getParameters + ")\n"+
                        "\t\t\t{\n\t\t\t\t" + fusionKey + "key(" + getActualParameters + ");\n" +
                        "\t\t\t\tif(((boost::fusion::at_key<"+ metaKey + " >(*this)).find(key)) == ((boost::fusion::at_key<"+ metaKey +
                        " >(*this)).end())){std::runtime_error notFound(\"Element not found\"); throw notFound;};\n" +
                        "\t\t\t\treturn (boost::unwrap_reference<paql::element::" + containedType + ">::type&)"+
                        "(((boost::fusion::at_key<"+ metaKey + " >(*this)).find(key))->second);\n\t\t\t}\n"
                    )
                );
                insertSections.add
                (
                    "\t\t\t\t{\n"+
                    "\t\t\t\t\t" + fusionKey + " key(" + elementKeyMembers + ");\n" +
                    "\t\t\t\t\tif((boost::fusion::at_key< " + metaKey + " >(*this)).count(key)) return false;\n"+
                    "\t\t\t\t\t(boost::fusion::at_key< " + metaKey + " >(*this)).insert\n" +
                    "\t\t\t\t\t(\n" +
                    "\t\t\t\t\t\tstd::map< " + fusionKey +", boost::reference_wrapper<paql::element::" + containedType +
                    ">, FusionVectorComparator< " + fusionKey + " > >::value_type(key, elementToInsert)\n" +
                    "\t\t\t\t\t);\n" +
                    "\t\t\t\t}\n"
                );
                removeSections.add
                (
                    "\t\t\t\t{\n"+
                    "\t\t\t\t\t" + fusionKey + " key(" + elementKeyMembers + ");\n" +
                    "\t\t\t\t\t(boost::fusion::at_key< " + metaKey + " >(*this)).erase(key);\n"+
                    "\t\t\t\t}\n"
                );
            }
            sourceString += "\t\t>\n\t\t{\n\t\t\tpublic:\n";
            Iterator<String> methodIterator = getMethods.iterator();
            while(methodIterator.hasNext()){sourceString += methodIterator.next();}
            sourceString += "\t\t\tbool insert(paql::element::" + containedType + "& element)\n\t\t\t{\n\t\t\t\t" +
                "boost::reference_wrapper<paql::element::" + containedType + "> elementToInsert = boost::ref<paql::element::" + containedType
                + ">(element);\n";
            methodIterator = insertSections.iterator();
            while(methodIterator.hasNext()){sourceString += methodIterator.next();}
            sourceString += "\t\t\t\tthis->push_back(boost::ref(element));\n\t\t\t\treturn true;\n\t\t\t}\n";
            sourceString += "\t\t\tvoid remove(paql::element::" + containedType + "& element)\n\t\t\t{\n";
            methodIterator = removeSections.iterator();
            while(methodIterator.hasNext()){sourceString += methodIterator.next();}
            sourceString += "\t\t\t\tthis->remove_if(ContentComparator<paql::element::" + containedType + ">(boost::ref(element)));\n\t\t\t}\n";
            sourceString += "\t\t};\n\t}\n}\n\n#endif";;
            sourceCode.add(new SourceFile(containerClass + ".h", sourceString));
        }
    }
    private void serializeQueries
    (
        LinkedHashSet< Triple<String, String, String> > queryDataStructure,
        LinkedHashSet< Pair<String, String> > containerDataStructure,
        HashMap<String, ElementInformation> elementsDataStructure,
        List<SourceFile> sourceCode
    )
    {
        Iterator< Triple<String, String, String> > queryIterator = queryDataStructure.iterator();
        while(queryIterator.hasNext())
        {
            Triple<String, String, String> query = queryIterator.next();
            String queryType = query.getFirst();
            String containerType = query.getSecond();
            String elementType = query.getThird();
            String containerClass = elementType + containerType;
            String queryClass = containerClass + queryType;
            String sourceString =
                new String
                (
                    header
                    + "#include \"" + elementType + ".h\"\n"
                    + "#include \"" + containerClass + ".h\"\n"
                    + "#include <list>\n"
                    + "#include <boost/ref.hpp>\n\n"
                );
            sourceString +=
                "#ifndef __" + queryClass.toUpperCase() + "_" + containerClass.toUpperCase() + "_QUERY_H__\n"
                + "#define __" + queryClass.toUpperCase() + "_" + containerClass.toUpperCase() + "_QUERY_H__\n\n"

                + "namespace paql\n{\n\tnamespace query\n\t{\n"
                + "\t\tclass " + queryClass + "\n"
                + "\t\t{\n"
                + "\t\t\tprivate:\n"
                + "\t\t\t\tpaql::container::" + containerClass + "& container;\n"
                + "\t\t\tpublic:\n"
                + "\t\t\t\t" + queryClass + "(paql::container::" + containerClass + "& containerToSet) : container(containerToSet){}\n"
                + "\t\t\t\tstd::list<boost::reference_wrapper<paql::element::" + elementType + "> > execute()\n"
                + "\t\t\t\t{\n"
                + "\t\t\t\t\tstd::list<boost::reference_wrapper<paql::element::" + elementType + "> > listToReturn;\n"
                + "\t\t\t\t\tfor(std::list< boost::reference_wrapper<paql::element::" + elementType + "> >::iterator i = container.begin(); i != container.end(); i++)\n"
                + "\t\t\t\t\t{\n"
                + "\t\t\t\t\t\tlistToReturn.push_back((*i));\n"
                + "\t\t\t\t\t}\n"
                + "\t\t\t\t\treturn listToReturn;\n"
                + "\t\t\t\t}\n"
                + "\t\t};\n"
                + "\t}\n"
                + "}\n\n"
                + "#endif";
            sourceCode.add(new SourceFile(queryClass + ".h", sourceString));
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
        serializeContainers(semanticStructure.containerDataStructure, semanticStructure.elementsDataStructure, sourceCode);
        serializeQueries
        (
            semanticStructure.queryDataStructure,
            semanticStructure.containerDataStructure,
            semanticStructure.elementsDataStructure,
            sourceCode
        );
        return sourceCode;
    }
}
