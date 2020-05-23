package application.service;

import application.core.ApplicationData;
import application.core.Row;
import helper.IO;

import java.util.List;

public class Exporter {
    public void export(List<Row> rows) {
        String output = "";
        for (int indexPoint = 0; indexPoint <= rows.get(0).getPoints().size() - 1; indexPoint++) {
            output += "\n";
            for (int indexRow = 0; indexRow <= rows.size() - 1; indexRow++) {
                Row row = rows.get(indexRow);
                if (row.getPoints().size() - 1 >= indexPoint) {
                    String point = row.getPoints().get(indexPoint).toString().replace(".", ",");
                    output += point + "; ";
                }
            }
        }
        IO.persistString(output,"exp.csv","out");
    }
}
