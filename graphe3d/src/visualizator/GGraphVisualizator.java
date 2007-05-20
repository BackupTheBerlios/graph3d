package visualizator;

import graph3d.elements.GGraph;
import graph3d.exception.GException;
import graph3d.parserXML.GParser;
import graph3d.universe.GGraphUniverse;
import graph3d.use.PanelButtonInteraction;
import graph3d.use.PanelCaracteristique;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * This class is used to define a visualizator which allow you to see a graph with the API Graph3d
 * @author Erwan Daubert && Nicolas Magnin
 * @version 1.0
 */
public class GGraphVisualizator extends JFrame implements ActionListener, WindowListener {

	JPanel view3d;
	PanelCaracteristique gLists;
	PanelButtonInteraction move;
	JPanel interaction;
	GGraphUniverse universe;
	JMenuItem load;
	JMenuItem quit;
	JMenuBar menu;
	JMenu file;
	
	
	public GGraphVisualizator() {
		super("Visualizator");
		
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		this.universe = new GGraphUniverse(new GGraph());

		this.menu = new JMenuBar();
		this.file = new JMenu("Fichier");
		this.load = new JMenuItem("Charger");
		this.load.addActionListener(this);
		this.file.add(this.load);
		this.quit = new JMenuItem("Quitter");
		this.quit.addActionListener(this);
		this.file.add(this.quit);
				
		this.menu.add(this.file);					
		this.setJMenuBar(this.menu);

		this.setLayout(new BorderLayout());
		
		this.createInteraction();
		this.createView3d();

		this.add(this.view3d,BorderLayout.CENTER);
		this.add(this.interaction, BorderLayout.EAST);
		

		this.addWindowListener(this);
		this.setSize(750, 600);
		this.setVisible(true);
		
	}
	
	public void createView3d() {
		this.view3d = new JPanel();
		this.view3d.setLayout(new BorderLayout());
		TitledBorder borderCentre = new TitledBorder(new EtchedBorder(),"Scène 3D");
		this.view3d.setBorder(borderCentre);
		this.view3d.add(this.universe.getCanvas());
	}
	
	public void createInteraction() {
		this.interaction = new JPanel();
		this.interaction.setLayout(new GridLayout(2, 1));
		this.createGLists();
		this.createMove();
	}
	
	public void createGLists() {
		this.gLists = new PanelCaracteristique(this.universe);
		this.interaction.add(this.gLists);	
	}
	
	public void createMove() {
		this.move = new PanelButtonInteraction(this.universe);
		this.interaction.add(this.move);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GGraphVisualizator();
	}

	public void actionPerformed(ActionEvent e) {
		
		String fileName = null;
		String path = null;
		File file = null;
		
		if(e.getSource()==this.load){
			JFileChooser choixXML=new JFileChooser();
			
			//  Ouverture d'un fichier XML
			FiltreFichier xml = new FiltreFichier("Fichiers XML (*.xml)", new String[] {"xml"});
			choixXML.addChoosableFileFilter(xml);
			choixXML.setAcceptAllFileFilterUsed(false);
			int valRetour = choixXML.showOpenDialog(null);

			if (valRetour == JFileChooser.APPROVE_OPTION) {
				fileName = choixXML.getSelectedFile().getName();
				path = choixXML.getCurrentDirectory().toString();
				file = choixXML.getSelectedFile();
				try{
					GGraph tmp = this.universe.getGraph();
					GParser parser = new GParser(path+File.separator+fileName); 
					GGraph graph = parser.getGraph();
					if (parser.isValidXMLFile()) {
						universe.setGraph(graph);
					}

				}catch(GException ee){
					ee.printStackTrace();
					ee.showError();
				}
			}
			

		}
		if(e.getSource()==this.quit){	
			System.exit(0);
		}
	}

	public void windowActivated(WindowEvent e) {}

	public void windowClosed(WindowEvent e) {}

	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowDeactivated(WindowEvent e) {}

	public void windowDeiconified(WindowEvent e) {}

	public void windowIconified(WindowEvent e) {}

	public void windowOpened(WindowEvent e) {}
}