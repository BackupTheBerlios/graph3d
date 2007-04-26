package graph3d.use;

import graph3d.universe.GGrapheUniverse;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelButtonInteraction extends JPanel{
	
	private JButton bZoomPlus,bZoomMoins,boutonHaut,boutonGauche,boutonDroite,boutonBas,boutonCentrer;
	private GGrapheUniverse universe;
	
	public PanelButtonInteraction(GGrapheUniverse universe){
		
		this.universe=universe;
		
	RecepBouton myRecepBouton=new RecepBouton();	
	
	// panel interaction 
	JPanel panelDroiteBas=new JPanel();
	panelDroiteBas.setLayout(new GridLayout(3,1,5,5));
	TitledBorder borderDroiteBas = new TitledBorder(new EtchedBorder(),"Interaction");
	panelDroiteBas.setBorder(borderDroiteBas);

	
		// boutons du zoom
		JPanel panelZoom=new JPanel();
		panelZoom.setLayout(new GridLayout(1,2,5,5));
		bZoomPlus =new JButton("+");
		bZoomPlus.addActionListener(myRecepBouton);
		panelZoom.add(bZoomPlus);
			bZoomMoins =new JButton("-");
			bZoomMoins.addActionListener(myRecepBouton);
			panelZoom.add(bZoomMoins);
		panelDroiteBas.add(panelZoom);
			
		// boutons deplacement de la camera
		JPanel panelInteraction=new JPanel();
		panelInteraction.setLayout(new GridLayout(3,1));
		
			JPanel panInteracHaut=new JPanel();
			panInteracHaut.setLayout(new GridLayout(1,3));
				panInteracHaut.add(new JLabel(""));
					boutonHaut =new JButton("^");
					boutonHaut.addActionListener(myRecepBouton);
					panInteracHaut.add(boutonHaut);
						panInteracHaut.add(new JLabel(""));
				panelInteraction.add(panInteracHaut);
			JPanel panInteracMilieu=new JPanel();
			panInteracMilieu.setLayout(new GridLayout(1,3));
				boutonGauche =new JButton("<");
				boutonGauche.addActionListener(myRecepBouton);
				panInteracMilieu.add(boutonGauche);
					panInteracMilieu.add(new JLabel(""));
						boutonDroite =new JButton(">");
						boutonDroite.addActionListener(myRecepBouton);
						panInteracMilieu.add(boutonDroite);
				panelInteraction.add(panInteracMilieu);
			JPanel panInteracBas=new JPanel();
			panInteracBas.setLayout(new GridLayout(1,3));
				panInteracBas.add(new JLabel(""));
					boutonBas=new JButton("v");
					boutonBas.addActionListener(myRecepBouton);
				 	panInteracBas.add(boutonBas);
				 		panInteracBas.add(new JLabel(""));
			panelInteraction.add(panInteracBas);
			
		panelDroiteBas.add(panelInteraction);
		
		// bouton centrer la vue
		boutonCentrer=new JButton("Centrer la vue");
		boutonCentrer.addActionListener(myRecepBouton);
		panelDroiteBas.add(boutonCentrer);
		
	this.add(panelDroiteBas);
	
	}
	
	
	class RecepBouton implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==bZoomPlus){
				universe.zoomMore();
			}
			if(e.getSource()==bZoomMoins){
				universe.zoomLess();
			}
			if(e.getSource()==boutonHaut){
				universe.rotateTop();
			}
			if(e.getSource()==boutonBas){
				universe.rotateBottom();
			}
			if(e.getSource()==boutonGauche){
				universe.rotateLeft();
			}
			if(e.getSource()==boutonDroite){
				universe.rotateRight();
			}
			if(e.getSource()==boutonCentrer){
				universe.centerView();
			}
		}
	}
}
