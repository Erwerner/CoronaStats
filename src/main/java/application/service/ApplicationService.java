package application.service;

import application.core.ApplicationData;
import application.core.Row;
import application.core.RowContent;
import application.core.RowType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static application.core.RowContent.*;
import static application.core.RowType.*;

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

            for (int shift = 1; shift <= data.getShift(rowIndex); shift++)
                adjustedPoints.add(0.0);


            for (Double point : row.getPoints())
                adjustedPoints.add(point * data.getScale(rowIndex));

            if (adjustedPoints.size() > 0) {
                for (int shift = data.getShift(rowIndex); shift < 0; shift++)
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
        data.reset();
        Row syncRow = data.getRow(0);
        int syncIndex = syncRow.getHighPointIdx();
        Double syncValue = syncRow.getPoints().get(syncIndex);
        for (int rowIndex = 1; rowIndex <= data.getRowCount() - 1; rowIndex++) {
            int rowHighPointIndex = data.getRow(rowIndex).getHighPointIdx();
            data.setShift(rowIndex, syncIndex - rowHighPointIndex);
            Double maxRowVlaue = data.getRow(rowIndex).getPoints().get(rowHighPointIndex);
            data.setScale(rowIndex, syncValue / maxRowVlaue);
        }
    }

    public void addRow(ApplicationData data, RowType rowType, RowContent rowContent) {
        Double[] points = input.readPoints(rowType, rowContent);
        data.addRow(new Row(rowType, rowContent, Arrays.asList(points)));
    }

    public void sycaleLast(ApplicationData data) {
        data.reset();
        Row syncRow = data.getRow(0);
        for (int rowIndex = 1; rowIndex <= data.getRowCount() - 1; rowIndex++)
            data.setScale(rowIndex, syncRow.getPoints().get(0) / data.getRow(rowIndex).getPoints().get(0));
    }

    private void addCountryStatsPack(ApplicationData data, RowContent country) {
        addCountryPercentageRow(data, ACT, country);
        addCountryPercentageRow(data, CFM, country);
        addCountryPercentageRow(data, RCV, country);
    }

    private void addCountryPercentageRow(ApplicationData data, RowType rowType, RowContent country) {
        addRow(data, rowType, country);
        data.setCursor(data.getCursor() + 1);
        data.setScale(data.getCursor(), 1.0 / country.getPopulation());
    }

    public void initializeModel(ApplicationData data) {
        addRow(data, DTH_OF_CFM, BL);
        addRow(data, DTH_OF_CFM, IT);
        addRow(data, DTH_OF_CFM, US);
        addRow(data, DTH_OF_CFM, TC);
        addRow(data, DTH_OF_CFM, BZ);
        addRow(data, DTH_OF_CFM, FR);
        addRow(data, DTH_OF_CFM, FN);
        addRow(data, DTH_OF_CFM, SP);
        addRow(data, DTH_OF_CFM, UK);
        addRow(data, DTH_OF_CFM, SW);
        addRow(data, DTH_OF_CFM, SZ);
        addRow(data, DTH_OF_CFM, DE);
        addRow(data, DTH_OF_CFM, OE);

        data.setCursor(12);

        addCountryStatsPack(data,SZ);
        addCountryStatsPack(data,DE);
        addCountryStatsPack(data,SW);
        addCountryStatsPack(data,SP);
        addCountryStatsPack(data,TC);
        addCountryStatsPack(data,UK);
        addCountryStatsPack(data,US);
        addCountryStatsPack(data,SK);
        addCountryStatsPack(data,BZ);
        addCountryStatsPack(data,IT);
        addCountryStatsPack(data,NL);
        addCountryStatsPack(data,FR);
        addCountryStatsPack(data,BL);
        addCountryStatsPack(data,OE);
        addCountryStatsPack(data,FN);

        addRow(data,DTH_OF_POP, SZ);
        addRow(data,DTH_OF_POP, DE);
        addRow(data,DTH_OF_POP, SW);
        addRow(data,DTH_OF_POP, SP);
        addRow(data,DTH_OF_POP, TC);
        addRow(data,DTH_OF_POP, UK);
        addRow(data,DTH_OF_POP, US);
        addRow(data,DTH_OF_POP, SK);
        addRow(data,DTH_OF_POP, BZ);
        addRow(data,DTH_OF_POP, IT);
        addRow(data,DTH_OF_POP, NL);
        addRow(data,DTH_OF_POP, FR);
        addRow(data,DTH_OF_POP, BL);
        addRow(data,DTH_OF_POP, OE);
        addRow(data,DTH_OF_POP, FN);
    }
}
