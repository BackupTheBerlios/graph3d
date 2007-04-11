package EditorGraph;

import graph3D.elements.GLink;
import graph3D.elements.GNode;
import graph3D.exception.BadElementTypeException;
import graph3D.universe.GView;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GEditor extends JPanel{

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(260,630);
		f.setLocation(100,100);
		f.setVisible(true);
		f.setTitle("éditeur de graphe 3D");
		GEditor editor = new GEditor();
		f.add(editor);
//		try{
//			editor.addComponent(new GNode("blabla"), true);
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
		
	}//main
	
	/*
	 * la vue associée
	 */
	private GView view;

	/*
	 * les zones de l'éditeur
	 */
	protected GTabArea tabArea;
	private GListArea listArea;
	private GCreationArea creationArea;

	/*
	 * marges
	 */
	private static final Insets
		BUTTON_INSETS = new Insets(2,2,2,2),
		EDITOR_INSETS = new Insets(1,1,1,1);
	
	/*
	 * les contraintes de positionnement des parties.
	 */
	private GridBagConstraints
	/*
	 * int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, 
	 * int anchor, int fill, Insets insets, int ipadx, int ipady 
	 */
	TAB_AREA_CONSTRAINTS = new GridBagConstraints(0,0,1,1,100,55,GridBagConstraints.CENTER, GridBagConstraints.BOTH,EDITOR_INSETS,1,1),
	LIST_AREA_CONSTRAINTS = new GridBagConstraints(0,1,1,1,100,40,GridBagConstraints.CENTER, GridBagConstraints.BOTH,EDITOR_INSETS,1,1),
	CREATE_AREA_CONSTRAINTS = new GridBagConstraints(0,2,1,1,100,5,GridBagConstraints.CENTER, GridBagConstraints.BOTH,EDITOR_INSETS,1,1);


	

	/**
	 * 
	 */
	public GEditor() {
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
		 * creéation des zones
		 */
		tabArea = new GTabArea();
		listArea = new GListArea(new JList());
		creationArea = new GCreationArea("C:\\Documents and Settings\\lino christophe\\Mes documents\\IUP MIS\\L3 INFO\\INF1603 - graphe3d\\CODAGE\\fichiertype.txt");
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
	public void addComponent(Object component, boolean focus) throws BadElementTypeException{

		if( component instanceof GNode || component instanceof GLink ){
			Class cl = component.getClass();
			/*
			 * créer une nouvelle sélection
			 */
		}else{
			throw new BadElementTypeException();
		}//if

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

	/**
	 * déselectionner tous les éléments (à voir si on la fait)
	 *
	 */
	public void unselectAll(){

		/*
		 * enlever tous les éléments séléctionnés.
		 */

	}//unselectAll

	/**
	 * 
	 * @author lino christophe
	 *
	 */
	private class GTabArea extends JPanel{
		/*
		 * les onglets.
		 */
		public JTabbedPane tabbedpane;

		/*
		 * nombre de noeuds sélectionnés.
		 */
		public LinkedList<GNode> nodes;

		/*
		 * boutons pour la déselection de l'élément courant des onglets, tous les onglets.
		 * boutons pour la suppression de l'élément courant des onglets, tous les onglets.
		 */
		private JButton unselect, unselect_all, remove, remove_all;

		/*
		 * marges entre les composants, et à l'intérieur des boutons.
		 */
		private final Insets 
		COMPONANT_INSETS=new Insets(3,3,3,3);

		/*
		 * contraintes de placement des composants.
		 */
		private final double button_weighty=0.5;
		private final GridBagConstraints
		UNSELECT_CONSTRAINTS = new GridBagConstraints(0,1,1,1,40,button_weighty,GridBagConstraints.CENTER, GridBagConstraints.BOTH,COMPONANT_INSETS,1,1),
		UNSELECT_ALL_CONSTRAINTS = new GridBagConstraints(1,1,1,1,60,button_weighty,GridBagConstraints.CENTER, GridBagConstraints.BOTH,COMPONANT_INSETS,1,1),
		REMOVE_CONSTRAINTS = new GridBagConstraints(0,0,1,1,40,button_weighty,GridBagConstraints.CENTER, GridBagConstraints.BOTH,COMPONANT_INSETS,1,1),
		REMOVE_ALL_CONSTRAINTS = new GridBagConstraints(1,0,1,1,GridBagConstraints.REMAINDER,button_weighty,GridBagConstraints.CENTER, GridBagConstraints.BOTH,COMPONANT_INSETS,1,1),
		TABBEDPANE_CONSTRAINTS = new GridBagConstraints(0,2,2,1,100,100-2*button_weighty,GridBagConstraints.CENTER, GridBagConstraints.BOTH,COMPONANT_INSETS,1,1);

		/**
		 * 
		 */
		public GTabArea() {
			/*
			 * nommage de la zone.
			 */
			setBorder(new TitledBorder(new EtchedBorder(),"Elements sélectionnés"));
			/*
			 * initialisation
			 */
			nodes = new LinkedList<GNode>();
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
			unselect.setMargin(BUTTON_INSETS);
			unselect_all.setMargin(BUTTON_INSETS);
			remove.setMargin(BUTTON_INSETS);
			remove_all.setMargin(BUTTON_INSETS);
			
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
			 * placement des composants.
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
			 * ajout des actions
			 */
			GButtonListener buttonListener = new GButtonListener();
			unselect.addMouseListener(buttonListener);
			unselect_all.addMouseListener(buttonListener);
			remove.addMouseListener(buttonListener);
			remove_all.addMouseListener(buttonListener);
		}//construct

		/**
		 * 
		 */
		public GNode[] getNodes(){
			if(nodes.size()==0)return null;
			else return nodes.toArray(new GNode[0]);
		}//getNodes

	}//inner class GTabArea

	/**
	 * 
	 * @author lino christophe
	 *
	 */
	private class GListArea extends JScrollPane{

		/*
		 * zone de la liste de sélection avec les éléments associés à l'onglet courant.
		 */
		public JList list;

		/**
		 * 
		 */
		public GListArea(JList list) {
			/*
			 * création à partir d'une JList vide,
			 * et récupération de celle-ci.
			 */
			super(list);
			this.list= list;
			list.addMouseListener(new GListListener());
			list.setVisibleRowCount(7);
			/*
			 * nommage de la zone
			 */
			setBorder(new TitledBorder(new EtchedBorder(),"Elements associés"));
		}//construct

		/**
		 * place des éléments de même type dans la liste (doivent être soit des GNode, soit des GLink).
		 * @param components
		 * @throws BadElementTypeException
		 */
		public void show(Object[] components) throws BadElementTypeException{
			if(!(components instanceof GNode[]) && !(components instanceof GLink[]))
				throw new BadElementTypeException();
			else
				list.setListData(components);
		}//show

		class GListListener extends MouseAdapter{

			public void mouseClicked(MouseEvent m){

			}//mouseClicked

		}//inner class GListListener

	}//inner class GListArea

	/**
	 * 
	 * @author lino christophe
	 *
	 */
	private class GCreationArea extends JPanel{

		/*
		 * 2 boutons pour la création d'un (plusieurs) lien(s).
		 * 1 bouton pour la création d'un noeud.
		 */
		JButton
			button_arrow,
			button_bridge,
			button_node;
		/*
		 * choix déroulants des types
		 */
		JComboBox
			combo_arrow,
			combo_bridge,
			combo_node;
		
		/*
		 * champs formatés (double) pour les coordonnées
		 */
		JSpinner[]
			coord_values=new JSpinner[3];
		
		/*
		 * tables de hachage contenant les types connus.
		 */
		public Hashtable<String, Class>
		table_link = new Hashtable<String, Class>(),
		table_node   = new Hashtable<String, Class>();

		public GCreationArea(String file){
			/*
			 * création de 2 zones
			 */
			JPanel links = new JPanel(), nodes = new JPanel();
			/*
			 * nommage des zones
			 */			
			links.setBorder(new TitledBorder(new EtchedBorder(),"liens"));
			nodes.setBorder(new TitledBorder(new EtchedBorder(),"noeuds"));
			/*
			 * initialisation des éléments
			 */
			button_arrow = new JButton("créer arc(s)");
			button_bridge = new JButton("créer arête(s)");
			button_node = new JButton("créer noeud");
			button_arrow.setMargin(BUTTON_INSETS);
			button_bridge.setMargin(BUTTON_INSETS);
			button_node.setMargin(BUTTON_INSETS);

			combo_arrow = new JComboBox();
			combo_arrow.setMaximumRowCount(5);
			combo_bridge = new JComboBox();
			combo_bridge.setMaximumRowCount(5);
			combo_node = new JComboBox();
			combo_node.setMaximumRowCount(5);

			JLabel
				x_label = new JLabel("x  ",SwingConstants.RIGHT),
				y_label = new JLabel("y  ",SwingConstants.RIGHT),
				z_label = new JLabel("z  ",SwingConstants.RIGHT);
	
				for(int i=0;i<3;i++)coord_values[i] = new JSpinner(new SpinnerNumberModel(0,-1*Double.MAX_VALUE,Double.MAX_VALUE,0.1));			
			/*
			 * chargement des types
			 */
			loadTypes(file);
			/*
			 * mise en place des types dans l'éditeur
			 */
			Object[]arrow_types = table_link.keySet().toArray();
			if(arrow_types.length==0){
				button_arrow.setEnabled(false);
				combo_arrow.setEnabled(false);
			}else
				for(int i=0;i<arrow_types.length;i++) combo_arrow.addItem(arrow_types[i]);
			
			Object[]bridge_types = table_link.keySet().toArray();
			if(bridge_types.length==0){
				button_bridge.setEnabled(false);
				combo_bridge.setEnabled(false);
			}else
				for(int i=0;i<bridge_types.length;i++) combo_bridge.addItem(bridge_types[i]);
			
			Object[]node_types = table_node.keySet().toArray();
			if(node_types.length==0){
				button_node.setEnabled(false);
				combo_node.setEnabled(false);
			}else
				for(int i=0;i<node_types.length;i++) combo_node.addItem(node_types[i]);
			/*
			 * placement
			 */
			setLayout(new GridLayout(1,2));
			links.setLayout(new GridLayout(5,1));
			nodes.setLayout(new GridLayout(5,1));
			/*
			 * ajout des éléments
			 */
			links.add(button_arrow);
			links.add(combo_arrow);
			links.add(button_bridge);
			links.add(combo_bridge);
			links.add(new JPanel());

			nodes.add(button_node);
			nodes.add(combo_node);
			JPanel coord[] = new JPanel[]{new JPanel(),new JPanel(),new JPanel()};
			for(int i=0;i<3;i++){
				coord[i].setLayout(new GridLayout(1,2));
				nodes.add(coord[i]);	
			}//for
			coord[0].add(x_label);
			coord[0].add(coord_values[0]);
			coord[1].add(y_label);
			coord[1].add(coord_values[1]);
			coord[2].add(z_label);
			coord[2].add(coord_values[2]);

			add(links);
			add(nodes);
			/*
			 * ajout des actions
			 */
			GButtonListener buttonListener = new GButtonListener();
			button_arrow.addMouseListener(buttonListener);
			button_bridge.addMouseListener(buttonListener);
			button_node.addMouseListener(buttonListener);
			
		}//construct

		private void loadTypes(String filename){
			File file = new File(filename);
			try{
				BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line;
				int line_count=0;
				while((line = buf.readLine())!=null){
					line_count++;
					String data[] = line.split(":");
					try{
						if(Class.forName(data[1]).newInstance() instanceof GLink){
							table_link.put(data[0],Class.forName(data[1]));
						}else if(Class.forName(data[1]).newInstance() instanceof GNode){
							table_node.put(data[0],Class.forName(data[1]));
						}else{
							throw new BadElementTypeException();
							/*
							 * 
							 */
						}//if
					}catch (BadElementTypeException e) {
						System.err.println("line "+line_count+" \""+data[1]+"\" : this class is not an instance of a graph element");
					}catch (Exception e) {
						System.err.println("line "+line_count+" \""+data[1]+"\" : this class does not exist or is not accessible");
					}
				}//while
			}catch (FileNotFoundException e) {
				/*
				 * 
				 */
			}catch (IOException e) {
				/*
				 * 
				 */
			}//try

		}//loadTypes

	}//inner class GCreationArea
	
	class GButtonListener extends MouseAdapter{
		
		public void mouseClicked(MouseEvent m){
			JButton button = (JButton) m.getSource();
			if(button == tabArea.remove){
				/*
				 * 
				 */
			}else if(button == tabArea.remove_all){
				/*
				 * 
				 */
			}else if(button == tabArea.unselect){
				GTab tab = (GTab)tabArea.tabbedpane.getSelectedComponent();
				if(tabArea.tabbedpane.getSelectedIndex()<=tabArea.nodes.size())
					tabArea.nodes.remove(tabArea.tabbedpane.getSelectedIndex());
				tabArea.tabbedpane.remove(tabArea.tabbedpane.getSelectedIndex());
			}else if(button == tabArea.unselect_all){
				tabArea.tabbedpane.removeAll();
				tabArea.nodes = new LinkedList<GNode>();
			}else if(button == creationArea.button_arrow){
				String type = (String) creationArea.combo_arrow.getSelectedItem();
				GNode[]nodes = tabArea.nodes.toArray(new GNode[0]);
				/*
				 * 
				 */
			}else if(button == creationArea.button_bridge){
				String type = (String) creationArea.combo_bridge.getSelectedItem();
				GNode[]nodes = tabArea.nodes.toArray(new GNode[0]);
				/*
				 * 
				 */
			}else if(button == creationArea.button_node){
				String type = (String) creationArea.combo_node.getSelectedItem();
				/*
				 * 
				 */
			}//if
			if(tabArea.tabbedpane.getComponentCount()==0){
				tabArea.remove.setEnabled(false);
				tabArea.remove_all.setEnabled(false);
				tabArea.unselect.setEnabled(false);
				tabArea.unselect_all.setEnabled(false);
			}
		}//mouseClicked
		
	}//inner class GButtonListener

}//class GEditor
