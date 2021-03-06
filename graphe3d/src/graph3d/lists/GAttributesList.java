package graph3d.lists;

import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.ASCIIFileNotFoundException;
import graph3d.exception.BadElementTypeException;
import graph3d.exception.GException;
import graph3d.exception.InexistantClassException;
import graph3d.universe.GGraphUniverse;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * this class implements the attributes list of selections for graph display,
 * which displays all the attributes of the selected graph elements.<br>
 * It can be linked to a GGraphUniverse object to receive selections from the
 * graph itself, and can be attached to a GConnectionsList object to receive
 * selections from the list of elements which are connected with the current
 * element in the list.<br>
 * Attributes of selected elements can be editable or not.
 * 
 * @author lino christophe
 * @since JDK 1.5
 * @version 1.0
 */
public class GAttributesList extends JTabbedPane {

	/*
	 * the graph universe which is linked to the list (can be null)
	 */
	private GGraphUniverse universe;

	/*
	 * the whole list of selected graph elements
	 */
	private LinkedList<Object> elements;

	/*
	 * the number of selected nodes
	 */
	private int nb_nodes;

	/*
	 * flags to check if the attributes are editable and if exit cross have to
	 * be shown
	 */
	boolean editable;

	/*
	 * lists of buttons which have to be be clickable or not according if there
	 * is at least one node or link or not.
	 */
	Hashtable<JComponent, Integer> attached_to_nodes, attached_to_links,
			attached_to_both;

	/*
	 * the connections list which is attached (can be null)
	 */
	private GConnectionsList connectionsList;

	/**
	 * constructs a non-editable GAttributesList linked to a GGraphUniverse The
	 * known types of graph elements will be loaded from the ascii file.
	 * 
	 * @param _Universe
	 *            the graph universe wivh is associated to the list (can be
	 *            null)
	 * @param _ascii_file
	 *            the name of the file which contains all the known types of
	 *            graph elements (can be null, in this case only basic types
	 *            "node" and "link" will be known)
	 * @throws GException
	 *             if the list is set editable and _Universe is null.
	 */
	public GAttributesList(GGraphUniverse _Universe, String _ascii_file)
			throws GException {
		this(_Universe, _ascii_file, false);
	}

	/**
	 * constructs a GAttributesList linked to a GGraphUniverse (or not if null),
	 * and which is editable or not. The known types of graph elements will be
	 * loaded from the ascii file.
	 * 
	 * @param _Universe
	 *            the graph universe wivh is associated to the list (can be
	 *            null)
	 * @param _ascii_file
	 *            the name of the file which contains all the known types of
	 *            graph elements. if it is null or if it cannot be found, only
	 *            basic "node" and "link" types will be loaded
	 * @param _editable
	 *            if the attributes will be editable or not
	 * @throws GException
	 *             if the list is set editable and _Universe is null.
	 */
	public GAttributesList(GGraphUniverse _Universe, String _ascii_file,
			boolean _editable) throws GException {
		this(_Universe, getTypes(_ascii_file), _editable);
		if (_editable && _Universe == null)
			throw new GException(
					"_Universe argument cannot be null because the tab is set editable !");
	}

	/**
	 * constructs a non-editable GAttributesList linked to a GGraphUniverse The
	 * known types of graph elements will be loaded from the ascii file.
	 * 
	 * @param _Universe
	 *            the graph universe wivh is associated to the list (can be
	 *            null)
	 * @param _table_types
	 *            the table of the already known graph elements types
	 */
	public GAttributesList(GGraphUniverse _Universe,
			Hashtable<Class, String> _table_types) {
		this(_Universe, _table_types, false);
	}

	/**
	 * constructs a non-editable GAttributesList linked to a GGraphUniverse The
	 * known types of graph elements will be loaded from the ascii file.
	 * 
	 * @param _Universe
	 *            the graph universe wivh is associated to the list (can be
	 *            null)
	 * @param _table_types
	 *            the table of the already known graph elements types
	 * @param _editable
	 *            if the tabs have to be set editable or not
	 */
	public GAttributesList(GGraphUniverse _Universe,
			Hashtable<Class, String> _table_types, boolean _editable) {
		super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		this.universe = _Universe;
		this.elements = new LinkedList<Object>();
		this.editable = _editable;
		this.nb_nodes = 0;
		this.attached_to_links = new Hashtable<JComponent, Integer>();
		this.attached_to_nodes = new Hashtable<JComponent, Integer>();
		this.attached_to_both = new Hashtable<JComponent, Integer>();
		setBorder(new TitledBorder(new EtchedBorder(), "Elements sélectionnés"));
		GTab.setTableTypes(_table_types);
	}

	/**
	 * this method provides the addition of a graph element in the selection.<br>
	 * the element will become the current element in the list
	 * 
	 * @param component
	 */
	public void add(Object component) {
		this.add(component, true);
	}

	/**
	 * this method provides the addition of a graph element in the selection.<br>
	 * If the element is present in the selection it will be unselected, and if
	 * it is not it will be no performed action. warning : the component must be
	 * a graph element.
	 * 
	 * @see GNode
	 * @see GLink
	 * @param component
	 *            the graph element which have to be added
	 * @param focus
	 *            if the element have to become the current element in the list
	 * @throws BadElementTypeException
	 *             if the component cannot be cast into a graph element
	 */
	public void add(Object component, boolean focus) {
		if (!elements.contains(component))
			try {
				GTab tab = new GTab(component, this, this.universe, editable);
				if (component instanceof GNode) {
					GNode node = (GNode) component;
					elements.add(nb_nodes, node);
					insertTab(node.getName(), null, tab, "noeud", nb_nodes);
					nb_nodes++;
				} else if (component instanceof GLink) {
					GLink link = (GLink) component;
					elements.add(link);
					add(tab, link.getName());
					setIconAt(indexOfComponent(tab), null);
					setToolTipTextAt(indexOfComponent(tab), "lien");
				} else {
					BadElementTypeException e = new BadElementTypeException(
							component.toString());
					e.printStackTrace();
					e.showError();
				}
				if (focus || getTabCount() == 1) {
					setSelectedComponent(tab);
					refreshList();
				}

				if (connectionsList != null)
					refreshList();
				checkAttachedComponents();
			} catch (GException e) {
				e.printStackTrace();
				e.showError();
				/*
				 * we must not go here
				 */
			}// try
	}// addTab

	/**
	 * this method provides the removal of a graph element from the selection.<br>
	 * If the element is present in the selection it will be unselected, and if
	 * it is not it will be no performed action. warning : the component must be
	 * a graph element.
	 * 
	 * @param component
	 *            the graph element which have to be removed
	 */
	public void remove(Object component) {
		int index = elements.indexOf(component);
		if (index != -1) {
			if (elements.get(index) instanceof GNode)
				nb_nodes--;
			super.remove(index);
			elements.remove(index);
		}
		if (connectionsList != null)
			refreshList();
		checkAttachedComponents();
	}// removeTab

	/**
	 * this method provides the removal of all graph components which was
	 * contained in the list.
	 */
	public void removeAll() {
		elements = new LinkedList<Object>();
		super.removeAll();
		nb_nodes = 0;
		if (connectionsList != null)
			refreshList();
		checkAttachedComponents();
	}

	/**
	 * this method ensures to attach a ConnectionsList. That list will attach
	 * this attributes lists as well. So it is not util to attach it to interact
	 * with the connections list.
	 * 
	 * @param _connectList
	 *            the connections list to attach.
	 */
	public void attachConnectionsList(GConnectionsList _connectList) {
		if (connectionsList != _connectList) {
			this.addMouseListener(new TabbedPaneListener());
			this.connectionsList = _connectList;
			_connectList.attachAttributeList(this);
			refreshList();
		}
	}

	/**
	 * enables to attach a component which have to be refreshed (to be enabled
	 * or disabled) according if getNodeCount() >= inf_limit.
	 * 
	 * @param comp
	 *            the button which have to be refreshed
	 * @param inf_limit
	 *            the inf limit of the nodes in the list
	 */
	public void attachToNodes(JComponent comp, int inf_limit) {
		if (this.attached_to_nodes.containsKey(comp))
			this.attached_to_nodes.remove(comp);
		this.attached_to_nodes.put(comp, inf_limit);
	}

	/**
	 * enables to attach a button which have to be refreshed (to be enabled or
	 * disabled) according if getLinkCount() >= inf_limit.
	 * 
	 * @param comp
	 *            the button which have to be refreshed
	 * @param inf_limit
	 *            the inf limit of the nodes in the list
	 */
	public void attachToLinks(JComponent comp, int inf_limit) {
		if (this.attached_to_links.containsKey(comp))
			this.attached_to_nodes.remove(comp);
		this.attached_to_links.put(comp, inf_limit);
	}

	/**
	 * enables to attach a button which have to be refreshed (to be enabled or
	 * disabled) according if getNodeCount()+getLinkCount() >= inf_limit.
	 * 
	 * @param comp
	 *            the button which have to be refreshed
	 * @param inf_limit
	 *            the inf limit of the nodes in the list
	 */
	public void attachToBoth(JComponent comp, int inf_limit) {
		if (this.attached_to_both.containsKey(comp))
			this.attached_to_both.remove(comp);
		this.attached_to_both.put(comp, inf_limit);
	}

	/**
	 * sets the tabs editable/not editable
	 * 
	 * @param _editable
	 *            if the tabs have to become editable or not editable
	 */
	public void setEditable(boolean _editable) throws GException {
		if (_editable && this.universe == null)
			throw new GException(
					"You cannot set the tab editable because there is no universe !");
		this.editable = _editable;
		for (int i = 0; i < getTabCount(); i++)
			((GTab) getComponentAt(i)).setEditable(_editable);
	}

	/**
	 * sets the tabs editable/not editable
	 * 
	 * @param _editable
	 *            if the tabs have to become editable or not editable
	 * @throws GException
	 */
	public void setNodesEditable(boolean _editable) throws GException {
		for (int i = 0; i < nb_nodes; i++)
			((GTab) getComponentAt(i)).setEditable(_editable);
	}

	/**
	 * sets the tabs editable/not editable
	 * 
	 * @param _editable
	 *            if the tabs have to become editable or not editable
	 * @throws GException
	 */
	public void setLinksEditable(boolean _editable) throws GException {
		for (int i = nb_nodes; i < getTabCount(); i++)
			((GTab) getComponentAt(i)).setEditable(_editable);
	}

	/**
	 * sets the tabs editable/not editable
	 * 
	 * @param _editable
	 *            if the tabs have to become editable or not editable
	 * @throws GException
	 */
	public void setComponentEditable(Object component, boolean _editable)
			throws GException {
		int index = elements.indexOf(component);
		GTab tab = (GTab) getComponentAt(index);
		tab.setEditable(_editable);
	}

	/**
	 * changes the GGraphUniverse of the GAttributesList.
	 * 
	 * @param _Universe
	 *            the new GGraphUniverse
	 */
	public void setUniverse(GGraphUniverse _Universe) {
		this.universe = _Universe;
		for (int i = 0; i < getTabCount(); i++)
			((GTab) getComponentAt(i)).setUniverse(_Universe);
		this.universe.addSelectionBehavior(this);
	}

	/**
	 * returns all the selected elements as a LinkedList<object>
	 * 
	 * @return the graph elements which are contained in the GAttributesList
	 */
	public LinkedList<Object> getElements() {
		return elements;
	}

	/**
	 * returns the number of nodes in the GAttributesList.
	 * 
	 * @return the number of nodes in the GAttributesList
	 */
	public int getNodeCount() {
		return nb_nodes;
	}

	/**
	 * returns the number of links in the GAttributesList.
	 * 
	 * @return the number of links in the GAttributesList
	 */
	public int getLinkCount() {
		return getTabCount() - getNodeCount();
	}

	/**
	 * this internal class is used to refresh the list of associated elements
	 * when a click is performed on a tab.
	 * 
	 * @author lino christophe
	 */
	class TabbedPaneListener extends MouseAdapter {
		public void mouseClicked(MouseEvent m) {
			refreshList();
		}// mouseClicked
	}// inner class TabbedPaneListener

	/**
	 * checks the number of elements to refresh state of the components which
	 * are attached to nodes, links or both.
	 */
	public void checkAttachedComponents() {
		boolean infLimitAtLeast;
		Enumeration<JComponent> enumer;

		enumer = attached_to_both.keys();
		while (enumer.hasMoreElements()) {
			JComponent comp = enumer.nextElement();
			Integer i = attached_to_both.get(comp);
			infLimitAtLeast = (getTabCount() >= i);
			comp.setEnabled(infLimitAtLeast);
		}

		enumer = attached_to_nodes.keys();
		while (enumer.hasMoreElements()) {
			JComponent comp = enumer.nextElement();
			Integer i = attached_to_nodes.get(comp);
			infLimitAtLeast = (getNodeCount() >= i);
			comp.setEnabled(infLimitAtLeast);
		}

		enumer = attached_to_links.keys();
		while (enumer.hasMoreElements()) {
			JComponent comp = enumer.nextElement();
			Integer i = attached_to_links.get(comp);
			infLimitAtLeast = (getLinkCount() >= i);
			comp.setEnabled(infLimitAtLeast);
		}
	}

	/**
	 * this class is used to implement an attachment between a JButton and an
	 * inf limit of graph elements number
	 * 
	 * @author lino christophe
	 * 
	 */
	class Attach {
		JButton button;

		int inf_limit;

		public Attach(JButton button, int inf_limit) {
			this.button = button;
			this.inf_limit = inf_limit;
		}
	}

	/**
	 * this method is used to update the connections list with elemnts wich are
	 * associated to the current tab
	 */
	private void refreshList() {
		GTab tab = (GTab) getSelectedComponent();
		if (tab != null) {
			Object element = tab.getElement();
			if (element instanceof GNode) {
				GNode node = (GNode) element;
				GLink[] links = node.getLinks().toArray(new GLink[0]);
				String[] names = new String[links.length];
				for (int i = 0; i < names.length; i++)
					names[i] = links[i].getName();
				try {
					connectionsList.show(links);
				} catch (BadElementTypeException e) {
					e.printStackTrace();
					e.showError();
				}
			} else {
				GLink link = (GLink) element;
				GNode[] nodes = new GNode[] { link.getFirstNode(),
						link.getSecondNode() };
				try {
					connectionsList.show(nodes);
				} catch (BadElementTypeException e) {
					e.printStackTrace();
					e.showError();
				}
			}// if
		} else {
			try {
				connectionsList.show(new Object[0]);
			} catch (BadElementTypeException e) {
				e.printStackTrace();
				e.showError();
			}
		}
	}

	/**
	 * this method is used to load the known classes which implements graph
	 * elements from an ascii file
	 * 
	 * @param filename
	 *            String : the name of the file containing all the known classes
	 *            which implements graph elements
	 */
	private static Hashtable<Class, String> getTypes(String filename) {
		filename = (filename == null) ? "" : filename;
		Hashtable<Class, String> table_types = new Hashtable<Class, String>();
		File file = new File(filename);
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line;
			int line_count = 0;
			/*
			 * reading all the lines of the source file
			 */
			while ((line = buf.readLine()) != null) {
				line_count++;
				String data[] = line.split(":");
				try {
					/*
					 * boolean to check if it is a graph element, that's to say
					 * if its type is a GNode or GLink (sub)type
					 */
					boolean graphe_element = false;
					Class cl = Class.forName(data[1]);
					while (cl != null && !graphe_element) {
						if (cl == GNode.class) {
							graphe_element = true;
							/*
							 * we add it as a known (node) type
							 */
							table_types.put(Class.forName(data[1]), data[0]);
						} else if (cl == GLink.class) {
							graphe_element = true;
							/*
							 * we add it as a known (link) type
							 */
							table_types.put(Class.forName(data[1]), data[0]);
						} else
							cl = cl.getSuperclass();
					}// while
					if (!graphe_element)
						(new BadElementTypeException(data[1], line_count))
								.showError();
				} catch (ClassNotFoundException e3) {
					(new InexistantClassException(data[1], line_count))
							.showError();
				} catch (Exception e4) {
					String message = "line "
							+ line_count
							+ " : empty lines or incomplete lines are not allowed !"
							+ "\nthe end of the file will not be read.";
					(new GException("Invalid ASCII file", message,
							GException.ERROR)).showError();
				}// try
			}// while
		} catch (FileNotFoundException e) {
			if (!filename.equals("")) {
				new ASCIIFileNotFoundException(filename).showError();
			}
		} catch (IOException e) {
			System.err.println("an I/O error occurred while reading file : "
					+ filename);
		}// try
		if (!table_types.containsKey(GNode.class))
			table_types.put(GNode.class, "node");
		if (!table_types.containsKey(GLink.class))
			table_types.put(GLink.class, "link");
		return table_types;
	}// getTypes

}// class GAttributesList
