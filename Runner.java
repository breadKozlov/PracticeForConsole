package Chernovik;

public class Runner {

    public static void main(String[] args) {

        Holodilnik hol = new Holodilnik("LG",1500,true,180.6);
        Holodilnik hol1 = new Holodilnik("Samsung",2600,250.9);
        MicroWave micro = new MicroWave("Panasonic",1000,111);
        MicroWave micro1 = new MicroWave("LG",800,80.4);
        StiralnajaMashina stir = new StiralnajaMashina("Zanussi",1800,94.2);
        Televisor tel = new Televisor("Toshiba",1250,true,358.7);
        Televisor tel1 = new Televisor("Sony",2580,390.2);
        MultiVarka multiVarka = new MultiVarka("Electrolux",750,true,68.5);
        Laptop lap = new Laptop("HP",1250,true,120.6);
        Laptop lap1 = new Laptop("Dell", 1300,150.9);

        HomeAppliances[] homeAppliances = {hol,hol1,micro,micro1,stir,tel,tel1,multiVarka,lap,lap1};

        int allPower = 0;

        for (HomeAppliances i: homeAppliances) {

            if (i.isTurnOn()) {
                allPower += i.getPower();
            }
        }

        System.out.println(allPower);

        bubbleSortToCost(homeAppliances);

        for (HomeAppliances i: homeAppliances) {
            System.out.println(i.getName() + " " + i.getCost());
        }
    }

    static <T extends HomeAppliances> void bubbleSortToCost(T[] arr) {
        // на каждым шаге справа будет появляться отсортированный элемент
        for (int i = 0; i < arr.length - 1; i++) {
            // оптимизация алгоритма если элементы уже упорядочены
            boolean noSwaps = true;
            // перемещение самого большого элемента вправо
            for (int j = 0; j < arr.length - i - 1; j++) {
                //если два соседних элемента стоят в неправильом порядке - происходит их перестановка
                if (arr[j].getCost() > arr[j + 1].getCost()) {
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    // меняем значение если была хотя бы 1 перестановка
                    noSwaps = false;
                }
            }
            // если не было перестановок - выходим из цикла
            if (noSwaps) {
                break;
            }
        }
    }
}
