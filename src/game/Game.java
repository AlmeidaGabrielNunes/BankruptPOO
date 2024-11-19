package game;

import model.Player;
import model.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private final List<Player> players;
    private final List<Property> properties;
    private final int maxRounds;
    private int currentRound;

    public Game(List<Player> players, List<Property> properties, int maxRounds) {
        this.players = players;
        this.properties = properties;
        this.maxRounds = maxRounds;
        this.currentRound = 0;
    }

    public int getCurrentRound() {
        return currentRound;
    }


    public Player play() {
        while (currentRound < maxRounds) {
            for (Player player : players) {
                if (!player.isActive()) continue;

                int diceRoll = new Random().nextInt(6) + 1;
                int position = diceRoll % properties.size();

                Property property = properties.get(position);
                handlePropertyInteraction(player, property);

                if (checkGameOver()) {
                    return getActivePlayer();
                }
            }
            currentRound++;
        }
        return getPlayerWithMostCoins();
    }

    private void handlePropertyInteraction(Player player, Property property) {
        if (!property.isOwned()) {
            if (player.decideToBuy(property)) {
                player.changeBalance(-property.getPrice());
                property.setOwner(player);
            }
        } else if (!property.getOwner().equals(player)) {
            int rent = property.getRent();
            player.changeBalance(-rent);
            property.getOwner().changeBalance(rent);
            if (player.getBalance() < 0) {
                player.deactivate();
                resetProperties(player);
            }
        }
    }

    private void resetProperties(Player player) {
        for (Property property : properties) {
            if (property.getOwner() != null && property.getOwner().equals(player)) {
                property.resetOwner();
            }
        }
    }

    private boolean checkGameOver() {
        return players.stream().filter(Player::isActive).count() == 1;
    }

    private Player getActivePlayer() {
        return players.stream().filter(Player::isActive).findFirst().orElse(null);
    }

    private Player getPlayerWithMostCoins() {
        return players.stream().max((p1, p2) -> Integer.compare(p1.getBalance(), p2.getBalance())).orElse(null);
    }
}
