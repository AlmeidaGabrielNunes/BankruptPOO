package game;

import model.BehaviorType;
import model.Player;
import model.Property;
import util.FileReaderUtil;

import java.util.ArrayList;
import java.util.List;

public class GameSimulator {
    private static final int MAX_ROUNDS = 1000;

    public static void main(String[] args) {
        List<Property> properties = FileReaderUtil.loadProperties("gameConfig.txt");

        int timeOuts = 0;
        int totalRounds = 0;
        int[] wins = new int[BehaviorType.values().length];

        for (int i = 0; i < 300; i++) {
            List<Player> players = createPlayers();
            Game game = new Game(players, properties, MAX_ROUNDS);
            Player winner = game.play();

            if (game.getCurrentRound() >= MAX_ROUNDS) {
                timeOuts++;
            }
            totalRounds += game.getCurrentRound();

            if (winner != null) {
                wins[winner.getBehavior().ordinal()]++;
            }
        }

        generateReport(timeOuts, totalRounds, wins);
    }

    private static List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1", BehaviorType.IMPULSIVE));
        players.add(new Player("Player 2", BehaviorType.DEMANDING));
        players.add(new Player("Player 3", BehaviorType.CAUTIOUS));
        players.add(new Player("Player 4", BehaviorType.RANDOM));
        return players;
    }

    private static void generateReport(int timeOuts, int totalRounds, int[] wins) {
        System.out.println("Partidas encerradas por timeout: " + timeOuts);
        System.out.println("Média de rodadas por partida: " + (totalRounds / 300.0));
        System.out.println("Vitórias por comportamento:");
        for (BehaviorType behavior : BehaviorType.values()) {
            System.out.printf("%s: %.2f%%\n", behavior, (wins[behavior.ordinal()] / 300.0) * 100);
        }
        BehaviorType bestBehavior = BehaviorType.values()[getIndexOfMax(wins)];
        System.out.println("Comportamento que mais vence: " + bestBehavior);
    }

    private static int getIndexOfMax(int[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
