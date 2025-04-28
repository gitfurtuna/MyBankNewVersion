import com.diogonunes.jcolor.Attribute;

public class Details {
    Text text;
    Color color;

    public void start(Text text) {
        text.greeting();
    }

    public void finish(Text text) {
        text.bestResult();
    }

    public Attribute winBack(Color color) {
        return color.winColor();
    }

    public Attribute loseBack(Color color) {
        return color.loseColor();
    }
}
