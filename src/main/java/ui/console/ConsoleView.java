package ui.console;

import application.core.Row;
import application.core.RowContent;
import application.core.RowType;
import application.mvc.ApplicationViewAccess;
import helper.IO;
import ui.template.Model;
import ui.template.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
            for (ConsoleControllerType type : controllers.keySet()) {
                System.out.print("[" + type + "] ");
                System.out.println(controllers.get(type).getDescription());
            }

            String controllerSelection = IO.readInputFor("Chose controller:");
            for (ConsoleControllerType type : controllers.keySet())
                if (type.toString().equals(controllerSelection))
                    controllers.get(type).execute();
        }
    }

    @Override
    protected void initController() {
        controllers = new HashMap<>();
        controllers.put(ConsoleControllerType.EX, initExitController());
        controllers.put(ConsoleControllerType.AD, initAddController());
        controllers.put(ConsoleControllerType.SL, initSelectController());
    }

    private ConsoleController initSelectController() {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                int cursor = Integer.parseInt(IO.readInputFor("Select Row"));
                getModel().setCursor(cursor);
            }

            @Override
            public String getDescription() {
                return "Select Row";
            }
        };
    }

    private ConsoleController initAddController() {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                RowType typeSelection = (RowType) IO.getEnumByInput("Choose RowType", RowType.values());
                RowContent contentSelection = (RowContent) IO.getEnumByInput("Choose ContentType", RowContent.values());
                getModel().addRow(typeSelection, contentSelection);
            }

            @Override
            public String getDescription() {
                return "Add Row";
            }
        };
    }

    private ConsoleController initExitController() {
        return new ConsoleController(model) {
            @Override
            public void execute() {
                active = false;
            }

            @Override
            public String getDescription() {
                return "Exit";
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
