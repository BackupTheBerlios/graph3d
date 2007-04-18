package editorGraph;

import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.BadElementTypeException;
import graph3d.universe.GView;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

/**
 * 
 * @author lino christophe
 *
 */
public class GTab extends JScrollPane{

	private static final int ENTRY_HEIGHT = 10;

	private Hashtable<JComponent,String> table_components;

	private Object element;

	private JTabbedPane owner;

	private GView view;

	public GTab(Object element, JTabbedPane owner, Hashtable<Class, String> table_types, GView view){
		super();
		this.element = element;
		table_components = new Hashtable<JComponent, String>();
		this.owner = owner;
		
		int nb_attributes = 0;

		ScrollPaneLayout spl = new ScrollPaneLayout();
		spl.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);
		setLayout(spl);
		JPanel panel= new JPanel();
		LostFocusListener listener = new LostFocusListener();
		if( element instanceof GNode){
			GNode node = (GNode) element;
			int size = node.getAttributes().size()+5/*type + name + coordonates*/;
			panel.setLayout(
					new GridLayout(size,1)
			);

			GridLayout layout = new GridLayout(1,2);
			JPanel entry = new JPanel(layout);
			JLabel label = new JLabel("name  ",JLabel.RIGHT);
			JTextField value = new JTextField(node.getName());
			value.addFocusListener(listener);
			table_components.put(value, "name");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("type  ",JLabel.RIGHT);
			value = new JTextField(table_types.get(node.getClass()));
			value.setEditable(false);
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("coord X  ",JLabel.RIGHT);
			DecimalFormat format = new DecimalFormat();
			format.setDecimalSeparatorAlwaysShown(true);
			value = new JFormattedTextField(format);
			value.setText(node.getCoordonnateX()+"");
			value.addFocusListener(listener);
			table_components.put(value, "coord X");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("coord Y  ",JLabel.RIGHT);
			value = new JFormattedTextField(format);
			value.setText(node.getCoordonnateY()+"");
			value.addFocusListener(listener);
			table_components.put(value, "coord Y");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("coord Z  ",JLabel.RIGHT);
			value = new JFormattedTextField(format);
			value.setText(node.getCoordonnateZ()+"");
			value.addFocusListener(listener);
			table_components.put(value, "coord Z");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			Object[] attributes = node.getAttributes().keySet().toArray();
			for(int i=0; i < node.getAttributes().size(); i++){
				String attr_name = (String)attributes[i];
				entry = newEntry(node.getAttributeByName(attr_name));
				panel.add(entry);
			}
			setViewportView(panel);
			
			nb_attributes = 5 + attributes.length;
		}else if( element instanceof GLink ){
			GLink link = (GLink) element;
			panel.setLayout(
					new GridLayout(link.getAttributes().size()+2/*type + name*/,1)
			);
			GridLayout layout = new GridLayout(1,2);
			JPanel entry = new JPanel(layout);
			JLabel label = new JLabel("name  ",JLabel.RIGHT);
			JTextField value = new JTextField(link.getName());
			value.addFocusListener(listener);
			table_components.put(value, "name");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("type  ",JLabel.RIGHT);
			String val = link.isType() ?	table_types.get(link.getClass())+" (arrow)" : table_types.get(link.getClass())+" (bridge)"; 
			value = new JTextField(val);
			value.setEditable(false);
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			Object[] attributes = link.getAttributes().keySet().toArray();
			for(int i=0; i < link.getAttributes().size(); i++){
				String attr_name = (String)attributes[i];
				entry = newEntry(link.getAttributeByName(attr_name));
				panel.add(entry);
			}//if
			setViewportView(panel);
			
			nb_attributes = 3 + attributes.length;
		}else{
			System.err.println(new BadElementTypeException("graph element").getMessage());
		}//if
		/*
		 * if there are too few attributes
		 * the layout is updated to improve graphics
		 */
		int attribute_height = 23;
		int nb_attributes_min = owner.getSize().height / attribute_height;
		if(nb_attributes < nb_attributes_min){
			panel.setLayout(new GridLayout(nb_attributes_min,1));
		}//if
	}//construct

	private JPanel newEntry(String[]attribute){
		GridLayout layout = new GridLayout(1,2);
		JPanel entry = new JPanel(layout);
		JLabel label = new JLabel(attribute[0]+"  ",JLabel.RIGHT);
		entry.add(label);
		String type = attribute[1];
		if(type.equals("int")){
			JFormattedTextField value = new JFormattedTextField(NumberFormat.getIntegerInstance());
			value.setText(attribute[2]);
			table_components.put(value, attribute[0]);
			value.addFocusListener(new LostFocusListener());
			entry.add(value);
		}else if(type.equals("float")){
			DecimalFormat format = new DecimalFormat();
			format.setDecimalSeparatorAlwaysShown(true);
			JFormattedTextField value = new JFormattedTextField(format);
			value.setText(attribute[2]);
			table_components.put(value, attribute[0]);
			value.addFocusListener(new LostFocusListener());
			entry.add(value);
		}else if(type.equals("double")){
			DecimalFormat format = new DecimalFormat();
			format.setDecimalSeparatorAlwaysShown(true);
			JFormattedTextField value = new JFormattedTextField(format);
			value.setText(attribute[2]);
			table_components.put(value, attribute[0]);
			value.addFocusListener(new LostFocusListener());
			entry.add(value);
		}else if(type.equals("boolean")){
			JPanel bool_value = new JPanel(layout);
			JRadioButton
			true_button = new JRadioButton("true"),
			false_button = new JRadioButton("false");
			ButtonGroup group = new ButtonGroup();
			group.add(true_button);
			group.add(false_button);
			if(attribute[2].equals("true")) true_button.setSelected(true);
			else false_button.setSelected(true);
			MouseListener m_listener = new RadioButtonListener();
			true_button.addMouseListener(m_listener);
			false_button.addMouseListener(m_listener);
			bool_value.add(true_button);
			bool_value.add(false_button);
			entry.add(bool_value);
		}else{
			JTextField value = new JTextField(attribute[2]);
			entry.add(value);
		}//if
		return entry;
	}//newEntry

	public Object getElement() {
		return element;
	}//getElement

	class LostFocusListener implements FocusListener{

		/*
		 * this string is used to save the old value of an attribute
		 * in case of a conflict with another element
		 */
		String old_value;
		
		public void focusGained(FocusEvent f){
			JTextField text = (JTextField) f.getSource();
			old_value = text.getText();
		}//focusGained
		
		public void focusLost(FocusEvent f){
			Component comp = (Component) f.getSource();
			String attr_name = table_components.get(comp);
			System.out.println(attr_name);
			if(element instanceof GLink){
				GLink link = (GLink) element;
				if(attr_name.equals("name")){
					JTextField text = (JTextField) comp;
					link.setName(text.getText());
					int index = owner.indexOfComponent(GTab.this);
					owner.setTitleAt(index, link.getName());
				}else{
					/*
					 * others attributes
					 */
					JTextField text = (JTextField) comp;
					link.setAttributeByName(attr_name, link.getAttributeByName(attr_name)[1] , text.getText());
				}//if
			}else{
				GNode node = (GNode) element;
				if(attr_name.equals("name")){
					JTextField text = (JTextField) comp;
					node.setName(text.getText());
					int index = owner.indexOfComponent(GTab.this);
					owner.setTitleAt(index, node.getName());
				}else if(attr_name.equals("coord X")){
					JTextField text = (JTextField) comp;
					node.setCoordonnateX(Float.parseFloat(text.getText().replace(',', '.')));
					System.out.println(node.getCoordonnateX());
//					view.refresh();
				}else if(attr_name.equals("coord Y")){
					JTextField text = (JTextField) comp;
					node.setCoordonnateY(Float.parseFloat(text.getText().replace(',', '.')));
					System.out.println(node.getCoordonnateY());
//					view.refresh();
				}else if(attr_name.equals("coord Z")){
					JTextField text = (JTextField) comp;
					node.setCoordonnateZ(Float.parseFloat(text.getText().replace(',', '.')));
					System.out.println(node.getCoordonnateZ());
//					view.refresh();
				}else{ 
					/*
					 * others attributes
					 */
					JTextField text = (JTextField) comp;
					node.setAttributeByName(attr_name,node.getAttributeByName(attr_name)[1] , text.getText());
				}//if
			}//if element instanceof GLink

		}
	}//inner class

	class RadioButtonListener extends MouseAdapter{

		public void mouseClicked(MouseEvent m){
			JRadioButton but = (JRadioButton) m.getSource();

		}

	}

}//class GTab