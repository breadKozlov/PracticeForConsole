package practice.PracticeForConsole;

public class Laptop extends HomeAppliances{

    public Laptop(String name, int power, boolean turnOn, double cost) {
        super(name, power, turnOn, cost);
    }

    public Laptop(String name, int power, double cost) {
        super(name, power, cost);
    }

    @Override
    void whatAreYouDoing() {
        System.out.println("Ja dla raboti");
    }
}
