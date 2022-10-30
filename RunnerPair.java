package practice.PracticeForConsole;

class Pair<T,K> {

    T name;
    K id;

    public Pair(T name, K id) {

        this.name = name;
        this.id = id;
    }

    public T first() {
        return name;
    }

    public K last() {
        return id;
    }

    public Pair<K,T> swap() {
        return new Pair<>(id,name);
    }

    public <L> Pair<L,K> replaceFirst(L in) {
        return new Pair<>(in,id);
    }

    public <L> Pair<T,L> replaceLast(L in) {
        return new Pair<>(name,in);
    }

    public static <J,G> Pair<J,G> createNewPairWithTwoType(J oneParam, G twoParam) {
        return new Pair<>(oneParam,twoParam);
    }
}

public class RunnerPair {

    public static void main(String[] args) {

        Pair<String, Integer> pair = new Pair<>("Pasha", 31);
        System.out.println("The first word is - " + pair.first());
        System.out.println("The last word is - " + pair.last());

        System.out.println("The first word after swap is - " + pair.swap().first());
        System.out.println("The last word after swap is - " + pair.swap().last());

        Pair<Double, String> rep = pair.replaceFirst(45.4).replaceLast("Big Boy");

        System.out.println("The first word after replaceFirst - " + rep.first());
        System.out.println("The last word after replaceLast - " + rep.last());

        //
        Pair<String, String> prob = Pair.createNewPairWithTwoType("Hello,", "world!");
        System.out.println(prob.first() + " " + prob.last());
    }
}



