package graph3d.lists;

import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.BadElementTypeException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * this class implements the connections list for graph display, wich contains
 * all the elements which are associated to the current selection. When you
 * click on an item of this list, this one will be added in the selections, if
 * there is an attributes list which is attached to it.<br>
 * 
 * @author lino christophe
 * @version 1.0
 * @since JDK 1.5
 */
public class GConnectionsList extends JScrollPane {

	/*
	 * area of selection list with elements which are associated to current tab
	 */
	private JList list;

	/*
	 * hashtable to access to the GNode or GLink elements contained in the list
	 */
	private Hashtable<String, Object> table;

	/*
	 * the attached AttributesList can be null if no AttributesList is attached
	 */
	private GAttributesList attributesList;

	/**
	 * constructs a list area with an initialized JList.
	 */
	public GConnectionsList() {
		/*
		 * creation from an empty list
		 */
		super();
		this.list = new JList();
		table = new Hashtable<String, Object>();
		setViewportView(list);
		/*
		 * area naming
		 */
		setBorder(new TitledBorder(new EtchedBorder(), "Elements associés"));
	}// construct

	/**
	 * this method ensures to attach an AttributesList. That list will attach
	 * this connections lists as well. So it is not util to attach it to
	 * interact with the attributes list.
	 * 
	 * @param _attrList
	 *            the attributes list to attach.
	 */
	public void attachAttributeList(GAttributesList _attrList) {
		if (attributesList != _attrList) {
			this.list.addMouseListener(new ConnectionsListListener());
			this.attributesList = _attrList;
			_attrList.attachConnectionsList(this);
		}
	}

	/**
	 * show an array of elements into the list (removes old elements from the
	 * list).<br>
	 * be careful : all the elements must have the same type, so the array must
	 * be of type GNode[] or GLink[].
	 * 
	 * @param components
	 *            the array of components to show in the list
	 * @throws BadElementTypeException
	 *             if the array is not of type GNode[] or GLink[]
	 */
	public void show(Object[] components) throws BadElementTypeException {
		table = new Hashtable<String, Object>();
		String[] names = new String[components.length];

		if (components instanceof GNode[]) {
			for (int i = 0; i < components.length; i++) {
				GNode node = (GNode) components[i];
				names[i] = node.getName();
				if (!table.containsKey(names[i]))
					// it is not a loop
					table.put(names[i], node);
				else {
					// it is a loop : the two nodes are identical
					names = new String[] { names[0] };
				}
			}// for
		} else if (components instanceof GLink[]) {
			for (int i = 0; i < components.length; i++) {
				GLink link = (GLink) components[i];
				names[i] = link.getName();
				table.put(link.getName(), link);
			}// for
		} else if (components.length == 0) {
			names = new String[0];
		} else
			throw new BadElementTypeException("graph element");
		list.setListData(names);
	}// show

	/**
	 * this class is used to do actions when a double click is performed on one
	 * or several item(s).
	 * 
	 * @author lino christophe
	 * 
	 */
	class ConnectionsListListener extends MouseAdapter {
		public void mouseClicked(MouseEvent m) {
			if (m.getClickCount() == 2) {
				Object[] values = list.getSelectedValues();
				for (int i = 0; i < values.length; i++) {
					GTab tab = (GTab) attributesList.getSelectedComponent();
					Object element = tab.getElement();
					if (element instanceof GNode) {
						GLink link = (GLink) table.get((String) values[i]);
						attributesList.add(link, true);
					} else {
						GNode node = (GNode) table.get((String) values[i]);
						attributesList.add(node, true);
					}// if
				}// if
			}// for
		}// mouseClicked
	}// inner class ListListener

}// class GConnectionsList
