import java.util.LinkedList;

public interface IParser {

    String getTableName();
    LinkedList<String[]> getAttributes();
    int getInsertCount();

}
