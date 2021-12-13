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
## Usage
To use the GUI you need to download the Ex2.jar file from the repository.  
You can download some json files that can be used with the GUI, click [here](https://github.com/benmoshe/OOP_2021/blob/main/Assignments/Ex2/data/G1.json).
<br>
To use the JAR, open command prompt in the folder that contains the JAR and JSON files and type the following command:<br>
java -jar Ex2.jar (json_filename).json  
Fill in the name of your json file in place of the "json_filename" in the brackets.  
You should be greeted with this window:

![Capture1](https://user-images.githubusercontent.com/7474985/145890506-6986537c-e4d2-4a79-a44c-b3458211d000.PNG)  
<br>
From this menu you have 4 options:  
1) Load Graph(from json file)<br>When you click on this button you will be presented with a choice: choose the file in you system or manually enter a filename/filepath.<br><br>
2) Save Graph(into json file)<br>When you click on this button you will be requested to enter a filename/filepath, and you will be prompted about the success/failure of the file save.<br><br>
3) Edit Graph<br>Here you can add nodes, remove nodes,add edges,etc.<br><br>
4) Algorithms<br>Here you can perform algorithms the graph and see their results.  


