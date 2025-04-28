import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class InputNumbers implements Runnable {
    private final AtomicInteger score;
    private Object lock = new Object();
    private final List<String> guessedNumbers;
    private final List<Integer> generatedNumbers;

    public InputNumbers(List<String> guessedNumbers,List<Integer> generatedNumbers, Object lock, AtomicInteger score){
        this.guessedNumbers = guessedNumbers;
        this.generatedNumbers = generatedNumbers;
        this.lock = lock;
        this.score = score;
    }


    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (lock) {
                while (generatedNumbers.size() == guessedNumbers.size()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                int userGuess;
                int generatedNumber = generatedNumbers.get(generatedNumbers.size() - 1);
                Scanner scanner = new Scanner(System.in);
                while (true) {
                   userGuess = scanner.nextInt();
                   if (userGuess == 444) {
                       System.out.println("EXIT");
                       System.exit(0);
                   } else if (userGuess < 0 || userGuess > 9) {
                       System.err.println("Invalid number. Enter number from 0 to 9.");
                   } else {
                       break;
                   }
                }
                if (userGuess == generatedNumber) {
                    guessedNumbers.add(String.valueOf(userGuess) + "T");
                    System.out.println(Ansi.colorize("You guessed the number :) ", Attribute.BRIGHT_YELLOW_TEXT()));
                    score.addAndGet(100);
                } else {
                    guessedNumbers.add(String.valueOf(userGuess) + "F");
                    System.out.println(Ansi.colorize("Wrong number !", Attribute.RED_TEXT()));
                }

                System.out.print("Correct number: ");
                System.out.println(String.valueOf(Ansi.colorize(" " + generatedNumber + " " , Attribute.BLACK_TEXT(),Attribute.WHITE_BACK())) + " ");
                lock.notify();
                    }

            }
        }
    }


