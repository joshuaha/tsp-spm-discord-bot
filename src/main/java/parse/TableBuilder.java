package parse;

import java.util.ArrayList;
import java.util.List;

public class TableBuilder {
    private final List<String[]> columns;
    private final int rows;

    public TableBuilder(int rows) {
        this.columns = new ArrayList<>();
        this.rows = rows;
    }

    /**
     * Adds a column to the table. The column length must match the number of rows of the table.
     *
     * @param column The array of values to insert into the table
     */
    public void append(Object[] column) {
        if (column != null && column.length != this.rows) {
            throw new IllegalArgumentException(String.format("Illegal column length %d, expected %d rows", column.length, this.rows));
        } else if (column == null) {
            throw new IllegalArgumentException("Column cannot be null");
        } else {
            final String[] array = new String[this.rows];
            for (int i = 0; i < this.rows; i++) {
                array[i] = String.valueOf(column[i]);
            }
            this.columns.add(array);
        }
    }

    /**
     * Converts the table into a formatted string.
     * @return the formatted string
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        final List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < this.rows; i++) {
            rows.add(new StringBuilder());
        }
        for (String[] column : this.columns) {
            int maxLength = 0;
            for (int i = 0; i < rows.size(); i++)
                if (column[i].length() > maxLength)
                    maxLength = column[i].length();
            for (int i = 0; i < rows.size(); i++)
                if (maxLength > 0)
                    rows.get(i).append(String.format("%" + maxLength + "s", column[i]));
        }
        for (StringBuilder row : rows) {
            builder.append(row.toString()).append(System.lineSeparator());
        }
        return builder.toString();
    }
}
