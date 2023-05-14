public class TestHashMap {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();

        System.out.println("Size: " + map.size());
        System.out.println("Current load factor: " + map.getCurrentLoadFactor());
        map.printMap();

        Integer[] testKeys = {0, 1, 2, 10, 11, 12, 20, 21, 22, 23};
        String[] testValues = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        for (int i = 0; i < testKeys.length; i++) {
            map.add(testKeys[i], testValues[i]);
            System.out.println("Size: " + map.size());
            System.out.println("Current load factor: " + map.getCurrentLoadFactor());
            map.printMap();
        }

        System.out.println(map.getValue(11));
        map.setValue(11, "z");
        System.out.println(map.getValue(11));
        System.out.println();

        System.out.println(map.getValue(13));
        map.setValue(13, "z");
        System.out.println(map.getValue(13));
        System.out.println();

        System.out.println(map.getLoadFactor());
        map.setLoadFactor(1);
        System.out.println(map.getLoadFactor());
        map.printMap();

        System.out.println(map.getLoadFactor());
        map.setLoadFactor(1.5);
        System.out.println(map.getLoadFactor());
        map.printMap();

        for (int i = 0; i < testKeys.length - 3; i++) {
            map.remove(testKeys[i]);
            System.out.println("Size: " + map.size());
            System.out.println("Current load factor: " + map.getCurrentLoadFactor());
            map.printMap();
        }

        map.clear();
        System.out.println(map.size());
        map.printMap();
    }
}