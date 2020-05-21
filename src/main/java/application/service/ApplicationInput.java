package application.service;

import application.core.RowContent;
import application.core.RowType;

public interface ApplicationInput {
    Double[] readPoints(RowType rowType, RowContent rowContent);
}
