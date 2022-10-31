package LiaoZhai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Menu extends JFrame implements KeyListener{
	
	// LiaoZhai - Menu �e�ﶵ��
	public int menus[][] = {
			
			{0},
			{2},
			{3},
			{5},
			{0},
	};
	
	
	// LiaoZhai - Title
	public JLabel titleFirst;
	public JLabel titleSecond;
	
	
	
	// LiaoZhai - Menu�ثe�y�� 
	public int mposY = 1;
	public int mposX = 0;
	
	
	// Menu - Selections �P������ɶZ��
	public int mTop = 400;
	public int mLeft = 280;
	
	//Menu - Selections �ﶵ�ϭ��� //3:10
	public int multY = 69;
	public int multX = 230;
	
	
	
	public Menu() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(400, 25, 780, 750);
		setTitle("Liao-Zhai Menu");
		getContentPane().setBackground(Color.BLACK);
		
		// JFrame-���h���D��
		setUndecorated(true);
		
		// �פJ Title
		title();

		
		addKeyListener(this);
		setVisible(true);
	}
	
	public void title() {
		
		titleFirst = new JLabel("�� �N");
		titleFirst.setBounds(80, 5, 800, 250);
		titleFirst.setFont(new java.awt.Font("�з���", 1, 132));
		titleFirst.setForeground(Color.RED);
		
		titleSecond = new JLabel("�Ťk�ջ�");
		titleSecond.setBounds(280, 160, 800, 250);
		titleSecond.setFont(new java.awt.Font("�з���", 1, 100));
		titleSecond.setForeground(Color.WHITE);
		
		
		add(titleFirst);
		add(titleSecond);
		
	}


	public void menusBlink() {
		
		// ���o ��Frame �� �e��
		Graphics mPen = this.getGraphics();
		
		// �פJ��ø�s�� Laio-Zhai �ﶵ��
		ImageIcon selectStart = new ImageIcon("images/Start.png");
		ImageIcon selectStartO = new ImageIcon("images/StartO.png");
		ImageIcon selectScore = new ImageIcon("images/Score.png");
		ImageIcon selectScoreO = new ImageIcon("images/ScoreO.png");
		ImageIcon selectReturn = new ImageIcon("images/Return.png");
		ImageIcon selectReturnO = new ImageIcon("images/ReturnO.png");
	
		
		
		
		// �}�lø�s
		for(int i=0 ; i<menus.length ; i++) {
			for(int j=0 ; j<menus[i].length ; j++) {
				
				if(menus[i][j]==1) {
					mPen.drawImage(selectStart.getImage(), 285+j*multX, mTop+i*multY, multX, multY, null);
					}
				if(menus[i][j]==2) {
					mPen.drawImage(selectStartO.getImage(), 285+j*multX, mTop+i*multY, multX, multY, null);
					}
				if(menus[i][j]==3) {
					mPen.drawImage(selectScore.getImage(), mLeft+j*multX, mTop+i*multY, multX, multY, null);
					}
				if(menus[i][j]==4) {
					mPen.drawImage(selectScoreO.getImage(), mLeft+j*multX, mTop+i*multY, multX, multY, null);
					}
				if(menus[i][j]==5) {
					mPen.drawImage(selectReturn.getImage(), mLeft+j*multX, mTop+i*multY, multX, multY, null);
					}
				if(menus[i][j]==6) {
					mPen.drawImage(selectReturnO.getImage(), mLeft+j*multX, mTop+i*multY, multX, multY, null);
					}

				
			}
		}
		
	} 
	
	public void paint(Graphics g) {
		
		super.paint(g);
		
		menusBlink();
		
	}
	
	public void keyPressed(KeyEvent menusKey){
		
		int menusKb = menusKey.getKeyCode();
		
		// Menu - ����V�W
		if(menusKb==KeyEvent.VK_UP) {
			
			if(menus[mposY-1][mposX]==1 ){
				menus[mposY-1][mposX]=2;
				menus[mposY][mposX]=3;
				mposY--;
			}
			
			else if(menus[mposY-1][mposX]==3){
				menus[mposY-1][mposX]=4;
				menus[mposY][mposX]=5;
				mposY--;
			}
		}
		
		if(menusKb==KeyEvent.VK_DOWN) {
			
			if(menus[mposY+1][mposX]==3){
				menus[mposY+1][mposX]=4;
				menus[mposY][mposX]=1;
				mposY++;
			}
			
			else if(menus[mposY+1][mposX]==5){
				menus[mposY+1][mposX]=6;
				menus[mposY][mposX]=3;
				mposY++;
			}
			
		
		}
		
		if(menusKb==KeyEvent.VK_ENTER) {
			
			if(menus[mposY][mposX]==2) {
				
				new ScoreBulletin();
				
			}
			
			if(menus[mposY][mposX]==4) {
				
				new SkyRankBulletin();
				
			}
			
			if(menus[mposY][mposX]==6) {
				
				System.out.println("Debug, �O�Ӧn����!");
				System.exit(0);
			
			}
			
			
		}
		
		menusBlink();
		
	}
	
	public void keyReleased(KeyEvent menusKey) {
		
		
	}
	
	public void keyTyped(KeyEvent menusKey) {
		
		
	}


//************************************ �{���i�J�f ********************************************
    public static void main(String[] args) {
		
		new Menu();

	}

}