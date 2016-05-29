package view.information;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.minefield.Minefield;

@SuppressWarnings({ "serial", "unused" })
public class Information extends JPanel{
	private static JLabel timeelement;
	private static JLabel minenumelement;
	private static int time;
	private static Timer timer;
	public static Face face;
//===============================================================================
	public Information(){
		timeelement=new JLabel();
		minenumelement=new JLabel("",JLabel.RIGHT);
		face=new Face();
		timer=new Timer(1000,new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(time<1000){
					time++;
					refreshTimeelement();
				}		
			}		
		});
//		GridBagLayout gridbag=new GridBagLayout();
//		GridBagConstraints cs=new GridBagConstraints();
//		setLayout(new GridLayout(1,3));
//		face.setBounds(50, 0, 20, 20);
		add(timeelement);
		add(face);
		add(minenumelement);
		face.setBounds(50, 0, 20, 20);
		refreshTimeelement();
		refrsehMinenumelement();
		setPreferredSize(new Dimension(0,30));
		setBackground(Color.WHITE);

	}
	public static void start(){
		timer.start();
	}
	public static void stop(){
		timer.stop();
	}
	public static void reFresh(){
	    timer.stop();
	    time=0;
	    refreshTimeelement();
	    refrsehMinenumelement();
	}
	public static void refreshTimeelement() {
		timeelement.setText("  Ê±¼ä : "+time);
	}		
	public static void refrsehMinenumelement() {
        minenumelement.setText("  Ê£ÓàÀ×Êý : "+Minefield.getLeftminenumf());
	}
}
