package model.mine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.minefield.Minefield;

@SuppressWarnings({ "unused", "serial" })
public class Mine extends JPanel implements MouseListener{
	//state
	public static final int ORIGIN=1;
	public static final int FLAG=2;
	public static final int QUESTION=3;
	public static final int PRESSED=4;
	public static final int OPENED=5;
	
	private int state=ORIGIN;//雷的状态
	private int feature=0;//雷的特点，9表明该块为雷，0~8表明周围雷的个数
	//先行加载完毕
    final static Image ORIGINP=new ImageIcon("img/origin.gif").getImage();
    final static Image FLAGP=new ImageIcon("img/flag.gif").getImage();
    final static Image QUESTIONP=new ImageIcon("img/question.gif").getImage();
    final static Image PRESSEDP=new ImageIcon("img/pressed.gif").getImage();
    final static Image[] OPENEDP=new Image[12];//加载图片，0~8表明周围雷的个数，9表明该块为雷，10为叉雷，11为红雷
	{
		for(int i=0;i<12;i++){
			OPENEDP[i]=new ImageIcon("img/"+i+".gif").getImage();
		}
	}
	
	private int x=0;
	private int y=0;

	private static boolean tempclick[]=new boolean[]{false,false,false};//临时储存单击类型
//================================================================================
	public Mine(int x,int y, int feature){
		this.x=x;
		this.y=y;
		this.feature=feature;
		addMouseListener(this);
	}
	public void add(){
		addMouseListener(this);
	}
	public void remove(){
		removeMouseListener(this);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		switch(state){
		case ORIGIN:
			g.drawImage(ORIGINP, 0, 0, getWidth(), getHeight(), this);
			break;
		case FLAG:
			//g.drawImage(FLAGP, 0, 0, getWidth(), getHeight(), this);
			g.setColor(Color.BLACK);
			break;
		case QUESTION:
			g.drawImage(QUESTIONP, 0, 0, getWidth(), getHeight(), this);
			break;
		case PRESSED:
			g.drawImage(PRESSEDP, 0, 0, getWidth(), getHeight(), this);
			break;
		case OPENED:
			g.drawImage(OPENEDP[feature], 0, 0, getWidth(), getHeight(), this);
			break;
		}
	}
	
    @Override
    public void mouseClicked(MouseEvent e) {
	    // TODO Auto-generated method stub
	
    }
    @Override
    public void mouseEntered(MouseEvent e) {
	    // TODO Auto-generated method stub
	
    }
    @Override
    public void mouseExited(MouseEvent e) {
	    // TODO Auto-generated method stub
	
    }
    @SuppressWarnings("static-access")
	@Override
    public void mousePressed(MouseEvent e) {
	    // TODO Auto-generated method stub
	    if(e.getButton()==e.BUTTON1){
	   	    Minefield.mouseClick(x,y,Minefield.PRESSED_LEFT);//左击按下一个变为PRESSED
		    tempclick[0]=true;
	    }
        if(e.getButton()==e.BUTTON3){
		    tempclick[1]=true;
	    }
	    if(tempclick[0]==true&&tempclick[1]==true){
	    	Minefield.mouseClick(x,y,Minefield.PRESSED_LEFT+Minefield.PRESSED_RIGHT);//左右按下多个变为PRESSED
	    	tempclick[2]=true;
	    }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
	    // TODO Auto-generated method stub
	    if(tempclick[0]==true&&tempclick[1]==false&&tempclick[2]==false){
	    	Minefield.mouseClick(x,y,Minefield.RELEASE_LEFT);//一个被左击释放打开
		    tempclick[0]=false;
	    }else if(tempclick[0]==false&&tempclick[1]==true&&tempclick[2]==false){
	    	Minefield.mouseClick(x,y,Minefield.RELEASE_RIGHT);//一个被右击标记
		    tempclick[1]=false;
	    }else if(tempclick[0]==true&&tempclick[1]==true&&tempclick[2]==true){
	    	Minefield.mouseClick(x,y,Minefield.RELEASE_LEFT+Minefield.RELEASE_RIGHT);//多个变回ORIGIN
		    tempclick[0]=false;
		    tempclick[1]=false;
		    tempclick[2]=false;
	    }
    }
    
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getFeature() {
		return feature;
	}
	public void setFeature(int feature) {
		this.feature = feature;
	}
}
