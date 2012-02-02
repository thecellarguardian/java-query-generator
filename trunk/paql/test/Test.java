package paql.test;
import java.io.*;
import java.util.*;
import paql.lib.Compiler.LexicalAnalyzer.LexicalAnalyzer;
import paql.lib.Compiler.SyntacticAnalyzer.SyntacticAnalyzer;
import paql.lib.Compiler.LexicalAnalyzer.OutputType.Token.Token;
import paql.lib.Compiler.SyntacticAnalyzer.OutputType.ParseTree.ParseTree;
import paql.src.PAQLCompiler.PAQLLexicalAnalyzer.PAQLTokenClass.PAQLTokenClass;
import paql.src.PAQLCompiler.PAQLLexicalAnalyzer.PAQLLexicalAnalyzer;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.PAQLParseTreeClass.PAQLParseTreeClass;
import paql.src.PAQLCompiler.PAQLSyntacticAnalyzer.PAQLSyntacticAnalyzer;

public class Test
{
    public static void main(String[] args)
    {
        FileInputStream file;
        try
        {
            file = new FileInputStream("prova");
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
        ParseTree<PAQLParseTreeClass> p;
        try
        {
            p = syntacticAnalyzer.transform(lexicalAnalyzer.transform(file));
        }
        catch(RuntimeException exception)
        {
            System.out.println(exception.getMessage());
            return;
        }
        System.out.println(p.toString(0));
    }
}
