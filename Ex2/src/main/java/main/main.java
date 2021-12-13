package main;

import Pages.MainGraphPage;
import api.NodeData;
import main.*;

import java.util.LinkedList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        Algorithm utility = new Algorithm();
        utility.load(args[0]);

        MainGraphPage mainGraphPage = new MainGraphPage();
        mainGraphPage.createAndShowGUI();
        mainGraphPage.changeGraph((Graph)utility.getGraph());
    }

}
