package ui.console;

import ui.template.Model;
import ui.template.ViewFactory;

public class ConsoleViewFactory extends ViewFactory {
    @Override
    public void makeViews(Model model) {
        new ConsoleView(model);
    }
}
