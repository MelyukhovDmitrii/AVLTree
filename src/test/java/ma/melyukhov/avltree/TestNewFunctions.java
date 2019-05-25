package ma.melyukhov.avltree;

import me.melyukhov.avltree.Map;
import me.melyukhov.avltree.MyTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestNewFunctions {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private static final Object OBJ = new Object[0];
    Map<Integer, Object> testTree;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    public TestNewFunctions(){
        testTree = new MyTree<>();
    }

    @Test
    public void testPrint(){
        Map<Integer, Object> map = new MyTree<>();
        for(int i = 0; i < 2; i++){
            map.put(i, null);
        }
        System.setOut(new PrintStream(outContent));
        map.print();
        String out = outContent.toString();//.replace(" ", "");
        out = out.replace("\n", "");
        assertEquals("\t1->null h=1 balance=00->null h=2 balance=-1", out);
        System.setOut(null);
    }

    @Test
    public void testFindValue(){
        Map<Integer, Integer> map = new MyTree<>();
        for(int i = 0; i < 10; i++){
            map.put(i, -i);
        }
        assertEquals(5, map.findValue(-5));
    }

    @Test
    public void testNodeList() {
        Map<Integer, Integer> map = new MyTree<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, -i);
        }
        List<MyTree.Node> nodes = map.nodeList();
        Comparable[] test = new Integer[10];
        for(int i = 0; i < 10; i++){
            assertEquals(nodes.get(i).key, i);
        }
    }
}
