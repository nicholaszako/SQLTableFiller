import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class CSVParser implements IParser{

    private Scanner fs;

    private String tableName;
    private int insertCount;
    private LinkedList<String[]> attributes;

    CSVParser(File csvFile){

        try{
            fs = new Scanner(new FileInputStream(csvFile)).useDelimiter(","); // Scanner for csv file
        }
        catch (FileNotFoundException e){
            System.out.println("Could not open input file " + csvFile.toString() + " for reading. Please check if file exists! Program will terminate after closing any opened files.");
            if (fs != null) fs.close(); // This condition should never be met.
            System.exit(1);
        }

    }

    public String getTableName() {
        return tableName;
    }

    public LinkedList<String[]> getAttributes() {
        return attributes;
    }

    public int getInsertCount() {
        return insertCount;
    }

    public void parse(){

        if(!fs.hasNextLine()) {
            System.out.println("Table name not found. The input file is likely empty.");
            fs.close();
            System.exit(1);
        }
        tableName = fs.nextLine();

        insertCount = Integer.parseInt(fs.nextLine());

        attributes = new LinkedList<>();

        while(fs.hasNextLine()) {
            String thisLine = fs.nextLine();

            if (!thisLine.isEmpty()) {
                String[] lineDelimited = thisLine.split(",");
                attributes.add(lineDelimited);
            }
        }

    }

}
