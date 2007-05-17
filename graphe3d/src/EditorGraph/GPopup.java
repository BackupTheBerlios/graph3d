package editorGraph;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import graph3d.elements.GLink;
import graph3d.elements.GNode;

/*
 * GPopup.java
 *
 * Created on 8 avril 2007, 12:31
 */

/**
 * this class provides a pop-up window which shows you all the link creation possibilities given a selection.<br>
 * when you have to check all the links you want to create, you have to click on "ok" to start the links' creation.<br>
 * they will be added in the graph, and in the GEditor as well.<br>
 * <br>
 * this class is a package class
 * 
 * @author  lino christophe
 * @since JDK 1.5
 * @version 1.0
 */
class GPopup extends JDialog {

	/*
	 * boolean representing an "arrows" or "bridges" choice in the
	 * "showPopup" method (to help user) 
	 */
	public static final boolean ARROW = true, BRIDGE = false;
	
	/*
	 * the only GPopup window which can be created
	 */
	public static GPopup POPUP;
	
	/*
	 * if the current GPopup will have to create arrows (if true)
	 * or bridges (if false)
	 */
	private static boolean is_arrow;

	/*
	 * the associated editor
	 */
	private static GEditor editor;
	
	/*
	 * the name of the link type to implement
	 */
	private static String link_type;
	
	/*
	 * the list of associations between two (previously selected in  the editor) nodes
	 */
	private static LinkedList<Association> list;
	
	/*
	 * the mouse listeners to implements actions
	 */
	private static final MouseAdapter
	CHECKBOX_LISTENER = new CheckboxListener(),
	BUTTON_LISTENER = new ButtonListener();

	/*
	 * the "cancel" button
	 */
	private JButton cancel_button;

	/*
	 * the "ok" button, to validate the creation
	 */
	private JButton ok_button;

	/*
	 * the title of the tabbedpane which invite you to select the links you want
	 */
	private JLabel label;

	/*
	 * the tabbed paned which contains all the possibilities of links that could be created.
	 */
	private JTabbedPane tabbedPane;

	/**
	 * create a new GPopup window
	 *
	 */
	private GPopup() {
		super();
		cancel_button = new JButton("Annuler");
		ok_button = new JButton("Ok");

		cancel_button.addMouseListener(BUTTON_LISTENER);
		ok_button.addMouseListener(BUTTON_LISTENER);

		label = new JLabel("");
		tabbedPane = new JTabbedPane();

		setBounds(100,100,440,338);
		setLayout(null);
		setResizable(false);

		add(cancel_button);
		cancel_button.setBounds(350, 270, 69, 23);
		cancel_button.setMargin(new Insets(2,2,2,2));

		add(ok_button);
		ok_button.setBounds(300, 270, 45, 23);
		ok_button.setMargin(new Insets(2,2,2,2));

		label.setFont(new java.awt.Font("Tahoma", 1, 12));
		add(label);
		label.setBounds(10, 20, 310, 15);

		add(tabbedPane);
		tabbedPane.setBounds(10, 50, 410, 210);
		setModal(true);
	}

	/**
	 * put in the pop-up window all the possibilities of arrow between two selected nodes.
	 * @param nodes
	 * 		the nodes of the selection.
	 */
	private void setArrows(GNode[] nodes){
		setTitle("Fenêtre de création d'arcs");
		tabbedPane.removeAll();
		label.setText("veuillez sélectionner les arcs à créer :");
		for(int i=0;i<nodes.length;i++){
			JPanel panel = new JPanel(new GridLayout(nodes.length,1));
			for(int j=0;j<nodes.length;j++){
				GCheckbox checkbox = new GCheckbox(nodes[i],nodes[j],GCheckbox.ARROW);
				checkbox.addMouseListener(CHECKBOX_LISTENER);
				panel.add(checkbox);
			}
			JScrollPane scrollPane=new JScrollPane(panel);

			tabbedPane.addTab(nodes[i].getName(), scrollPane);
		}
		remove(tabbedPane);
		add(tabbedPane);
	}//

	/**
	 * put in the pop-up window all the possibilities of bridge between two selected nodes.
	 * @param nodes
	 * 		the nodes of the selection.
	 */
	private void setBridges(GNode[] nodes){
		setTitle("Fenêtre de création d'arêtes");
		tabbedPane.removeAll();
		label.setText("veuillez sélectionner les arêtes à créer :");
		for(int i=0;i<nodes.length;i++){
			JScrollPane scrollPane = new JScrollPane();
			JPanel panel = new JPanel(new GridLayout(nodes.length,1));
			for(int j=i;j<nodes.length;j++){
				GCheckbox checkbox = new GCheckbox(nodes[i],nodes[j],GCheckbox.BRIDGE);
				checkbox.setBounds(10, 10+20*(j-i), 123, 20);
				checkbox.addMouseListener(CHECKBOX_LISTENER);
				panel.add(checkbox);
			}
			scrollPane.setViewportView(panel);

			tabbedPane.addTab(nodes[i].getName(), scrollPane);

		}
		remove(tabbedPane);
		add(tabbedPane);
	}//

	/**
	 * show the pop-up window which is associated to a link creation request.
	 * @param editor
	 * 		the editor which resquests that creation
	 * @param nodes
	 * 		nodes which are selected as an array
	 * @param type
	 * 		flag to check if the created links must be arrows or bridges
	 * @param link_type
	 * 		the name which represents the link type
	 */
	public static void showPopup(GEditor editor, GNode[]nodes, boolean type, String link_type){
		if(POPUP==null)POPUP = new GPopup();
		list = new LinkedList<Association>();
		if(type) // ARROW
			POPUP.setArrows(nodes);
		else 	 // BRIDGE
			POPUP.setBridges(nodes);
		GPopup.link_type = link_type;
		GPopup.editor = editor;
		POPUP.setVisible(true);
	}//showPopup

	/**
	 * this inner class is used to implement the actions which are associated
	 * to a click on a checkbox :<br>
	 * - if the link is selected, it will be added in the list of links to create,
	 * - and if it is unselected, it will be remove from the list.
	 * 
	 * @author lino christophe
	 *
	 */
	static class CheckboxListener extends MouseAdapter{
		public void mouseClicked(MouseEvent m){
			GCheckbox checkbox = (GCheckbox) m.getSource();
			Association association = checkbox.getAssociation();
			if(checkbox.isSelected()) list.add(association);
			else list.remove(association);
		}
	}//listener

	/**
	 * this class is used to implement the actions which are associated to a click on
	 * the "Ok" button and the "Annuler" button.<br>
	 * If you click on "Ok", the links which have been selected will be created, and then added
	 * to the graph and to the editor as well.
	 * 
	 * @author lino christophe
	 *
	 */
	static class ButtonListener extends MouseAdapter{		
		public void mouseClicked(MouseEvent m){
			if(m.getButton() == MouseEvent.BUTTON1){
				JButton button = (JButton) m.getSource();

				if(button == POPUP.cancel_button)
					POPUP.setVisible(false);
				else if(button == POPUP.ok_button){
					createLink(GPopup.editor, link_type, is_arrow, list);
					POPUP.setVisible(false);
				}//if
			}//if

		}//mouseClicked

	}//listener

	/**
	 * enables to create some links with a specified type.
	 * @param link_type
	 * 		the name of the link type 
	 * @param arrow
	 * 		true to create arrows, and false to create bridges
	 * @param list
	 * 		list of associations between nodes 
	 */
	static void createLink(GEditor editor, String link_type, boolean arrow, LinkedList<Association> list){
		try{
			Object[] param = new Object[4];
			param[1] = "";
			param[0] = arrow;
			Class[] construct = new Class[]{boolean.class, String.class, GNode.class, GNode.class};
			for(int i=0;i<list.size();i++){
				param[2] = list.get(i).node_1;
				param[3] = list.get(i).node_2;

				Class link_class = editor.creationArea.table_link.get(link_type);
				GLink link = (GLink) link_class.getDeclaredConstructor(construct).newInstance(param);
				/*
				 * trying to associate a non-busy name for this link
				 */
				link.setName(link_type+"_");
				String name = link.getName();
				int j=1;
				link.setName(name+j);
				/*
				 * boolean to check if the name is not busy.
				 */
				boolean good = editor.universe.getGraph().addLink(link);
				while( ! good ){
					j++;
					link.setName(name+j);
					good = editor.universe.getGraph().addLink(link);
				}//while
				editor.universe.getGraph().removeLink(link.getName());
				editor.addComponent(link, true);
				editor.universe.addGLink(link);
				editor.tabArea.refreshList();
			}//for
		}catch (Exception e) {
			e.printStackTrace();
			/*
			 * we mustn't go here
			 */
		}//try
	}


	/**
	 * this class is used to represent an association between 2 nodes.
	 * 
	 * @author lino christophe
	 *
	 */
	public static class Association{
		GNode node_1,node_2;

		public Association(GNode _node_1, GNode _node_2) {
			this.node_1 = _node_1;
			this.node_2 = _node_2;
		}
	}

	/**
	 * this inner class is used to implement a checkbox which contains the association between the
	 * 2 nodes of the represented link. 
	 * 
	 * @author lino christophe
	 *
	 */
	class GCheckbox extends JCheckBox{
		static final String ARROW = "-->", BRIDGE = "--";
		Association association;

		public GCheckbox(GNode node_1,GNode node_2,String link){
			super(node_1.getName()+" "+link+" "+node_2.getName());
			association = new Association(node_1,node_2);
		}

		public Association getAssociation(){
			return association;
		}
	}





}
