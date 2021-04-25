package ui.console;

import application.core.Row;
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
    private boolean active = false;

    public ConsoleView(Model model) {
        super(model);
        run();
    }

    private void run() {
        update();
        ApplicationControllerAccess applicationControllerAccess = (ApplicationControllerAccess) model;
        applicationControllerAccess.initializeModel();
        applicationControllerAccess.export();
        while (active) {
            ConsoleControllerType selection = (ConsoleControllerType) IO.getEnumByInput("Choose Controller", ConsoleControllerType.values());
            controllers.get(selection).execute();
        }
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
