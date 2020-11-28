package application.mvc;

import application.core.RowContent;
import application.core.RowType;

public interface ApplicationControllerAccess {
    void shiftCursor(int shift);

    void cutRowsStart(int cut);

    void cutRowsEnd(int cut);

    void removeCursor();

    void addRow(RowType rowType, RowContent rowContent);

    void resetRows();

    void scaleCursor(Double faktor);

    void export();

    void syncRows();

    void setCursor(int index);

    void scaleLast();

    void initializeModel();
}
