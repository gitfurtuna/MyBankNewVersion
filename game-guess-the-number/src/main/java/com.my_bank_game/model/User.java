import com.diogonunes.jcolor.Attribute;

public class User implements Text, Color {

    protected int id;
    protected String gender;
    protected String name;
    protected int bestScore;

    protected User (String gender, String name) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.gender = gender;
        this.name = name;
        this.bestScore = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    @Override
    public Attribute winColor() {
        return null;
    }

    @Override
    public Attribute loseColor() {
        return null;
    }

    @Override
    public void greeting() {

    }

    @Override
    public void bestResult() {

    }
}
