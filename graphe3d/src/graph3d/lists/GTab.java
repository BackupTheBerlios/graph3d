package graph3d.lists;

import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.BadElementTypeException;
import graph3d.exception.CollisionException;
import graph3d.exception.GException;
import graph3d.exception.InvalidAttributeTypeException;
import graph3d.exception.SameNameException;
import graph3d.exception.TooMuchAttributesForClassException;
import graph3d.universe.GGraphUniverse;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;

/**
 * This class is used to create a graph selection tab.<br>
 * In this tab, are shown all the attributes of the selection. You can edit the
 * value of some attributes, and so change its name in the graph, move the
 * element if it is a node thanks to its coordonates, and even change all others
 * attributes declared in the implemented class of the element.
 * 
 * @author lino christophe
 * @since JDK 1.5
 * @version 1.0
 */
public class GTab extends JScrollPane implements ItemListener {

	/*
	 * the hash table wich contains all the known graph element types
	 */
	private static Hashtable<Class, String> table_types;

	/*
	 * the standard height of an entry in the table of attributes
	 */
	private static final int ENTRY_HEIGHT = 23;

	/*
	 * all the colors which are available for links
	 */
	private static final String COLORS[] = new String[] { "black", "white",
			"red", "blue", "cyan", "gray", "green", "magenta", "orange",
			"pink", "yellow" };

	/*
	 * the hash table of graphic components. one component is associated to one
	 * attribute
	 */
	private Hashtable<JComponent, String> table_components;

	/*
	 * the element which is associated to this tab
	 */
	private Object element;

	/*
	 * known type name of the element or "unknown"
	 */
	private JTextField type_text;

	/*
	 * the GGrapheUniverse which have to be refreshed when changes are
	 * performed. Can be null if the tab is not editable.
	 */
	private GGraphUniverse universe;

	/*
	 * the tabbedpane owner Can be null if the tab is not editable.
	 */
	private GAttributesList attributes_list;

	/*
	 * flag to check if the attributes are editable
	 */
	private boolean editable;

	/**
	 * constructs a selection tab wich contains a selected graph element. it is
	 * a package constructor.
	 * 
	 * @param _element
	 *            Object : the element to asociate to this tab
	 * @param _attrList
	 *            JtabbedPane : the tabbedpane wich will contain the tab. Must
	 *            not be null if the tab is editable.
	 * @param _Universe
	 *            GGraphe Universe : the graphe universe which have to be
	 *            refreshed after changes. Must not be null if the tab is
	 *            editable.
	 * @param _editable
	 *            boolean : if the attributes have to be editable
	 * @throws GException
	 *             if the tab is set editable and _Universe is null.
	 */
	public GTab(Object _element, GAttributesList _attrList,
			GGraphUniverse _Universe, boolean _editable) throws GException {
		super();
		this.setMinimumSize(new Dimension(200, 75));
		/*
		 * we check if there is no problem in arguments
		 */
		if (_attrList == null)
			throw new GException("_attrList argument cannot be null !");
		if (_editable && _Universe == null)
			throw new GException(
					"_Universe argument cannot be null because the tab is set editable !");
		this.element = _element;
		this.table_components = new Hashtable<JComponent, String>();
		this.universe = _Universe;
		this.editable = _editable;

		int nb_attributes = 0;

		ScrollPaneLayout spl = new ScrollPaneLayout();
		spl
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setLayout(spl);
		JPanel panel = new JPanel();
		String title = "fermer";
		this.attributes_list = _attrList;
		panel.add(new ExitCross(title, this.attributes_list, this), 0);
		if (_element instanceof GNode) {
			GNode node = (GNode) _element;
			int size = node.getAttributes().size() + 7;/*
														 * type + name + radius +
														 * 3 coordonates + exit
														 * cross
														 */
			panel.setLayout(new GridLayout(size, 1));

			GridLayout layout = new GridLayout(1, 2);
			JPanel entry = new JPanel(layout);
			JLabel label = new JLabel("nom  ", SwingConstants.RIGHT);
			JTextField value = new JTextField(node.getName());
			value.addFocusListener(new LostFocusListener());
			value.setEditable(this.editable);
			this.table_components.put(value, "name");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("type  ", SwingConstants.RIGHT);
			String type = table_types.get(node.getClass());
			type = type == null ? "unknown" : type;
			value = new JTextField(type);
			// we reference this text field to be able
			// to dynamically change it in the tab
			this.type_text = value;
			value.setEditable(false);
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			/*
			 * creating a decimal format for the following float values
			 */
			DecimalFormat format = new DecimalFormat();
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			dfs.setGroupingSeparator(' ');
			format.setDecimalFormatSymbols(dfs);
			format.setDecimalSeparatorAlwaysShown(true);
			format.setMinimumFractionDigits(1);
			format.setMaximumFractionDigits(10);

			entry = new JPanel(layout);
			label = new JLabel("rayon  ", SwingConstants.RIGHT);
			value = new JFormattedTextField(format);
			value.setText((node.getRadius() + "").replace(",", "."));
			value.addFocusListener(new LostFocusListener());
			value.setEditable(this.editable);
			this.table_components.put(value, "radius");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("coordonnée X  ", SwingConstants.RIGHT);
			value = new JFormattedTextField(format);
			value.setText((node.getCoordonnateX() + "").replace(",", "."));
			value.addFocusListener(new LostFocusListener());
			value.setEditable(this.editable);
			this.table_components.put(value, "coord X");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("coordonnée Y  ", SwingConstants.RIGHT);
			value = new JFormattedTextField(format);
			value.setText((node.getCoordonnateY() + "").replace(",", "."));
			value.addFocusListener(new LostFocusListener());
			value.setEditable(this.editable);
			this.table_components.put(value, "coord Y");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("coordonnée Z  ", SwingConstants.RIGHT);
			value = new JFormattedTextField(format);
			value.setText((node.getCoordonnateZ() + "").replace(",", "."));
			value.addFocusListener(new LostFocusListener());
			value.setEditable(this.editable);
			this.table_components.put(value, "coord Z");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			Object[] attributes = node.getAttributes().keySet().toArray();
			for (int i = 0; i < node.getAttributes().size(); i++) {
				String attr_name = (String) attributes[i];
				entry = newEntry(node.getAttributeByName(attr_name));
				panel.add(entry);
			}
			setViewportView(panel);

			nb_attributes = 7 + attributes.length;
		} else if (_element instanceof GLink) {
			GLink link = (GLink) _element;
			panel
					.setLayout(new GridLayout(
							link.getAttributes().size() + 3/*
															 * type + name +
															 * exit cross
															 */,
							1));
			GridLayout layout = new GridLayout(1, 2);
			JPanel entry = new JPanel(layout);
			JLabel label = new JLabel("nom  ", SwingConstants.RIGHT);
			JTextField value = new JTextField(link.getName());
			value.addFocusListener(new LostFocusListener());
			value.setEditable(this.editable);
			this.table_components.put(value, "name");
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			entry = new JPanel(layout);
			label = new JLabel("type  ", SwingConstants.RIGHT);
			String type = table_types.get(link.getClass());
			type = type == null ? "unknown" : type;
			String val = link.isType() ? type + " (arc)" : type + " (arête)";
			value = new JTextField(val);
			value.setEditable(false);
			entry.add(label);
			entry.add(value);
			panel.add(entry);

			if (this.editable) {
				entry = new JPanel(layout);
				label = new JLabel("color", SwingConstants.RIGHT);
				JComboBox combo = new JComboBox(COLORS);
				if (link.getColor().equals("black")) {
					combo.setSelectedIndex(0);
				} else if (link.getColor().equals("white")) {
					combo.setSelectedIndex(1);
				} else if (link.getColor().equals("red")) {
					combo.setSelectedIndex(2);
				} else if (link.getColor().equals("blue")) {
					combo.setSelectedIndex(3);
				} else if (link.getColor().equals("cyan")) {
					combo.setSelectedIndex(4);
				} else if (link.getColor().equals("gray")) {
					combo.setSelectedIndex(5);
				} else  if (link.getColor().equals("green")) {
					combo.setSelectedIndex(6);
				} else if (link.getColor().equals("magenta")) {
					combo.setSelectedIndex(7);
				} else if (link.getColor().equals("orange")) {
					combo.setSelectedIndex(8);
				} else if (link.getColor().equals("pink")) {
					combo.setSelectedIndex(9);
				} else if (link.getColor().equals("yellow")) {
					combo.setSelectedIndex(10);
				}else {
					combo.setSelectedIndex(-1);
				}
				combo.addItemListener(this);
				//combo.addFocusListener(new LostFocusListener());
				combo.setEditable(true);
				this.table_components.put(combo, "color");
				entry.add(label);
				entry.add(combo);
			} else {
				entry = new JPanel(layout);
				label = new JLabel("color", SwingConstants.RIGHT);
				value = new JTextField(link.getColor()); 
				value.setEditable(false);
				this.table_components.put(value, "color");
				entry.add(label);
				entry.add(value);
			}
			panel.add(entry);

			Object[] attributes = link.getAttributes().keySet().toArray();
			for (int i = 0; i < link.getAttributes().size(); i++) {
				String attr_name = (String) attributes[i];
				entry = newEntry(link.getAttributeByName(attr_name));
				panel.add(entry);
			}// if
			setViewportView(panel);

			nb_attributes = 4 + attributes.length;
		} else {
			System.err.println(new BadElementTypeException("graph element")
					.getMessage());
		}// if

		/*
		 * if there are too few attributes the layout is updated to improve
		 * graphics
		 */
		int nb_attributes_min = this.attributes_list.getSize().height / ENTRY_HEIGHT;
		if (nb_attributes < nb_attributes_min) {
			panel.setLayout(new GridLayout(nb_attributes_min, 1));
		}// if
	}// construct

	/**
	 * this method create a new entry in the table for an attribute which is not
	 * standard, that's to say if it is not a name, type or coordonate
	 * attribute.
	 * 
	 * @param attribute
	 *            the attribute as an array, which contains its name, its type,
	 *            and its initial value.
	 * @return a JPanel which contains the entry of this attribute
	 */
	private JPanel newEntry(String[] attribute) {
		GridLayout layout = new GridLayout(1, 2);
		JPanel entry = new JPanel(layout);
		JLabel label = new JLabel(attribute[0] + "  ", JLabel.RIGHT);
		entry.add(label);
		String type = attribute[1];
		if (type.equals("int")) {
			JFormattedTextField value = new JFormattedTextField(NumberFormat
					.getIntegerInstance());
			value.setText(attribute[2]);
			table_components.put(value, attribute[0]);
			value.addFocusListener(new LostFocusListener());
			value.setEditable(editable);
			entry.add(value);
		} else if (type.equals("float")) {
			DecimalFormat format = new DecimalFormat();
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			dfs.setGroupingSeparator(' ');
			format.setDecimalFormatSymbols(dfs);
			format.setDecimalSeparatorAlwaysShown(true);
			format.setMinimumFractionDigits(1);
			format.setMaximumFractionDigits(10);
			JFormattedTextField value = new JFormattedTextField(format);
			value.setText(attribute[2].replace(",", "."));
			table_components.put(value, attribute[0]);
			value.addFocusListener(new LostFocusListener());
			value.setEditable(editable);
			entry.add(value);
		} else if (type.equals("double")) {
			DecimalFormat format = new DecimalFormat();
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			dfs.setGroupingSeparator(' ');
			format.setDecimalFormatSymbols(dfs);
			format.setDecimalSeparatorAlwaysShown(true);
			format.setMinimumFractionDigits(1);
			format.setMaximumFractionDigits(10);
			JFormattedTextField value = new JFormattedTextField(format);
			value.setText(attribute[2].replace(",", "."));
			table_components.put(value, attribute[0]);
			value.addFocusListener(new LostFocusListener());
			value.setEditable(editable);
			entry.add(value);
		} else if (type.equals("boolean")) {
			JPanel bool_value = new JPanel(layout);
			JRadioButton true_button = new JRadioButton("true"), false_button = new JRadioButton(
					"false");
			ButtonGroup group = new ButtonGroup();
			group.add(true_button);
			group.add(false_button);
			if (attribute[2].equals("true"))
				true_button.setSelected(true);
			else
				false_button.setSelected(true);
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
		} else {
			JTextField value = new JTextField(attribute[2]);
			entry.add(value);
		}// if
		return entry;
	}// newEntry

	/**
	 * returns the elements (node or link) associated to this tab.
	 * 
	 * @return the element which is shown in this tab
	 */
	public Object getElement() {
		return element;
	}// getElement

	/**
	 * refreshes the type name according to the table of known types.
	 */
	public void refreshType() {
		String type = table_types.get(element.getClass());
		type = type == null ? "unknown" : type;
		type_text.setText(type);
	}

	/**
	 * sets the tab editable or not editable.
	 * 
	 * @param _editable
	 *            if the tab have to become editable or not editable
	 */
	public void setEditable(boolean _editable) throws GException {
		if (_editable && this.universe == null)
			throw new GException(
					"You cannot set the tab editable because there is no universe !");
		Enumeration<JComponent> comp = table_components.keys();
		while (comp.hasMoreElements()) {
			JComponent c = comp.nextElement();
			if ((c instanceof JTextField)
					&& !(table_components.get(c).equals("type")))
				((JTextField) c).setEditable(_editable);
			else if (c instanceof JRadioButton)
				((JRadioButton) c).setEnabled(_editable);
		}// while
	}

	/**
	 * changes the GGrapheUniverse of this GEditor
	 * 
	 * @param _universe
	 *            the new GGrapheUniverse
	 */
	public void setUniverse(GGraphUniverse _universe) {
		this.universe = _universe;
	}

	/**
	 * this class is used to perform actions associated to changes of an
	 * attribute value.<br>
	 * when focus is gained, the old value is saved, and when focus is lost,
	 * there are some checks which are carried on to validate and to spread
	 * changes or not on this attributes.
	 * 
	 * @author lino christophe
	 * 
	 */
	class LostFocusListener implements FocusListener {

		/*
		 * this string is used to save the old value of an attribute in case of
		 * a conflict with another element
		 */
		String old_value;

		public void focusGained(FocusEvent f) {
			try {
				JTextField text = (JTextField) f.getSource();
				old_value = text.getText();
			} catch (Exception e) {
				JComboBox combo = (JComboBox) f.getSource();
				old_value = (String) combo.getSelectedItem();
			}
		}// focusGained

		public void focusLost(FocusEvent f) {
			Component comp = (Component) f.getSource();
			/*
			 * we gets the attribute name corresponding to this component from
			 * the table of components
			 */
			String attr_name = table_components.get(comp);
			if (element instanceof GLink) {
				GLink link = (GLink) element;
				if (attr_name.equals("name")) {
					JTextField text = (JTextField) comp;
					if (!text.getText().equals(old_value)) {
						GLink lk = universe.getGraph().getLink(text.getText());
						if (lk == null) { // not already busy
							universe.deleteGLink(old_value);
							link.setName(text.getText());
							universe.addGLink(link);
							int index = attributes_list
									.indexOfComponent(GTab.this);
							attributes_list.setTitleAt(index, link.getName());
						} else {
							(new SameNameException(text.getText())).showError();
							text.setText(old_value);
						}
					}
				} else if (attr_name.equals("color")) {
					JComboBox combo = (JComboBox) comp;
					String text = (String) combo.getSelectedItem();
					if (!text.equals(old_value)) {
						link.setColor((String) combo.getSelectedItem());
						universe.updateGLink(link.getName());
					}
				} else {
					/*
					 * others attributes
					 */
					JTextField text = (JTextField) comp;
					try {
						String type = link.getAttributeByName(attr_name)[1];
						if (type.equals("float") || type.equals("double"))
							if (!text.getText().contains("."))
								text.setText(text.getText() + ".0");
						link.setAttributeByName(attr_name, link
								.getAttributeByName(attr_name)[1], text
								.getText());
					} catch (InvalidAttributeTypeException e) {
						e.printStackTrace();
						e.showError();
					} catch (TooMuchAttributesForClassException e) {
						e.printStackTrace();
						e.showError();
					} catch (GException e) {
						e.printStackTrace();
						e.showError();
					}
				}// if
			} else {
				GNode node = (GNode) element;
				if (attr_name.equals("name")) {
					JTextField text = (JTextField) comp;
					if (!text.getText().equals(old_value)) {
						if (universe.getGraph().getNode(text.getText()) == null) { // not already busy
							universe.deleteGNode(old_value);
							node.setName(text.getText());
							universe.addGNode(node);
							int index = attributes_list
									.indexOfComponent(GTab.this);
							attributes_list.setTitleAt(index, node.getName());
						} else {
							(new SameNameException(text.getText())).showError();
							text.setText(old_value);
						}
					}
				} else if (attr_name.equals("coord X")) {
					JTextField text = (JTextField) comp;
					/*
					 * we check if the type of the entered value matches float
					 * type
					 */
					try {
						text.setText(text.getText().replace(',', '.'));
						if (!text.getText().contains("."))
							text.setText(text.getText() + ".0");
						Float.parseFloat(text.getText());
					} catch (Exception e) {
						text.setText(old_value);
					}
					if (!text.getText().equals(old_value)) {
						node.setCoordonnateX(Float.parseFloat(text.getText()));
						if (universe.getGraph().haveCollision(node)) {
							(new CollisionException()).showError();
							node.setCoordonnateX(Float.parseFloat(old_value));
							text.setText(old_value);
						} else {
							universe.updateGNode(node.getName());
						}
					}
				} else if (attr_name.equals("coord Y")) {
					JTextField text = (JTextField) comp;
					/*
					 * we check if the type of the entered value matches float
					 * type
					 */
					try {
						text.setText(text.getText().replace(',', '.'));
						if (!text.getText().contains("."))
							text.setText(text.getText() + ".0");
						Float.parseFloat(text.getText());
					} catch (Exception e) {
						text.setText(old_value);
					}
					if (!text.getText().equals(old_value)) {
						node.setCoordonnateY(Float.parseFloat(text.getText()));
						if (universe.getGraph().haveCollision(node)) {
							(new CollisionException()).showError();
							node.setCoordonnateY(Float.parseFloat(old_value));
							text.setText(old_value);
						} else {
							universe.updateGNode(node.getName());
						}
					}
				} else if (attr_name.equals("coord Z")) {
					JTextField text = (JTextField) comp;
					/*
					 * we check if the type of the entered value matches float
					 * type
					 */
					try {
						text.setText(text.getText().replace(',', '.'));
						if (!text.getText().contains("."))
							text.setText(text.getText() + ".0");
						Float.parseFloat(text.getText());
					} catch (Exception e) {
						text.setText(old_value);
					}
					if (!text.getText().equals(old_value)) {
						node.setCoordonnateZ(Float.parseFloat(text.getText()));
						if (universe.getGraph().haveCollision(node)) {
							(new CollisionException()).showError();
							node.setCoordonnateZ(Float.parseFloat(old_value));
							text.setText(old_value);
						} else {
							universe.updateGNode(node.getName());
						}
					}
				} else if (attr_name.equals("radius")) {
					JTextField text = (JTextField) comp;
					/*
					 * we check if the type of the entered value matches float
					 * type
					 */
					try {
						text.setText(text.getText().replace(',', '.'));
						if (!text.getText().contains("."))
							text.setText(text.getText() + ".0");
						float radius = Float.parseFloat(text.getText());
						if (radius <= -0.1) {
							new GException(
									"Negative value",
									"Negative values are not allowed for radius."
											+ "\nThe value will be set to the absolute value.",
									GException.WARNING).showError();
							text.setText("" + Math.abs(radius));
						} else if (radius > -0.1 && radius < 0.1) {
							String s = "";
							if (radius < 0)
								s = " and negative";
							new GException(
									"Too small value",
									"This value is too small"
											+ s
											+ ". The radius will be set to \"0.1\" .",
									GException.WARNING).showError();
							text.setText("0.1");
						}
					} catch (Exception e) {
						text.setText(old_value);
					}
					if (!text.getText().equals(old_value)) {
						node.setRadius(Float.parseFloat(text.getText()));
						if (universe.getGraph().haveCollision(node)) {
							(new CollisionException()).showError();
							node.setRadius(Float.parseFloat(old_value));
							text.setText(old_value);
						} else {
							universe.deleteGNode(node.getName());
							universe.addGNode(node);
							
						}
					}
				} else {
					/*
					 * others attributes
					 */
					JTextField text = (JTextField) comp;
					String type = node.getAttributeByName(attr_name)[1];
					/*
					 * we check if the type of the entered value matches the
					 * attribute type
					 */
					try {
						text.setText(text.getText().replace(',', '.'));
						type = node.getAttributeByName(attr_name)[1];
						if (type.equals("float") || type.equals("double"))
							if (text.getText().contains("."))
								text.setText(text.getText() + ".0");
						if (type.equals("float"))
							Float.parseFloat(text.getText());
						else if (type.equals("double"))
							Double.parseDouble(text.getText());
						else if (type.equals("int"))
							Integer.parseInt(text.getText());
					} catch (Exception e) {
						text.setText(old_value);
					}
					if (!text.getText().equals(old_value)) {
						try {
							node.setAttributeByName(attr_name, node
									.getAttributeByName(attr_name)[1], text
									.getText());
						} catch (InvalidAttributeTypeException e) {
							e.printStackTrace();
							e.showError();
						} catch (TooMuchAttributesForClassException e) {
							e.printStackTrace();
							e.showError();
						}
					}
				}// if
			}// if element instanceof GLink

		}
	}// inner class

	/**
	 * this inner class is used to perform the action associated to a click on a
	 * "true/false" radio button.
	 * 
	 * @author lino christophe
	 * 
	 */
	class RadioButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent m) {
			JRadioButton but = (JRadioButton) m.getSource();
			String attr_name = table_components.get(but);
			if (element instanceof GLink) {
				GLink link = (GLink) element;
				try {
					link.setAttributeByName(attr_name, link
							.getAttributeByName(attr_name)[1], but.getText());
				} catch (InvalidAttributeTypeException e) {
					e.printStackTrace();
					e.showError();
				} catch (TooMuchAttributesForClassException e) {
					e.printStackTrace();
					e.showError();
				} catch (GException e) {
					e.printStackTrace();
					e.showError();
				}
			} else { // GNode
				GNode node = (GNode) element;
				try {
					node.setAttributeByName(attr_name, node
							.getAttributeByName(attr_name)[1], but.getText());
				} catch (InvalidAttributeTypeException e) {
					e.printStackTrace();
					e.showError();
				} catch (TooMuchAttributesForClassException e) {
					e.printStackTrace();
					e.showError();
				}
			}// if
		}// mouseClicked
	}// inner class RadioButtonListener

	/**
	 * this method (re)initialize the table of known graph element types
	 */
	public static void setTableTypes(Hashtable<Class, String> _table_types) {
		if (_table_types.size() != 0 || table_types == null)
			table_types = _table_types;
	}// setTableTypes

	public void itemStateChanged(ItemEvent e) {
		if (this.element instanceof GLink) {
			String color = ((String)e.getItem());
			GLink link = ((GLink)this.element);
			if (!color.equals(link.getColor())) {
				link.setColor(color);
				universe.deleteGLink(link.getName());
				universe.addGLink(link);
			}
		}
	}

}// class GTab
