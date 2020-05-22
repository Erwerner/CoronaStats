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
        for (int rowIndex = 0; rowIndex <= data.getRowCount() - 1; rowIndex++) {
            Row row = data.getRow(rowIndex);
            List<Double> adjustedPoints = new ArrayList<>();

            for (int shift = data.getShift(rowIndex); shift < 0; shift++)
                adjustedPoints.add(0.0);

            adjustedPoints.addAll(row.getPoints());

            if (adjustedPoints.size() > 0) {
                for (int shift = 1; shift <= data.getShift(rowIndex); shift++)
                    adjustedPoints.remove(0);
                for (int cut = 1; cut <= data.getCutStart(); cut++)
                    adjustedPoints.remove(0);
                for (int cut = 1; cut <= data.getCutEnd(); cut++)
                    adjustedPoints.remove(adjustedPoints.size() - 1);
            }

            rows.add(new Row(row.getRowType(), row.getRowContent(), adjustedPoints));
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
