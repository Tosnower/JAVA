package view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import controller.minefield.Minefield;
import view.sweeper.Sweeper;;

@SuppressWarnings("serial")
public class Game extends JMenu{
	private JMenuItem    miStart=new JMenuItem("Start(S)",KeyEvent.VK_S),
			             miLow=new JMenuItem("Primary(L)",KeyEvent.VK_L),
			             miMeddle=new JMenuItem("Interme(M)",KeyEvent.VK_M),
			             miHigh=new JMenuItem("Senior(H)",KeyEvent.VK_H),
			             miCustom=new JMenuItem("Custom(C)",KeyEvent.VK_C),
			             miWorldHeroes=new JMenuItem("WorldHeroes(W)",KeyEvent.VK_W),
			             miQuit=new JMenuItem("Quit(Q)",KeyEvent.VK_Q);
	
	public Game(String label,int nAccelerator){
		super(label);
		setMnemonic(nAccelerator);
		//����
		miStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Minefield.reStart();
			}
		});
		//����
		miLow.addActionListener(new ActionListener(){
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e){
				if(9!=Minefield.getRow()||9!=Minefield.getRow()||10!=Minefield.getMinenum()){
					Minefield.reStart();
					Sweeper.minefield.setRow(9);
					Sweeper.minefield.setCol(9);
					Sweeper.minefield.setMinenum(10);
					Sweeper.startGame();
				}
			}
		});
		//�м�
		miMeddle.addActionListener(new ActionListener(){
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e){
				if(16!=Minefield.getRow()||16!=Minefield.getRow()||40!=Minefield.getMinenum()){
					Minefield.reStart();
					Sweeper.minefield.setRow(16);
					Sweeper.minefield.setCol(16);
					Sweeper.minefield.setMinenum(40);
					Sweeper.startGame();
				}
			}
		});
		//�߼�
		miHigh.addActionListener(new ActionListener(){
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e){
				if(16!=Minefield.getRow()||30!=Minefield.getRow()||99!=Minefield.getMinenum()){
					Minefield.reStart();
					Sweeper.minefield.setRow(22);
					Sweeper.minefield.setCol(42);
					Sweeper.minefield.setMinenum(99);
					Sweeper.startGame();
				}
			}
		});
		//�Զ���
		miCustom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
                //���öԻ���
				JDialog dialog=new JDialog(Sweeper.frame,"Custom",true);
				dialog.setLayout(new BorderLayout());
				//����JPANEL
				JPanel pword=new JPanel();
				JPanel pputin=new JPanel();
				JPanel pbutton=new JPanel();
				//���ð�ť  ����panel ������ʽ
				JButton sure=new JButton("Sure");
				JButton cancel=new JButton("Cancel");
				
				//���ñ�ǩ ����panel ������ʽ
				JLabel height=new JLabel("Height");
				JLabel width=new JLabel("Width");
				JLabel minenumber=new JLabel("MineNumber");
				
				//�����ı�  ����panel ������ʽ
				JTextField textrow=new JTextField(String.valueOf(Minefield.getRow()));
				JTextField textcol=new JTextField(String.valueOf(Minefield.getCol()));
				JTextField textmines=new JTextField(String.valueOf(Minefield.getMinenum()));
				
				//�������λ��
				
				//��Ӱ�������
				cancel.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						dialog.dispose();
					}
				});
				sure.addActionListener(new ActionListener(){
					@SuppressWarnings("static-access")
					public void actionPerformed(ActionEvent arg0) {
						int row=Minefield.getRow();
						int col=Minefield.getCol();
						int mines=Minefield.getMinenum();
						try{
						    row=Integer.parseInt(textrow.getText());
					        col=Integer.parseInt(textcol.getText());
						    mines=Integer.parseInt(textmines.getText());
						}catch(NumberFormatException e){
							JOptionPane.showMessageDialog(null, "The number should be integer!");
						}

						Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //�õ���Ļ�ĳߴ� 
						if(45*row>screenSize.height){
							JOptionPane.showMessageDialog(null, "We suggest that the Height should be less than "+(int)((screenSize.height-90)/45));
						}
						if(45*col>screenSize.width){
							JOptionPane.showMessageDialog(null, "We suggest that the width should be less than "+(int)((screenSize.width-90)/45));
						}
						if(row<3&&col<3){
							JOptionPane.showMessageDialog(null, "The Height and Width must greater less than 4��");
						}else if(row!=Minefield.getRow()||mines!=Minefield.getMinenum()){
								Minefield.reStart();
								Sweeper.minefield.setRow(row);
								Sweeper.minefield.setCol(col);
								Sweeper.minefield.setMinenum(mines);
								Sweeper.startGame();
								dialog.dispose();
							}
							dialog.dispose();
						}
				});
				pbutton.add(sure);
				pbutton.add(cancel);
				pbutton.setLayout(new GridLayout(2,1,10,10));
				
				pword.add(height);
				pword.add(width);
				pword.add(minenumber);
				pword.setLayout(new GridLayout(3,1,10,10));
				
				pputin.add(textrow);
				pputin.add(textcol);
				pputin.add(textmines);
				pputin.setLayout(new GridLayout(3,1,10,10));
				
				dialog.add(pword,BorderLayout.WEST);
				dialog.add(pputin,BorderLayout.CENTER);
				dialog.add(pbutton,BorderLayout.EAST);
				//���öԻ���λ��
				dialog.pack();
				dialog.setLocationRelativeTo(null);
				dialog.setVisible(true);
			}
		});
		//�˳�
		miQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		add(miStart);
		add(new JSeparator());
		add(miLow);
		add(miMeddle);
		add(miHigh);
		add(miCustom);
		add(new JSeparator());
		add(miQuit);
	}
}
