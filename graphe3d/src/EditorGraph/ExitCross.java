package editorGraph;

import graph3d.elements.GLink;
import graph3d.lists.GAttributesList;

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
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;


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
    private final GAttributesList attr_list;
    private final GEditor editor;
    private final GTab tab;

    /**
     * 
     * @param _title
     * @param _pane
     * @param _editor
     * @param _tab
     */
    private ExitCross(String _title, final GAttributesList _attrList, final GEditor _editor, GTab _tab) {
    	//unset default FlowLayout' gaps
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.attr_list = _attrList;
        this.editor = _editor;
        this.tab = _tab;
        setOpaque(false);
        
        //tab button
        JButton button = new ExitButton();
        add(button);
        //add more space to the top of the component
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
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


