package ui.console;

import application.mvc.ApplicationControllerAccess;
import ui.template.Controller;
import ui.template.Model;

public class ConsoleController extends Controller {

    public ConsoleController(Model model) {
        super(model);
    }

    public void execute() {
        getModel().execute();
    }

    private ApplicationControllerAccess getModel() {
        return (ApplicationControllerAccess) this.model;
    }
}
