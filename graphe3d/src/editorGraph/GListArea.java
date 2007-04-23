package editorGraph;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import graph3d.elements.GLink;
import graph3d.elements.GNode;
import graph3d.exception.BadElementTypeException;

/**
 * this class is used to implement the list area of a graph editor,
 * wich contains all the elements which are associated to the current selection.<br>
 * When you click on an item of this list, this one will be added in the selections.<br>
 * <br>
 * this class is a package class
 * 
 * @author lino christophe
 *
 */
class GListArea extends JScrollPane{

	/*
	 * the GEditor owner.
	 */
	GEditor editor;

	/*
	 * area of selection list with current tab associated elements
	 */
	private JList list;

	/*
	 * hashtable to access to the GNode or GLink elements contained in the list
	 */
	private Hashtable<String, Object> table;

	/**
	 * constructs a list area with an initialized JList.
	 * @param list
	 * 		JList : the list to show in the area
	 * @param owner
	 * 	 	GEditor : the editor owner 
	 */
	public GListArea(JList list, GEditor owner) {
		/*
		 * creation from an empty list
		 */
		super(list);
		this.list= list;
		this.editor = owner;
		list.addMouseListener(new ListListener());
		table = new Hashtable<String, Object>();
		/*
		 * nommage de la zone
		 */
		setBorder(new TitledBorder(new EtchedBorder(),"Elements associés"));
	}//construct

	/**
	 * show an array of elements into the list (removes old elements from the list).<br>
	 * be careful : all the elements must have the same type, so the array must be
	 * of type GNode[] or GLink[].
	 * @param components
	 * 		the array of components to show in the list
	 * @throws BadElementTypeException
	 * 		if the array is not of type GNode[] or GLink[]
	 */
	public void show(Object[] components) throws BadElementTypeException{
		table = new Hashtable<String, Object>();
		String[]names = new String[components.length];

		if( components instanceof GNode[] ){			
			for(int i=0; i<components.length; i++){	
				GNode node = (GNode) components[i];
				names[i] = node.getName();
				if( ! table.containsKey(names[i]))
					// it is not a loop
					table.put(names[i], node);
				else{
					// it is a loop : the two nodes are identical 
					names = new String[]{names[0]};
				}
			}//for
		}else if( components instanceof GLink[] ){
			for(int i=0; i<components.length; i++){
				GLink link = (GLink) components[i];
				names[i] = link.getName();
				table.put(link.getName(), link);
			}//for
		}else if (components.length == 0){
			names = new String[0];
		}else
			throw new BadElementTypeException("graph element");
		list.setListData(names);
	}//show

	/**
	 * this class is used to perform actions when double clicking on one or several item(s).
	 * @author lino christophe
	 *
	 */
	class ListListener extends MouseAdapter{
		public void mouseClicked(MouseEvent m){
			if( m.getClickCount() == 2){
				Object[] values = list.getSelectedValues();
				boolean focus = ! (values.length > 1);
				for(int i=0;i<values.length;i++){
					GTab tab = (GTab) editor.tabArea.tabbedpane.getSelectedComponent();
					Object element = tab.getElement();
					if(element instanceof GNode){
						GLink link = (GLink) table.get( (String) values[i] );
						editor.addComponent(link, focus);
					}else{
						GNode node = (GNode) table.get( (String) values[i] );
						editor.addComponent(node, focus);
					}//if
				}//if
			}//for
		}//mouseClicked
	}//inner class ListListener

}//inner class GListArea