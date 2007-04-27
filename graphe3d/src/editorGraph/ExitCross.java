package editorGraph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

import graph3d.lists.GAttributesList;


/**
 * Component to be used as exit button;
 * Contains a JButton to close the tab it belongs to<br>
 * <br>
 * this class is a package class
 * 
 * @author lino christophe
 * @version 1.0
 * @since JDK 1.5
 */ 
class ExitCross extends JPanel {
   
	/*
	 * associated GAttributeList
	 * can be null if the tab is inserted into an editor
	 * (in this case, the "editor" attribute cannot be null)
	 */
	private final GAttributesList attr_list;
    
	/*
	 * associated GEditor
	 * can be null if the tab is inserted into a GAttributesList
	 * (in this case, the "attr_list" attribute cannot be null)
	 */
	private final GEditor editor;
    
	/*
	 * the tab where the exit cross is inserted
	 */
	private final GTab tab;

    /**
     * constructs an ExitCross to inserted ether in a GEditor or in a GAttributesList.
     * It is impossible to create an Exit cross with a null GattributesList and a null GEditor.
     * @param _title
     * 		
     * @param _attrList
     * 		the GAttributesList where the tab will be inserted.
     * 		(can be null, in this case _editor is not null)
     * @param _editor
     * 		the GEditorwhere the tab will be inserted.
     * 		(can be null, in this case _attrList is not null)
     * @param _tab
     * 		the GTab element wher the exit cross will be set
     */
    private ExitCross(String _title, final GAttributesList _attrList, final GEditor _editor, GTab _tab) {
        super(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        this.attr_list = _attrList;
        this.editor = _editor;
        this.tab = _tab;
        setOpaque(false);
        
        /*
         * we create a label for the exit cross
         */
        JLabel label = new JLabel(_title);
        add(label);
        /*
         *  we create the exit button and add it in the panel
         */
        JButton button = new ExitButton();
        add(button);
        
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    }

    /**
     * 
     * @param _title
     * @param _attrList
     * @param _tab
     */
    public ExitCross(String _title, final GAttributesList _attrList, GTab _tab) {
    	this(_title, _attrList, null, _tab);
    }
    
    /**
     * 
     * @param _title
     * @param _editor
     * @param _tab
     */
    public ExitCross(String _title, final GEditor _editor, GTab _tab) {
    	this(_title, null, _editor, _tab);
    }
    
    /**
     * 
     * @author lino christophe
     *
     */
    private class ExitButton extends JButton implements ActionListener {
        public ExitButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("enlever de la sélection");
            //Make the button looks the same for all Laf's
            setUI(new BasicButtonUI());
            //Make it transparent
            setContentAreaFilled(false);
            //No need to be focusable
            setFocusable(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            //Making nice rollover effect
            //we use the same listener for all buttons
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            //Close the proper tab by clicking the button
            addActionListener(this);            
        }
        
        /**
         * 
         */
        public void actionPerformed(ActionEvent e) {
        	try{
        		editor.unselect();
        	}catch (Exception e1) {
				try{
					attr_list.remove(tab.getElement());
				}catch (Exception e2) {
					System.err.println("erreur exirt cross actionPerformed");
				}
			}
        }

        //we don't want to update UI for this button
        public void updateUI() {
        }

        //paint the cross
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            //shift the image for pressed buttons
            if (getModel().isPressed()) {
                g2.translate(1, 1);
            } 
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (getModel().isRollover()) {
                g2.setColor(Color.RED);
            }            
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}


