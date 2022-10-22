package practice.PracticeForConsole;

public class StiralnajaMashina extends HomeAppliances{

    public StiralnajaMashina(String name, int power, boolean turnOn, double cost) {
        super(name, power, turnOn, cost);
    }

    public StiralnajaMashina(String name, int power, double cost) {
        super(name, power, cost);
    }

    @Override
    void whatAreYouDoing() {
        System.out.println("Ja stiraju mashini");
    }
}
