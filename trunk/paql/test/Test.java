package paql.test;
import java.io.*;
import java.util.*;
import paql.lib.Compiler.LexicalAnalyzer.LexicalAnalyzer;
import paql.lib.Compiler.LexicalAnalyzer.OutputType.Token.Token;
import paql.src.PAQLCompiler.PAQLLexicalAnalyzer.PAQLTokenClass.PAQLTokenClass;
import paql.src.PAQLCompiler.PAQLLexicalAnalyzer.PAQLLexicalAnalyzer;

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
            exception.printStackTrace();
            return;
        }
        LexicalAnalyzer<PAQLTokenClass> lexicalAnalyzer =
            new PAQLLexicalAnalyzer();
        List< Token<PAQLTokenClass> > l = lexicalAnalyzer.transform(file);
        for(Token<PAQLTokenClass> token : l)
        {
            System.out.println(token);
        }
    }
}
