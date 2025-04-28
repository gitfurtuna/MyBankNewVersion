import java.util.List;
import java.util.Random;

public class NumbersGenerator implements Runnable {
    private Object lock = new Object();
    private final List<Integer> generatedNumbers;

    private final List<String> guessedNumbers;

    public NumbersGenerator(List<Integer> generatedNumbers, List<String> guessedNumbers, Object lock ) {
        this.generatedNumbers = generatedNumbers;
        this.guessedNumbers = guessedNumbers;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (lock) {
                while (generatedNumbers.size() > guessedNumbers.size()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                Random random = new Random();
                int nextNumber = random.nextInt(10);
                System.out.println("The number has been generated, guess the number: ");
                generatedNumbers.add(nextNumber);
                lock.notify();
            }
        }
    }
}
