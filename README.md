# GraphGUI
This project is a GUI that displays directed weighted graphs.
## Introduction
This GUI is designed to load and save graphs from json files, display them on the screen,</br> allow various interactions such as: deleting nodes, inserting edges, running algorithms on the graph and receiving feedback.
## Preplanning
The project will be consisted of two main parts: the GUI and the data structure, the GUI will serve as the main program and it will use the data structure for its purpose.  
For the GUI we will have 4 main classes, each representing a page in our menu, and an additional class for displaying the graph.  
The classes will use a pool of shared resources that will be available to them through a superclass.  
For the data structure we will have 2 main classes, an Algorithm class, and a Graph class.<br>The graph will have a hashset of Nodes, each node has a hashset of edges, representing the edges that are originating in that node.
<br>
<br>
The GUI has an Algorithm object which holds a Graph object, and therefore every action we perform in the GUI translates to data changes in the Algorithm object, and internally, the Graph object.<br>
In the app's lifecycle there is only one Algorithm object per GUI instance.

