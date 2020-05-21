package ui.template;

public abstract class View {
    protected final Model model;

    public View(Model model) {
        this.model = model;
        model.registerView(this);
        initController();
    }

    protected abstract void initController();

    public abstract void update();
}
