package ui.console;

import application.core.Row;
import application.mvc.ApplicationViewAccess;
import helper.IO;
import ui.template.Model;
import ui.template.View;

import java.util.HashMap;
import java.util.List;

public class ConsoleView extends View {
    private HashMap<ConsoleControllerType, ConsoleController> controllers;
    private boolean active = true;

    public ConsoleView(Model model) {
        super(model);
        run();
    }

    private void run() {
        update();

        while (active) {
            ConsoleControllerType selection = (ConsoleControllerType) IO.getEnumByInput("Choose Controller", ConsoleControllerType.values());
            controllers.get(selection).execute();
        }
    }

    @Override
    protected void initController() {
        ConsoleControllerFactory controllerFactory = new ConsoleControllerFactory();
        controllers = new HashMap<>();
        controllers.put(ConsoleControllerType.EXIT, initExitController());
        controllers.put(ConsoleControllerType.ADD, controllerFactory.initAddController(model));
        controllers.put(ConsoleControllerType.SELC, controllerFactory.initSelectController(model));
        controllers.put(ConsoleControllerType.SYNC, controllerFactory.initSyncController(model));
        controllers.put(ConsoleControllerType.SHFT, controllerFactory.initShiftController(model));
        controllers.put(ConsoleControllerType.CUTS, controllerFactory.initCutStartController(model));
        controllers.put(ConsoleControllerType.CUTE, controllerFactory.initCutEndController(model));
        controllers.put(ConsoleControllerType.REMV, controllerFactory.initRemoveController(model));
        controllers.put(ConsoleControllerType.RSET, controllerFactory.initResetController(model));
        controllers.put(ConsoleControllerType.SCAL, controllerFactory.initScaleController(model));
        controllers.put(ConsoleControllerType.EXP, controllerFactory.initExportController(model));
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
