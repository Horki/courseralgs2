package sedgewick.coursera.week4.interfaces;

public interface StringST<T> {
    void put(String key, T value);

    T get(String key);

    boolean contains(String key);

    Iterable<String> keys();

    Iterable<String> keysWithPrefix(String prefix);

    String longestPrefixOf(String s);

    Iterable<String> keysThatMatch(String s);

    int size();

    void delete(String key);
}