package tests;

import main.Edge;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.charset.Charset;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EdgeTest {

    Edge edge;
    int src;
    int dest;
    double weight;
    String info;
    int tag;

    Random rand = new Random();

    private String randString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return generatedString;
    }

    @BeforeAll
    void create(){
        src = rand.nextInt(Integer.MAX_VALUE);
        dest = rand.nextInt(Integer.MAX_VALUE);

        weight=Integer.MAX_VALUE * rand.nextDouble();
        info=randString();
        tag=rand.nextInt(Integer.MAX_VALUE);

        edge = new Edge(src,dest,weight,info,tag);
    }

    @Test
    void testGetSrc() {
        assertEquals(src,edge.getSrc());
    }

    @Test
    void testGetDest() {
        assertEquals(dest,edge.getDest());
    }

    @Test
    void testGetWeight() {
        assertEquals(weight,edge.getWeight());
    }

    @Test
    void testGetInfo() {
        assertEquals(info,edge.getInfo());
    }

    @Test
    void testSetInfo() {
        String str = randString();
        edge.setInfo(str);
        assertEquals(str,edge.getInfo());
    }

    @Test
    void testGetTag() {
        assertEquals(tag,edge.getTag());
    }

    @Test
    void testSetTag() {
        int num = rand.nextInt(Integer.MAX_VALUE);
        edge.setTag(num);
        assertEquals(num,edge.getTag());
    }
}
