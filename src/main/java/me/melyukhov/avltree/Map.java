package me.melyukhov.avltree;

import java.util.List;

public interface Map<Key extends Comparable<Key>, Value> {

    MyTree.Node getFirstNode();

    MyTree.Node getLastNode();

    List<Key> keySet();

    void put(Key key, Value value);

    void remove(Key key);

    Key minKey();

    Key maxKey();

    Value get(Key key);

    void print();

    Comparable<Key> findValue(Value value);

    List<MyTree.Node> nodeList();
}
