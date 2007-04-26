package editorGraph;

import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.BadElementTypeException;
import graph3d.exception.DeleteNodeWithLinksException;
import graph3d.exception.GException;
import graph3d.lists.GConnectionsList;
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
	 * associated universe
	 */
	GGrapheUniverse universe = new GGrapheUniverse(new GGraph());

	/*
	 * editor areas
	 */
	GTabArea tabArea;
	GConnectionsList listArea;
	GCreationArea creationArea;

	/*
	 * margins
	 */
	static final Insets
	BUTTON_INSETS = new Insets(2,2,2,2),
	EDITOR_INSETS = new Insets(1,1,1,1);

	/*
	 * areas placement constraints
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
		/*
		 * editor naming
		 */
		setBorder(new TitledBorder(new EtchedBorder(),"Editeur"));
		setMinimumSize(new Dimension(260,600));
		/*
		 * placement
		 */
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		/*
		 *area creation
		 */
		creationArea = new GCreationArea(_ascii_file,this);
		tabArea = new GTabArea(this, _ascii_file);
		listArea = new GConnectionsList();
		listArea.attachAttributeList(tabArea.attributes_list);
		/*
		 * we associate the editor to an universe
		 * and we set the selection behaviour between the universe and the attributes list
		 */
		this.universe = _Universe;
		this.universe.addSelectionBehavior(tabArea.attributes_list);
		/*
		 * areas placement
		 */
		gbl.addLayoutComponent(tabArea, TAB_AREA_CONSTRAINTS);
		gbl.addLayoutComponent(listArea, LIST_AREA_CONSTRAINTS);
		gbl.addLayoutComponent(creationArea, CREATE_AREA_CONSTRAINTS);
		/*
		 *	areas addition
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
		if( tabArea.attributes_list.getElements().indexOf(component) == -1 ){ // component is not already selected 

			try{
				if( component instanceof GNode ){
					GNode node = (GNode) component;
					tabArea.attributes_list.add(node, focus);
				}else if( component instanceof GLink ){
					GLink link = (GLink) component;
					tabArea.attributes_list.add(link, focus);
				}else
					System.err.println(new BadElementTypeException("graph element").getMessage());

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
				GTab tab = (GTab) tabArea.attributes_list.getSelectedComponent();
				tabArea.attributes_list.remove(tab.getElement());
				doCheck();
			}else if(button == tabArea.remove_all){
				tabArea.attributes_list.removeAll();
				/*
				 * we check if there is at least one element in the list
				 * of course no
				 */
				doCheck();
			}else if(button == tabArea.unselect){
				unselect();
				/*
				 * we check if there is at least one element in the list
				 */
				doCheck();
			}else if(button == tabArea.unselect_all){
				unselectAll();
				/*
				 * we check if there is at least one element in the list
				 * of course no
				 */
				doCheck();
			}else if(button == creationArea.button_arrow){
				JComboBox combo = creationArea.combo_arrow;
				String type_of_links  = (String) combo.getSelectedItem();
				Class link_class = creationArea.table_link.get(type_of_links);
				GNode[]nodes = new GNode[tabArea.attributes_list.getNodeCount()];
				for(int i=0; i<nodes.length;i++)
					nodes[i] = (GNode) tabArea.attributes_list.getElements().get(i);
				GPopup.showPopup(GEditor.this, nodes, GPopup.ARROW, link_class,type_of_links);
			}else if(button == creationArea.button_bridge){
				JComboBox combo = creationArea.combo_bridge;
				String type_of_links = (String) combo.getSelectedItem();
				Class link_class = creationArea.table_link.get(type_of_links);
				GNode[]nodes = new GNode[tabArea.attributes_list.getNodeCount()];
				for(int i=0; i<nodes.length;i++)
					nodes[i] = (GNode) tabArea.attributes_list.getElements().get(i);
				GPopup.showPopup(GEditor.this, nodes, GPopup.BRIDGE, link_class, type_of_links);
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
		GTab tab = (GTab) tabArea.attributes_list.getSelectedComponent();
		tabArea.attributes_list.remove(tab.getElement());
	}//unselect

	/**
	 * this method performs unselection of the whole selection.
	 */
	void unselectAll(){
		tabArea.attributes_list.removeAll();
	}//unselectAll

	/**
	 * this method performs removal of a GNode.<br>
	 * if this one is associated to one link at least, it will warn you and ask you
	 * if you really want to remove it :
	 * 	- if you agree the node will be removed, and the associated links as well as.
	 * 	- and if you disagree nothing will be done.
	 * if the node is associated to no link, it will be remove without warning.
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
				tabArea.attributes_list.getNodeCount();
			}//if
		}else{
			universe.deleteGNode(node.getName());
			tabArea.attributes_list.getNodeCount();
		}//if
	}// remove
	
	public void doCheck(){
		boolean isOneAtLeast = true;
		if(tabArea.attributes_list.getTabCount()==0){
			isOneAtLeast = false;
		}
		tabArea.remove.setEnabled(isOneAtLeast);
		tabArea.remove_all.setEnabled(isOneAtLeast);
		tabArea.unselect.setEnabled(isOneAtLeast);
		tabArea.unselect_all.setEnabled(isOneAtLeast);
		
		boolean isOneNodeAtLeast = true;
		if(tabArea.attributes_list.getNodeCount()==0){
			isOneNodeAtLeast = false;
		}
		creationArea.button_arrow.setEnabled(isOneNodeAtLeast);
		creationArea.button_bridge.setEnabled(isOneNodeAtLeast);
		
	}

}//class GEditor
