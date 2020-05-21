import application.initializer.ApplicationInitializer;
import ui.console.ConsoleViewFactory;

public class Starter {
    public static void main(String[] args) {
        new ApplicationInitializer(new ConsoleViewFactory(), new InfrastructureFactory());
    }
}
