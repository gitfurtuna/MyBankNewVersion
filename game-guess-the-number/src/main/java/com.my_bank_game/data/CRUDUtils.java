import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    private static String INSERT_PlAYERS = "INSERT INTO players(gender, name, score) VALUES (?,?,?);";
    private static String UPDATE_SCORE  = "UPDATE players SET score = ? WHERE id = ? ;";

    private static String SHOW_WINNERS = "SELECT * FROM players  WHERE score > 0 ;";

    private static String SHOW_PLAYERS = "SELECT * FROM players ;";

    public static List<String> getPlayersData(Connection connection) {
        List<String> players = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_PLAYERS)) {
             ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String gender = rs.getString("gender");
                String name = rs.getString("name");
                int score = rs.getInt("score");

                players.add(" id: "+ id + " gender: " + gender + " name: " + name + " score: " + score);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return players;
    }

    public static void savePlayers(User user, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PlAYERS)) {
            preparedStatement.setString(1, user.getGender());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setInt(3, user.getBestScore());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void updatePlayers(User user, int score, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SCORE)) {
            preparedStatement.setInt(1, score);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> hideLosers(Connection connection) {
        List<String> players = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SHOW_WINNERS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String gender = rs.getString("gender");
                String name = rs.getString("name");
                int score = rs.getInt("score");

                players.add(" id: "+ id + " gender: " + gender + " name: " + name + " score: " + score);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return players;
    }
}
