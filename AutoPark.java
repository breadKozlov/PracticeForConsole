package AutoPark;

import java.util.ArrayList;
import java.util.List;

public class AutoPark {
    public static void main(String[] args) {


        FreightTransport auto1 = new FreightTransport("DAF","CF85",1998,"diesel",12.9,
                560,80,20000,"tent");
        FreightTransport auto2 = new FreightTransport("Ford","Transit L3H2",2018,"diesel",
                2.2,80,12,2550,"close");
        PassengerTransport auto3 = new PassengerTransport("Mercedes","Sprinter 515CDI",2001,
                "petrol",2.1,55,22);
        PassengerTransport auto4 = new PassengerTransport("Gas","Gasel 3221",2013,"diesel",
                3.0,65,20);
        CargoPassengerTransport auto5 = new CargoPassengerTransport("Fiat","Ducato 9",2005,"petrol",
                2.3,55,8,2300,9);
        FreightTransport auto6 = new FreightTransport("Volvo","MX 123",2020,"diesel"
                ,13.1,560,90,21000,"zic");
        FreightTransport auto7 = new FreightTransport("Peugeot","Astra 123",2020,"gas"
                ,11.3,500,45,15000,"ref");

        Order order1 = new Order("Children",34,56,3);
        Order order2 = new Order("Oil",0,567,23,1245,"prom");
        Order order3 = new Order("Meet",45,89,23,3000,"skor");
        Order order4 = new Order("Wine",89,234,34,2000,"wat");


        List<Transport> autoPark = new ArrayList<>();
        autoPark.add(auto1);
        autoPark.add(auto2);
        autoPark.add(auto3);
        autoPark.add(auto4);
        autoPark.add(auto5);
        autoPark.add(auto6);
        autoPark.add(auto7);

        findAutosForOrder(autoPark,order4);
        
    }
    public static void findAutosForOrder(List<Transport> array, Order order) {

        if (order.getNumPeople() > 0) {

            for (Transport a: array) {

                if (a instanceof PassengerTransport && order.getNumPeople()
                        <= (((PassengerTransport) a).getPassCapacity() - ((PassengerTransport) a).getWorkLoadPass())) {
                    System.out.println("Transport - " + a.getBrand() + ", уже посажено людей - "
                            + ((PassengerTransport) a).getWorkLoadPass());
                }

                if (a instanceof CargoPassengerTransport && order.getNumPeople()
                        <= (((CargoPassengerTransport) a).getPassCapacity()
                        - ((CargoPassengerTransport) a).getWorkLoadPass())) {
                    System.out.println("Transport - " + a.getBrand() + ", уже посажено людей - "
                            + ((CargoPassengerTransport) a).getWorkLoadPass());
                }
            }
        } else {

            for (Transport b: array) {

                if (b instanceof FreightTransport && order.getVolume() <= ((FreightTransport) b).getBodyVolume()
                - ((FreightTransport) b).getWorkLoadBody()
                        && order.getWeight() <= ((FreightTransport) b).getCarryingCapacity()
                        - ((FreightTransport) b).getWorkLoadCapacity()
                        && ((FreightTransport) b).checkCargoType(order.getCargoType())
                        .contains(((FreightTransport) b).getBodyView())) {

                    System.out.println("Transport - " + b.getBrand() + ", загружено, кг - "
                            + ((FreightTransport) b).getWorkLoadCapacity() + ", загружено, кубов - "
                            + ((FreightTransport) b).getWorkLoadBody());
                }

                if (b instanceof CargoPassengerTransport && order.getVolume()
                        <= ((CargoPassengerTransport) b).getBodyVolume()
                        - ((CargoPassengerTransport) b).getWorkLoadBody() && order.getWeight()
                        <= ((CargoPassengerTransport) b).getCarryingCapacity()
                        - ((CargoPassengerTransport) b).getWorkLoadCapacity()
                        && ((CargoPassengerTransport) b).checkCargoType(order.getCargoType())
                        .contains(((CargoPassengerTransport) b).getBodyView())) {

                    System.out.println("Transport - " + b.getBrand() + ", загружено, кг - "
                            + ((CargoPassengerTransport) b).getBodyView()
                            + ", загружено, кубов" + ((CargoPassengerTransport) b).getWorkLoadBody());
                }
            }
        }
    }
}

class Transport {
    private final String brand;
    private final String model;

    private final int yearOfCreation;
    private final String fuel; //топливо
    private final double consumption; //расход
    private final int levelFuel;//обьем бака

    private int indicateLevelFuel;

    private int conditionCar = 100;


    @Override
    public String toString() {
        return "Transport{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", yearOfCreation=" + yearOfCreation +
                ", fuel='" + fuel + '\'' +
                ", consumption=" + consumption +
                ", levelFuel=" + levelFuel +
                ", indicateLevelFuel=" + indicateLevelFuel +
                ", conditionCar=" + conditionCar +
                '}';
    }

    public Transport(String brand, String model, int yearOfCreation, String fuel, double consumption, int levelFuel) {
        this.yearOfCreation = yearOfCreation;
        this.brand = brand;
        this.model = model;
        this.fuel = fuel;
        this.consumption = consumption;
        this.levelFuel = levelFuel;
        this.indicateLevelFuel = levelFuel;
    }

    public int getYearOfCreation() {
        return yearOfCreation;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }
    public int getLevelFuel() {
        return levelFuel;
    }

    public int getIndicateLevelFuel() {
        return indicateLevelFuel;
    }

    public String getFuel() {
        return fuel;
    }

    public double getConsumption() {
        return consumption;
    }

    public int getConditionCar() {
        return conditionCar;
    }

    public void setIndicateLevelFuel(int indicateLevelFuel) {
        this.indicateLevelFuel = indicateLevelFuel;
    }

    public void setConditionCar(int conditionCar) {
        this.conditionCar = conditionCar;
    }

    //заправка
    public void refuelFull() {

        this.indicateLevelFuel = this.levelFuel;
    }

    public void repairFull() {
        this.conditionCar = 100;
    }
}

class FreightTransport extends Transport {

    private final int bodyVolume; // обьем кузова
    private final int carryingCapacity; //грузоподьемность

    private String bodyView = "open"; //вид кузова

    private int workLoadBody = 0; //загруженность кузова по обьему на данный момент

    private int workLoadCapacity = 0; //загруженность кузова по массе

    private List<Order> orders = new ArrayList<>();

    public FreightTransport(String brand, String model,int yearOfCreation, String fuel, double consumption, int levelFuel
            , int bodyVolume, int carryingCapacity, String bodyView) {
        super(brand, model, yearOfCreation, fuel, consumption, levelFuel);
        this.bodyVolume = bodyVolume;
        this.carryingCapacity = carryingCapacity;
        this.bodyView = bodyView;
    }

    public FreightTransport(int yearOfCreation, String brand, String model, String fuel, double consumption, int levelFuel
            , int bodyVolume, int carryingCapacity) {
        super(brand, model, yearOfCreation, fuel, consumption, levelFuel);
        this.bodyVolume = bodyVolume;
        this.carryingCapacity = carryingCapacity;
    }

    public int getBodyVolume() {
        return bodyVolume;
    }

    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public String getBodyView() {
        return bodyView;
    }

    public int getWorkLoadBody() {
        return workLoadBody;
    }

    public int getWorkLoadCapacity() {
        return workLoadCapacity;
    }

    public void setWorkLoadBody(int workLoadBody) {
        this.workLoadBody = workLoadBody;
    }

    public void setWorkLoadCapacity(int workLoadCapacity) {
        this.workLoadCapacity = workLoadCapacity;
    }

    public void setOrders(Order a) {

        ArrayList<String> input = checkCargoType(a.getCargoType());

        if (this.workLoadBody + a.getVolume() <= this.bodyVolume
                && this.workLoadCapacity + a.getWeight() <= this.carryingCapacity
                && input.contains(bodyView)){
            this.workLoadBody += a.getVolume();
            this.workLoadCapacity += a.getWeight();
            this.orders.add(a);
            System.out.println("Товар загружен.");
        } else {
            System.out.println("Машина перегружена или не подходит по типу кузова - тип кузова: " + getBodyView()
                    + ", загружено кубов: " + getWorkLoadBody() + ", загружено, кг: "
                    + getWorkLoadCapacity());
        }
    }

    public ArrayList<String> checkCargoType(String cargoType) {

        ArrayList<String>arrayType = new ArrayList<>();

        switch(cargoType) {
            case "prom" -> {arrayType.add("ref"); arrayType.add("tent"); arrayType.add("close");}
            case "skor" -> {arrayType.add("ref");}
            case "wat" -> {arrayType.add("zic");}
            default -> {arrayType.add("tent");arrayType.add("close");arrayType.add("open");}
        }
        return arrayType;
    }

    public void lookOrders() {

        if (!orders.isEmpty()) {

            for (Order o: orders) {
                System.out.println(o.getNameOrder() + " " + o.getWeight() + " " + o.getVolume());
            }
        } else {
            System.out.println("Нет заказов");
        }
    }

    //опломбирование
    public static void isSeal() {

    }
}

class Order{

    private final String nameOrder;
    private final int startPoint;
    private final int finishPoint;
    private int volume;
    private double weight;
    private String cargoType;
    private int numPeople;
    public Order(String nameOrder, int startPoint, int finishPoint, int numPeople) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.numPeople = numPeople;
        this.nameOrder = nameOrder;
    }

    public Order(String nameOrder, int startPoint, int finishPoint, int volume, double weight, String cargoType) {
        this.nameOrder = nameOrder;
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.volume = volume;
        this.weight = weight;
        this.cargoType = cargoType;
    }

    public int getDistance() {
        return finishPoint - startPoint;
    }

    public String getNameOrder() {
        return nameOrder;
    }

    public int getVolume() {
        return volume;
    }

    public double getWeight() {
        return weight;
    }

    public String getCargoType() {
        return cargoType;
    }

    public int getNumPeople() {
        return numPeople;
    }
}
class PassengerTransport extends Transport {

    private final int passCapacity;

    private int workLoadPass = 0;

    private List<Order> orders = new ArrayList<>();

    public PassengerTransport(String brand, String model, int yearOfCreation, String fuel, double consumption
            , int levelFuel, int passCapacity) {
        super(brand, model, yearOfCreation, fuel, consumption, levelFuel);
        this.passCapacity = passCapacity;
    }

    public int getPassCapacity() {
        return passCapacity;
    }

    public int getWorkLoadPass() {
        return workLoadPass;
    }

    public void setWorkLoadPass(int workLoadPass) {
        this.workLoadPass = workLoadPass;
    }

    public void setOrders(Order a) {

        if (a.getNumPeople() > 0 && this.workLoadPass + a.getNumPeople() <= this.passCapacity) {

            this.workLoadPass += a.getNumPeople();
            System.out.println("Заказ загружен.");

        } else {
            System.out.println("Машина уже перегружена. "
                    + "Загружено человек: " + getWorkLoadPass());
        }
    }

    public void lookOrders() {

        if (!orders.isEmpty()) {

            for (Order o: orders) {
                System.out.println(o.getNameOrder() + " " + o.getNumPeople());
            }
        } else {
            System.out.println("Нет заказов");
        }
    }

    public void isSalonDisinfection() {}


}

class CargoPassengerTransport extends Transport {

    private final int bodyVolume; // обьем кузова
    private final double carryingCapacity; //грузоподьемность
    private final int passCapacity; //пасажжировместимость

    private int workLoadBody = 0; //загруженность кузова по обьему на данный момент

    private int workLoadCapacity = 0; //загруженность кузова по массе

    private int workLoadPass = 0; //загруженность кузова людьми

    private String bodyView = "close";

    private List<Order> orders = new ArrayList<>();

    public CargoPassengerTransport(String brand, String model, int yearOfCreation, String fuel, double consumption
            , int levelFuel, int bodyVolume, int carryingCapacity, int passCapacity) {
        super(brand, model, yearOfCreation, fuel, consumption, levelFuel);
        this.bodyVolume = bodyVolume;
        this.carryingCapacity = carryingCapacity;
        this.passCapacity = passCapacity;
    }

    public int getBodyVolume() {
        return bodyVolume;
    }

    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public int getPassCapacity() {
        return passCapacity;
    }

    public String getBodyView() {
        return bodyView;
    }

    public int getWorkLoadBody() {
        return workLoadBody;
    }

    public int getWorkLoadCapacity() {
        return workLoadCapacity;
    }

    public int getWorkLoadPass() {
        return workLoadPass;
    }

    public void setWorkLoadBody(int workLoadBody) {
        this.workLoadBody = workLoadBody;
    }

    public void setWorkLoadCapacity(int workLoadCapacity) {
        this.workLoadCapacity = workLoadCapacity;
    }

    public void setWorkLoadPass(int workLoadPass) {
        this.workLoadPass = workLoadPass;
    }

    public void setOrders(Order a) {

        ArrayList<String> input = checkCargoType(a.getCargoType());

        if (this.workLoadBody + a.getVolume() <= this.bodyVolume
                && this.workLoadCapacity + a.getWeight() <= this.carryingCapacity
                && input.contains("close") && a.getNumPeople() == 0){
            this.workLoadBody += a.getVolume();
            this.workLoadCapacity += a.getWeight();
            this.orders.add(a);
            System.out.println("Товар загружен.");
        } else if (a.getNumPeople() > 0 && this.workLoadPass + a.getNumPeople() <= this.passCapacity) {

            this.workLoadPass += a.getNumPeople();
            System.out.println("Заказ загружен.");

        } else {
                System.out.println("Машина перегружена или не подходит по типу кузова - тип кузова: " + getBodyView()
                        + ", загружено кубов: " + getWorkLoadBody() + ", загружено, кг: "
                        + getWorkLoadCapacity() + ", загружено человек: " + getWorkLoadPass());
            }
    }

    public ArrayList<String> checkCargoType(String cargoType) {

        ArrayList<String>arrayType = new ArrayList<>();

        switch(cargoType) {
            case "prom" -> {arrayType.add("ref"); arrayType.add("tent"); arrayType.add("close");}
            case "skor" -> {arrayType.add("ref");}
            case "wat" -> {arrayType.add("zic");}
            default -> {arrayType.add("tent");arrayType.add("close");arrayType.add("open");}
        }
        return arrayType;
    }

    public void lookOrders() {

        if (!orders.isEmpty()) {

            for (Order o: orders) {
                System.out.println(o.getNameOrder() + " " + ((o.getWeight()!=0)?o.getWeight():"") + " "
                        + ((o.getVolume()!=0)?o.getVolume():"") + ((o.getNumPeople()!=0)?o.getNumPeople():""));
            }
        } else {
            System.out.println("Нет заказов");
        }
    }

    public void salonDisinfection() {}

    public static void seal() {

    }
}
