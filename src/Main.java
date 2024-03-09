import DataModel.TurnResult;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Game game = new Game();
        Bot bot = new Bot();
        StasBot stasBot = new StasBot();
        bot.InitGameStart();
        stasBot.InitGameStart();
        String secret = game.getSecret();
        System.out.println("Secret = " + secret);

        TurnResult result = null;
        TurnResult result1 = null;
        int turnCount = 0;
        int turnCount1 = 0;

        while (result1 == null || result1.DigitsInPlace < 4){
            turnCount1++;
            String turn1 = stasBot.getTurn();
            System.out.println("StasBot turn1: " + turn1);
            result1 = game.Turn(turn1);
            stasBot.setTurnResult(result1);
            System.out.println("Result: correct digits = "+ result1.CorrectDigits + ", digits in place = " + result1.DigitsInPlace);
        }

        while (result == null || result.DigitsInPlace < 4){
            turnCount++;
            String turn = bot.getTurn();
            System.out.println("Bot turn: " + turn);
            result = game.Turn(turn);
            bot.setTurnResult(result);
            System.out.println("Result: correct digits = "+ result.CorrectDigits + ", digits in place = " + result.DigitsInPlace);
        }

        System.out.println("Bot guessed the number in " + turnCount + " turns.");
        System.out.println("StasBot guessed the number in " + turnCount1 + " turns.");
    }
}