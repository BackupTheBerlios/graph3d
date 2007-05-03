package visualizator;

import graph3d.elements.GGraph;
import graph3d.exception.GException;
import graph3d.parserXML.GParser;
import graph3d.universe.GGrapheUniverse;
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
public class GGrapheVisualizator extends JFrame implements ActionListener, WindowListener {

	JPanel view3d;
	PanelCaracteristique gLists;
	PanelButtonInteraction move;
	JPanel interaction;
	GGrapheUniverse universe;
	JMenuItem load;
	JMenuItem quit;
	JMenuBar menu;
	JMenu file;
	
	
	public GGrapheVisualizator() {
		super("Visualizator");
		
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		
		this.universe = new GGrapheUniverse(new GGraph());

		this.menu = new JMenuBar();
		this.file = new JMenu("File");
		this.load = new JMenuItem("Load");
		this.load.addActionListener(this);
		this.file.add(this.load);
		this.quit = new JMenuItem("Quit");
		this.quit.addActionListener(this);
		this.file.add(this.quit);
				
		this.menu.add(this.file);					
		this.setJMenuBar(this.menu);

		this.setLayout(new BorderLayout());
		
		this.createInteraction();
		this.createView3d();
		

		this.addWindowListener(this);
		this.setSize(750, 600);
		this.setVisible(true);
		
	}
	
	public void createView3d() {
		JPanel panelView3d = new JPanel();
		panelView3d.setLayout(new BorderLayout());
		TitledBorder borderCentre = new TitledBorder(new EtchedBorder(),"Scène 3D");
		panelView3d.setBorder(borderCentre);
		panelView3d.add(this.universe.getCanvas());
		this.add(panelView3d,BorderLayout.CENTER);
	}
	
	public void createInteraction() {
		this.interaction = new JPanel();
		this.interaction.setLayout(new GridLayout(2, 1));
		this.createGLists();
		this.createMove();
		this.add(this.interaction, BorderLayout.EAST);
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
		new GGrapheVisualizator();
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
					
					universe.setGraph(new GParser(path+File.separator+fileName).getGraph());

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