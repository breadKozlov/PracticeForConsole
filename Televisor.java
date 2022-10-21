package Chernovik;

public class Televisor extends HomeAppliances {

    public Televisor(String name, int power, boolean turnOn, double cost) {
        super(name, power, turnOn, cost);
    }

    public Televisor(String name, int power, double cost) {
        super(name, power, cost);
    }

    @Override
    void whatAreYouDoing() {
        System.out.println("Ja pokazivaju TV");
    }
}
