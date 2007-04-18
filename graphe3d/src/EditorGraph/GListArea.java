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
	 * 
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
	 * place des éléments de même type dans la liste (doivent être soit des GNode, soit des GLink).
	 * @param components
	 * @throws BadElementTypeException
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