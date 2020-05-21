package infrastructure.input;

import application.core.RowContent;
import application.core.RowType;
import application.service.ApplicationInput;

public class InfrastructureInput implements ApplicationInput {
    @Override
    public Double[] readPoints(RowType rowType, RowContent rowContent) {
        return new Double[0];
    }
}
