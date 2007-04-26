package editorGraph;

import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.BadElementTypeException;
import graph3d.exception.DeleteNodeWithLinksException;
import graph3d.exception.GException;
import graph3d.universe.GGrapheUniverse;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * This class provide a graph editor to dynamically edit a graph.<br>
 * It implements a selections view wich enables you :<br>
 *  - to change the value of the attributes of the selected elements,<br>
 *  - to create new graph elements,<br>
 *  - to navigate in the graph.<br>
 * 
 * @author lino christophe
 * @since JDK 1.5
 * @version 1.0
 */
public class GEditor extends JPanel{

	/*
	 * la vue associée
	 */
	GGrapheUniverse universe = new GGrapheUniverse(new GGraph());

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
	 * constructs a new graph editor.
	 * @param _ascii_file
	 * 		the name of the file containing all the known classes which implements graph elements
	 * @param _Universe
	 * 		the graph universe which contains the graph
	 */
	public GEditor(String _ascii_file, GGrapheUniverse _Universe) {
		this.universe = _Universe;
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
		listArea = new GListArea(this);
		creationArea = new GCreationArea(_ascii_file,this);
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
	 * add a graph element into the selection (only elements that can be cast into a GNode or GLink object),
	 * with or without focus.
	 * If focus is set, it will become the current element in the selection.
	 * And if this element is already selected, it will be not added.
	 * @param component
	 * 		the graph element to add
	 * @param focus
	 * 		if the added element have to become the current selection
	 */
	public void addComponent(Object component, boolean focus) {
		if( tabArea.elements.indexOf(component) == -1 ){ // component is not already selected 

			try{
				GTab tab = new GTab(component, this, this.universe,true);
				if( component instanceof GNode ){
					GNode node = (GNode) component;
					tabArea.elements.add(tabArea.nb_nodes,node);
					tabArea.tabbedpane.insertTab(node.getName(), null, tab, "noeud", tabArea.nb_nodes);	
					tabArea.nb_nodes++;
				}else if( component instanceof GLink ){
					GLink link = (GLink) component;
					tabArea.elements.add(link);
					tabArea.tabbedpane.add(tab,link.getName());
					tabArea.tabbedpane.setToolTipTextAt(tabArea.tabbedpane.indexOfComponent(tab), "lien");
				}else
					System.err.println(new BadElementTypeException("graph element").getMessage());

				if(focus){
					tabArea.tabbedpane.setSelectedComponent(tab);
					tabArea.refreshList();
				}
				tabArea.remove.setEnabled(true);
				tabArea.remove_all.setEnabled(true);
				tabArea.unselect.setEnabled(true);
				tabArea.unselect_all.setEnabled(true);
			}catch (GException e) {
				/*
				 * we must not go here
				 */
			}//try

		}//if tabArea.elements.indexOf(component) == -1
	}//addComponent

	/**
	 * this inner class is used to implements actions associated to a performed click on a button.<br>
	 * it provides actions for :<br>
	 * - unselection & whole unselection buttons [from tab area],<br> 
	 * - removal & whole removal buttons [from tab area],<br>
	 * - node addition [from addition area],<br>
	 * - bridge addtion & arrow addition [from addtion area].<br>
	 */
	class ButtonListener extends MouseAdapter{

		public void mouseClicked(MouseEvent m){
			JButton button = (JButton) m.getSource();
			if(button == tabArea.remove){
				/*
				 * removal
				 */
				GTab tab = (GTab) tabArea.tabbedpane.getSelectedComponent();
				if(tab.getElement() instanceof GNode){
					remove((GNode) tab.getElement());
				}else{
					GLink link = (GLink) tab.getElement();
					link.getFirstNode().removeLink(link);
					link.getSecondNode().removeLink(link);
					universe.deleteGLink(link.getName());
				}//if
				universe.getCanvas().repaint();
				/*
				 * unselection
				 */
				unselect();
			}else if(button == tabArea.remove_all){
				JTabbedPane tabbedpane = tabArea.tabbedpane;
				/*
				 * removal
				 */
				for(int i=0; i<tabbedpane.getTabCount(); i++){
					GTab tab = (GTab) tabbedpane.getComponentAt(i);
					if(tab.getElement() instanceof GNode){
						GNode node = (GNode) tab.getElement();
						remove(node);
					}else{
						GLink link = (GLink) tab.getElement();
						universe.deleteGLink(link.getName());
					}//if
				}//for
				universe.getCanvas().repaint();
				/*
				 * whole unselection
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
				GPopup.showPopup(GEditor.this,nodes, GPopup.ARROW, link_class,type_of_links);
			}else if(button == creationArea.button_bridge){
				JComboBox combo = creationArea.combo_bridge;
				String type_of_links = (String) combo.getSelectedItem();
				Class link_class = creationArea.table_link.get(type_of_links);
				GNode[]nodes = new GNode[tabArea.nb_nodes];
				for(int i=0; i<nodes.length;i++)
					nodes[i] = (GNode) tabArea.elements.get(i);
				System.out.println(type_of_links);
				GPopup.showPopup(GEditor.this,nodes, GPopup.BRIDGE, link_class, type_of_links);
			}else if(button == creationArea.button_node){
				/*
				 * node addition
				 */
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
					boolean good = universe.getGraph().addNode(node);
					System.out.println(good);
					while( ! good ){
						j++;
						node.setName(name+j);
						good = universe.getGraph().addNode(node);
					}//while
					addComponent(node, true);
					universe.addGNode(node);
					tabArea.refreshList();
					universe.getCanvas().repaint();
					creationArea.button_arrow.setEnabled(true);
					creationArea.button_bridge.setEnabled(true);
				}catch (Exception e) {
					/*
					 * we mustn't go here
					 */
				}//try
			}//if
		}//mouseClicked

	}//inner class GButtonListener

	/**
	 * this method performs unselection of the current tab
	 */
	void unselect(){
		int index = tabArea.tabbedpane.getSelectedIndex();
		tabArea.elements.remove(index);
		if(index < tabArea.nb_nodes){
			tabArea.nb_nodes--;
			if(tabArea.nb_nodes==0){
				creationArea.button_arrow.setEnabled(false);
				creationArea.button_bridge.setEnabled(false);
			}
		}//if
		tabArea.tabbedpane.remove(index);
		tabArea.refreshList();
		if(tabArea.tabbedpane.getTabCount()==0){
			tabArea.remove.setEnabled(false);
			tabArea.remove_all.setEnabled(false);
			tabArea.unselect.setEnabled(false);
			tabArea.unselect_all.setEnabled(false);
		}//if
		if(tabArea.nb_nodes==0){
			creationArea.button_arrow.setEnabled(false);
			creationArea.button_bridge.setEnabled(false);
		}
		tabArea.refreshList();
	}//unselect

	/**
	 * this method preforms unselection of the whole selection.
	 */
	void unselectAll(){
		tabArea.tabbedpane.removeAll();
		tabArea.elements = new LinkedList<Object>();
		tabArea.nb_nodes = 0;
		tabArea.refreshList();
		tabArea.remove.setEnabled(false);
		tabArea.remove_all.setEnabled(false);
		tabArea.unselect.setEnabled(false);
		tabArea.unselect_all.setEnabled(false);
		creationArea.button_arrow.setEnabled(false);
		creationArea.button_bridge.setEnabled(false);
		tabArea.refreshList();
	}//unselectAll

	/**
	 * this method is used to remove a GNode.<br>
	 * if this one is associated to one link at least, it will warn you and ask
	 * if you really want to remove it.
	 * If yes it will remove the node, and the associated links as well as.
	 * And if no it will do nothing.
	 */
	void remove(GNode node){
		LinkedList<GLink> list_links = node.getLinks();
		if(list_links.size()>0){
			if(new DeleteNodeWithLinksException(node).showError()){
				/*
				 * user is ok for removal
				 */
				for(int i=0; i<list_links.size();i++){
					GLink link = list_links.get(i);
					universe.deleteGLink(link.getName());
					if(link.getFirstNode()==node)
						link.getSecondNode().removeLink(link);
					else
						link.getFirstNode().removeLink(link);
				}//for
				universe.deleteGNode(node.getName());
				tabArea.unselectlinks(list_links);
			}//if
		}else{
			universe.deleteGNode(node.getName());
		}//if
		unselect();
		tabArea.nb_nodes--;
	}// remove

}//class GEditor
