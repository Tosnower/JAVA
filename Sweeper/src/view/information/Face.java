package view.information;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.minefield.Minefield;

public class Face extends JPanel implements MouseListener{ 
	public static final int SMILE=1;
    public static final int SAD=2;
    public static final int HAPPY=3;
    public static final int O=4;
	private final static Image SMILEP=new ImageIcon("img/smile.gif").getImage();
	private final static Image SADP=new ImageIcon("img/sad.gif").getImage();
	private final static Image HAPPYP=new ImageIcon("img/happy.gif").getImage();
	private final static Image OP=new ImageIcon("img/o.gif").getImage();
	private static int state=SMILE;
//================================================================================
	public Face(){
		setPreferredSize(new Dimension(20,20));
		addMouseListener(this);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		switch(getState()){
		case SMILE:
			g.drawImage(SMILEP, 0, 0, getWidth(), getHeight(), this);
			break;
		case SAD:
			g.drawImage(SADP, 0, 0, getWidth(), getHeight(), this);
			break;
		case HAPPY:
			g.drawImage(HAPPYP, 0, 0, getWidth(), getHeight(), this);
			break;
		case O:
			g.drawImage(OP, 0, 0, getWidth(), getHeight(), this);
			break;
		}
	}	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Minefield.reStart();
	}
	public static int getState() {
		return state;
	}
	public static void setState(int state) {
		Face.state = state;
	}
	
}
