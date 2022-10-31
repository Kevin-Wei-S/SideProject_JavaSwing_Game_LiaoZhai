package LiaoZhai;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Menu extends JFrame implements KeyListener{
	
	// LiaoZhai - Menu 畫選項圖
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
	
	
	
	// LiaoZhai - Menu目前座標 
	public int mposY = 1;
	public int mposX = 0;
	
	
	// Menu - Selections 與視窗邊界距離
	public int mTop = 400;
	public int mLeft = 280;
	
	//Menu - Selections 選項圖倍數 //3:10
	public int multY = 69;
	public int multX = 230;
	
	
	
	public Menu() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(400, 25, 780, 750);
		setTitle("Liao-Zhai Menu");
		getContentPane().setBackground(Color.BLACK);
		
		// JFrame-隱去標題欄
		setUndecorated(true);
		
		// 匯入 Title
		title();

		
		addKeyListener(this);
		setVisible(true);
	}
	
	public void title() {
		
		titleFirst = new JLabel("聊 齋");
		titleFirst.setBounds(80, 5, 800, 250);
		titleFirst.setFont(new java.awt.Font("標楷體", 1, 132));
		titleFirst.setForeground(Color.RED);
		
		titleSecond = new JLabel("倩女幽魂");
		titleSecond.setBounds(280, 160, 800, 250);
		titleSecond.setFont(new java.awt.Font("標楷體", 1, 100));
		titleSecond.setForeground(Color.WHITE);
		
		
		add(titleFirst);
		add(titleSecond);
		
	}


	public void menusBlink() {
		
		// 取得 此Frame 之 畫筆
		Graphics mPen = this.getGraphics();
		
		// 匯入需繪製之 Laio-Zhai 選項圖
		ImageIcon selectStart = new ImageIcon("images/Start.png");
		ImageIcon selectStartO = new ImageIcon("images/StartO.png");
		ImageIcon selectScore = new ImageIcon("images/Score.png");
		ImageIcon selectScoreO = new ImageIcon("images/ScoreO.png");
		ImageIcon selectReturn = new ImageIcon("images/Return.png");
		ImageIcon selectReturnO = new ImageIcon("images/ReturnO.png");
	
		
		
		
		// 開始繪製
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
		
		// Menu - 按鍵向上
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
				
				System.out.println("Debug, 是個好消遣!");
				System.exit(0);
			
			}
			
			
		}
		
		menusBlink();
		
	}
	
	public void keyReleased(KeyEvent menusKey) {
		
		
	}
	
	public void keyTyped(KeyEvent menusKey) {
		
		
	}


//************************************ 程式進入口 ********************************************
    public static void main(String[] args) {
		
		new Menu();

	}

}