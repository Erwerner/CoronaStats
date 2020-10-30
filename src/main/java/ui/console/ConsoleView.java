package ui.console;

import application.core.Row;
import application.core.RowContent;
import application.core.RowType;
import application.mvc.ApplicationControllerAccess;
import application.mvc.ApplicationViewAccess;
import helper.IO;
import ui.template.Model;
import ui.template.View;

import java.util.HashMap;
import java.util.List;

import static ui.console.ConsoleControllerType.*;

public class ConsoleView extends View {
    private HashMap<ConsoleControllerType, ConsoleController> controllers;
    private boolean active = true;

    public ConsoleView(Model model) {
        super(model);
        run();
    }

    private void run() {
        update();
        double szFaktor = 1.0 / 8500000;
        double deFaktor = 1.0 / 83000000;
        ((ApplicationControllerAccess) model).addRow(RowType.ACT, RowContent.SZ);
        ((ApplicationControllerAccess) model).addRow(RowType.ACT, RowContent.DE);
        ((ApplicationControllerAccess) model).addRow(RowType.DTH, RowContent.SZ);
        ((ApplicationControllerAccess) model).addRow(RowType.DTH, RowContent.DE);
        scaleForCountry(szFaktor, 0);
        scaleForCountry(deFaktor, 1);
        scaleForCountry(szFaktor, 2);
        scaleForCountry(deFaktor, 3);
        ((ApplicationControllerAccess) model).addRow(RowType.ACT, RowContent.SZ);
        ((ApplicationControllerAccess) model).addRow(RowType.CFM, RowContent.SZ);
        ((ApplicationControllerAccess) model).addRow(RowType.RCV, RowContent.SZ);
        ((ApplicationControllerAccess) model).addRow(RowType.ACT, RowContent.DE);
        ((ApplicationControllerAccess) model).addRow(RowType.CFM, RowContent.DE);
        ((ApplicationControllerAccess) model).addRow(RowType.RCV, RowContent.DE);
        scaleForCountry(szFaktor, 4);
        scaleForCountry(szFaktor, 5);
        scaleForCountry(szFaktor, 6);
        scaleForCountry(deFaktor, 7);
        scaleForCountry(deFaktor, 8);
        scaleForCountry(deFaktor, 9);
        ((ApplicationControllerAccess) model).export();
        while (active) {
            ConsoleControllerType selection = (ConsoleControllerType) IO.getEnumByInput("Choose Controller", values());
            controllers.get(selection).execute();
        }
    }

    private void scaleForCountry(double faktor, int i) {
        ((ApplicationControllerAccess) model).setCursor(i);
        ((ApplicationControllerAccess) model).scaleCursor(faktor);
    }

    @Override
    protected void initController() {
        ConsoleControllerFactory controllerFactory = new ConsoleControllerFactory();
        controllers = new HashMap<>();
        controllers.put(EXIT, initExitController());
        controllers.put(ADD, controllerFactory.initAddController(model));
        controllers.put(SELC, controllerFactory.initSelectController(model));
        controllers.put(SYNC, controllerFactory.initSyncController(model));
        controllers.put(SHFT, controllerFactory.initShiftController(model));
        controllers.put(CUTS, controllerFactory.initCutStartController(model));
        controllers.put(CUTE, controllerFactory.initCutEndController(model));
        controllers.put(REMV, controllerFactory.initRemoveController(model));
        controllers.put(RSET, controllerFactory.initResetController(model));
        controllers.put(SCAL, controllerFactory.initScaleController(model));
        controllers.put(SCLA, controllerFactory.initScaleLastController(model));
        controllers.put(EXP, controllerFactory.initExportController(model));
    }


    private ConsoleController initExitController() {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                active = false;
                System.out.println("Closing View...");
            }
        };
    }

    @Override
    public void update() {
        ApplicationViewAccess model = getModel();
        Integer cursor = model.getCursor();
        System.out.println();
        System.out.println();
        System.out.println("Selected cursor [" + cursor + "]");
        List<Row> rows = model.getAdjustedRows();
        if (rows != null)
            for (Row row : rows) {
                System.out.print(row.getRowContent() + "(" + row.getRowType() + ")");
                for (Double point : row.getPoints()) {
                    System.out.print(point + ",");
                }
                System.out.println();
            }
        else
            System.out.println("[No Rows]");
    }

    private ApplicationViewAccess getModel() {
        return (ApplicationViewAccess) model;
    }
}
