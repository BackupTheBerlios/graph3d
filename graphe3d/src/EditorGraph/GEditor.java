package editorGraph;

import editorGraph.GPopup.Association;
import graph3d.elements.GGraph;
import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.BadElementTypeException;
import graph3d.exception.DeleteNodeWithLinksException;
import graph3d.exception.GException;
import graph3d.lists.GConnectionsList;
import graph3d.lists.GTab;
import graph3d.parserXML.GParser;
import graph3d.universe.GGrapheUniverse;
import graph3d.use.PanelButtonInteraction;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.media.j3d.Canvas3D;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import visualizator.FiltreFichier;

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
	 * margins
	 */
	static final Insets
	BUTTON_INSETS = new Insets(2,2,2,2),
	EDITOR_INSETS = new Insets(1,1,1,1);

	/*
	 * placement constraints 
	 */
	private static GridBagConstraints
	VIEW_CONSTRAINTS
	= new GridBagConstraints(0,1,1,1,100,90,GridBagConstraints.CENTER, GridBagConstraints.BOTH,EDITOR_INSETS,1,1),
	EDITOR_CONSTRAINTS
	= new GridBagConstraints(0,0,1,1,100,10,GridBagConstraints.CENTER, GridBagConstraints.BOTH,EDITOR_INSETS,1,1);		

	/**
	 * launches the GEditor application
	 * @param args
	 * 		parameters of the launcher (but no need parameter)
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Editeur de Graphe3D");
		frame.addWindowListener(new EditorListener());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GridBagLayout gbl = new GridBagLayout();
		frame.setLayout(gbl);
		JMenu 
		file = new JMenu("Fichier"),
		types = new JMenu("Eléments");
		JMenuItem
		new_graph = new JMenuItem("Nouveau graphe"),
		load_graph = new JMenuItem("Charger un graphe..."),
		load_ascii = new JMenuItem("Cherger des types..."),
		save = new JMenuItem("Enregistrer..."),
		save_as = new JMenuItem("Enregistrer sous..."),
		exit = new JMenuItem("Quitter");
		file.add(new_graph);
		file.add(load_graph);
		file.add(save);
		file.add(save_as);
		file.add(exit);
		types.add(load_ascii);
		JMenuBar menubar = new JMenuBar();
		menubar.add(file);
		menubar.add(types);
		frame.setJMenuBar(menubar);
		GGrapheUniverse universe = new GGrapheUniverse(new GGraph());
		PanelButtonInteraction interaction = new PanelButtonInteraction(universe);
		GEditor editor = new GEditor(null,universe);
		Canvas3D view = universe.getCanvas();
//		gbl.addLayoutComponent(editor, EDITOR_CONSTRAINTS);
//		gbl.addLayoutComponent(view, VIEW_CONSTRAINTS);
		frame.setLayout(new GridLayout(1,2));
		editor.setLayout(new GridLayout(3,1));
		editor.add(interaction);
		frame.add(editor);
		frame.add(view);
		/*
		 * building the editor listener
		 */
		EditorListener listener = new EditorListener();
		listener.owner = frame;
		listener.editor = editor;
		listener.load_ascii = load_ascii;
		listener.load_graph = load_graph;
		listener.save = save;
		listener.save_as = save_as;
		listener.new_graph = new_graph;
		listener.exit = exit;
		/*
		 * adding this listener to every menu items
		 */
		new_graph.addActionListener(listener);
		load_ascii.addActionListener(listener);
		load_graph.addActionListener(listener);
		save.addActionListener(listener);
		save_as.addActionListener(listener);
		exit.addActionListener(listener);

		frame.setSize(800,800);
		frame.setVisible(true);
	}

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
		setLayout(new GridLayout(2,1));
		/*
		 * we associate the editor to an universe
		 * and we set the selection behaviour between the universe and the attributes list
		 */
		this.universe = _Universe;
		/*
		 *area creation
		 */
		creationArea = new GCreationArea(_ascii_file,this);
		tabArea = new GTabArea(this, _ascii_file);
		listArea = new GConnectionsList();
		listArea.attachAttributeList(tabArea.attributes_list);
		this.universe.addSelectionBehavior(tabArea.attributes_list);
		/*
		 *	areas addition
		 */
		add(tabArea);
		creationArea.setLayout(new GridLayout(1,3));
		creationArea.add(listArea,0);
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
			if( component instanceof GNode ){
				GNode node = (GNode) component;
				tabArea.attributes_list.add(node);
			}else if( component instanceof GLink ){
				GLink link = (GLink) component;
				tabArea.attributes_list.add(link);
			}else {
				BadElementTypeException e = new BadElementTypeException(component.toString());
				e.printStackTrace();
				e.showError();
			}
			tabArea.remove.setEnabled(true);
			tabArea.remove_all.setEnabled(true);
			tabArea.unselect.setEnabled(true);
			tabArea.unselect_all.setEnabled(true);
		}//if tabArea.elements.indexOf(component) == -1
	}//addComponent

	/**
	 * loads a ascii file where are defined some graph elements types
	 * and refreshes types into tabs. 
	 */
	public void loadTypes(String filename){
		creationArea.loadTypes(filename);
		GTab.setTableTypes(creationArea.getTableTypes());
		int count = tabArea.attributes_list.getTabCount();
		for(int i=0;i<count;i++){
			GTab tab = (GTab) tabArea.attributes_list.getComponentAt(i);
			tab.refreshType();
		}
	}

	/**
	 * replaces the old GGrapheUniverse by a new GGrapheUniverse.
	 * It will be associated to the GEditor.
	 */
	public void setUniverse(GGrapheUniverse _universe){
		this.universe = _universe;
		tabArea.attributes_list.setUniverse(_universe);
	}

	/**
	 * returns the GGrapheUniverse which is associated to the GEditor.
	 * @return
	 * 		the associated GGrapheUniverse
	 */
	public GGrapheUniverse getUniverse(){
		return universe;
	}

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
			if(button.isEnabled()){
				if(button == tabArea.remove){
					GTab tab = (GTab) tabArea.attributes_list.getSelectedComponent();
					Object component = tab.getElement();
					if(component instanceof GNode){
						removeNode((GNode) component);
					}else{ // component instanceof GLink
						universe.deleteGLink(((GLink)component).getName());
					}
					tabArea.attributes_list.remove(component);
					tabArea.attributes_list.checkAttachedButtons();
				}else if(button == tabArea.remove_all){
					LinkedList<Object> elements = tabArea.attributes_list.getElements();
					for(int i=0; i<elements.size();i++)
						if(elements.get(i) instanceof GNode)
							removeNode((GNode) elements.get(i));
						else
							universe.deleteGLink(((GLink)elements.get(i)).getName());
					tabArea.attributes_list.removeAll();
					/*
					 * we check if there is at least one element in the list
					 * of course no
					 */
					tabArea.attributes_list.checkAttachedButtons();
				}else if(button == tabArea.unselect){
					unselect();
					/*
					 * we check if there is at least one element in the list
					 */
					tabArea.attributes_list.checkAttachedButtons();
				}else if(button == tabArea.unselect_all){
					unselectAll();
					/*
					 * we check if there is at least one element in the list
					 * of course no
					 */
					tabArea.attributes_list.checkAttachedButtons();
				}else if(button == creationArea.button_arrow){
					JComboBox combo = creationArea.combo_arrow;
					String type_of_links  = (String) combo.getSelectedItem();
					if(tabArea.attributes_list.getNodeCount()==1){
						LinkedList<Association> list = new LinkedList<Association>();
						GNode node = (GNode) tabArea.attributes_list.getElements().get(0);
						list.add(new Association(node, node));
						GPopup.createLink(GEditor.this,type_of_links, true, list);
					}else{
						GNode[]nodes = new GNode[tabArea.attributes_list.getNodeCount()];
						for(int i=0; i<nodes.length;i++)
							nodes[i] = (GNode) tabArea.attributes_list.getElements().get(i);
						GPopup.showPopup(GEditor.this, nodes, GPopup.ARROW, type_of_links);
					}
				}else if(button == creationArea.button_bridge){
					JComboBox combo = creationArea.combo_bridge;
					String type_of_links = (String) combo.getSelectedItem();

					GNode[]nodes = new GNode[tabArea.attributes_list.getNodeCount()];
					for(int i=0; i<nodes.length;i++)
						nodes[i] = (GNode) tabArea.attributes_list.getElements().get(i);
					GPopup.showPopup(GEditor.this, nodes, GPopup.BRIDGE, type_of_links);
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
						while( ! good ){
							j++;
							node.setName(name+j);
							good = universe.getGraph().addNode(node);
						}//while
						universe.getGraph().removeNode(node.getName());
						/*
						 * associating the radius to the node
						 */
						float radius = ( (Double) creationArea.radius.getValue() ).floatValue();
						node.setRadius(radius);
						/*
						 * adding the node into the application
						 */
						addComponent(node, true);
						universe.addGNode(node);
						tabArea.refreshList();
						/*
						 * refreshing state of action buttons
						 */
						tabArea.attributes_list.checkAttachedButtons();
					}catch (NoSuchMethodException e) {
						GException e1 = new GException("vous devez déclarer un constructeur :\npublic "
								+node_class.getCanonicalName()
								+" (String, float, float, float)");
						e1.printStackTrace();
						e1.showError();
					}catch (SecurityException e) {
						GException e1 = new GException("un problème est survenu lors de l'accès au constructeur :\npublic "
								+node_class.getCanonicalName()
								+" (String, float, float, float)\nvérifiez que ce constructeur est public et accessible");
						e1.printStackTrace();
						e1.showError();
					}catch (Exception e) {
						e.printStackTrace();
						System.exit(1);
						/*
						 * we mustn't go here
						 */
					}//try
				}//if
			}
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
	 * 	- if you agree the node will be removed, and the associated links as well.
	 * 	- and if you disagree nothing will be done.
	 * if the node is associated to no link, it will be remove without warning.
	 */
	void removeNode(GNode node){
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
						try {
							link.getSecondNode().removeLink(link);
						} catch (GException e) {
							e.printStackTrace();
							e.showError();
						}
						else
							try {
								link.getFirstNode().removeLink(link);
							} catch (GException e) {
								e.printStackTrace();
								e.showError();
							}
				}//for
				universe.deleteGNode(node.getName());
				tabArea.unselectlinks(list_links);
			}//if
		}else{
			universe.deleteGNode(node.getName());
			tabArea.attributes_list.getNodeCount();
		}//if
	}// remove

	/**
	 * this inner class is used perform the actions which are associated to the GEditor
	 * application. 
	 */
	static class EditorListener extends WindowAdapter implements ActionListener{

		public void windowClosing(WindowEvent w){
			exit();
		}

		/*
		 * the file path and the file name
		 */
		String FILEBASE="", FILENAME="";

		/*
		 * the menus in the frame
		 */
		JMenuItem load_ascii, load_graph, save, save_as, exit, new_graph;

		/*
		 * the GEditor of the frame
		 */
		GEditor editor;

		/*
		 * the JFrame owner
		 */
		JFrame owner;

		public void actionPerformed(ActionEvent a){
			JMenuItem item = (JMenuItem) a.getSource();
			if(item == load_ascii){
				JFileChooser chooser = new JFileChooser(FILEBASE);
				int returnVal = chooser.showDialog(this.owner, "chargement");
				if(returnVal == JFileChooser.APPROVE_OPTION){
					FILEBASE = chooser.getSelectedFile().getParent();
					String filename = chooser.getSelectedFile().getAbsolutePath();
					editor.loadTypes(filename);
				}
			}else if(item == load_graph){
				JFileChooser chooser = new JFileChooser(FILEBASE);
				chooser.setFileFilter(new FiltreFichier("fichier de graphe (.xml)",new String[]{"xml"}));
				int returnVal = chooser.showDialog(this.owner, "chargement");
				if(returnVal == JFileChooser.APPROVE_OPTION){
					FILEBASE = chooser.getSelectedFile().getParent();
					FILENAME = chooser.getSelectedFile().getName();
					try{
						String path = FILEBASE+System.getProperty("file.separator")+FILENAME;
						GParser parser = new GParser(path);
						this.editor.getUniverse().setGraph(parser.getGraph());
					}catch (GException e) {
						e.printStackTrace();
						e.showError();
					}//try
				}//if
			}else if(item == save){
				save();
			}else if(item == save_as){
				saveAs();
			}else if(item == new_graph){
				this.editor.universe.setGraph(new GGraph());
				this.FILENAME="";
			}else if(item == exit){
				exit();
			}//if item == ...
		}//

		/**
		 * performs the "Save" functionnality of the menu bar
		 * opening a file chooser only if no name has been specified
		 * otherwise it will save the graph with the last specified name
		 */
		void save(){
			if(FILENAME.equals("")) saveAs();
			else
				try{
					String path = FILEBASE+System.getProperty("file.separator")+FILENAME;
					GParser parser = new GParser(editor.getUniverse().getGraph(),path);
					parser.saveGraph();
				}catch (GException e) {
					e.printStackTrace();
					e.showError();
					System.exit(1);
				}//try
		}

		/**
		 * performs the "Save as" functionnality of the menu bar
		 * opening a file chooser
		 */
		void saveAs(){
			JFileChooser chooser = new JFileChooser(FILEBASE);
			chooser.setFileFilter(new FiltreFichier("fichier de graphe (.xml)",new String[]{"xml"}));
			int returnVal = chooser.showDialog(this.owner, "sauvegarde");
			if(returnVal == JFileChooser.APPROVE_OPTION){
				this.FILEBASE = chooser.getSelectedFile().getParent();
				this.FILENAME = chooser.getSelectedFile().getName();
				/*
				 * if there is no ".xml" suffix we set it
				 */
				if(!FILENAME.endsWith(".xml")) FILENAME+=".xml";
				try{
					String path = FILEBASE+System.getProperty("file.separator")+FILENAME;
					GParser parser = new GParser(editor.getUniverse().getGraph(),path);
					parser.saveGraph();
				}catch (GException e) {
					e.printStackTrace();
					e.showError();
					System.exit(1);
				}//try
			}//if
		}

		/**
		 * performs the exiting of the application
		 * it ask you if you want to save your work before exiting
		 * you have 3 possibilities : "yes", "no" and "cancel" (which does no close the application)
		 */
		void exit(){
			boolean exit = true;
			Object[] options = new Object[] {"YES", "NO", "CANCEL"};
			int  result = JOptionPane.showOptionDialog(null, "do you want to save your graph ?", "save resource", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			switch(result){
			case 0:
				save();
				break;
			case 2:
				exit = false;
				break;
			}
			if(exit) System.exit(0);
		}

	}//inner class

}//class GEditor
