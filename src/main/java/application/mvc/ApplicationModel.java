package application.mvc;

import application.core.ApplicationData;
import application.core.Row;
import application.core.RowContent;
import application.core.RowType;
import application.service.ApplicationInput;
import application.service.ApplicationService;
import application.service.Exporter;
import ui.template.Model;

import java.util.List;

public class ApplicationModel extends Model implements ApplicationControllerAccess, ApplicationViewAccess {
    private final ApplicationData data;
    private final ApplicationService service;

    public ApplicationModel(ApplicationInput input) {
        data = new ApplicationData();
        service = new ApplicationService(input);
    }

    //View
    @Override
    public List<Row> getAdjustedRows() {
        return service.calcAdjustedRows(data);
    }

    @Override
    public Integer getCursor() {
        return data.getCursor();
    }

    //Controller
    @Override
    public void shiftCursor(int shift) {
        data.setShift(data.getCursor(), shift);
        notifyViews();
    }

    @Override
    public void cutRowsStart(int cut) {
        data.setCutStart(cut);
        notifyViews();
    }

    @Override
    public void cutRowsEnd(int cut) {
        data.setCutEnd(cut);
        notifyViews();
    }

    @Override
    public void removeCursor() {
        data.removeCursor();
        notifyViews();
    }

    @Override
    public void addRow(RowType rowType, RowContent rowContent) {
        service.addRow(data, rowType, rowContent);
        notifyViews();
    }

    @Override
    public void resetRows() {
        data.reset();
        notifyViews();
    }

    @Override
    public void scaleCursor(Double faktor) {
        data.setScale(data.getCursor(), faktor);
        notifyViews();
    }

    @Override
    public void export() {
        List<Row> rows = service.calcAdjustedRows(data);
        new Exporter().export(rows);
        notifyViews();
    }

    @Override
    public void syncRows() {
        service.syncRows(data);
        notifyViews();
    }

    @Override
    public void setCursor(int index) {
        data.setCursor(index);
        notifyViews();
    }

    @Override
    public void scaleLast() {
        service.sycaleLast(data);
        notifyViews();
    }

    @Override
    public void initializeModel() {
        service.initializeModel(data);
        notifyViews();
    }
}
