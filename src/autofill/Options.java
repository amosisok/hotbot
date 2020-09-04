package autofill;

public class Options {
    boolean headless;
    private static Options instance;

    public static Options getInstance() {
        if(instance == null) {
            instance = new Options();
        }
        return instance;
    }

    public boolean isHeadless() {
        return headless;
    }

    public void setHeadless(boolean headless) {
        this.headless = headless;
    }
}
