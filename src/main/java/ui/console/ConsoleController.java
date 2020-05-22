package ui.console;

import application.core.RowContent;
import application.core.RowType;
import application.mvc.ApplicationControllerAccess;
import ui.template.Controller;
import ui.template.Model;

public abstract class ConsoleController extends Controller {

    public ConsoleController(Model model) {
        super(model);
    }

    public abstract void execute();

    protected ApplicationControllerAccess getModel() {
        return (ApplicationControllerAccess) this.model;
    }
}
