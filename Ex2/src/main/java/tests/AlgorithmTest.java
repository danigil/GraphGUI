package tests;

import main.Algorithm;
import main.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.commons.lang3.builder.EqualsBuilder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlgorithmTest {
    Algorithm algorithm;
    @BeforeEach
    void create(){
        algorithm = new Algorithm();
        algorithm.init(Graph.generateRandomGraph(1000));
    }

    @Test
    void copyTest(){
        //assertTrue(EqualsBuilder.reflectionEquals(algorithm.copy(),algorithm.getGraph()));
    }

    @Test
    void isConnectedTest(){

    }
}
