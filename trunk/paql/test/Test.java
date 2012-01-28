package paql.test;
import java.io.*;
import java.util.*;
import paql.lib.Compiler.LexicalAnalyzer.LexicalAnalyzer;
import paql.lib.Compiler.LexicalAnalyzer.Tokenizer.OutputType.Token.Token;
import paql.src.PAQLCompiler.PAQLScanner.PAQLScanner;
import paql.src.PAQLCompiler.PAQLTokenizer.PAQLTokenizer;
import paql.src.PAQLCompiler.PAQLTokenizer.PAQLTokenClass.PAQLTokenClass;

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
        LexicalAnalyzer<Integer, PAQLTokenClass> lexicalAnalyzer =
        	new LexicalAnalyzer<Integer, PAQLTokenClass>
        	(
        		new PAQLScanner(),
        		new PAQLTokenizer()
        	);
        List< Token<PAQLTokenClass> > l = lexicalAnalyzer.transform(file);
        for(Token<PAQLTokenClass> token : l)
        {
            System.out.println(token);
        }
    }
}
