
import com.diogonunes.jcolor.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class App {


    public static void main(String[] args) throws SQLException {

         Connection connection = DBUtils.getConnection();

         AtomicInteger atomicScore = new AtomicInteger(0);

         Details details = new Details();

         final Object lock = new Object();

         final List<Integer> generatedNumbers = new ArrayList<>();

         final List<String> guessedNumbers = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        System.out.println("START !");

        while (true) {
            generatedNumbers.clear();
            guessedNumbers.clear();
            atomicScore.set(0);
            String gender = "";
            String name = "";

            while (true) {
                System.out.println("Enter your gender (<boy> or <girl>) :");
                gender = scanner.nextLine();
                if (gender.replaceAll(" ", "").equals("444")) {
                    System.out.println("EXIT");
                    System.exit(0);
                } else if (gender.toLowerCase().equals("boy") || gender.toLowerCase().equals("girl")) {
                    break;
                } else {
                    System.err.println("Invalid gender. Please enter 'boy' or 'girl'.");
                }
            }
            while (true) {
                System.out.println("Enter your name :");
                name = scanner.nextLine();
                if (name.replaceAll(" ", "").equals("444")) {
                    System.out.println("EXIT");
                    System.exit(0);
                } else if (name.matches("^[A-Za-z]+$")) {
                  break;
                } else {
                    System.err.println("Invalid name. Name should contain only latin letters.");
                }
            }

            User player = gender.toLowerCase().equals("boy") ? new Boy(gender, name) : new Girl(gender, name);

            Thread first = new Thread(new NumbersGenerator(generatedNumbers, guessedNumbers, lock));
            Thread second = new Thread(new InputNumbers(guessedNumbers, generatedNumbers, lock, atomicScore));

            details.start(player);
            CRUDUtils.savePlayers(player, connection);
            Attribute winColor = details.winBack(player);
            Attribute loseColor = details.loseBack(player);
            System.out.println("Guess the number from 0 to 9 :)");
            first.start();
            second.start();

            try {
                first.join();
                second.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("The main thread has been interrupted.");
            }
            System.out.println();
            System.out.println(Ansi.colorize("RESULTS:", Attribute.BRIGHT_BLUE_TEXT()));
            System.out.println(Ansi.colorize("You won " + Ansi.colorize(String.valueOf(atomicScore.get()),Attribute.GREEN_TEXT()) + " coins !", Attribute.BOLD()));
            System.out.println();
            System.out.print(Ansi.colorize("Your numbers:    ", Attribute.BOLD()));
            for (String el : guessedNumbers) {
                String onlyDigits = el.replaceAll("\\D+", "");
                if (el.contains("T")) {
                    System.out.print(String.valueOf(Ansi.colorize(" " + onlyDigits + " ", winColor, Attribute.BLACK_TEXT()) + " "));
                } else {
                    System.out.print(String.valueOf(Ansi.colorize(" " + onlyDigits + " ", loseColor, Attribute.BLACK_TEXT()) + " "));
                }
            }
            System.out.println();
            System.out.print(Ansi.colorize("Correct numbers: ",Attribute.BOLD()));
            for (Integer num : generatedNumbers
            ) {

                System.out.print(String.valueOf(Ansi.colorize(" " + num + " ", Attribute.BLACK_TEXT(), Attribute.WHITE_BACK())) + " ");
            }
            System.out.println();

            player.setBestScore(atomicScore.get());
            CRUDUtils.updatePlayers(player, atomicScore.get(), connection);
            System.out.println();
            System.out.println(Ansi.colorize("Game statistics : ", Attribute.BOLD()));
            CRUDUtils.getPlayersData(connection).forEach(System.out::println);
            System.out.println();
            System.out.println(Ansi.colorize("Only winners:", Attribute.BOLD()));
            CRUDUtils.hideLosers(connection).forEach(System.out::println);
            System.out.println();
        }


    }

}


