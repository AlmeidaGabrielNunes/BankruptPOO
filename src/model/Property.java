package model;

public class Property {
    private final int price;
    private final int rent;
    private Player owner;

    public Property(int price, int rent) {
        this.price = price;
        this.rent = rent;
        this.owner = null;
    }

    public int getPrice() {
        return price;
    }

    public int getRent() {
        return rent;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isOwned() {
        return owner != null;
    }

    public void resetOwner() {
        this.owner = null;
    }
}
