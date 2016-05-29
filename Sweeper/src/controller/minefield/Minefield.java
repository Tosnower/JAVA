package controller.minefield;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import controller.template.Template;

import model.mine.Mine;
import view.information.Face;
import view.information.Information;

@SuppressWarnings("serial")
public class Minefield extends JPanel{
    public static final int PRESSED_LEFT=1;//按下左键
    public static final int PRESSED_RIGHT=3;//按下右键
    public static final int RELEASE_LEFT=5;//释放左键
    public static final int RELEASE_RIGHT=7;//释放右键
    
    public static final int WIN=0;
    public static final int LOSE=1;
    
	private static int row=9;	
	private static int col=9;	
	private static int minenum=10;	//地雷数
	private static int leftnum; //剩余的块数
	private static int  leftminenumf;//剩余的假的地雷数
	private static int  leftminenumt;//剩余的真的地雷数
	private static boolean isEnd=false;	//是否结束
	private static boolean isFirstClick=true;   //是否第一次点击
	
	private static int[][]template;
	private static Mine[][]mines;
//============================================================================
    public Minefield() {
    	leftnum=col*row;
    	leftminenumf=minenum;//!!!!!!!!
    	leftminenumt=minenum;//!!!!!!!!
    	template=new Template().getTemplate();
    	mines=new Mine[row][col];
    	for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				mines[i][j]=new Mine(i,j,template[i][j]);
				add(mines[i][j]);
			}
		}
        setLayout(new GridLayout(Minefield.getRow(),Minefield.getCol(),0,0));
        setPreferredSize(new Dimension(45*Minefield.getCol(),45*Minefield.getRow()));
    }
	
    public static boolean isWin(){//
    	boolean flag=false;
    	if(leftminenumt==0&&leftnum==0&&leftminenumf>=0){//如果所有的块都被打开，则胜利
    		flag=true;
    		//leftminenumt==leftminenumf
    		//leftnum==leftminenumt判断剩下的块都是雷
    	}else if(leftminenumt==leftminenumf&&leftnum==leftminenumt){
    		flag=true;
    	}
    	return flag;
    	
    }
	public static void showAllmines(){
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				if(mines[i][j].getState()!=Mine.OPENED&&mines[i][j].getState()!=Mine.FLAG&&mines[i][j].getFeature()==9){
					mines[i][j].setState(Mine.OPENED);
					mines[i][j].repaint();
				}
				else if(mines[i][j].getState()==Mine.FLAG&&mines[i][j].getFeature()!=9){
					mines[i][j].setFeature(10);
					mines[i][j].setState(Mine.OPENED);
					mines[i][j].repaint();
				}
			}
		}
	}
	public static void reFresh(){
		template=new Template().getTemplate();
		leftnum=col*row;
    	leftminenumf=minenum;//!!!!!!!!
    	leftminenumt=minenum;//!!!!!!!!
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				mines[i][j].setState(Mine.ORIGIN);
				mines[i][j].setFeature(template[i][j]);
				mines[i][j].repaint();
			}
		}
	}
	public static void endGame(int overstate){
		isEnd=true;
		isFirstClick=true;
		Information.stop();
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				mines[i][j].remove();
			}
		}
		if(overstate==WIN){
			Face.setState(Face.HAPPY);
			Information.face.repaint();
			JOptionPane.showMessageDialog(null,"You Win!");
		}
		else if(overstate==LOSE){
			Face.setState(Face.SAD);
			Information.face.repaint();
			showAllmines();
			JOptionPane.showMessageDialog(null,"You Lose!");
		}
	}
	public static void reStart(){
		if(isEnd==true){
			for(int i=0;i<row;i++){
				for(int j=0;j<col;j++){
					mines[i][j].add();
				}
			}
		}
		Face.setState(Face.SMILE);
		Information.face.repaint();
		isEnd=false;
		isFirstClick=true;
		reFresh();
		Information.reFresh();
	}
	public static void mouseClick(int i,int j,int clicktype){
		if(isFirstClick==true&&isEnd==false){
			isFirstClick=false;
			Information.start();
		}
		if(clicktype==PRESSED_LEFT&&mines[i][j].getState()==Mine.ORIGIN){
			//左键按下
			mines[i][j].setState(Mine.PRESSED);
			Face.setState(Face.O);
			Information.face.repaint();
			mines[i][j].repaint();
		}
		if(clicktype==RELEASE_LEFT&&isEnd==false&&(mines[i][j].getState()==Mine.ORIGIN||mines[i][j].getState()==Mine.QUESTION||mines[i][j].getState()==Mine.PRESSED)){
			//左键释放
			--leftnum;
			Face.setState(Face.SMILE);
			Information.face.repaint();
			if(mines[i][j].getFeature()==9){				
				mines[i][j].setFeature(11);
				mines[i][j].setState(Mine.OPENED);
				mines[i][j].repaint();
				endGame(LOSE);
			}else if(mines[i][j].getFeature()!=0){
				mines[i][j].setState(Mine.OPENED);
				mines[i][j].repaint();
				if(isWin()==true){
					endGame(WIN);
				}
			}else if(mines[i][j].getFeature()==0){
				mines[i][j].setState(Mine.OPENED);
				mines[i][j].repaint();
				if(isWin()==true){
					endGame(WIN);
					return;
				}
				for(int x=-1;x<2;x++){
					for(int y=-1;y<2;y++){
						if((i+x)>=0&&(i+x)<Minefield.getRow()&&(j+y)>=0&&(j+y)<Minefield.getCol())
							mouseClick(i+x,j+y,RELEASE_LEFT);
					}
				}
			}
		}else if(clicktype==RELEASE_RIGHT&&isEnd==false&&mines[i][j].getState()!=Mine.OPENED){
			//右键释放
			if(mines[i][j].getState()==Mine.ORIGIN){
				--leftminenumf;
				--leftnum;
				if(mines[i][j].getFeature()==9){
					--leftminenumt;
				}
				mines[i][j].setState(Mine.FLAG);
				mines[i][j].repaint();
				Information.refrsehMinenumelement();
				if(isWin())
					endGame(WIN);
			}else if(mines[i][j].getState()==Mine.FLAG){
				++leftminenumf;
				++leftnum;
				if(mines[i][j].getFeature()==9){
					++leftminenumt;
				}
				mines[i][j].setState(Mine.QUESTION);
				mines[i][j].repaint();
				Information.refrsehMinenumelement();
			}else if(mines[i][j].getState()==Mine.QUESTION){
				mines[i][j].setState(Mine.ORIGIN);
				mines[i][j].repaint();
			}
		}else if(clicktype==(PRESSED_LEFT+PRESSED_RIGHT)&&isEnd==false){
			//双键按下
			for(int x=-1;x<2;x++){
				for(int y=-1;y<2;y++){
					if((i+x)>=0&&(i+x)<Minefield.getRow()&&(j+y)>=0&&(j+y)<Minefield.getCol()&&mines[i+x][j+y].getState()==Mine.ORIGIN){
						mines[i+x][j+y].setState(Mine.PRESSED);
						mines[i+x][j+y].repaint();
					}
				}
	    	}
		}else if(clicktype==(RELEASE_LEFT+RELEASE_RIGHT)&&isEnd==false){
			//双键释放
			for(int x=-1;x<2;x++){
				for(int y=-1;y<2;y++){
					if((i+x)>=0&&(i+x)<Minefield.getRow()&&(j+y)>=0&&(j+y)<Minefield.getCol()&&mines[i+x][j+y].getState()==Mine.PRESSED){
						mines[i+x][j+y].setState(Mine.ORIGIN);
						mines[i+x][j+y].repaint();
					}
				}
	    	}
			if(mines[i][j].getState()==Mine.OPENED){
				int num=0;
				for(int x=-1;x<2;x++){
					for(int y=-1;y<2;y++){
						if((i+x)>=0&&(i+x)<Minefield.getRow()&&(j+y)>=0&&(j+y)<Minefield.getCol()&&mines[i+x][j+y].getState()==Mine.FLAG){
							num++;
						}
					}
				}
				if(num==mines[i][j].getFeature()){
					for(int x=-1;x<2;x++){
						for(int y=-1;y<2;y++){
							if((i+x)>=0&&(i+x)<Minefield.getRow()&&(j+y)>=0&&(j+y)<Minefield.getCol()&&(mines[i+x][j+y].getState()==Mine.ORIGIN||mines[i+x][j+y].getState()==Mine.QUESTION)){
								mouseClick(i+x,j+y,Minefield.RELEASE_LEFT);
							}
						}
					}
				}
			}
		}
	}
	
	
	public static int getRow() {
		return row;
	}
	public static void setRow(int row) {
		Minefield.row = row;
	}
	public static int getCol() {
		return col;
	}
	public static void setCol(int col) {
		Minefield.col = col;
	}
	public static int getMinenum() {
		return minenum;
	}
	public static void setMinenum(int minenum) {
		Minefield.minenum = minenum;
	}
	public int getLeftnum() {
		return leftnum;
	}
	@SuppressWarnings("static-access")
	public void setLeftnum(int leftnum) {
		this.leftnum = leftnum;
	}
	public static int getLeftminenumf() {
		return leftminenumf;
	}
	@SuppressWarnings("static-access")
	public void setLeftminenum(int leftminenumf) {
		this.leftminenumf = leftminenumf;
	}
	public static boolean isEnd() {
		return isEnd;
	}
	public static void setEnd(boolean isEnd) {
		Minefield.isEnd = isEnd;
	}
	public static boolean isFirstClick() {
		return isFirstClick;
	}
	public static void setFirstClick(boolean isFirstClick) {
		Minefield.isFirstClick = isFirstClick;
	}
	public static int getLeftminenumt() {
		return leftminenumt;
	}

	public static void setLeftminenumt(int leftminenumt) {
		Minefield.leftminenumt = leftminenumt;
	}
}
