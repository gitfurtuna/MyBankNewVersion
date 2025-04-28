import com.diogonunes.jcolor.Attribute;

public class Boy extends User {

    public Boy(String gender, String name) {
        super(gender,  name);
    }

    @Override
    public void greeting() {
        System.out.println("Hello, super-boy " + name + " !");
    }

    @Override
    public void bestResult() {
        System.out.println("This is your best result, super-boy !");
    }

    @Override
    public Attribute winColor() {
        return Attribute.GREEN_BACK();
    }

    @Override
    public Attribute loseColor() {
        return Attribute.RED_BACK();
    }


}
