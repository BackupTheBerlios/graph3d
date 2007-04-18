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
 *
 * @author  lino christophe
 */
class GPopup extends JDialog {

	public static final boolean ARROW = true, BRIDGE = false;
	public static GPopup POPUP;
	private static boolean is_arrow;

	private static GEditor owner;
	private static Class link_class;
	private static String link_type;
	private static LinkedList<Association> list;
	private static final MouseAdapter
	CHECKBOX_LISTENER = new CheckboxListener(),
	BUTTON_LISTENER = new ButtonListener();

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private JButton cancel_button;
	private JButton ok_button;
	private JLabel label;
	private JTabbedPane tabbedPane;
	// End of variables declaration//GEN-END:variables

	/** Creates new form GPopup */
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

	private void setArrows(GNode[] nodes){
		setTitle("Fenêtre de création d'arcs");
		tabbedPane.removeAll();
		label.setText("veuillez sélectionner les arcs à créer :");
		for(int i=0;i<nodes.length;i++){
			JPanel panel = new JPanel(new GridLayout(nodes.length,1));
			for(int j=0;j<nodes.length;j++){
				GCheckbox checkbox = new GCheckbox(nodes[i],nodes[j],"-->");
				//checkbox.setBounds(10, 10+20*j, 123, 20);
				checkbox.addMouseListener(CHECKBOX_LISTENER);
				panel.add(checkbox);
			}
			JScrollPane scrollPane=new JScrollPane(panel);

			tabbedPane.addTab(nodes[i].getName(), scrollPane);
		}
		remove(tabbedPane);
		add(tabbedPane);
	}//

	private void setBridges(GNode[] nodes){
		setTitle("Fenêtre de création d'arêtes");
		tabbedPane.removeAll();
		label.setText("veuillez sélectionner les arêtes à créer :");
		for(int i=0;i<nodes.length-1;i++){
			JScrollPane scrollPane = new JScrollPane();
			JPanel panel = new JPanel(null);
			for(int j=i;j<nodes.length;j++){
				GCheckbox checkbox = new GCheckbox(nodes[i],nodes[j],"--");
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

	public static void showPopup(GEditor editor, GNode[]nodes,boolean type,Class link_class, String link_type) throws Exception{
		if(POPUP==null)POPUP = new GPopup();
		list = new LinkedList<Association>();
		if(type) // ARROW
			POPUP.setArrows(nodes);
		else 	 // BRIDGE
			POPUP.setBridges(nodes);
		GPopup.link_class = link_class;
		GPopup.link_type = link_type;
		owner = editor;
		POPUP.setVisible(true);
	}//showPopup

	/**
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
					try{
						Object[] param = new Object[4];
						param[1] = "";
						param[0] = is_arrow;
						Class[] construct = new Class[]{boolean.class, String.class, GNode.class, GNode.class};
						for(int i=0;i<list.size();i++){
							param[2] = list.get(i).node_1;
							param[3] = list.get(i).node_2;

							GLink link = (GLink) link_class.getDeclaredConstructor(construct).newInstance(param);
							/*
							 * trying to associate a non-used name for this link
							 */
							link.setName(link_type+"_");
							String name = link.getName();
							int j=1;
							link.setName(name+j);
							boolean good = owner.graph.addLink(link);
							while( ! good ){
								j++;
								link.setName(name+j);
								good = owner.graph.addLink(link);
							}//while
							owner.addComponent(link, true);
							owner.tabArea.refreshList();
						}//for
					}catch (Exception e) {
						e.printStackTrace();
						/*
						 * on ne doit pas passer ici
						 */
					}//try


					POPUP.setVisible(false);

				}//if2

			}//if1

		}//mouseClicked

	}//listener

	/**
	 * 
	 * @author lino christophe
	 *
	 */
	public class Association{
		GNode node_1,node_2;

		public Association() {
			// TODO Auto-generated constructor stub
		}
	}

	/**
	 * 
	 * @author lino christophe
	 *
	 */
	class GCheckbox extends JCheckBox{
		Association association;

		public GCheckbox(GNode node_1,GNode node_2,String link){
			super(node_1.getName()+" "+link+" "+node_2.getName());
			association = new Association();
			association.node_1 = node_1;
			association.node_2 = node_2;
		}

		public Association getAssociation(){
			return association;
		}
	}





}
