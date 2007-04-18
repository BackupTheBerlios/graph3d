package editorGraph;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Tests {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setLayout(new GridLayout(1,1));
		f.setBounds(200,200,200,200);
		JPanel p = new JPanel(new GridLayout(10,1));
		p.setSize(200, 400);
		for(int i=0;i<10;i++) p.add(new JSpinner(new SpinnerNumberModel(0,-2,2,1)));
		f.add(new JScrollPane(p));
		f.setVisible(true);
	}
	
}