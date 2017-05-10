package bbhs.appbowl2017.summation;

public abstract class Question {

    protected final int layoutResID;
    protected final SummationGame game;

    protected Question(int layoutResID, SummationGame game) {
        this.layoutResID = layoutResID;
        this.game = game;
    }

    public abstract void initiate();

}
