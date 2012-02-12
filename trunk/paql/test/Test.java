package paql.test;
import java.io.*;
import java.util.*;
import paql.lib.Compiler.LexicalAnalyzer.LexicalAnalyzer;
import paql.lib.Compiler.SyntacticAnalyzer.SyntacticAnalyzer;
import paql.lib.Compiler.SemanticAnalyzer.SemanticAnalyzer;
import paql.lib.Compiler.LexicalAnalyzer.OutputType.Token.Token;
import paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree.ParseTree;
import paql.lib.Compiler.CodeGenerator.CodeGenerator;
import paql.lib.Compiler.CodeGenerator.OutputType.SourceFile.SourceFile;
import paql.src.PAQLCompiler.PAQLLexicalAnalyzer.PAQLTokenClass.PAQLTokenClass;
import paql.src.PAQLCompiler.PAQLLexicalAnalyzer.PAQLLexicalAnalyzer;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.PAQLParseTreeClass.PAQLParseTreeClass;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.PAQLSyntacticAnalyzer;
import paql.src.PAQLCompiler.PAQLSemanticAnalyzer.PAQLSemanticAnalyzer;
import paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure.PAQLSemanticStructure;
import paql.src.PAQLCompiler.PAQLSemanticAnalyzer.OutputType.PAQLSemanticStructure.ElementInformation.ElementInformation;
import paql.src.PAQLCompiler.PAQLCodeGenerator.PAQLCodeGenerator;

public class Test
{
    public static void main(String[] args)
    {
        FileInputStream file;
        try
        {
            file = new FileInputStream("descriptionTest.paql");
        }
        catch(FileNotFoundException exception)
        {
            System.out.println("exception: " + exception.getMessage());
            return;
        }
        LexicalAnalyzer<PAQLTokenClass> lexicalAnalyzer =
            new PAQLLexicalAnalyzer();
        SyntacticAnalyzer<PAQLTokenClass, PAQLParseTreeClass> syntacticAnalyzer =
            new PAQLSyntacticAnalyzer();
        SemanticAnalyzer<PAQLParseTreeClass, PAQLSemanticStructure> semanticAnalyzer =
            new PAQLSemanticAnalyzer();
        CodeGenerator<PAQLSemanticStructure> codeGenerator = new PAQLCodeGenerator();
        List< Token<PAQLTokenClass> > l;
        ParseTree<PAQLParseTreeClass> p;
        PAQLSemanticStructure s;
        List<SourceFile> sc;
        try
        {
            l = lexicalAnalyzer.transform(file);
            p = syntacticAnalyzer.transform(l);
            s = semanticAnalyzer.transform(p);
            sc = codeGenerator.transform(s);
            //s = semanticAnalyzer.transform(syntacticAnalyzer.transform(lexicalAnalyzer.transform(file)));
        }
        catch(RuntimeException exception)
        {
            System.out.println(exception.getMessage());
            //exception.printStackTrace();
            return;
        }
        System.out.println("<<---------TOKEN LIST--------->>");
        Iterator< Token<PAQLTokenClass> > token = l.iterator();
        while(token.hasNext())
        {
            System.out.println(token.next());
        }
        System.out.println("<<---------PARSE TREE--------->>");
        System.out.println(p.toString(0));
        System.out.println("<<-----SEMANTIC STRUCTURE----->>");
        System.out.println("<<ELEMENTS>>");
        Set<String> keys = s.elementsDataStructure.keySet();
        Collection<ElementInformation> info = s.elementsDataStructure.values();
        Iterator<String> k = keys.iterator();
        Iterator<ElementInformation> i = info.iterator();
        while(k.hasNext() && i.hasNext())
        {
           System.out.println("(\n" + k.next()+ " " + i.next() + "\n)");
        }
        System.out.println("<<CONTAINERS>>");
        System.out.println(s.containerDataStructure);
        System.out.println("<<QUERIES>>");
        System.out.println(s.queryDataStructure);
        Iterator<SourceFile> source = sc.iterator();
        while(source.hasNext())
        {
            (source.next()).writeSourceFile();
        }
    }
}
