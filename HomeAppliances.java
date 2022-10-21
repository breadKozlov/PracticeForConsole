package Chernovik;

public abstract class HomeAppliances {

    private String name;
    private int power;
    private boolean turnOn;
    private double cost;

    public HomeAppliances(String name, int power, boolean turnOn, double cost) {
        this.name = name;
        this.power = power;
        this.turnOn = turnOn;
        this.cost = cost;
    }

    public HomeAppliances(String name, int power, double cost) {
        this.name = name;
        this.power = power;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isTurnOn() {
        return turnOn;
    }

    public void setTurnOn(boolean turnOn) {
        this.turnOn = turnOn;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    abstract void whatAreYouDoing();
}
