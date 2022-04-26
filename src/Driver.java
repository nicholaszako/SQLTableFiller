import java.io.File;

public class Driver {

    public static void main(String[] args){

        // The input file to scan from. Defaults to "in.csv"
        File csvFile = new File("in.csv");

        if(args.length > 0)
        {
            // First arg is considered the input file
            csvFile = new File(args[0]);
        }


        // Parse CSV file

        CSVParser parser = new CSVParser(csvFile);
        parser.parse();


        // Generate insert statements

        String[] statements =
                StatementGenerator.generate(parser.getTableName(), parser.getAttributes(), parser.getInsertCount());



        // Output statements (currently only to a .csv)

        File outFile = new File("out.sql");

        if(args.length > 1)
        {
            // Second arg is considered the output file
            // Note that additional arguments are simply ignored
            outFile = new File(args[1]);
        }

        FileOutput out = new FileOutput(outFile);
        out.printStatements(statements);

    }

}
