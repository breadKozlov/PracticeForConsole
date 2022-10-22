package practice.PracticeForConsole;

public class Holodilnik extends HomeAppliances{

    public Holodilnik(String name, int power, boolean turnOn, double cost) {
        super(name, power, turnOn, cost);
    }

    public Holodilnik(String name, int power, double cost) {
        super(name, power, cost);
    }

    @Override
    void whatAreYouDoing() {
        System.out.println("Ja ohlazhdaju edu");

    }
}
