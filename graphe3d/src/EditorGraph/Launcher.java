package editorGraph;

import graph3d.elements.Computer;
import graph3d.elements.EthernetLink;
import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.parserXML.GParser;
import graph3d.universe.GGrapheUniverse;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Launcher {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setLayout(new GridLayout(1,2));
		JPanel p = new JPanel(new GridLayout(2,1));
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
		
		GParser parser;
		try{
			parser = new GParser("xml/test2.xml");
		}catch(Exception e){
			parser = new GParser("/xml/test2.xml");
		}
		GGraph graph = parser.getGraph();
//		GGraph graph = new GGraph();
//		graph.addNode(node_1);
//		graph.addNode(node_2);
//		graph.addNode(comput);
//		graph.addLink(link_1);
//		graph.addLink(link_2);
		
		GGrapheUniverse universe = new GGrapheUniverse(graph);
		
		GEditor editor;
		try{
			editor = new GEditor("types/fichiertype.txt", universe);
		}catch(Exception e){
			editor = new GEditor("/types/fichiertype.txt", universe);
		}
//		GEditor editor = new GEditor("", universe);

		f.add(universe.getCanvas());
		f.add(editor);
		f.setVisible(true);
		
		String name = graph.getLinks().keySet().toArray(new String[0])[0];
		GLink link = graph.getLink(name);
		editor.addComponent(link, false);
		name = graph.getNodes().keySet().toArray(new String[0])[1];
		GNode  node = graph.getNode(name);
		editor.addComponent(node, false);
		
	}//main

}