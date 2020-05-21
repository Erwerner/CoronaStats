package ui.console;

import application.core.RowContent;
import application.core.RowType;
import application.mvc.ApplicationControllerAccess;
import ui.template.Controller;
import ui.template.Model;

public class ConsoleController extends Controller {

    public ConsoleController(Model model) {
        super(model);
    }


    void shiftCursor(int shift) {

    }

    void cutRowsStart(int cut) {

    }

    void cutRowsEnd(int cut) {

    }

    void removeCursor() {

    }

    void addRow() {
        getModel().addRow(RowType.ALL, RowContent.DE);
    }

    void resetRows() {

    }

    void scaleCursor(Double faktor) {

    }

    void export() {

    }

    void syncRows() {

    }

    void setCursor() {
        getModel().setCursor(0);
    }

    private ApplicationControllerAccess getModel() {
        return (ApplicationControllerAccess) this.model;
    }
}
