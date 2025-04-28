import com.diogonunes.jcolor.Attribute;

public class Girl extends User {

    public Girl(String gender, String name) {
        super(gender, name);
    }

    @Override
    public void greeting() {
        System.out.println("Hello, super-girl " + name + " !");
    }

    @Override
    public void bestResult() {
        System.out.println("This is your best result, super-girl !");
    }

    @Override
    public Attribute winColor() {
        return Attribute.BRIGHT_YELLOW_BACK();
    }

    @Override
    public Attribute loseColor() {
        return Attribute.BRIGHT_MAGENTA_BACK();
    }


}
