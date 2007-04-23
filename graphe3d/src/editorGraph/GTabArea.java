package editorGraph;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import editorGraph.GEditor.ButtonListener;
import graph3d.elements.GLink;
import graph3d.elements.GNode;

/**
 * this class is used to implement the tabs area in the graph editor.<br>
 * there is here :<br>
 * - unselect and remove buttons,<br>
 * - the tabbed pane containing all the selections<br>
 * 
 * this class is a package class
 * 
 * @author lino christophe
 *
 */
class GTabArea extends JPanel{

	/*
	 * the GEditor owner
	 */
	GEditor editor;

	/*
	 * the tabbedpane : contains all the selection tabs
	 */
	JTabbedPane tabbedpane;

	/*
	 * the whole list of selected graph elements
	 */
	LinkedList<Object> elements;

	/*
	 * the number of selected nodes
	 */
	int nb_nodes;

	/*
	 * two buttons for unselecting all or only the current element from the tabbedpane
	 * two buttons for removing all or only the current element from y=the graph
	 */
	JButton unselect, unselect_all, remove, remove_all;

	/*
	 * margins between graphic components
	 */
	private final Insets 
	COMPONANT_INSETS=new Insets(3,3,3,3);

	/*
	 * placement constraints of graphic components
	 */
	private final double button_weighty = 0.5;
	private final GridBagConstraints
	UNSELECT_CONSTRAINTS 
	= new GridBagConstraints(0,1,1,1,40,button_weighty,GridBagConstraints.CENTER, GridBagConstraints.BOTH,COMPONANT_INSETS,1,1),
	UNSELECT_ALL_CONSTRAINTS
	= new GridBagConstraints(1,1,1,1,60,button_weighty,GridBagConstraints.CENTER, GridBagConstraints.BOTH,COMPONANT_INSETS,1,1),
	REMOVE_CONSTRAINTS
	= new GridBagConstraints(0,0,1,1,40,button_weighty,GridBagConstraints.CENTER, GridBagConstraints.BOTH,COMPONANT_INSETS,1,1),
	REMOVE_ALL_CONSTRAINTS
	= new GridBagConstraints(1,0,1,1,GridBagConstraints.REMAINDER,button_weighty,GridBagConstraints.CENTER, GridBagConstraints.BOTH,COMPONANT_INSETS,1,1),
	TABBEDPANE_CONSTRAINTS
	= new GridBagConstraints(0,2,2,1,100,100-2*button_weighty,GridBagConstraints.CENTER, GridBagConstraints.BOTH,COMPONANT_INSETS,1,1);

	/**
	 * construct a new area to put into an editor. 
	 */
	public GTabArea(GEditor owner) {
		this.editor = owner;
		/*
		 * nommage de la zone.
		 */
		setBorder(new TitledBorder(new EtchedBorder(),"Elements sélectionnés"));
		/*
		 * initialisation
		 */
		elements = new LinkedList<Object>();
		nb_nodes = 0;
		/*
		 * création des boutons
		 */
		unselect = new JButton("déselectionner");
		unselect_all = new JButton("déselectionner tout");
		remove = new JButton("supprimer");
		remove_all = new JButton("supprimer tout");
		/*
		 * modification des marges des boutons.
		 * ajout d'un curseur main.
		 * état initial : désactivés.
		 */
		unselect.setMargin(GEditor.BUTTON_INSETS);
		unselect_all.setMargin(GEditor.BUTTON_INSETS);
		remove.setMargin(GEditor.BUTTON_INSETS);
		remove_all.setMargin(GEditor.BUTTON_INSETS);

		unselect.setCursor(new Cursor(Cursor.HAND_CURSOR));
		unselect_all.setCursor(new Cursor(Cursor.HAND_CURSOR));
		remove.setCursor(new Cursor(Cursor.HAND_CURSOR));
		remove_all.setCursor(new Cursor(Cursor.HAND_CURSOR));

		remove.setEnabled(false);
		remove_all.setEnabled(false);
		unselect.setEnabled(false);
		unselect_all.setEnabled(false);
		/*
		 * création des onglets. 
		 */
		tabbedpane = new JTabbedPane();
		/*
		 * placement.
		 */
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		/*
		 * components placement
		 */
		gbl.addLayoutComponent(unselect,UNSELECT_CONSTRAINTS);
		gbl.addLayoutComponent(unselect_all,UNSELECT_ALL_CONSTRAINTS);
		gbl.addLayoutComponent(remove,REMOVE_CONSTRAINTS);
		gbl.addLayoutComponent(remove_all,REMOVE_ALL_CONSTRAINTS);
		gbl.addLayoutComponent(tabbedpane,TABBEDPANE_CONSTRAINTS);
		add(unselect);
		add(unselect_all);
		add(remove);
		add(remove_all);
		add(tabbedpane);
		/*
		 * actions adding
		 */
		tabbedpane.addMouseListener(new TabbedPaneListener());

		ButtonListener buttonListener = editor.new ButtonListener();
		unselect.addMouseListener(buttonListener);
		unselect_all.addMouseListener(buttonListener);
		remove.addMouseListener(buttonListener);
		remove_all.addMouseListener(buttonListener);
	}//construct

	/**
	 * this methods return the whole list of selected nodes as an array.
	 */
	public GNode[] getNodes(){
		if(elements.size()==0)return null;
		else return elements.toArray(new GNode[0]);
	}//getNodes

	/**
	 * this method is used to update the list of associated elements
	 * of the current editor.<br>
	 */
	void refreshList(){
		GTab tab = (GTab) tabbedpane.getSelectedComponent();
		if( tab != null ){
			Object element = tab.getElement();
			if( element instanceof GNode){
				GNode node  = (GNode) element;
				GLink[]links = node.getLinks().toArray(new GLink[0]);
				String[]names = new String[links.length];
				for(int i=0; i<names.length; i++) names[i] = links[i].getName();
				editor.listArea.show(links);
			}else{
				GLink link  = (GLink) element;
				GNode[] nodes = new GNode[]{link.getFirstNode(),link.getSecondNode()};
				editor.listArea.show(nodes);
			}//if
		}else{
			editor.listArea.show(new Object[0]);
		}
	}//refreshList
	
	/**
	 * this method is used to remove a list of links from the selection.
	 * @param list_links
	 * LinkedList : the list of links to remove
	 */
	void unselectlinks(LinkedList<GLink> list_links){
		for(int i=0;i<list_links.size();i++){
			int idx = elements.indexOf(list_links.get(i));
			if(idx!=-1){
				tabbedpane.remove(idx);
				elements.remove(idx);
			}
		}//for
	}//unselectLinks

	/**
	 * this internal class is used to refresh the list of associated elements when a
	 * click is performed on a tab.
	 *  
	 * @author lino christophe
	 */
	class TabbedPaneListener extends MouseAdapter{

		public void mouseClicked(MouseEvent m){
			refreshList();
		}//mouseClicked

	}//inner class TabbedPaneListener

}//inner class GTabArea