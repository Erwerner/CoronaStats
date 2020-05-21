package application.core;

import java.util.List;

public class Row {
    private final RowType rowType;
    private final RowContent rowContent;
    private final List<Double> points;

    public Row(RowType rowType, RowContent rowContent, List<Double> points) {
        this.rowType = rowType;
        this.rowContent = rowContent;
        this.points = points;
    }

    public Integer getHighPointIdx() {
        return null;
    }
}
