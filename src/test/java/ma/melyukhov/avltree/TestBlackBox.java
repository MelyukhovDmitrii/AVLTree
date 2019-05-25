package ma.melyukhov.avltree;

import me.melyukhov.avltree.Map;
import me.melyukhov.avltree.MyTree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestBlackBox {

    TreeMap<Integer, Object> treeMap;
    Map<Integer, Object> testTree;

    private static final Object OBJ = new Object[0];

    public TestBlackBox() {
        treeMap = new TreeMap<>();
        testTree = new MyTree<>();
    }
    @Test
    public void testRandomFilling() {
        Random r = new Random();
        for(int i = 0; i < 10000; i++) {
            int randomVal = r.nextInt();
            treeMap.put(randomVal, OBJ);
            testTree.put(randomVal, OBJ);
        }
        assertArrayEquals(treeMap.keySet().toArray(),
                testTree.keySet().toArray(), "Тест на заполнение случайными значениями провалился");
    }
    @Test
    public void testBigValueFilling() {
        for(float i = 0.01f; i < 1; i+=0.01f) {
            treeMap.put((int)(Integer.MAX_VALUE * i), OBJ);
            testTree.put((int)(Integer.MAX_VALUE * i), OBJ);
        }
        assertArrayEquals(treeMap.keySet().toArray(),
                testTree.keySet().toArray(), "Тест на заполнение большими значениями провалился");
    }

    @Test
    public void testFillAndDeleteRandom() {
        Random r = new Random();
        ArrayList<Integer> randomValues = new ArrayList<>();
        for(int i =0; i < 1000; i++) {
            randomValues.add(r.nextInt());
        }
        for(Integer key: randomValues) {
            treeMap.put(key, OBJ);
            testTree.put(key, OBJ);
        }
        assertArrayEquals(treeMap.keySet().toArray(),
                testTree.keySet().toArray(), "Тест на заполнение случайными значениями провалился");
        for(int i = 0; i < randomValues.size(); i++) {
            int index = r.nextInt(randomValues.size());
            treeMap.remove(randomValues.get(index));
            testTree.remove(randomValues.get(index));
            assertArrayEquals(treeMap.keySet().toArray(),
                    testTree.keySet().toArray(), "Тест на удаление случайного ключа: " +
                            randomValues.get(index).toString() + " провалился");
        }
    }
}
