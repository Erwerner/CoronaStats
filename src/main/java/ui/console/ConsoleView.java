package ui.console;

import application.mvc.ApplicationViewAccess;
import ui.template.Model;
import ui.template.View;

public class ConsoleView extends View {
    private ConsoleController controller;

    public ConsoleView(Model model) {
        super(model);
        run();
    }

    private void run() {

    }

    @Override
    protected void initController() {
        controller = new ConsoleController(model);
    }

    @Override
    public void update() {

    }

    private ApplicationViewAccess getModel() {
        return (ApplicationViewAccess)model;
    }
}
