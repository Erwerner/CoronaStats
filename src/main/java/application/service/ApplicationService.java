package application.service;

import application.core.ApplicationData;
import application.core.Row;
import application.core.RowContent;
import application.core.RowType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationService {
    private final ApplicationInput input;

    public ApplicationService(ApplicationInput input) {
        this.input = input;
    }

    public List<Row> calcAdjustedRows(ApplicationData data) {
        ArrayList<Row> rows = new ArrayList<>();
        for (int i = 0; i <= data.getRowCount() - 1; i++) {
            rows.add(data.getRow(i));
        }
        return rows;
    }

    public void syncRows(ApplicationData data) {

    }

    public void addRow(ApplicationData data, RowType rowType, RowContent rowContent) {
        Double[] points = input.readPoints(rowType, rowContent);
        data.addRow(new Row(rowType, rowContent, Arrays.asList(points)));
    }
}
