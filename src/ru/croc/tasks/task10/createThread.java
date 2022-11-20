package src.ru.croc.tasks.task10;

import src.ru.croc.tasks.task9.PasswordConversion;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class createThread implements Runnable {
    public void run() {
        while (LocalDateTime.now().isBefore(Task10.lot.timeEnd)){
            int randomNum = ThreadLocalRandom.current().nextInt(0, 10001);
            byte[] array = new byte[5];
            new Random().nextBytes(array);
            String generatedString = new String(array, StandardCharsets.UTF_8); // рандомная строка из 5 символов
            Task10.lot.acceptTheBet(new Bet(randomNum, generatedString, LocalDateTime.now()));
        }

    }
}
