package practice.PracticeForConsole;

public class MicroWave extends HomeAppliances{

    public MicroWave(String name, int power, double cost) {
        super(name, power, cost);
    }

    public MicroWave(String name, int power, boolean turnOn, double cost) {
        super(name, power, turnOn, cost);
    }

    @Override
    void whatAreYouDoing() {
        System.out.println("Ja nagrevaju edu");
    }
}
