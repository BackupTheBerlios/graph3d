package graph3d.use;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelCaracteristique extends JPanel{
	
	public PanelCaracteristique(){
	
		// panel caractéristiques
		JPanel panelDroiteHaut=new JPanel();
		panelDroiteHaut.setLayout(new GridLayout(2,1));
		TitledBorder borderDroiteHaut = new TitledBorder(new EtchedBorder(),"Caractéristiques");
		panelDroiteHaut.setBorder(borderDroiteHaut);
			
			panelDroiteHaut.add(new JLabel("A completer ..."));
			
		this.add(panelDroiteHaut);
	
	}

}
