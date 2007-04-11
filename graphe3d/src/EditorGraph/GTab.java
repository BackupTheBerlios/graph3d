package EditorGraph;

import graph3D.elements.GLink;
import graph3D.elements.GNode;
import graph3D.exception.BadElementTypeException;
import graph3D.universe.GView;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 * 
 * @author lino christophe
 *
 */
public class GTab extends JScrollPane{

	public static void main(String[] args) {
		JFrame f = new JFrame("test GTab");
		f.setLayout(new GridLayout(1,1));
		JTabbedPane tabbedpane = new JTabbedPane();
		f.add(tabbedpane);
		try{
			GTab tab = new GTab(new GNode("blabla"),new Hashtable<Class, String>(),new graph3D.universe.GView());
			tabbedpane.add(tab);
		}catch (Exception e) {
			// TODO: handle exception
		}
		f.setVisible(true);
	}
	
	private static final int ENTRY_HEIGHT = 10;

	private static Hashtable<JComponent,String> table_components;

	private Object element;
	
	private GView view;

	public GTab(Object element, Hashtable<Class, String> table_types, GView view)
		throws BadElementTypeException{
		
		super();
		this.element = element;
		table_components = new Hashtable<JComponent, String>();
		JPanel panel= new JPanel();
		ValueChangedListener listener = new ValueChangedListener();
		if( element instanceof GNode){
			GNode node = (GNode) element;
			int size = node.getAttributes().size()+5/*type + name + coordonates*/;
			panel.setLayout(
					new GridLayout(size,1)
			);
			//panel.setSize(tabbedpane.getBounds().width,size*ENTRY_HEIGHT);
			/*
			 * 
			 */
			GridLayout layout = new GridLayout(1,2);
			JPanel entry = new JPanel(layout);
			JLabel label = new JLabel("name");
			JTextField value = new JTextField(node.getName());
			value.addFocusListener(listener);
			table_components.put(value, "name");
			entry.add(label);
			entry.add(value);
			panel.add(entry);
			
			entry = new JPanel(layout);
			label = new JLabel("type");
			value = new JTextField(table_types.get(node.getClass()));
			value.setEditable(false);
			entry.add(label);
			entry.add(value);
			panel.add(entry);
			
			entry = new JPanel(layout);
			label = new JLabel("coord X");
			value = new JFormattedTextField(DecimalFormat.getInstance());
			value.setText(node.getCoordonnateX()+"");
			value.addFocusListener(listener);
			table_components.put(value, "coord X");
			entry.add(label);
			entry.add(value);
			panel.add(entry);
			
			entry = new JPanel(layout);
			label = new JLabel("coord Y");
			value = new JFormattedTextField(DecimalFormat.getInstance());
			value.setText(node.getCoordonnateY()+"");
			value.addFocusListener(listener);
			table_components.put(value, "coord Y");
			entry.add(label);
			entry.add(value);
			panel.add(entry);
			
			entry = new JPanel(layout);
			label = new JLabel("coord Z");
			value = new JFormattedTextField(DecimalFormat.getInstance());
			value.setText(node.getCoordonnateZ()+"");
			value.addFocusListener(listener);
			table_components.put(value, "coord Z");
			entry.add(label);
			entry.add(value);
			panel.add(entry);
			
			Object[] attributes = node.getAttributes().keySet().toArray();
			for(int i=0; i < node.getAttributes().size(); i++){
				entry = new JPanel(layout);
				String attr_name = (String)attributes[i];
				label = new JLabel(attr_name);
				entry.add(label);
				String type = node.getAttributeByName(attr_name)[1];
				if(type.equals("int")){
					value = new JFormattedTextField(NumberFormat.getIntegerInstance());
					value.setText(node.getAttributeByName(attr_name)[2]);
					entry.add(value);
				}else if(type.equals("float")		
						 || type.equals("double")){
					value = new JFormattedTextField(DecimalFormat.getInstance());
					value.setText(node.getAttributeByName(attr_name)[2]);
					entry.add(value);
				}else if(type.equals("boolean")){
					JPanel bool_value = new JPanel(layout);
					JRadioButton
						true_button = new JRadioButton("true"),
						false_button = new JRadioButton("false");
					ButtonGroup group = new ButtonGroup();
					group.add(true_button);
					group.add(false_button);
					if(node.getAttributeByName(attr_name)[2].equals("true")) true_button.setSelected(true);
					else false_button.setSelected(true);
					bool_value.add(true_button);
					bool_value.add(false_button);
					entry.add(bool_value);
				}else{
					value = new JTextField(node.getAttributeByName(attr_name)[2]);
					entry.add(value);
				}
				panel.add(entry);
				
				setViewportView(panel);
			}
		}else if( element instanceof GLink ){
			GLink link = (GLink) element;
			panel.setLayout(
					new GridLayout(link.getAttributes().size()+2/*type + name*/,1)
			);
			GridLayout layout = new GridLayout(1,2);
			JPanel entry = new JPanel(layout);
			JLabel label = new JLabel("name");
			JTextField value = new JTextField(link.getName());
			value.addFocusListener(listener);
			table_components.put(value, "name");
			entry.add(label);
			entry.add(value);
			panel.add(entry);
			
			label = new JLabel("type");
			value = link.isType() ? new JTextField(table_types.get(link.getClass())+" (arrow)") : new JTextField(table_types.get(link.getClass())+" (bridge)");
			value.setEditable(false);
			entry.add(label);
			entry.add(value);
			panel.add(entry);
		}else{
			/*
			 * throw new BadElementTypeException();
			 * Remarque : on ne devrait pas arriver ici
			 */
		}
		
	}

	class ValueChangedListener extends FocusAdapter{
		
		public void focusLost(FocusEvent f){
			JTextField text = (JTextField) f.getSource();
			String attr_name = table_components.get(text);
			if(element instanceof GLink){
				GLink link = (GLink) element;
				if(attr_name.equals("name")){
					link.setName(text.getText());
				}else{
//					link.setAttributeByName(attr_name, getTypeDeL'Attribut , text.getText());
				}
			}else{
				GNode node = (GNode) element;
				if(attr_name.equals("name")){
					node.setName(text.getText());
				}else if(attr_name.equals("coord X")){
					node.setCoordonnateX(Integer.parseInt(text.getText()));
//					view.refresh();
				}else if(attr_name.equals("coord Y")){
					node.setCoordonnateY(Integer.parseInt(text.getText()));
//					view.refresh();
				}else if(attr_name.equals("coord Z")){
					node.setCoordonnateZ(Integer.parseInt(text.getText()));
//					view.refresh();
				}
			}

		}
	}//inner class

}//class GTab