package ui.console;

import application.core.Row;
import application.mvc.ApplicationViewAccess;
import ui.template.Model;
import ui.template.View;

import java.util.List;

public class ConsoleView extends View {
    private ConsoleController controller;

    public ConsoleView(Model model) {
        super(model);
        run();
    }

    private void run() {
        update();
        controller.addRow();
        controller.setCursor();
    }

    @Override
    protected void initController() {
        controller = new ConsoleController(model);
    }

    @Override
    public void update() {
        ApplicationViewAccess model = getModel();
        Integer cursor = model.getCursor();
        System.out.println("");
        System.out.println("");
        System.out.println("Selected cursor [" + cursor + "]");
        List<Row> rows = model.getAdjustedRows();
        if (rows != null)
            for (Row row : rows) {
                System.out.print(row.getRowContent() + "(" + row.getRowType() + ")");
                for (Double point : row.getPoints()) {
                    System.out.print(point + ",");
                }
                System.out.println("");
            }
        else
            System.out.println("[No Rows]");
    }

    private ApplicationViewAccess getModel() {
        return (ApplicationViewAccess) model;
    }
}
