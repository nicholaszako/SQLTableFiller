import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileOutput {

    private static PrintWriter out;
    private File outFile;

    public FileOutput(File outFile){
        this.outFile = outFile;
    }

    public void printStatements(String[] statements){

        try{
            out = new PrintWriter(new FileWriter(outFile));
        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        for(String s : statements){
            // System.out.println(s);
            out.println(s);
        }

        out.flush();
        out.close();

    }



}
