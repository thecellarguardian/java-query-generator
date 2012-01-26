package paql.test;
import java.io.*;
import java.util.*;
import paql.src.PAQLCompilationStep.PAQLScanner.PAQLScanner;
import paql.src.PAQLCompilationStep.PAQLTokenizer.PAQLTokenizer;
import paql.src.PAQLCompilationStep.Utilities.PAQLTokenClass.PAQLTokenClass;
import paql.lib.CompilationStep.Utilities.Word.Word;
import paql.lib.CompilationStep.Utilities.Token.Token;

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
        PAQLScanner scanner = new PAQLScanner();
        PAQLTokenizer tokenizer = new PAQLTokenizer();
        List< Token<PAQLTokenClass> > l = tokenizer.transform(scanner.transform(file));
        for(Token<PAQLTokenClass> token : l)
        {
            System.out.println(token);
        }
    }
}
