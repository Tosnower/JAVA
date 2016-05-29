package view.sweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import controller.minefield.Minefield;
import view.information.Information;
import view.menu.Game;

@SuppressWarnings("unused")
public class Sweeper {
	public static JFrame frame;
	public static Minefield minefield;
	public static Information information;
	public static JMenuBar mb;
	public static JMenu game;
	
//===========================================================================
	public static void startGame(){
		if(frame!=null){
			frame.dispose();
		}
		frame=new JFrame("SWEEPER");
		minefield=new Minefield();
		information=new Information();
		mb=new JMenuBar();
		game=new Game("Game(G)",KeyEvent.VK_G);
        //设置Frame样式
		frame.add(minefield,BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);//要使得窗体居中，这setLocationRelativeTo一定要在pack方法的后面。
		frame.setResizable(false);
		frame.setVisible(true);
		//设置imformation
		frame.add(information,BorderLayout.NORTH);
		//加入JMenuBar和Game
		frame.setJMenuBar(mb);
	    mb.add(game);
	}
	
	public static void main(String[] args) {
		startGame();
	}
}
