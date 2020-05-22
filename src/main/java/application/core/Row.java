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
        Integer index = 0;
        for (int i = 0; i <= points.size() - 1; i++) {
            if (points.get(i) > points.get(index))
                index = i;

        }
        return index;
    }

    public RowType getRowType() {
        return rowType;
    }

    public RowContent getRowContent() {
        return rowContent;
    }

    public List<Double> getPoints() {
        return points;
    }
}
