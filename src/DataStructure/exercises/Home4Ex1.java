package DataStructure.exercises;
//Lidia Davydova
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Home4Ex1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        Map<Integer, String> students = new HashMap<>(n, 10000);
        for (int i=0; i<n; i++) {
            students.put(in.nextInt(), in.nextLine());
        }
        List<Entry<Integer, String>> list = students.entrySet();
        Entry<Integer, String> mid = smallest(list, (list.size()-1)/2);
        System.out.println(mid.getVal());
    }

    public static Entry<Integer, String> smallest(List<Entry<Integer, String>> list, int n) {
        int idx = select(list, 0, list.size()-1, n);
        return list.get(idx);
    }
    public static int select(List<Entry<Integer, String>> list, int left, int right, int n) {
        while (true) {
            if (left == right) {
                return left;
            }
            int pivotIdx = pivot(list, left, right);
            pivotIdx = partition(list, left, right, pivotIdx, n);
            if (n == pivotIdx) {
                return n;
            } else if (n < pivotIdx) {
                right = pivotIdx - 1;
            } else {
                left = pivotIdx + 1;
            }
        }
    }

    public static int pivot(List<Entry<Integer, String>> list, int left, int right) {
        if (right - left < 5) {
            return partition5(list, left, right);
        }
        for (int i=left; i<=right; i+=5) {
            int subRight = i + 4;
            if (subRight > right) {
                subRight = right;
            }
            int median5 = partition5(list, i, subRight);
            Entry<Integer, String> t = list.get(median5);
            list.set(median5, list.get(left+(int)Math.floor((double) (i-left)/5)));
            list.set(left+(int)Math.floor((double)(i-left)/5), t);
        }
        int mid = (int)Math.floor((double)(right-left)/10) + left + 1;
        return select(list, left, left + (int)Math.floor((double)(right-left)/5), mid);
    }
    public static int partition(List<Entry<Integer, String>> list, int left, int right, int pivotIdx, int n) {
        int pivot = list.get(pivotIdx).getKey();
        Entry<Integer, String> t = list.get(right);
        list.set(right, list.get(pivotIdx));
        list.set(pivotIdx, t);
        int storeIdx = left;
        for (int i=left; i<right; i++) {
            if (list.get(i).getKey() < pivot) {
                t = list.get(storeIdx);
                list.set(storeIdx, list.get(i));
                list.set(i, t);
                storeIdx++;
            }
        }
        int storeIdxEq = storeIdx;
        for (int i=storeIdx; i<right; i++) {
            if (list.get(i).getKey() == pivot) {
                t = list.get(storeIdxEq);
                list.set(storeIdxEq, list.get(i));
                list.set(i, t);
                storeIdxEq++;
            }
        }
        t = list.get(storeIdxEq);
        list.set(storeIdxEq, list.get(right));
        list.set(right, t);
        if (n < storeIdx) {
            return storeIdx;
        }
        if (n <= storeIdxEq) {
            return n;
        }
        return storeIdxEq;
    }

    public static int partition5(List<Entry<Integer, String>> list, int left, int right) {
        int i = left + 1;
        while (i<=right) {
            int j = i;
            while (j > left && list.get(j-1).getKey()>list.get(j).getKey()) {
                Entry<Integer, String> t = list.get(j);
                list.set(j, list.get(j-1));
                list.set(j-1, t);
                j--;
            }
            i++;
        }
        //return left + (right - left) / 2;
        return (int)Math.floor((double) (right + left) / 2);
    }
}


class HashMap<K, V> implements Map<K, V> {
    int mapSize;
    int capacity;
    List<Entry<K, V>>[] hashTable;
    List<K> keys = new ArrayList<>();

    public HashMap(int mapSize, int capacity) {
        this.mapSize = mapSize;
        this.capacity = capacity;
        this.hashTable = new List[capacity];
        for (int i=0; i<capacity; i++) {
            this.hashTable[i] = new ArrayList<>();
        }
    }
    private Entry<K, V> getEntry(K key) {
        int hash = Math.abs(key.hashCode()) % capacity;
        for (Entry entry : hashTable[hash]) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return this.mapSize;
    }

    @Override
    public boolean isEmpty() {
        return mapSize == 0;
    }

    @Override
    public V get(K key) {
        int hash = Math.abs(key.hashCode()) % capacity;
        for (Entry<K, V> entry : hashTable[hash]) {
            if (entry.getKey().equals(key)) {
                return entry.getVal();
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int f = 0;
        int hash = Math.abs(key.hashCode()) % this.capacity;
        for (Entry<K, V> entry : this.hashTable[hash]) {
            if (entry.getKey().equals(key)) {
                entry.setVal(value);
                f = 1;
                break;
            }
        }
        if (f == 0) {
            this.hashTable[hash].add(new Entry<>(key, value));
            mapSize++;
            keys.add(key);
        }
    }
    @Override
    public List<Entry<K, V>> entrySet() {
        List<Entry<K, V>> iter = new ArrayList<>();
        for (K key : keys) {
            iter.add(this.getEntry(key));
        }
//        for (List<Entry<K, V>> list : this.hashTable) {
//            if (!list.isEmpty()) {
//                for (Entry<K, V> entry : list) {
//                    iter.add(entry);
//                }
//            }
//        }
        return iter;
    }
    @Override
    public List<K> keys() {
        return keys;
    }
}

class Entry<K, V> {
    private K key;
    private V val;
    public Entry(K key, V val) {
        this.key = key;
        this.val = val;
    }
    public K getKey() {
        return key;
    }
    public V getVal() {
        return val;
    }
    public void setKey(K key) {
        this.key = key;
    }
    public void setVal(V val) {
        this.val = val;
    }
    @Override
    public int hashCode() {
        return key == null ? 0 : key.hashCode();
    }
}

interface Map<K, V> {
    int size();
    boolean isEmpty();
    V get(K key);
    void put(K key, V value);
    List<Entry<K, V>> entrySet();
    List<K> keys();
}