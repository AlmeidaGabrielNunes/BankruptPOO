package model;

import java.util.Random;

public class Player {
    private final String name;
    private final BehaviorType behavior;
    private int balance;
    private boolean active;

    public Player(String name, BehaviorType behavior) {
        this.name = name;
        this.behavior = behavior;
        this.balance = 300;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public BehaviorType getBehavior() {
        return behavior;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }

    public void changeBalance(int amount) {
        this.balance += amount;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean decideToBuy(Property property) {
        if (!active || balance < property.getPrice()) return false;

        switch (behavior) {
            case IMPULSIVE:
                return true;
            case DEMANDING:
                return property.getRent() > 50;
            case CAUTIOUS:
                return (balance - property.getPrice()) >= 80;
            case RANDOM:
                return new Random().nextBoolean();
            default:
                throw new IllegalStateException("Unknown behavior type.");
        }
    }
}
