package mobop.booklist.app.data.database.builder;

public class Table {

    private String name;
    private Column[] columns;
    private String[] columnsNames;
    public static final String ID = "id";

    public Table(String name, Column... columns) {
        this.name = name;
        this.columns = columns;
        this.columnsNames = new String[columns.length + 1];
        columnsNames[0] = ID;
        for(int i = 0 ; i < columns.length ; i++) {
            columnsNames[i+1] = columns[i].getName();
        }
    }

    public String getName() {
        return name;
    }

    public Column[] getColumns() {
        return columns;
    }

    public String[] getColumnsNames() {
        return columnsNames;
    }

    public void deleteTableQuery(StringBuilder builder) {
        builder.append("DROP TABLE ");
        builder.append(name);
        builder.append(';');
    }

    public static String nameForType(ColumnType type) {
        switch (type) {
            case Int:
                return "INTEGER";
            case Long:
                return "LONG";
            case Text:
                return "TEXT";
            default:
                throw new IllegalArgumentException();
        }
    }

    public void createTableQuery(StringBuilder builder) {
        builder.append("CREATE TABLE ");
        builder.append(name);
        builder.append("(");
        builder.append(ID);
        builder.append(" ");
        builder.append(nameForType(ColumnType.Int));
        builder.append(" PRIMARY KEY AUTOINCREMENT");
        for(Column column : columns) {
            builder.append(",");
            builder.append(column.getName());
            builder.append(" ");
            builder.append(nameForType(column.getType()));
        }
        builder.append(");");
    }
}
