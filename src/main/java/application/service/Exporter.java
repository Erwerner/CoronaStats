package application.service;

import application.core.ApplicationData;
import application.core.Row;

import java.util.List;

public class Exporter {
    public void export(List<Row> rows) {
        for (int indexPoint = 0; indexPoint <= rows.get(0).getPoints().size() - 1; indexPoint++) {
            System.out.println();
            for (int indexRow = 0; indexRow <= rows.size() - 1; indexRow++) {
                Row row = rows.get(indexRow);
                if (row.getPoints().size() - 1 >= indexPoint) {
                    String point = row.getPoints().get(indexPoint).toString().replace(".", ",");
                    System.out.print(point + "; ");
                }
            }
        }
    }
}
