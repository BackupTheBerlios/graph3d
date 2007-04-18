package editorGraph;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import editorGraph.GEditor.ButtonListener;
import graph3d.elements.GLink;
import graph3d.elements.GNode;

/**
 * 
 * @author lino christophe
 *
 */
class GCreationArea extends JPanel{

	/*
	 * the Geditor owner
	 */
	GEditor editor;
	
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

	public GCreationArea(String file, GEditor owner){
		this.editor = owner;
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
		button_arrow.setMargin(editor.BUTTON_INSETS);
		button_bridge.setMargin(editor.BUTTON_INSETS);
		button_node.setMargin(editor.BUTTON_INSETS);

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
		Object[]link_types = table_link.keySet().toArray();
		if(link_types.length==0){
			button_arrow.setEnabled(false);
			combo_arrow.setEnabled(false);
		}else
			for(int i=0;i<link_types.length;i++){
				combo_arrow.addItem(link_types[i]);
				combo_bridge.addItem(link_types[i]);
			}

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
		ButtonListener buttonListener = editor.new ButtonListener();
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
					boolean graphe_element = false;
					Class cl = Class.forName(data[1]);
					while(cl!=null && !graphe_element){
						if(cl == GNode.class){
							graphe_element = true;
							table_node.put(data[0], Class.forName(data[1]));
						}else if(cl == GLink.class){
							graphe_element = true;
							table_link.put(data[0], Class.forName(data[1]));
						}else
							cl = cl.getSuperclass();
					}//while
					if(!graphe_element)
						System.err.println("line "+line_count+" \""+data[1]+"\" : this class is not an instance of a graph element");
				}catch (ClassNotFoundException e3){
						System.err.println("line "+line_count+" \""+data[1]+"\" : this class does not exist or is not accessible");
				}//try
			}//while
		}catch (FileNotFoundException e) {
			System.err.println(filename+" : file not found !");
		}catch (IOException e) {
			System.err.println("an I/O error occurred while reading file : "+filename);	
		}//try

	}//loadTypes

}//inner class GCreationArea