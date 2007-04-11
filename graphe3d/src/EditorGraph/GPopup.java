package EditorGraph;

import graph3D.elements.GLink;
import graph3D.elements.GNode;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.print.DocFlavor.STRING;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/*
 * GPopup.java
 *
 * Created on 8 avril 2007, 12:31
 */

/**
 *
 * @author  lino christophe
 */
public class GPopup extends JDialog {

	public static void main(String[] args) {
		GNode[]tab=new GNode[15];
		for(int i=1;i<=15;i++) tab[i-1]=new GNode("noeud_"+i);
		try{
			GPopup p = GPopup.showPopup(tab,ARROW,Class.forName("graph3D.elements.GLink"));
			p.setVisible(true);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}//try
	}//main
	
	public static final int ARROW = 0, BRIDGE = 1;
	public static GPopup POPUP;
	public static boolean is_arrow;
	private static Class link_class;
	private static LinkedList<Association> list;
	private static LinkedList<GLink> list_of_links;
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
		tabbedPane.removeAll();
		list = new LinkedList<Association>();
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
		tabbedPane.removeAll();
		list = new LinkedList<Association>();
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
			
			tabbedPane.addTab(nodes[i].toString(), scrollPane);

		}
		remove(tabbedPane);
		add(tabbedPane);
	}//

	public static GPopup showPopup(GNode[]nodes,int type,Class link_class) throws Exception{
		if(POPUP==null)POPUP = new GPopup();
		if(type == ARROW)
			POPUP.setArrows(nodes);
		else if(type == BRIDGE)
			POPUP.setBridges(nodes);
		else
			throw new Exception();
		POPUP.link_class = link_class;
		return POPUP;
	}

	public static GLink[] getCreatedLinks(){
		return list_of_links.toArray(new GLink[0]);
	}

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
					list_of_links = new LinkedList<GLink>();
					for(int i=0;i<list.size();i++){
						try{
							Class[] construct = new Class[]{Boolean.class,GNode.class,GNode.class};
							Object[] param = new Object[3];
							if(is_arrow) param[0] = Boolean.TRUE;
							param[1] = list.get(i).node_1;
							param[2] = list.get(i).node_2;
							GLink e = (GLink) link_class
							.getDeclaredConstructor(construct).newInstance(param);
							list_of_links.add(e);
							System.out.println(e);
						}catch (Exception e) {
							System.out.println(e);
							/*
							 * on ne doit pas passer ici
							 */
						}//try
					}//for
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
