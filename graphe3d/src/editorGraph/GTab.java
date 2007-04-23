package editorGraph;

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
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.BadElementTypeException;
import graph3d.exception.CollisionException;
import graph3d.exception.SameNameException;

/**
 * This class is used to create a graph selection tab.<br>
 * In this tab, are shown all the attributes of the selection. You can edit the value of some
 * attributes, and so change its name in the graph, move the element if it is a node thanks to
 * its coordonates, and even change all others attributes declared in the implemented class of
 * the element. 
 * 
 * @author lino christophe
 * @since JDK 1.5
 */
public class GTab extends JScrollPane{

	/*
	 * the hash table wich contains all the known graph element types
	 */
	private static Hashtable<Class, String> table_types;

	/*
	 * the standard height of an entry in the attribute table
	 */
	private static final int ENTRY_HEIGHT = 23;

	/*
	 * the hash table graphic components.
	 * one component is associated to one attribute
	 */
	private Hashtable<JComponent,String> table_components;

	/*
	 * the element which is associated to this tab
	 */
	private Object element;

	/*
	 * the GEditor owner 
	 */
	private GEditor editor;


	/**
	 * constructs a selection tab wich contains a selected graph element.
	 * @param element
	 * 		the element to asociate to this tab
	 * @param owner
	 * 		the editor owner
	 */
	public GTab(Object element, GEditor owner){
		super();
		this.element = element;
		table_components = new Hashtable<JComponent, String>();
		this.editor = owner;

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
			String type_name = table_types.get(link.getClass());
			String val = link.isType() ? type_name+" (arrow)" : type_name+" (bridge)"; 
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
		int nb_attributes_min = owner.tabArea.tabbedpane.getSize().height / ENTRY_HEIGHT;
		if(nb_attributes < nb_attributes_min){
			panel.setLayout(new GridLayout(nb_attributes_min,1));
		}//if
	}//construct

	/**
	 * this method create a new entry in the table for an attribute which is not standard, that's to say
	 * if it is not a name, type or coordonate attribute.
	 * @param attribute
	 * 		the attribute as an array, which contains its name, its type, and its initial value.
	 * @return
	 * 		a JPanel which contains the entry of this attribute
	 */
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

	/**
	 * this method returns the elements associated to this tab
	 * @return
	 * 		the element which is shown in this tab
	 */
	public Object getElement() {
		return element;
	}//getElement

	/**
	 * this class is used to perform actions associated to changes of an attribute value.<br>
	 * when focus is gained, the old value is saved, and when focus is lost, there are some
	 * checks which are carried on to validate and to spread changes or not on this attributes.
	 * 
	 * @author lino christophe
	 *
	 */
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
			if(element instanceof GLink){
				GLink link = (GLink) element;
				if(attr_name.equals("name")){
					JTextField text = (JTextField) comp;
					if(text.getText()!=old_value){
						GLink lk = editor.universe.getGraph().getLink(text.getText());
						if(lk == null){ // not already busy
							editor.universe.getGraph().removeLink(old_value);
							link.setName(text.getText());
							editor.universe.getGraph().addLink(link);
							int index = editor.tabArea.tabbedpane.indexOfComponent(GTab.this);
							editor.tabArea.tabbedpane.setTitleAt(index, link.getName());
						}else{
							(new SameNameException(text.getText())).showError();
							text.setText(old_value);
						}
					}
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
					if(text.getText()!=old_value){
						GNode nd = editor.universe.getGraph().getNode(text.getText());
						if(nd == null){ // not already busy
							editor.universe.getGraph().removeNode(old_value);
							node.setName(text.getText());
							editor.universe.getGraph().addNode(node);
							int index = editor.tabArea.tabbedpane.indexOfComponent(GTab.this);
							editor.tabArea.tabbedpane.setTitleAt(index, node.getName());
						}else{
							(new SameNameException(text.getText())).showError();
							text.setText(old_value);
						}
					}
				}else if(attr_name.equals("coord X")){
					JTextField text = (JTextField) comp;
					node.setCoordonnateX(Float.parseFloat(text.getText().replace(',', '.')));
					if(editor.universe.getGraph().haveCollision(node)){
						(new CollisionException()).showError();
						node.setCoordonnateX(Float.parseFloat(old_value.replace(',', '.')));
						text.setText(old_value);
					}else{
						editor.universe.update(node);
						editor.universe.getCanvas().repaint();
					}
				}else if(attr_name.equals("coord Y")){
					JTextField text = (JTextField) comp;
					node.setCoordonnateY(Float.parseFloat(text.getText().replace(',', '.')));
					if(editor.universe.getGraph().haveCollision(node)){
						(new CollisionException()).showError();
						node.setCoordonnateY(Float.parseFloat(old_value.replace(',', '.')));
						text.setText(old_value);
					}else{
						editor.universe.update(node);
						editor.universe.getCanvas().repaint();
					}
				}else if(attr_name.equals("coord Z")){
					JTextField text = (JTextField) comp;
					node.setCoordonnateZ(Float.parseFloat(text.getText().replace(',', '.')));
					if(editor.universe.getGraph().haveCollision(node)){
						(new CollisionException()).showError();
						node.setCoordonnateZ(Float.parseFloat(old_value.replace(',', '.')));
						text.setText(old_value);
					}else{
						editor.universe.update(node);
						editor.universe.getCanvas().repaint();
					}
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

	/**
	 * this inner class is used to perform the action associated to a click on a "true/false" radio button.
	 * 
	 * @author lino christophe
	 *
	 */
	class RadioButtonListener extends MouseAdapter{
		public void mouseClicked(MouseEvent m){
			JRadioButton but = (JRadioButton) m.getSource();
			String attr_name = table_components.get(but);
			if(element instanceof GLink){
				GLink link = (GLink) element;
				link.setAttributeByName(attr_name, link.getAttributeByName(attr_name)[1], but.getText());
			}else{ //GNode
				GNode node = (GNode) element;
				node.setAttributeByName(attr_name, node.getAttributeByName(attr_name)[1], but.getText());
			}//if
		}//mouseClicked
	}//inner class RadioButtonListener

	/**
	 * this method (re)initialize the table of known graph element types
	 */
	public static void setTableTypes(Hashtable<Class, String> table_types) {
		GTab.table_types = table_types;
	}// setTableTypes

}//class GTab