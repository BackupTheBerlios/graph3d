package editorGraph;

import graph3d.elements.Computer;
import graph3d.elements.EthernetLink;
import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.BadElementTypeException;
import graph3d.universe.GView;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GEditor extends JPanel{

	/*
	 * à virer !!
	 */
	GGraph graph = new GGraph();
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(300,630);
		f.setLocation(100,100);
		f.setTitle("éditeur de graphe 3D");
		
		GEditor editor = new GEditor("C:\\Documents and Settings\\lino christophe\\Mes documents\\IUP MIS\\L3 INFO\\INF1603 - graphe3d\\CODAGE\\fichiertype.txt");
		
		f.add(editor);
		f.setVisible(true);
		
		GNode
		node_1 = new GNode("blabla"),
		node_2 = new GNode("blabla 2"),
		comput = new Computer("my_computer");
		
		GLink
		link = new GLink("toto",node_1,node_2),
		link_2 = new EthernetLink("tutu",node_1,comput);
		
		editor.graph.addNode(node_1);
		editor.graph.addNode(node_2);
		editor.graph.addNode(comput);
		editor.graph.addLink(link);
		editor.graph.addLink(link_2);

		editor.addComponent(node_1, true);
		editor.addComponent(link,true);
		editor.addComponent(node_2, false);
		editor.addComponent(comput, true);
	}//main

	/*
	 * la vue associée
	 */
	GView view;

	/*
	 * les zones de l'éditeur
	 */
	GTabArea tabArea;
	GListArea listArea;
	GCreationArea creationArea;

	/*
	 * marges
	 */
	static final Insets
	BUTTON_INSETS = new Insets(2,2,2,2),
	EDITOR_INSETS = new Insets(1,1,1,1);

	/*
	 * les contraintes de positionnement des parties.
	 */
	GridBagConstraints
	TAB_AREA_CONSTRAINTS
	= new GridBagConstraints(0,0,1,1,100,55,GridBagConstraints.CENTER, GridBagConstraints.BOTH,EDITOR_INSETS,1,1),
	LIST_AREA_CONSTRAINTS
	= new GridBagConstraints(0,1,1,1,100,40,GridBagConstraints.CENTER, GridBagConstraints.BOTH,EDITOR_INSETS,1,1),
	CREATE_AREA_CONSTRAINTS
	= new GridBagConstraints(0,2,1,1,100,5,GridBagConstraints.CENTER, GridBagConstraints.BOTH,EDITOR_INSETS,1,1);




	/**
	 * 
	 */
	public GEditor(String ascii_file) {
		/*
		 * nommage de l'éditeur
		 */
		setBorder(new TitledBorder(new EtchedBorder(),"Editeur"));
		setMinimumSize(new Dimension(260,600));
		/*
		 * placement
		 */
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		/*
		 * création des zones
		 */
		tabArea = new GTabArea(this);
		listArea = new GListArea(new JList(), this);
		creationArea = new GCreationArea(ascii_file,this);
		/*
		 * placement des zones
		 */
		gbl.addLayoutComponent(tabArea, TAB_AREA_CONSTRAINTS);
		gbl.addLayoutComponent(listArea, LIST_AREA_CONSTRAINTS);
		gbl.addLayoutComponent(creationArea, CREATE_AREA_CONSTRAINTS);
		/*
		 * ajout des zones à l'éditeur
		 */
		add(tabArea);
		add(listArea);
		add(creationArea);
	}//construct

	/**
	 * ajoute un objet qui doit être un élément de graphe (type GNode ou GLink),
	 * avec ou sans focus.
	 * @param component
	 * 		l'élément à ajouter dans les onglets.
	 * @param focus
	 * 		est-ce qu'il sera l'élément courant.
	 */
	public void addComponent(Object component, boolean focus) {
		if( tabArea.elements.indexOf(component) == -1 ){ // component is not already selected 
			
			Hashtable<Class, String> table_types = new Hashtable<Class, String>();
			Object[] types_link = creationArea.table_link.keySet().toArray();
			Object[] types_node = creationArea.table_node.keySet().toArray();
			
			for(int i=0;i<types_link.length;i++)
				table_types.put(creationArea.table_link.get(types_link[i]), (String)types_link[i]);
			for(int i=0;i<types_node.length;i++)
				table_types.put(creationArea.table_node.get(types_node[i]), (String)types_node[i]);

			GTab tab = new GTab(component, tabArea.tabbedpane, table_types, view);
			if( component instanceof GNode ){
				GNode node = (GNode) component;
				tabArea.elements.add(tabArea.nb_nodes,node);
				tabArea.tabbedpane.add(tab,tabArea.nb_nodes);
				tabArea.tabbedpane.setTitleAt(tabArea.nb_nodes, node.getName());			
				tabArea.nb_nodes++;
			}else if( component instanceof GLink ){
				GLink link = (GLink) component;
				tabArea.elements.add(link);
				tabArea.tabbedpane.add(tab,link.getName());
			}else
				System.err.println(new BadElementTypeException("graph element").getMessage());

			if(focus) tabArea.tabbedpane.setSelectedComponent(tab);
			tabArea.remove.setEnabled(true);
			tabArea.remove_all.setEnabled(true);
			tabArea.unselect.setEnabled(true);
			tabArea.unselect_all.setEnabled(true);
		
		}//if tabArea.elements.indexOf(component) == -1
	}//addComponent

	/**
	 * ajoute ou remplace la vue associée à cet éditeur
	 *
	 */
	public void setView(GView view) {
		this.view = view;
	}//addView

	public GView getView(){
		return view;
	}

	class ButtonListener extends MouseAdapter{

		public void mouseClicked(MouseEvent m){
			JButton button = (JButton) m.getSource();
			if(button == tabArea.remove){
				/*
				 * suppression
				 */
				GTab tab = (GTab) tabArea.tabbedpane.getSelectedComponent();
				if(tab.getElement() instanceof GNode){
					GNode node = (GNode) tab.getElement();
					graph.removeNode(node.getName());
				}else{
					GLink link = (GLink) tab.getElement();
					graph.removeLink(link.getName());
				}//if
				/*
				 * unselect
				 */
				unselect();
			}else if(button == tabArea.remove_all){
				JTabbedPane tabbedpane = tabArea.tabbedpane;
				/*
				 * suppressions
				 */
				for(int i=0; i<tabbedpane.getComponentCount(); i++){
					GTab tab = (GTab) tabbedpane.getComponentAt(i);
					if(tab.getElement() instanceof GNode){
						GNode node = (GNode) tab.getElement();
						graph.removeNode(node.getName());
					}else{
						GLink link = (GLink) tab.getElement();
						graph.removeLink(link.getName());
					}//if
				}//for
				/*
				 * unselect_all
				 */
				unselectAll();
			}else if(button == tabArea.unselect){
				unselect();
			}else if(button == tabArea.unselect_all){
				unselectAll();
			}else if(button == creationArea.button_arrow){
				JComboBox combo = creationArea.combo_arrow;
				String type_of_links  = (String) combo.getSelectedItem();
				Class link_class = creationArea.table_link.get(type_of_links);
				GNode[]nodes = new GNode[tabArea.nb_nodes];
				for(int i=0; i<nodes.length;i++)
					nodes[i] = (GNode) tabArea.elements.get(i);
				try{
					GPopup.showPopup(GEditor.this,nodes, GPopup.ARROW, link_class,type_of_links);
				}catch (Exception e) {
					System.out.println(e+" button listener : button_arrow");
					/*
					 * on ne devrait pas arriver ici
					 */
				}//try
			}else if(button == creationArea.button_bridge){
				JComboBox combo = creationArea.combo_bridge;
				String type_of_links = (String) combo.getSelectedItem();
				Class link_class = creationArea.table_link.get(type_of_links);
				GNode[]nodes = new GNode[tabArea.nb_nodes];
				for(int i=0; i<nodes.length;i++)
					nodes[i] = (GNode) tabArea.elements.get(i);
				try{
					System.out.println(type_of_links);
					GPopup.showPopup(GEditor.this,nodes, GPopup.BRIDGE, link_class, type_of_links);
				}catch (Exception e) {
					System.out.println(e+" button listener : button_bridge");
					/*
					 * on ne devrait pas arriver ici
					 */
				}//try
			}else if(button == creationArea.button_node){
				JComboBox combo = creationArea.combo_node;
				String type_of_node = (String) combo.getSelectedItem();
				Class node_class = creationArea.table_node.get(type_of_node);
				Class[] construct_param = new Class[]{String.class, float.class, float.class, float.class};
				try{
					/*
					 * getting back the coordonates x y z from the spinners
					 */
					float
					x = ( (Double) creationArea.coord_values[0].getValue() ).floatValue(),
					y = ( (Double) creationArea.coord_values[1].getValue() ).floatValue(),
					z = ( (Double) creationArea.coord_values[2].getValue() ).floatValue();
					/*
					 * and creating a new node with this parameters
					 */
					GNode node = (GNode) node_class.getConstructor(construct_param).newInstance("",x,y,z);
					/*
					 * trying to associate a non-used name for this node
					 */
					node.setName(type_of_node+"_");
					String name = node.getName();
					int j=1;
					node.setName(name+j);
					boolean good = graph.addNode(node);
					while( ! good ){
						j++;
						node.setName(name+j);
						good = graph.addNode(node);
					}//while
					addComponent(node, true);
					tabArea.refreshList();
				}catch (Exception e) {
					System.out.println(e+" button listener : button_node");
				}//try
			}//if
			if(tabArea.tabbedpane.getComponentCount()==0){
				tabArea.remove.setEnabled(false);
				tabArea.remove_all.setEnabled(false);
				tabArea.unselect.setEnabled(false);
				tabArea.unselect_all.setEnabled(false);
			}//if
			for(int i=0; i<graph.getNodes().size();i++)
				System.out.println(graph.getNodes().keySet().toArray()[i]);

			for(int i=0; i<graph.getLinks().size();i++)
				System.out.println(graph.getLinks().keySet().toArray()[i]);			
		}//mouseClicked

		private void unselect(){
			int index = tabArea.tabbedpane.getSelectedIndex();
			tabArea.elements.remove(index);
			if(index < tabArea.nb_nodes){
				tabArea.nb_nodes--;
			}//if
			tabArea.tabbedpane.remove(index);
			tabArea.refreshList();
		}//unselect

		private void unselectAll(){
			tabArea.tabbedpane.removeAll();
			tabArea.elements = new LinkedList<Object>();
			tabArea.nb_nodes = 0;
			tabArea.refreshList();
		}//unselectAll

	}//inner class GButtonListener

}//class GEditor
