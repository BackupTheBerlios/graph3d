package editorGraph;

import graph3d.elements.Computer;
import graph3d.elements.EthernetLink;
import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.universe.GGrapheUniverse;

import java.awt.GridLayout;

import javax.swing.JFrame;

class Launcher {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setLayout(new GridLayout(1,2));
		f.setSize(800,630);
		f.setLocation(100,100);
		f.setTitle("éditeur de graphe 3D");

		GNode
		node_1 = new GNode("blabla",0,0,0),
		node_2 = new GNode("blabla 2",10,-10,5),
		comput = new Computer("my_computer",40,5,-3);
		
		GLink
		link_1 = new GLink("toto",node_1,node_2),
		link_2 = new EthernetLink("tutu",node_1,comput);
		
		GGraph graph = new GGraph();
		graph.addNode(node_1);
		graph.addNode(node_2);
		graph.addNode(comput);
		graph.addLink(link_1);
		graph.addLink(link_2);
		
		GGrapheUniverse universe = new GGrapheUniverse(graph);
		f.add(universe.getCanvas());
		
		GEditor editor = new GEditor("../../docs/ascii/fichiertype.txt", universe);
		
		f.add(editor);
		f.setVisible(true);

		editor.addComponent(node_1, true);
		editor.addComponent(link_1,true);
		editor.addComponent(node_2, false);
		editor.addComponent(comput, true);
	}//main

}