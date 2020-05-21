package infrastructure.input;

import application.core.RowContent;
import application.core.RowType;
import application.service.ApplicationInput;

public class InfrastructureInput implements ApplicationInput {
    @Override
    public Double[] readPoints(RowType rowType, RowContent rowContent) {
        Double[] points = new Double[2];
        points[0] = 0.5;
        points[1] = 0.6;
        return points;
    }
}
