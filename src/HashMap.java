import java.util.ArrayList;
import java.util.LinkedList;

interface HashFunctionResolver<T> {
    int calculateHash(T object);
}

class Entry<K extends Comparable<K>, V> {
    K key;
    V value;

    Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class HashMap<K extends Comparable<K>, V> {
    private final HashFunctionResolver<K> hashFunction;
    private ArrayList<LinkedList<Entry<K, V>>> map;
    private int size = 0;
    private int bucketAmount = 1;
    private double loadFactor = 2.0F;

    public HashMap() {
        this(Object::hashCode);
    }

    public HashMap(HashFunctionResolver<K> hashFunction) {
        this.hashFunction = hashFunction;
        map = new ArrayList<>(bucketAmount);
        map.add(new LinkedList<>());
    }

    public void clear() {
        map.clear();

        size = 0;
        bucketAmount = 1;
        map = new ArrayList<>(bucketAmount);
        map.add(new LinkedList<>());
    }

    public void add(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value);
        int bucketIndex = hashFunction.calculateHash(key) % bucketAmount;
        map.get(bucketIndex).add(entry);

        size++;
        reHash(calculateNewBucketAmount());
    }

    public V getValue(K key) {
        Entry<K, V> entry = findEntry(key);

        return entry != null ? entry.value: null;
    }

    public void setValue(K key, V value) {
        Entry<K, V> entry = findEntry(key);
        if (entry != null) {
            entry.value = value;
        }
    }

    public void remove(K key) {
        Entry<K, V> entry = findEntry(key);
        if (entry != null) {
            int bucketIndex = hashFunction.calculateHash(key) % bucketAmount;
            map.get(bucketIndex).remove(entry);

            size--;
            reHash(calculateNewBucketAmount());
        }
    }

    private Entry<K, V> findEntry(K key) {
        int bucketIndex = hashFunction.calculateHash(key) % bucketAmount;
        LinkedList<Entry<K, V>> bucket = map.get(bucketIndex);
        for (Entry<K, V> entry : bucket) {
            if (key.compareTo(entry.key) == 0) {
                return entry;
            }
        }

        System.out.println("This key does not exist!");
        return null;
    }

    public int size() {
        return size;
    }

    public double getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(double loadFactor) {
        this.loadFactor = loadFactor;
        reHash(calculateNewBucketAmount());
    }

    public double getCurrentLoadFactor() {
        return (double) size / bucketAmount;
    }

    private void reHash(int newBucketAmount) {
        if (newBucketAmount != bucketAmount) {
            ArrayList<LinkedList<Entry<K, V>>> newMap = new ArrayList<>(newBucketAmount);
            for (int i = 0; i < newBucketAmount; i++) {
                newMap.add(new LinkedList<>());
            }

            for (LinkedList<Entry<K, V>> bucket : map) {
                for (Entry<K, V> entry : bucket) {
                    int newBucketIndex = hashFunction.calculateHash(entry.key) % newBucketAmount;
                    newMap.get(newBucketIndex).add(entry);
                }
            }

            map = newMap;
            bucketAmount = newBucketAmount;
        }
    }

    private int calculateNewBucketAmount() {
        int newBucketAmount = bucketAmount;

        if (getCurrentLoadFactor() > loadFactor) {
            while ((double) size / newBucketAmount > loadFactor) {
                newBucketAmount = 2 * newBucketAmount + 1;
            }
        } else {
            while (newBucketAmount - 1 > 0
                    && (double) size / (newBucketAmount - 1) <= loadFactor) {
                newBucketAmount--;
            }
        }

        return newBucketAmount;
    }

    public void printMap() {
        System.out.println("Map:");
        for (LinkedList<Entry<K, V>> bucket : map) {
            System.out.print("[");
            for (Entry<K, V> entry : bucket) {
                System.out.print(" (" + entry.key + " : " + entry.value + ") ");
            }
            System.out.println("]");
        }
        System.out.println();
    }
}