package application.service;

import application.core.ApplicationData;
import application.core.Row;
import application.core.RowContent;
import application.core.RowType;

import java.util.List;

public class ApplicationService {
    private final ApplicationInput input;

    public ApplicationService(ApplicationInput input) {
        this.input = input;
    }

    public List<Row> calcAdjustedRows(ApplicationData data) {
        return null;
    }

    public void syncRows(ApplicationData data) {

    }

    public void addRow(ApplicationData data, RowType rowType, RowContent rowContent) {

    }
}
