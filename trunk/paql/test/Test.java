package paql.test;
import java.io.*;
import java.util.*;
import paql.src.PAQLCompilationStep.PAQLScanner.PAQLScanner;
import paql.lib.CompilationStep.Utilities.Word.Word;

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
        List< Word<Integer> > l = scanner.transform(file);
        for(Word<Integer> word : l)
        {
            System.out.println(word);
        }
    }
}
