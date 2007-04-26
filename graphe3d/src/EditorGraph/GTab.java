package editorGraph;

import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.BadElementTypeException;
import graph3d.exception.CollisionException;
import graph3d.exception.GException;
import graph3d.exception.SameNameException;
import graph3d.lists.GAttributesList;
import graph3d.universe.GGrapheUniverse;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
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
 * This class is used to create a graph selection tab.<br>
 * In this tab, are shown all the attributes of the selection. You can edit the value of some
 * attributes, and so change its name in the graph, move the element if it is a node thanks to
 * its coordonates, and even change all others attributes declared in the implemented class of
 * the element. 
 * 
 * @author lino christophe
 * @since JDK 1.5
 * @version 1.0
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
	 * the hash table of graphic components.
	 * one component is associated to one attribute
	 */
	private Hashtable<JComponent,String> table_components;

	/*
	 * the element which is associated to this tab
	 */
	private Object element;

	/*
	 * the GGrapheUniverse which have to be refreshed when changes are performed.
	 * Can be null if the tab is not editable.
	 */
	private GGrapheUniverse universe;

	/*
	 * the tabbedpane owner
	 * Can be null if the tab is not editable.
	 */
	private JTabbedPane tabbedpane;

	/*
	 * flag to check if the attributes are editable
	 */
	private boolean editable;

	/**
	 * constructs a selection tab wich contains a selected graph element.
	 * it is a package constructor.
	 * @param _element
	 * 		Object : the element to asociate to this tab
	 * @param _comp
	 * 		JtabbedPane : the tabbedpane wich will contain the tab.
	 * 		Must not be null if the tab is editable.
	 * @param _Universe
	 * 		GGraphe Universe : the graphe universe which have to be refreshed after changes.
	 * 		Must not be null if the tab is editable.
	 * @param _editable
	 * 		boolean : if the attributes have to be editable
	 * @throws GException
	 * 		if the tab is set editable and _Universe is null.
	 */
	public GTab(Object _element, JComponent _comp, GGrapheUniverse _Universe, boolean _editable)
	throws GException{
		super();
		/*
		 * we check if there is no problem in arguments
		 */
		if(_comp==null)
			throw new GException("_comp argument cannot be null !");
		if(_editable && _Universe==null)
			throw new GException("_Universe argument cannot be null because the tab is set editable !");
		this.element = _element;
		table_components = new Hashtable<JComponent, String>();
		this.universe = _Universe;
		this.editable = _editable;

		int nb_attributes = 0;

		ScrollPaneLayout spl = new ScrollPaneLayout();
		spl.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_NEVER);
		setLayout(spl);
		JPanel panel= new JPanel();
		String title = "";
		if( _element instanceof GNode){
			GNode node = (GNode) _element;
			int size = node.getAttributes().size()+5/*type + name + coordonates*/;
			size++; // TabButton
			panel.setLayout(
					new GridLayout(size,1)
			);

			GridLayout layout = new GridLayout(1,2);
			JPanel entry = new JPanel(layout);
			JLabel label = new JLabel("name  ",JLabel.RIGHT);
			JTextField value = new JTextField(node.getName());
			title = node.getName();
			value.addFocusListener(new LostFocusListener());
			value.setEditable(editable);
			table_components.put(value, "name");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("type  ",JLabel.RIGHT);
			String type = table_types.get(node.getClass());
			type = type == null ? "unknown" : type;
			value = new JTextField(type);
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
			value.addFocusListener(new LostFocusListener());
			value.setEditable(editable);
			table_components.put(value, "coord X");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("coord Y  ",JLabel.RIGHT);
			value = new JFormattedTextField(format);
			value.setText(node.getCoordonnateY()+"");
			value.addFocusListener(new LostFocusListener());
			value.setEditable(editable);
			table_components.put(value, "coord Y");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("coord Z  ",JLabel.RIGHT);
			value = new JFormattedTextField(format);
			value.setText(node.getCoordonnateZ()+"");
			value.addFocusListener(new LostFocusListener());
			value.setEditable(editable);
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
		}else if( _element instanceof GLink ){
			GLink link = (GLink) _element;
			panel.setLayout(
					new GridLayout(link.getAttributes().size()+2/*type + name*/,1)
			);
			GridLayout layout = new GridLayout(1,2);
			JPanel entry = new JPanel(layout);
			JLabel label = new JLabel("name  ",JLabel.RIGHT);
			JTextField value = new JTextField(link.getName());
			title = link.getName();
			value.addFocusListener(new LostFocusListener());
			value.setEditable(editable);
			table_components.put(value, "name");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("type  ",JLabel.RIGHT);
			String type = table_types.get(link.getClass());
			type = type==null ? "unknown" : type;
			String val = link.isType() ? type+" (arrow)" : type+" (bridge)"; 
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
		if(_comp instanceof GAttributesList){
			GAttributesList attr_list = (GAttributesList) _comp;
			this.tabbedpane = attr_list;
			panel.add(new ExitCross(title,attr_list,this),0);
		}else if(_comp instanceof GEditor){
			GEditor editor = (GEditor) _comp;
			this.tabbedpane = editor.tabArea.tabbedpane;
			panel.add(new ExitCross(title,editor,this),0);
		}else
			throw new GException("_comp cannot of type \""+_comp.getClass()+"\" !");
		
		/*
		 * if there are too few attributes
		 * the layout is updated to improve graphics
		 */
		int nb_attributes_min = tabbedpane.getSize().height / ENTRY_HEIGHT;
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
			value.setEditable(editable);
			entry.add(value);
		}else if(type.equals("float")){
			DecimalFormat format = new DecimalFormat();
			format.setDecimalSeparatorAlwaysShown(true);
			JFormattedTextField value = new JFormattedTextField(format);
			value.setText(attribute[2]);
			table_components.put(value, attribute[0]);
			value.addFocusListener(new LostFocusListener());
			value.setEditable(editable);
			entry.add(value);
		}else if(type.equals("double")){
			DecimalFormat format = new DecimalFormat();
			format.setDecimalSeparatorAlwaysShown(true);
			JFormattedTextField value = new JFormattedTextField(format);
			value.setText(attribute[2]);
			table_components.put(value, attribute[0]);
			value.addFocusListener(new LostFocusListener());
			value.setEditable(editable);
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
			table_components.put(true_button, attribute[0]);
			table_components.put(false_button, attribute[0]);
			MouseListener m_listener = new RadioButtonListener();
			true_button.addMouseListener(m_listener);
			false_button.addMouseListener(m_listener);
			true_button.setEnabled(editable);
			false_button.setEnabled(editable);
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
	 * returns the elements (node or link) associated to this tab.
	 * @return
	 * 		the element which is shown in this tab
	 */
	public Object getElement() {
		return element;
	}//getElement

	/**
	 * sets the tab editable or not editable.
	 * @param _editable
	 * 		if the tab have to become editable or not editable
	 */
	public void setEditable(boolean _editable) throws GException {
		if(_editable && this.universe==null)
			throw new GException("You cannot set the tab editable because there is no universe !");
		Enumeration<JComponent> comp = table_components.keys();
		while(comp.hasMoreElements()){
			JComponent c = comp.nextElement();
			if( (c instanceof JTextField)  && ! (table_components.get(c).equals("type")) )
				((JTextField) c).setEditable(_editable);
			else if( c instanceof JRadioButton )
				((JRadioButton) c).setEnabled(_editable);
		}//while
	}

	public void setUniverse(GGrapheUniverse _universe){
		this.universe = _universe;
	}

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
					if(!text.getText().equals(old_value)){
						GLink lk = universe.getGraph().getLink(text.getText());
						if(lk == null){ // not already busy
							universe.getGraph().removeLink(old_value);
							link.setName(text.getText());
							universe.getGraph().addLink(link);
							int index = tabbedpane.indexOfComponent(GTab.this);
							tabbedpane.setTitleAt(index, link.getName());
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
					if(!text.getText().equals(old_value)){
						System.out.println("\""+text.getText()+"\"");
						GNode nd = universe.getGraph().getNode(text.getText());
						if(nd == null){ // not already busy
							universe.getGraph().removeNode(old_value);
							node.setName(text.getText());
							universe.getGraph().addNode(node);
							int index = tabbedpane.indexOfComponent(GTab.this);
							tabbedpane.setTitleAt(index, node.getName());
						}else{
							(new SameNameException(text.getText())).showError();
							text.setText(old_value);
						}
					}
				}else if(attr_name.equals("coord X")){
					JTextField text = (JTextField) comp;
					node.setCoordonnateX(Float.parseFloat(text.getText().replace(',', '.')));
					if(universe.getGraph().haveCollision(node)){
						(new CollisionException()).showError();
						node.setCoordonnateX(Float.parseFloat(old_value.replace(',', '.')));
						text.setText(old_value);
					}else{
						universe.update(node);
						universe.getCanvas().repaint();
					}
				}else if(attr_name.equals("coord Y")){
					JTextField text = (JTextField) comp;
					node.setCoordonnateY(Float.parseFloat(text.getText().replace(',', '.')));
					if(universe.getGraph().haveCollision(node)){
						(new CollisionException()).showError();
						node.setCoordonnateY(Float.parseFloat(old_value.replace(',', '.')));
						text.setText(old_value);
					}else{
						universe.update(node);
						universe.getCanvas().repaint();
					}
				}else if(attr_name.equals("coord Z")){
					JTextField text = (JTextField) comp;
					node.setCoordonnateZ(Float.parseFloat(text.getText().replace(',', '.')));
					if(universe.getGraph().haveCollision(node)){
						(new CollisionException()).showError();
						node.setCoordonnateZ(Float.parseFloat(old_value.replace(',', '.')));
						text.setText(old_value);
					}else{
						universe.update(node);
						universe.getCanvas().repaint();
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
	public static void setTableTypes(Hashtable<Class, String> _table_types) {
		if(_table_types.size()!=0 || table_types == null)
			table_types = _table_types;
	}// setTableTypes

}//class GTab