package main;

import api.GeoLocation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void getKey() {
        GeoLocation location= new Point3D(50,50,100);
        Node toCheck= new Node(1, location , 6.5, "", 1);
        int tmp= toCheck.getKey();
        assertEquals(tmp, 1);
    }

    @Test
    void setKey() {
        GeoLocation location= new Point3D(6,87,123);
        Node toCheck= new Node(2, location , 6.8, "ADA", 31);
        toCheck.setKey(3);
        assertEquals(toCheck.getKey(), 3);
    }

    @Test
    void getLocation() {
        GeoLocation location= new Point3D(6,57,13);
        Node toCheck= new Node(3, location , 5.8, "AmA", 4);
        assertEquals(toCheck.getLocation(), location);
    }

    @Test
    void setLocation() {
        GeoLocation location= new Point3D(8,57,13);
        Node toCheck= new Node(3, location , 5.8, "AjA", 54);
        GeoLocation newLocate= new Point3D(1,1,1);
        toCheck.setLocation(newLocate);
        assertEquals(toCheck.getLocation(), newLocate);
    }

    @Test
    void getWeight() {
        GeoLocation location= new Point3D(11,52,13);
        Node toCheck= new Node(3, location , 5.8, "rtmA", 4);
        assertEquals(toCheck.getWeight(), 5.8);
    }

    @Test
    void setWeight() {
        GeoLocation location= new Point3D(11,52,13);
        Node toCheck= new Node(3, location , 5.8, "emA", 4);
        toCheck.setWeight(8.5);
        assertEquals(toCheck.getWeight(), 8.5);
    }

    @Test
    void getInfo() {
        GeoLocation location= new Point3D(11,52,13);
        Node toCheck= new Node(3, location , 5.8, "vb", 4);
        assertEquals(toCheck.getInfo(), "vb");
    }

    @Test
    void setInfo() {
        GeoLocation location= new Point3D(11,52,13);
        Node toCheck= new Node(3, location , 5.8, "A4mA", 4);
        String inf= "newInfo";
        toCheck.setInfo(inf);
        assertEquals(toCheck.getInfo(), inf);
    }

    @Test
    void getTag() {
        GeoLocation location= new Point3D(11,52,13);
        Node toCheck= new Node(3, location , 5.8, "afe", 4);
        assertEquals(toCheck.getTag(), 4);
    }

    @Test
    void setTag() {
        GeoLocation location= new Point3D(11,52,13);
        Node toCheck= new Node(3, location , 5.8, "afe", 4);
        toCheck.setTag(2234);
        assertEquals(toCheck.getTag(), 2234);
    }
}