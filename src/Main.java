import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static int length3 = 0;
    public static int length4 = 0;
    public static int length5 = 0;
    public static AtomicInteger atomic = new AtomicInteger();
    public static AtomicInteger atomic1 = new AtomicInteger();
    public static AtomicInteger atomic2 = new AtomicInteger();

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isSameCharacters(String text) {
        char c = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != c) {
                return false;
            }
        }
        return true;
    }

    public static void length(String text) {
        int length = text.length();
        if (length == 3) {
            length3 = atomic.incrementAndGet();
        } else if (length == 4) {
            length4 = atomic1.incrementAndGet();
        } else if (length == 5) {
            length5 = atomic2.incrementAndGet();
        }
    }

    public static boolean increasing(String text) {
        char c = text.charAt(0);
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < c) {
                return false;
            } else if (text.charAt(i) == c) {
                continue;
            } else if (text.charAt(i) > c) {
                c = text.charAt(i);
                continue;
            }
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String revers = new StringBuilder(texts[i]).reverse().toString();
                if (texts[i].equals(revers)) {
                    length(texts[i]);
                }
            }
        });
        thread1.start();
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (isSameCharacters(texts[i])) {
                    length(texts[i]);
                }
            }
        });
        thread2.start();
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (increasing(texts[i])) {
                    length(texts[i]);
                }
            }
        });
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();
        System.out.println("Красивых слов с длиной 3:" + length3 + " шт\n" +
                "Красивых слов с длиной 4:" + length4 + " шт\n" +
                "Красивых слов с длиной 5:" + length5 + " шт");
    }
}