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
import graph3d.exception.BadElementTypeException;
import graph3d.exception.InexistantClassException;

/**
 * this class is used to implement the creation area of a graph editor.<br>
 * there is in this area two areas for node and link adding in the associated graph.<br>
 * <br>
 * this class is a package class
 * 
 * @author lino christophe
 *
 */
class GCreationArea extends JPanel{

	/*
	 * the editor owner
	 */
	GEditor editor;

	/*
	 * two buttons for link(s) creation.
	 * one button for node creation.
	 */
	JButton
	button_arrow,
	button_bridge,
	button_node;
	/*
	 * types pulldown menu
	 */
	JComboBox
	combo_arrow,
	combo_bridge,
	combo_node;

	/*
	 * formatted fileds (double) for coordonates
	 */
	JSpinner[]
	         coord_values=new JSpinner[3];

	/*
	 * hash tables contening all known types
	 */
	public Hashtable<String, Class>
	table_link = new Hashtable<String, Class>(),
	table_node   = new Hashtable<String, Class>();

	/**
	 * constructs a creation area to put into an editor
	 * @param file
	 * 		String : the name of the file containing all the known classes which implements graph elements
	 * @param owner
	 * 		GEditor : the editor owner
	 */
	public GCreationArea(String file, GEditor owner){
		this.editor = owner;
		/*
		 * creation of 2 separated areas for links and for nodes
		 */
		JPanel links = new JPanel(), nodes = new JPanel();
		/*
		 * areas namming
		 */			
		links.setBorder(new TitledBorder(new EtchedBorder(),"liens"));
		nodes.setBorder(new TitledBorder(new EtchedBorder(),"noeuds"));
		/*
		 * elements initialization
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
		 * loading types
		 */
		loadTypes(file);
		/*
		 * we put types in place into the pulldown menus
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
		 * we create a reversed table from the link types and node types
		 * and we send it to the GTab class
		 */
		Hashtable<Class, String> table_types = new Hashtable<Class, String>();
		Object[] types_link = table_link.keySet().toArray();
		Object[] types_node = table_node.keySet().toArray();

		for(int i=0;i<types_link.length;i++)
			table_types.put(table_link.get(types_link[i]), (String)types_link[i]);
		for(int i=0;i<types_node.length;i++)
			table_types.put(table_node.get(types_node[i]), (String)types_node[i]);

		GTab.setTableTypes(table_types);
		/*
		 * placement
		 */
		setLayout(new GridLayout(1,2));
		links.setLayout(new GridLayout(5,1));
		nodes.setLayout(new GridLayout(5,1));
		/*
		 * elements addition
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
		 * actions addition
		 */
		ButtonListener buttonListener = editor.new ButtonListener();
		button_arrow.addMouseListener(buttonListener);
		button_bridge.addMouseListener(buttonListener);
		button_node.addMouseListener(buttonListener);

	}//construct

	/**
	 * this method is used to load the known classes which implements graph elements from an ascii file
	 * @param filename
	 * 		String : the name of the file containing all the known classes which implements graph elements
	 */
	private void loadTypes(String filename){
		File file = new File(filename);
		try{
			BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			int line_count=0;
			/*
			 * reading all the lines of the source file
			 */
			while((line = buf.readLine())!=null){
				line_count++;
				String data[] = line.split(":");
				try{
					/*
					 * boolean to check if it is a graph element, that's to say if its type is a
					 * GNode or GLink (sub)type
					 */
					boolean graphe_element = false;
					Class cl = Class.forName(data[1]);
					while(cl!=null && !graphe_element){
						if(cl == GNode.class){
							graphe_element = true;
							/*
							 * we add it as a know node type
							 */
							table_node.put(data[0], Class.forName(data[1]));
						}else if(cl == GLink.class){
							graphe_element = true;
							/*
							 * we add it as a know link type
							 */
							table_link.put(data[0], Class.forName(data[1]));
						}else
							cl = cl.getSuperclass();
					}//while
					if(!graphe_element)
						(new BadElementTypeException(data[1])).showError(line_count);
				}catch (ClassNotFoundException e3){
					(new InexistantClassException(data[1])).showError(line_count);
				}//try
			}//while
		}catch (FileNotFoundException e) {
			System.err.println(filename+" : file not found !");
		}catch (IOException e) {
			System.err.println("an I/O error occurred while reading file : "+filename);	
		}//try

	}//loadTypes

}//inner class GCreationArea