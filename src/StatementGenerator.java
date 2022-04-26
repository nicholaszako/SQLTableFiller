import java.util.LinkedList;

/**
 * Generates values with which to fill SQL tables. All values generated assume a signed range.
 * MySQL data type details taken from https://www.w3schools.com/sql/sql_datatypes.asp
 *
 * @author Nicholas Zako
 */
public class StatementGenerator {

    private static final String alphanumeric = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String[] generate(String tableName, LinkedList<String[]> attributes, int insertCount){

        String[] statements = new String[insertCount];

        // Start generating the first part of the INSERT string that will be the same for a table //

        String str1 = ("INSERT INTO " + tableName + "(");

        LinkedList<String[]> attClone = (LinkedList<String[]>) attributes.clone();

        // Add column names to insert statement
        while(!attClone.isEmpty()){
            str1 = str1.concat(attClone.pop()[0]);
            if(!attClone.isEmpty()){
                str1 = str1.concat(", ");
            }
        }

        str1 = str1.concat(") VALUES (");


        // Start generating the second part of the INSERT string that contains random values

        for(int i = 0; i < insertCount; i++){

            StringBuilder str2 = new StringBuilder();

            attClone = (LinkedList<String[]>) attributes.clone();

            while(!attClone.isEmpty()){

                String[] thisAtt = attClone.pop();

                switch (thisAtt[1].toUpperCase()){
                    case "NULL":
                        str2.append("NULL");
                        break;
                    case "CHAR":
                        if (thisAtt.length == 2) str2.append(genChar(1));
                        else str2.append(genChar(Integer.parseInt(thisAtt[2])));
                        break;
                    case "VARCHAR":
                        str2.append(genVarchar(Integer.parseInt(thisAtt[2])));
                        break;
                    case "TINYINT":
                        str2.append(genInt(127));
                        break;
                    case "SMALLINT":
                        str2.append(genInt(32767));
                        break;
                    case "MEDIUMINT":
                        str2.append(genInt(8388607));
                        break;
                    case "INT":
                    case "INTEGER":
                        str2.append(genInt(2147483647));
                        break;
                    case "BIGINT":
                        str2.append(genLong(9223372036854775807L));
                        break;
                    case "FLOAT":
                        str2.append(genFloat());
                        break;
                    case "DOUBLE":
                        str2.append(genDouble());
                        break;
                    case "DEC":
                    case "DECIMAL":
                        if (thisAtt.length == 2) str2.append(genDec());
                        else if (thisAtt.length == 3) str2.append(genDec(Integer.parseInt(thisAtt[2])));
                        else str2.append(genDec(Integer.parseInt(thisAtt[2]) , Integer.parseInt(thisAtt[3])));
                        break;
                    case "BOOL":
                    case "BOOLEAN":
                        str2.append(genBool());
                        break;
                    default:
                        str2.append("[UNKNOWN DATA TYPE - \"").append(thisAtt[1]).append("\"]");
                        break;
                }

                if(!attClone.isEmpty()){
                    str2 = str2.append(", ");
                }

            }
            str2 = str2.append(");");

            statements[i] = (str1 + str2);

        }

        return statements;

    }

    // STRING DATA TYPES //

    private static String genChar(int length){

        if(length < 0 || length > 255){
            throw new RuntimeException("Illegal CHAR length");
        }

        String str = "'";

        for(int i = 0; i < length; i++){
            str = str + (alphanumeric.charAt((int) (Math.random() * alphanumeric.length())));
        }

        str = str + "'";
        return str;

    }

    private static String genVarchar(int maxLength){

        if(maxLength < 0 || maxLength > 65535){
            throw new RuntimeException("Illegal VARCHAR length");
        }

        String str = "'";

        int length = (int) (Math.random() * maxLength) + 1;
        for(int i = 0; i < length; i++){
            str = str + (alphanumeric.charAt((int) (Math.random() * alphanumeric.length())));
        }

        str = str + "'";
        return str;

    }

    // NUMERIC DATA TYPES //

    private static String genInt(int max){
        int a = (int) (2*Math.random()*(max+1) - (max+1));
        return Integer.toString(a);
    }

    private static String genLong(long max){
        long a = (long) (2*Math.random()*(max+1) - (max+1));
        return Long.toString(a);
    }

    private static String genDec(){
        return genDec(10, 0);
    }

    /**
     *
     * @param size Total digits
     * @return Random decimal as a string
     */
    private static String genDec(int size){
        return(genDec(size,0));
    }

    /**
     *
     * @param size Total digits
     * @param d Number of digits after decimal point
     * @return Random decimal as a string
     */
    private static String genDec(int size, int d){
        if(size > 65 || d > 30)
            throw new RuntimeException("Exceeded maximum decimal length");

        return randInt(size-d) + "." + Math.abs(Integer.parseInt(randInt(d)));
    }

    private static String genFloat(){

        double a = (Float.MAX_VALUE)*(Math.random());

        if (Math.random() < 0.5){   // Negative
            a = -1*a;
        }

        return Double.toString(a);
    }

    private static String genDouble(){

        double a = (Double.MAX_VALUE)*(Math.random());

        if (Math.random() < 0.5){   // Negative
            a = -1*a;
        }

        return Double.toString(a);
    }

    private static String genBool(){
        return Integer.toString((int) (Math.random() * 2));
    }

    // DATE/TIME DATA TYPES //
    // TODO

    // CUSTOM DATA TYPES //
    // TODO

    // MISC/HELPER //

    /**
     *
     * @param n Number of digits
     * @return Random int as a string
     */
    private static String randInt(int n){
        int a = (int) (2 * Math.random() * Math.pow(10, n) - Math.pow(10, n));
        return Integer.toString(a);
    }

}
