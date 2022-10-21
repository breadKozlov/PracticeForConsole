package Chernovik;

public class MultiVarka extends HomeAppliances {

    public MultiVarka(String name, int power, boolean turnOn, double cost) {
        super(name, power, turnOn, cost);
    }

    public MultiVarka(String name, int power, double cost) {
        super(name, power, cost);
    }

    @Override
    void whatAreYouDoing() {
        System.out.println("Ja varu kashu");
    }
}
