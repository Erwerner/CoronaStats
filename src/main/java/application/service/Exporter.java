package application.service;

import application.core.Row;
import helper.IO;

import java.util.List;

import static java.lang.String.format;

public class Exporter {
    public void export(List<Row> rows) {
        StringBuilder output = new StringBuilder();
        for (int indexRow = 0; indexRow <= rows.size() - 1; indexRow++) {
            output.append(format("%s %s;", rows.get(indexRow).getRowContent(), rows.get(indexRow).getRowType()));
        }
        for (int indexPoint = 0; indexPoint <= rows.get(0).getPoints().size() - 1; indexPoint++) {
            output.append("\n");
            for (int indexRow = 0; indexRow <= rows.size() - 1; indexRow++) {
                Row row = rows.get(indexRow);
                if (row.getPoints().size() - 1 >= indexPoint) {
                    String point = row.getPoints().get(indexPoint).toString().replace(".", ",");
                    output.append(point).append("; ");
                }
            }
        }
        IO.persistString(output.toString(),"exp.csv","out");
    }
}
