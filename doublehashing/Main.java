package gad.doublehashing;

public final class Main {
    private Main() {

    }

    public static void main(String[] args) {
        DoubleHashTable<Integer, String> table = new DoubleHashTable<>(Integer.MAX_VALUE, new IntHashableFactory());

  //      table.insert(42, "Hallo");
    //    table.insert(69, "Welt");
        table.insert(5,"pk");

        System.out.println(table.find(Integer.MAX_VALUE));
    }
}
