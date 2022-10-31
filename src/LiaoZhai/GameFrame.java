package LiaoZhai;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.util.*;
import javax.swing.JOptionPane;






public class GameFrame extends JFrame implements KeyListener{
	
	//地圖繪製
	public int maps[][] = {
			                                                              
			{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
			{30, 0, 4, 0, 0, 8,17, 0, 1, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0,33}, 
			{31, 0, 0, 0, 0, 4, 0,95, 0, 0,95, 0, 0, 0, 0, 0, 0,95, 0, 0, 0,36}, 
			{31, 0, 0,95, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 4, 0, 0, 0,33}, 
			{31, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,36},
			{31, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,33},
			{31, 0, 0, 0,95, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,36},
			{31, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0,95, 0, 0, 0, 0, 0,33},
			{31, 0, 0, 0, 0, 0, 0, 0,95, 0, 4, 0,95, 0, 0, 0, 0,95, 0, 0, 0,33},
			{31, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,34}, 
			{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}
	};
	
	// 地圖資料成員
	
	// 地圖 與 Frame 間距
	public int top = 80, left = 9;
	
	// 人物座標
	public int posX = 8, posY = 1;
	
	// 圖片倍數   //69
	public int M = 69;
	
	// 合成及收妖-時間暫緩倍數, 目前測試75不會造成生怪紊亂 
	// 合成符及收妖圖片轉換遲滯會累積, 造成生怪系統紊亂, 如果生符系統完善, 數字可能需下修
	int timeSpeed = 75;
	
	// 樹妖-Monster座標
	public int mposX = 17;
	public int mposY = 5;
	
	// 樹妖S-Monster座標
	public int mSposX = 18;
	public int mSposY = 6;
	
	// 隨機樹妖- (i) 計算目前地圖上的樹妖總數, 且視為編號
	// 隨機樹妖- (ii) 為編號設定 Y軸座標
	// 隨機樹妖- (iii) 為編號設定 X軸座標
	// 隨機樹妖- (iv) 樹妖數量
	// 隨機樹妖- (v) 樹妖編號
	public ArrayList <Integer> monsterNums;
	//public ArrayList <Integer> monsterYaxis;
	//public ArrayList <Integer> monsterXaxis;
	public int nums = 0;
	//public int numsID = -1;
	
	//隨機生成FuLu符籙
	public int fuluNums = 9;
	
	// JProgressBar-陽氣值成員
	public JProgressBar jb; 
	public JLabel l0, l1 ;
	public int sun = 2000;
	
	// JLabel-陰德值成員
	public JLabel lvirtue ;
	public JLabel lvirtueTitle ;
	public int virtue = 0;
	
	// JLabel-冤孽值成員
	public JLabel lguilt ;
	public JLabel lguiltTitle ;
	public int guilt = 0;
	
	// JLabel-斷捨離成員
	public JLabel lExit;
	
	// 各執行緒
	Thread mGThrA;
	Thread mGThrB;
	Thread mGThrC;
	Thread mGThrD;
	Thread mGThrE;
	Thread sunThr;
	Thread virtueThr;
	Thread guiltThr;
	
	// LiaoZhai-LMusic
	public static LMusic LM;
	
	
	
	// 符籙FuLu成員, 按等級分組, EX: FuLu1-等級1以上的符籙FuLu
	public ArrayList <Integer> FuLu1; 
	public ArrayList <Integer> FuLuSum; 
	public ArrayList <Integer> FuLuColor; 
	
	
	
	// LiaoZhai-Score
	public static int score;
	
	// 玩家名稱
	ScoreBulletin sb;

	
//*********************************以下為 GameFrame-建構子******************************************	
	
	// GameFrame 建構子
	public GameFrame() {
		
		// LMusic - 背景遊戲音樂
		LM = new LMusic("Music/LMusic.wav");
		LM.start();

		
		// JFrame-maps 基本設定
		setTitle("Liao-Zhai");
		//1600, 900
		setBounds(0, 0, 1600, 900);
		setLayout(null); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		// JFrame-隱去標題欄
		setUndecorated(true);
		
		
		
		// JFrame-JFrame背景底色設為黑色
		getContentPane().setBackground(Color.BLACK);
		
		
		// 陽氣值-Sun 在建構子執行 - 單獨執行緒
		sunThr = new Thread() {
			public void run() {
				Sun();}
			};
		
		sunThr.start();
		
		
		// 陰德值-virtue 在建構子執行 - 單獨執行緒
		virtueThr = new Thread() {
			public void run() {
				Virtue();}
			};
				
		virtueThr.start();
		
		
		// 冤孽值-guilt 在建構子執行 - 單獨執行緒
		guiltThr = new Thread() {
			public void run() {
				Guilt();}
			};
				
		guiltThr.start();
		
	
		// <返回人間>-Exit 在建構子執行
		Exit();
	
		addKeyListener(this);
		setVisible(true);

	}

//*********************************以上為GameFrame-建構子******************************************	
	
	// 將符籙等級組, 添加相應成員, 按等級分組, EX: FuLu1-等級1以上的符籙FuLu
	public void FuLu() {
		
		FuLu1 = new ArrayList<Integer>();
		FuLuSum = new ArrayList<Integer>();
		FuLuColor = new ArrayList<Integer>();
		
		
		for(int i=95 ; i<100 ; i++) 
			
			FuLu1.add(i);
		
		for(int i=190 ; i<199 ; i++) 
			
			FuLuSum.add(i);
		
		for(int i=96 ; i<100 ; i++) 
			
			FuLuColor.add(i);
		
		for(int i=0 ; i<5 ; i++)
			
			FuLuColor.add(0);
		
		}
		
	
	// 陽氣值-Sun
	public void Sun() {
		
		jb=new JProgressBar(5,2000); 
		jb.setStringPainted(true);
		jb.setForeground(Color.yellow);
		jb.setBackground(Color.CYAN);
		jb.setString("");
		jb.setBounds(185, 13, 280, 50); 
				
		jb.setStringPainted(true);
		l0 = new JLabel("陽氣值");
		l0.setBounds(45, 2, 170, 80);
		l0.setFont(new java.awt.Font("標楷體", 1, 36));
		l0.setForeground(Color.MAGENTA);
		add(l0);
		add(jb);
				
		while(0<=sun){
			
			jb.setValue(sun); 
			sun-=5;         
			
			//ProgressBar讀條速度150
			try{Thread.sleep(150);}catch(Exception e){}    
			
			if(sun==0) {
		
				  UIManager.put("OptionPane.messageFont", new Font("標楷體", Font.BOLD, 20));
				  UIManager.put("OptionPane.buttonFont", new Font("標楷體", Font.PLAIN, 20));
				  UIManager.put("OptionPane.yesButtonText", "聊齋選單");
				  UIManager.put("OptionPane.noButtonText", "回魂寫Code");
				  UIManager.put("OptionPane.cancelButtonText", "查閱天榜");
				  
				  score = virtue - guilt;
				  System.out.println("virtue　:　" + virtue);
				  System.out.println("guilt　:　" + guilt);
				  System.out.println("score　:　" + score);
				  
				  int result = JOptionPane.showConfirmDialog(this, "      降妖者: " + sb.scoreName + 
						                                    "\n" + "      陰德值: " + virtue + 
									                        "\n" + "      冤孽值: " + guilt +
									                        "\n" + "      功德值: " + score +
									                        "\n" + " 請前往天榜, 查詢是否上榜", "功過亭", 1);
				  
				  
				  
				   if(result==0) {
					  
					   sun=0; 
					  LM.auline.close();
					  dispose();
					  
				  } else if(result==1) {
					  
					  sun=0;
					  LM.auline.close();
					  System.exit(0);
					  
				  } else if(result==2) {
					  
					  sun=0;
					  LM.auline.close();
					  new SkyRank();
					  new SkyRankBulletin();
					  dispose();
					  
				  } 	  
			  }
		}
	}
	
	// 陰德值-Virtue
	
	public void Virtue() {
		
		lvirtueTitle = new JLabel("陰德值 :");
		lvirtue = new JLabel();
		
		lvirtueTitle.setBounds(540, 2, 170, 80);
		lvirtueTitle.setFont(new java.awt.Font("標楷體", 1, 36));
		lvirtueTitle.setForeground(Color.WHITE);
		
		
		lvirtue.setBounds(705, 5, 300, 70);
		lvirtue.setText(""+virtue);
		lvirtue.setFont(new java.awt.Font("Arial", 1, 46));
		lvirtue.setForeground(Color.WHITE);
		
		add(lvirtueTitle);
		add(lvirtue);
			
	}
	
	// 冤孽值-Guilt
	public void Guilt() {
		
		lguiltTitle = new JLabel("冤孽值 :");
		lguilt = new JLabel();
			
		lguiltTitle.setBounds(940, 2, 170, 80);
		lguiltTitle.setFont(new java.awt.Font("標楷體", 1, 36));
		lguiltTitle.setForeground(Color.GRAY);
			
		
		lguilt.setBounds(1105, 5, 300, 70);
		lguilt.setText(""+guilt);
		lguilt.setFont(new java.awt.Font("Arial", 1, 46));			
		lguilt.setForeground(Color.GRAY);
			
		add(lguiltTitle);			
		add(lguilt);
	}
	
	
	// Q | q <返回人間>-Exit
	public void Exit() {
		
		lExit = new JLabel("<html>按鍵Q<br>返回人間</html>");
		lExit.setBounds(1390, 6, 110, 65);
	
		
		Font fExit = new Font("標楷體" ,Font.BOLD, 26);
	    lExit.setFont(fExit);
	    lExit.setOpaque(true);
	    lExit.setForeground(Color.BLACK); 
	    lExit.setBackground(Color.RED);
	    
	    add(lExit);
		
	}
	
	
//******************************** 以下LiaoZhai圖匯入作圖區 ************************************	
	
	
		// 大局作圖預備區
		public void refresh() {
			
			Graphics gPen = this.getGraphics();
			
			
			// 匯入地圖及角色圖片 & 符籙等級 白 > 紫 > 青 > 粉 > 黃
			ImageIcon fulu = new ImageIcon(("images/FuLuRev.png"));
			ImageIcon grass = new ImageIcon("images/GrassRev.png");
			ImageIcon fence = new ImageIcon("images/Fence.png");
			ImageIcon actor = new ImageIcon("images/Actor.png");
			ImageIcon actress = new ImageIcon("images/Actress.png");
			ImageIcon soil = new ImageIcon("images/Soil.png");
			ImageIcon stone = new ImageIcon("images/Stone.png");
			ImageIcon temple = new ImageIcon("images/Temple.png");
			ImageIcon actorL = new ImageIcon("images/ActorL.png");
			ImageIcon actorInGrassL = new ImageIcon("images/ActorInGrassL.png");
			ImageIcon actorInGrassR = new ImageIcon("images/ActorInGrassR.png");
			ImageIcon fulupink = new ImageIcon("images/FuLuPink.png");
			ImageIcon fulucyan = new ImageIcon("images/FuLuCyan.png");
			ImageIcon fulupurple = new ImageIcon("images/FuLuPurple.png");
			ImageIcon fuluorigin = new ImageIcon("images/FuLuOrigin.png");
			ImageIcon dou = new ImageIcon("images/Dou.png");
			ImageIcon back = new ImageIcon("images/Back.png");
			ImageIcon monster = new ImageIcon("images/Monster.png");
			ImageIcon human = new ImageIcon("images/Human.png");
			ImageIcon pbound = new ImageIcon("images/Pbound.png");
			ImageIcon pcircle = new ImageIcon("images/Pcircle.png");
			ImageIcon gbound = new ImageIcon("images/Gbound.png");
			ImageIcon underworld = new ImageIcon("images/UnderWorld.png");
			ImageIcon gcircle = new ImageIcon("images/Gcircle.png");
			ImageIcon entrance = new ImageIcon("images/Entrance.png");
			ImageIcon hulu = new ImageIcon("images/HuLu.png");
			ImageIcon threetoxic = new ImageIcon("images/ThreeToxic.png");
			ImageIcon ninetails = new ImageIcon("images/NineTails.png");
			ImageIcon loulou = new ImageIcon("images/LouLou.png");
			ImageIcon fulurs = new ImageIcon("images/FuLuRS.png");
			ImageIcon fulurborn = new ImageIcon("images/FuLuBorn.png");
			ImageIcon umbrella = new ImageIcon("images/Umbrella.png");
			
			// 為 maps代碼 做相對應的圖
			for(int i=0 ; i<maps.length ; i++) {
				for(int j=0 ; j<maps[i].length ; j++) {
					
					if(maps[i][j]==0) {
						gPen.drawImage(soil.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==1) {
						gPen.drawImage(actor.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==2) {
						gPen.drawImage(fence.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==3) {
						gPen.drawImage(stone.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==4) {
						gPen.drawImage(grass.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==5) {
						gPen.drawImage(actorL.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==6) {
						gPen.drawImage(actorInGrassL.getImage(), left+j*M, top+i*M, M, M, null);
				    }
					if(maps[i][j]==7) {
						gPen.drawImage(actorInGrassR.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==8) {
						gPen.drawImage(temple.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==17) {
						gPen.drawImage(umbrella.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==18) {
						gPen.drawImage(actress.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==30) {
						gPen.drawImage(human.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==31) {
						gPen.drawImage(pbound.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==32) {
						gPen.drawImage(pcircle.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==33) {
						gPen.drawImage(gbound.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==34) {
						gPen.drawImage(underworld.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==35) {
						gPen.drawImage(gcircle.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==36) {
						gPen.drawImage(entrance.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==46) {
						gPen.drawImage(loulou.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==47) {
						gPen.drawImage(ninetails.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==48) {
						gPen.drawImage(monster.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==49) {
						gPen.drawImage(back.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==55) {
						gPen.drawImage(hulu.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==56) {
						gPen.drawImage(fulurs.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==57) {
						gPen.drawImage(fulurborn.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==95) {
						gPen.drawImage(fulu.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==96) {
						gPen.drawImage(fulupink.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==97) {
						gPen.drawImage(fulucyan.getImage(), left+j*M, top+i*M, M, M, null);
				    }
					if(maps[i][j]==98) {
						gPen.drawImage(fulupurple.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==99) {
						gPen.drawImage(fuluorigin.getImage(), left+j*M, top+i*M, M, M, null);
					}
					if(maps[i][j]==50) {
						gPen.drawImage(dou.getImage(), left+j*M, top+i*M, M, M, null);
					}
					
				}}}

//****************************************以下LiaoZhai作圖具現區*************************************************			
	
	// 作圖具現區
	public void paint(Graphics g) {
		
		super.paint(g);
		 refresh();
		 		 
		 //自然生成群妖- Akali
		 mGThrA = new Thread() {
			 public void run() {
				 try {
					 
					 monsterGroup();
					 
				 }catch(Exception e) {System.out.println("mGThrA停止執行緒有被執行.");}
			 }
		 };
		 mGThrA.start();
		
		 
		 
		 //自然生成群妖 - Bebe
		 mGThrB = new Thread() {
			 public void run() {
				 try {
					 
					 monsterGroup();
					 
				 }catch(Exception e) {System.out.println("mGThrB停止執行緒有被執行.");}
			 }
		 };
		 mGThrB.start();
		
		 
		 
		 //自然生成群妖- Cycle
		 mGThrC = new Thread() {
			 public void run() {
				 try {
					 
					 monsterGroup();
					 
				 }catch(Exception e) {System.out.println("mGThrC停止執行緒有被執行.");}
			 }
		 };
		 
		 mGThrC.start();
		 
		 
		 
		 //自然生成群妖 - Debug
		 mGThrD = new Thread() {
			 public void run() {
				 try {
					 
					 monsterGroup();
					 
				 }catch(Exception e) {System.out.println("mGThrD停止執行緒有被執行.");}
			 }
		 };
		 mGThrD.start();
		
		 
		 
		 //自然生成群妖- Eagle
		 mGThrE = new Thread() {
			 public void run() {
				 try {
					 
					 monsterGroup();
					 
					 
				 }catch(Exception e) {System.out.println("mGThrE停止執行緒有被執行.");}
			 }
		 };
		 mGThrE.start();
		 

	}
	
//*********************************** 以下群妖移動作圖預備區 ****************************************
//*****************************MonsterGroupID - 自然生成群妖ID**********************************

	public void monsterGroup() {
		
		
		int numsID = -1;
		
		//numsID
		ArrayList<Integer> monsterYaxis = new ArrayList<Integer>();
		ArrayList<Integer> monsterXaxis = new ArrayList<Integer>();
		
		
		// 群妖數不滿5, 產生群妖ID: (i)編號 (ii)Y軸座標 (iii)X軸座標
		while(nums<5) {
			
			
			
			int randomMG = (int)(Math.random()*10); 
			int monsterKd = (int)(Math.random()*100);
			

				// 出樹妖機率
				if(monsterKd<80) {
					
					if(maps[2][21]==36 && maps[4][21]==36 && maps[6][21]==36) {
						
						if(randomMG<3) {
							
							monsterYaxis.add(2);
							monsterXaxis.add(21);
							
						} else if(7<randomMG) {
							
							monsterYaxis.add(6);
							monsterXaxis.add(21);
							
						} else{
							
							monsterYaxis.add(4);
							monsterXaxis.add(21);
							
						}
					
					numsID++;
					nums++;
					
					// 古井-樹妖通道
					maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
					refresh();
					try{Thread.sleep(180);}catch(Exception e){}
					
					// 結界-樹妖現形
					maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 35;
					refresh();
					try{Thread.sleep(180);}catch(Exception e){}
					
					// 運用樹妖ID - 生成樹妖
					maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 48;
					refresh();
					
					
	                // 樹妖最終目標地 - 陰陽交界 - 樹妖因為趨性使然, 不會後退.
					while(0<monsterXaxis.get(numsID)) {
						
						// 製造 0-100 的整數亂數, 讓樹妖移動具變化性.
						int randomM = (int)(Math.random()*100);
						
						// 從幽冥界現生必向左走(這樣我比較好Coding)
						if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)==21 ) {
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 48;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
						    int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
							
						}
						
						// 上前下-為空
						else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48){
							
							
							// 樹妖移動 運用亂數增加移動變化性 往前30% 往上35% 往下35% (randomM為0~99亂數)
							if(29<randomM && randomM<=64) {
								
								maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 48;
							    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							    int mY = monsterYaxis.get(numsID);
							    mY--;
							    monsterYaxis.set(numsID, mY);
							    
							} else if(randomM<=29) {
								
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 48;
							    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							    int mX = monsterXaxis.get(numsID);
							    mX--;
							    monsterXaxis.set(numsID, mX);
								
							} else if(64<randomM) {
								
								maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 48;
							    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							    int mY = monsterYaxis.get(numsID);
							    mY++;
							    monsterYaxis.set(numsID, mY);
								
							}}
						
						// 上前-為空
						else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48) {
							
			                  
							if(30<randomM) {
								
								maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 48;
							    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							    int mY = monsterYaxis.get(numsID);
							    mY--;
							    monsterYaxis.set(numsID, mY);
							    
							} else if(randomM<=30 ) {
								
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 48;
							    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							    
							    int mX = monsterXaxis.get(numsID);
							    mX--;
							    monsterXaxis.set(numsID, mX);
							    
							}}
						
						// 前下-為空
						else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48) {
							
							if(randomM<=30) {
								
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 48;
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
								
								int mX = monsterXaxis.get(numsID);
							    mX--;
							    monsterXaxis.set(numsID, mX);
							    
							} else if(30<randomM ) {
											
								maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 48;
							    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							    int mY = monsterYaxis.get(numsID);
							    mY++;
							    monsterYaxis.set(numsID, mY);
										    
							}}
			//****************** 下上為空 & 前為人界通道 *******************
						else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==31 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48 ) {
							
							if(randomM<=15) {
								
								maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 48;
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
								int mY = monsterYaxis.get(numsID);
							    mY++;
							    monsterYaxis.set(numsID, mY);
							
							} else if(15<randomM && randomM<=30) {
								
								maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 48;
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
								int mY = monsterYaxis.get(numsID);
							    mY--;
							    monsterYaxis.set(numsID, mY);
																    
							} else{
								
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 48;
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
								refresh();
								try{Thread.sleep(180);}catch(Exception e){}
								
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 32;
								refresh();
								try{Thread.sleep(180);}catch(Exception e){}
								
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 31;
								
								int mX = monsterXaxis.get(numsID);
							    mX--;
							    monsterXaxis.set(numsID, mX);
								
							    nums--;
								
								guilt+=5000;
								lguilt.setText(""+guilt);
														
							}}
						//******************前為人界通道 *******************
						else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==31 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48) {
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 48;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							refresh();
							try{Thread.sleep(180);}catch(Exception e){}
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 32;
							refresh();
							try{Thread.sleep(180);}catch(Exception e){}
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 31;
							
							int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
							
						    nums--;
							
							guilt+=5000;
							lguilt.setText(""+guilt);
							
						}
						
						// 下上-為空
						else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48) {
							
							if(randomM<=50) {
								
								maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 48;
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
								int mY = monsterYaxis.get(numsID);
							    mY++;
							    monsterYaxis.set(numsID, mY);
							    
							} else if(50<randomM) {
								
								maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 48;
								maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
								int mY = monsterYaxis.get(numsID);
							    mY--;
							    monsterYaxis.set(numsID, mY);
													    
							}}
						
						// 上-為空
						else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48) {
							
							maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 48;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY--;
						    monsterYaxis.set(numsID, mY);
												    
					        
						}
						
						// 前-為空
						else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)!=21 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48) {
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 48;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
					        
						}
						
						// 下-為空
						else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48) {
							
							maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 48;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY++;
						    monsterYaxis.set(numsID, mY);
					        
						}
						
						//樹妖被收, 座標圖改為0, 樹妖執行緒因條件設定無法移動, 設定滿足出迴圈條件, 群妖數量-1
	                    else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==0) {
	                    	int mX = monsterXaxis.get(numsID);
	                    	mX = 0;
	                    	monsterXaxis.set(numsID, mX);
	                    	nums--;
	                    	
						}
				
						try{Thread.sleep(350);}catch(Exception e) {}
						refresh();
						
					}}}
			   
			
			    
			// 出九尾狐機率
			if(79<=monsterKd && monsterKd<94) {
				
				if(maps[2][21]==36 && maps[4][21]==36 && maps[6][21]==36) {
					
					if(randomMG<3) {
						
						monsterYaxis.add(2);
						monsterXaxis.add(21);
						
					} else if(7<randomMG) {
						
						monsterYaxis.add(6);
						monsterXaxis.add(21);
						
					} else{
						
						monsterYaxis.add(4);
						monsterXaxis.add(21);
						
					}
				
				numsID++;
				nums++;
				
				// 古井-九尾狐通道
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
				refresh();
				try{Thread.sleep(180);}catch(Exception e){}
				
				// 結界-九尾狐現形
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 35;
				refresh();
				try{Thread.sleep(180);}catch(Exception e){}
				
				// 運用群妖ID - 生成九尾狐
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 47;
				refresh();
				
				
                // 九尾狐最終目標地 - 陰陽交界 - 九尾狐因為趨性使然, 不會後退.
				while(0<monsterXaxis.get(numsID)) {
					
					// 製造 0-100 的整數亂數, 讓九尾狐移動具變化性.
					int randomM = (int)(Math.random()*100);
					
					// 從幽冥界現生必向左走(這樣我比較好Coding)
					if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)==21 ) {
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 47;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
					    int mX = monsterXaxis.get(numsID);
					    mX--;
					    monsterXaxis.set(numsID, mX);
						
					}
					
					// 上前下-為空
					else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47){
						
						
						
						if(49<randomM && randomM<=74) {
							
							maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 47;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY--;
						    monsterYaxis.set(numsID, mY);
						    
						} else if(randomM<=49) {
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 47;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
							
						} else if(74<randomM) {
							
							maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 47;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY++;
						    monsterYaxis.set(numsID, mY);
							
						}}
					
					// 上前-為空
					else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47) {
						
		                  
						if(50<randomM) {
							
							maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 47;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY--;
						    monsterYaxis.set(numsID, mY);
						    
						} else if(randomM<=50 ) {
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 47;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    
						    int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
						    
						}}
					
					// 前下-為空
					else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47) {
						
						if(randomM<=50) {
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 47;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							
							int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
						    
						} else if(50<randomM ) {
										
							maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 47;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY++;
						    monsterYaxis.set(numsID, mY);
									    
						}}
		//****************** 下上為空 & 前為人界通道 *******************
					else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==31 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47 ) {
						
						if(randomM<=15) {
							
							maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 47;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							int mY = monsterYaxis.get(numsID);
						    mY++;
						    monsterYaxis.set(numsID, mY);
						
						} else if(15<randomM && randomM<=30) {
							
							maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 47;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							int mY = monsterYaxis.get(numsID);
						    mY--;
						    monsterYaxis.set(numsID, mY);
															    
						} else{
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 47;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							refresh();
							try{Thread.sleep(180);}catch(Exception e){}
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 32;
							refresh();
							try{Thread.sleep(180);}catch(Exception e){}
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 31;
							
							int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
							
						    nums--;
							
							guilt+=20000;
							lguilt.setText(""+guilt);
													
						}}
					//******************前為人界通道 *******************
					else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==31 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47 ) {
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 47;
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						refresh();
						try{Thread.sleep(180);}catch(Exception e){}
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 32;
						refresh();
						try{Thread.sleep(180);}catch(Exception e){}
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 31;
						
						int mX = monsterXaxis.get(numsID);
					    mX--;
					    monsterXaxis.set(numsID, mX);
						
					    nums--;
						
						guilt+=20000;
						lguilt.setText(""+guilt);
						
					}
					
					
					// 下上-為空
					else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47) {
						
						if(randomM<=50) {
							
							maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 47;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							int mY = monsterYaxis.get(numsID);
						    mY++;
						    monsterYaxis.set(numsID, mY);
						    
						} else if(50<randomM) {
							
							maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 47;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							int mY = monsterYaxis.get(numsID);
						    mY--;
						    monsterYaxis.set(numsID, mY);
												    
						}}
					
					// 上-為空
					else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47) {
						
						maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 47;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mY = monsterYaxis.get(numsID);
					    mY--;
					    monsterYaxis.set(numsID, mY);
											    
				        
					}
					
					// 前-為空
					else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)!=21 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47) {
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 47;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mX = monsterXaxis.get(numsID);
					    mX--;
					    monsterXaxis.set(numsID, mX);
				        
					}
					
					// 下-為空
					else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47) {
						
						maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 47;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mY = monsterYaxis.get(numsID);
					    mY++;
					    monsterYaxis.set(numsID, mY);
				        
					}
					
					// 九尾狐被收, 座標圖改為0, 九尾狐執行緒因條件設定無法移動, 設定滿足出迴圈條件, 群妖數量-1
                    else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==0) {
                    	int mX = monsterXaxis.get(numsID);
                    	mX = 0;
                    	monsterXaxis.set(numsID, mX);
                    	nums--;
                    	 	
					}
			
					try{Thread.sleep(280);}catch(Exception e) {}
					refresh();
					
				}}}
			
			
			// 出姥姥機率
			if(95<=monsterKd) {
				
				if(maps[2][21]==36 && maps[4][21]==36 && maps[6][21]==36) {
					
					if(randomMG<3) {
						
						monsterYaxis.add(2);
						monsterXaxis.add(21);
						
					} else if(7<randomMG) {
						
						monsterYaxis.add(6);
						monsterXaxis.add(21);
						
					} else{
						
						monsterYaxis.add(4);
						monsterXaxis.add(21);
						
					}
				
				numsID++;
				nums++;
				
				// 古井-姥姥通道
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
				refresh();
				try{Thread.sleep(180);}catch(Exception e){}
				
				// 結界-姥姥現形
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 35;
				refresh();
				try{Thread.sleep(180);}catch(Exception e){}
				
				// 運用群妖ID - 生成姥姥
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 46;
				refresh();
				
				
                // 姥姥最終目標地 - 陰陽交界 - 姥姥因為趨性使然, 不會後退.
				while(0<monsterXaxis.get(numsID)) {
					
					// 製造 0-100 的整數亂數, 讓姥姥移動具變化性.
					int randomM = (int)(Math.random()*100);
					
					// 從幽冥界現生必向左走(這樣我比較好Coding)
					if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)==21 ) {
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 46;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
					    int mX = monsterXaxis.get(numsID);
					    mX--;
					    monsterXaxis.set(numsID, mX);
						
					}
					
					// 上前下-為空
					else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46){
						
						
						
						if(69<randomM && randomM<=84) {
							
							maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 46;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY--;
						    monsterYaxis.set(numsID, mY);
						    
						} else if(randomM<=69) {
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 46;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
							
						} else if(84<randomM) {
							
							maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 46;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY++;
						    monsterYaxis.set(numsID, mY);
							
						}}
					
					// 上前-為空
					else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46) {
						
		                  
						if(70<randomM) {
							
							maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 46;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY--;
						    monsterYaxis.set(numsID, mY);
						    
						} else if(randomM<=70 ) {
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 46;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    
						    int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
						    
						}}
					
					// 前下-為空
					else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46) {
						
						if(randomM<=70) {
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 46;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							
							int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
						    
						} else if(70<randomM ) {
										
							maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 46;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY++;
						    monsterYaxis.set(numsID, mY);
									    
						}}
		//****************** 下上為空 & 前為人界通道 *******************
					else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==31 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46 ) {
						
						if(randomM<=15) {
							
							maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 46;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							int mY = monsterYaxis.get(numsID);
						    mY++;
						    monsterYaxis.set(numsID, mY);
						
						} else if(15<randomM && randomM<=30) {
							
							maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 46;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							int mY = monsterYaxis.get(numsID);
						    mY--;
						    monsterYaxis.set(numsID, mY);
															    
						} else{
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 46;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							refresh();
							try{Thread.sleep(180);}catch(Exception e){}
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 32;
							refresh();
							try{Thread.sleep(180);}catch(Exception e){}
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 31;
							
							int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
							
						    nums--;
							
							guilt+=100000;
							lguilt.setText(""+guilt);
													
						}}
					
					//******************前為人界通道 *******************
					else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==31 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46 ) {
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 46;
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						refresh();
						try{Thread.sleep(180);}catch(Exception e){}
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 32;
						refresh();
						try{Thread.sleep(180);}catch(Exception e){}
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 31;
						
						int mX = monsterXaxis.get(numsID);
					    mX--;
					    monsterXaxis.set(numsID, mX);
						
					    nums--;
						
						guilt+=100000;
						lguilt.setText(""+guilt);
						
					}
					
					// 下上-為空
					else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46) {
						
						if(randomM<=50) {
							
							maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 46;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							int mY = monsterYaxis.get(numsID);
						    mY++;
						    monsterYaxis.set(numsID, mY);
						    
						} else if(50<randomM) {
							
							maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 46;
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
							int mY = monsterYaxis.get(numsID);
						    mY--;
						    monsterYaxis.set(numsID, mY);
												    
						}}
					
					// 上-為空
					else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46) {
						
						maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 46;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mY = monsterYaxis.get(numsID);
					    mY--;
					    monsterYaxis.set(numsID, mY);
											    
				        
					}
					
					// 前-為空
					else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)!=21 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46) {
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 46;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mX = monsterXaxis.get(numsID);
					    mX--;
					    monsterXaxis.set(numsID, mX);
				        
					}
					
					// 下-為空
					else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46) {
						
						maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 46;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mY = monsterYaxis.get(numsID);
					    mY++;
					    monsterYaxis.set(numsID, mY);
				        
					}
					
					//姥姥被收, 座標圖改為0, 姥姥執行緒因條件設定無法移動, 設定滿足出迴圈條件, 群妖數量-1
                    else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==0) {
                    	int mX = monsterXaxis.get(numsID);
                    	mX = 0;
                    	monsterXaxis.set(numsID, mX);
                    	nums--;
                    	
					}
			
					try{Thread.sleep(200);}catch(Exception e) {}
					refresh();
					
				}
			}
			
			}}}

//************************************FuLu符籙生成系統***********************************************
	public void fuluBorn() {
		
		int fuluID = -1;
		ArrayList <Integer> fuluVacantY = new ArrayList <Integer>();
		ArrayList <Integer> fuluVacantX = new ArrayList <Integer>();
		
		while(fuluNums<5) {
			
			
			
				for(int i=0 ; i<maps.length ; i++) {
						for(int j=0 ; j<maps[i].length ; j++) {
							
							if(maps[i][j]==0) {
								
								if((1<i && i<9) && (1<j && j<20)) {
								
								fuluID++;
								fuluVacantY.add(i);
								fuluVacantX.add(j);
							
							}
						}}}
				
				int fuluIndex = (int)(Math.random()*(fuluVacantY.size()));
					
				maps[fuluVacantY.get(fuluIndex)][fuluVacantX.get(fuluIndex)] = 57;
				refresh();
				try{Thread.sleep(150);}catch(Exception e) {}
					
				maps[fuluVacantY.get(fuluIndex)][fuluVacantX.get(fuluIndex)] = 95;
				refresh();
					
				fuluNums++;
			}
		
		}
	

	
//*****************************Monster - 樹妖(目前已停用)*************************************	
	public void monster() {
		
		
		maps[mposY][mposX]= 48;
		refresh();
		nums++;
		
		// 樹妖最終目標地 - 陰陽交界 - 樹妖因為趨性使然, 不會後退.
		while(0<mposX) {
			
			// 製造 0-100 的整數亂數, 讓樹妖移動具變化性.
			int randomM = (int)(Math.random()*100);
			
			// 上前下-為空
			if(maps[mposY-1][mposX]==0 && maps[mposY][mposX-1]==0 && maps[mposY+1][mposX]==0){
				
				
				
				if(30<randomM && randomM<=65) {
					
					maps[mposY-1][mposX] = 48;
				    maps[mposY][mposX] = 0;
				    mposY--;
				    
				} else if(randomM<=30) {
					
					maps[mposY][mposX-1] = 48;
				    maps[mposY][mposX] = 0;
				    mposX--;
					
				} else if(65<randomM) {
					
					maps[mposY+1][mposX] = 48;
				    maps[mposY][mposX] = 0;
				    mposY++;
					
				}}
			
			// 上前-為空
			else if(maps[mposY-1][mposX]==0 && maps[mposY][mposX-1]==0) {
				
                  
				if(30<randomM) {
					
					maps[mposY-1][mposX] = 48;
				    maps[mposY][mposX] = 0;
				    mposY--;
				    
				} else if(randomM<=30 ) {
					
					maps[mposY][mposX-1] = 48;
				    maps[mposY][mposX] = 0;
				    mposX--;
				    
				}}
			
			// 前下-為空
			else if(maps[mposY][mposX-1]==0 && maps[mposY+1][mposX]==0) {
				
				if(randomM<=30) {
					
					maps[mposY][mposX-1] = 48;
					maps[mposY][mposX] = 0;
				    mposX--;
				    
				} else if(30<randomM ) {
								
					maps[mposY+1][mposX] = 48;
				    maps[mposY][mposX] = 0;
					mposY++;
							    
				}}
//****************** 下上為空 & 前為人界通道 *******************
			else if(maps[mposY+1][mposX]==0 && maps[mposY-1][mposX]==0 && maps[mposY][mposX-1]==31 ) {
				
				if(randomM<=15) {
					
					maps[mposY+1][mposX] = 48;
					maps[mposY][mposX] = 0;
					mposY++;
				
				} else if(15<randomM && randomM<=30) {
					
					maps[mposY-1][mposX] = 48;
					maps[mposY][mposX] = 0;
					mposY--;
													    
				} else{
					
					maps[mposY][mposX-1] = 48;
					maps[mposY][mposX] = 0;
					refresh();
					try{Thread.sleep(200);}catch(Exception e){}
					
					maps[mposY][mposX-1] = 32;
					refresh();
					try{Thread.sleep(200);}catch(Exception e){}
					
					maps[mposY][mposX-1] = 31;
					
					mposX--;
					nums--;
					
					guilt+=5000;
					lguilt.setText(""+guilt);

				}}
			
			// 下上-為空
			else if(maps[mposY+1][mposX]==0 && maps[mposY-1][mposX]==0) {
				
				if(randomM<=50) {
					
					maps[mposY+1][mposX] = 48;
					maps[mposY][mposX] = 0;
				    mposY++;
				    
				} else if(50<randomM) {
					
					maps[mposY-1][mposX] = 48;
					maps[mposY][mposX] = 0;
					mposY--;
										    
				}}
			
			// 上-為空
			else if(maps[mposY-1][mposX]==0) {
				
				maps[mposY-1][mposX] = 48;
			    maps[mposY][mposX] = 0;
		        mposY--;
		        
			}
			
			// 前-為空
			else if(maps[mposY][mposX-1]==0) {
				
				maps[mposY][mposX-1] = 48;
			    maps[mposY][mposX] = 0;
		        mposX--;
		        
			}
			
			// 下-為空
			else if(maps[mposY+1][mposX]==0) {
				
				maps[mposY+1][mposX] = 48;
			    maps[mposY][mposX] = 0;
		        mposY++;
		        
			}
			
			
	
	
			try{Thread.sleep(350);}catch(Exception e) {}
			refresh();
			
		}
		
	}
	
	
	
//*****************************MonsterS - 超級樹妖(目前已停用)*************************************
	
	public void monsterS() {
	
		maps[mSposY][mSposX]= 48;
		refresh();
		nums++;
		
		// 樹妖最終目標地 - 陰陽交界 - 樹妖因為趨性使然, 不會後退.
		while(0<mSposX) {
			
			// 製造 0-100 的整數亂數, 讓樹妖移動具變化性.
			int randomM = (int)(Math.random()*100);
			
			// 上前下-為空
			if(maps[mSposY-1][mSposX]==0 && maps[mSposY][mSposX-1]==0 && maps[mSposY+1][mSposX]==0){
				
				
				
				if(30<randomM && randomM<=65) {
					
					maps[mSposY-1][mSposX] = 48;
				    maps[mSposY][mSposX] = 0;
				    mSposY--;
				    
				} else if(randomM<=30) {
					
					maps[mSposY][mSposX-1] = 48;
				    maps[mSposY][mSposX] = 0;
				    mSposX--;
					
				} else if(65<randomM) {
					
					maps[mSposY+1][mSposX] = 48;
				    maps[mSposY][mSposX] = 0;
				    mSposY++;
					
				}}
			
			// 上前-為空
			else if(maps[mSposY-1][mSposX]==0 && maps[mSposY][mSposX-1]==0) {
				
                  
				if(30<randomM) {
					
					maps[mSposY-1][mSposX] = 48;
				    maps[mSposY][mSposX] = 0;
				    mSposY--;
				    
				} else if(randomM<=30 ) {
					
					maps[mSposY][mSposX-1] = 48;
				    maps[mSposY][mSposX] = 0;
				    mSposX--;
				    
				}}
			
			// 前下-為空
			else if(maps[mSposY][mSposX-1]==0 && maps[mSposY+1][mSposX]==0) {
				
				if(randomM<=30) {
					
					maps[mSposY][mSposX-1] = 48;
					maps[mSposY][mSposX] = 0;
				    mSposX--;
				    
				} else if(30<randomM ) {
								
					maps[mSposY+1][mSposX] = 48;
				    maps[mSposY][mSposX] = 0;
					mSposY++;
							    
				}}
			// 上下為空 & 前方為人間通道
			else if(maps[mSposY+1][mSposX]==0 && maps[mSposY-1][mSposX]==0 && maps[mSposY][mSposX-1]==31 ) {
					
					if(randomM<=15) {
						
						maps[mSposY+1][mSposX] = 48;
						maps[mSposY][mSposX] = 0;
						mSposY++;
					
					} else if(15<randomM && randomM<=30) {
						
						maps[mSposY-1][mSposX] = 48;
						maps[mSposY][mSposX] = 0;
						mSposY--;
														    
					} else{
						
						maps[mSposY][mSposX-1] = 48;
						maps[mSposY][mSposX] = 0;
						refresh();
						try{Thread.sleep(200);}catch(Exception e){}
						
						maps[mSposY][mSposX-1] = 32;
						try{Thread.sleep(200);}catch(Exception e){}
						refresh();
						
						maps[mSposY][mSposX-1] = 31;
						
						mSposX--;
						nums--;
						
						guilt+=5000;
						lguilt.setText(""+guilt);
				
					}}
			
			// 下上-為空
			else if(maps[mSposY+1][mSposX]==0 && maps[mSposY-1][mSposX]==0) {
				
				if(randomM<=50) {
					
					maps[mSposY+1][mSposX] = 48;
					maps[mSposY][mSposX] = 0;
				    mSposY++;
				    
				} else if(50<randomM) {
					
					maps[mSposY-1][mSposX] = 48;
					maps[mSposY][mSposX] = 0;
					mSposY--;
										    
				}}
			
			// 上-為空
			else if(maps[mSposY-1][mSposX]==0) {
				
				maps[mSposY-1][mSposX] = 48;
			    maps[mSposY][mSposX] = 0;
		        mSposY--;
		        
			}
			
			// 前-為空
			else if(maps[mSposY][mSposX-1]==0) {
				
				maps[mSposY][mSposX-1] = 48;
			    maps[mSposY][mSposX] = 0;
		        mSposX--;
		        
			}
			
			// 下-為空
			else if(maps[mSposY+1][mSposX]==0) {
				
				maps[mSposY+1][mSposX] = 48;
			    maps[mSposY][mSposX] = 0;
		        mSposY++;
		        
			}
	
	
			try{Thread.sleep(200);}catch(Exception e) {}
			refresh();
			
		}
		
	}
	
	
	
	
	
	
	

//************************************* 主角移動判斷系統 *********************************************
	public void	keyPressed(KeyEvent situ) {
		
		int kB = situ.getKeyCode();
		
		// 匯入符籙 FuLu-ArrayList 有助於判斷
		FuLu();
		
		
		// 人物向上走
		if(kB==KeyEvent.VK_UP) {
			
			//上推-單推五種符籙FuLu1
			
		    if(94<maps[posY-1][posX] && maps[posY-2][posX]==0) {
		    	
		    	for(int i=0 ; i<FuLu1.size() ; i++) {
		    		
		    		if(maps[posY-1][posX]==FuLu1.get(i) && maps[posY-2][posX]==0 && maps[posY][posX]==6) {
		    			
		    			maps[posY-2][posX] = FuLu1.get(i);
		    			maps[posY-1][posX] = 5;
		    			maps[posY][posX] = 4;
		    			posY--;
		    			
		    		} else if(maps[posY-1][posX]==FuLu1.get(i) && maps[posY-2][posX]==0 && maps[posY][posX]==7) {
		    			
		    			maps[posY-2][posX] = FuLu1.get(i);
		    			maps[posY-1][posX] = 1;
		    			maps[posY][posX] = 4;
		    			posY--;
		    			
		    		} else if(maps[posY-1][posX]==FuLu1.get(i) && maps[posY-2][posX]==0) {
		    			
		    			maps[posY-2][posX] = FuLu1.get(i);
		    			maps[posY-1][posX] = 1;
		    			maps[posY][posX] = 0;
		    			posY--;
		    		}}}
		    
		    // 上推-合成符籙FuLu系統
		    else if(94<maps[posY-1][posX] && 94<maps[posY-2][posX]) {
				
		    	for(int i=0 ; i<9 ; i++) {
		    		
		    		if(maps[posY-1][posX]+maps[posY-2][posX]==FuLuSum.get(i) && maps[posY][posX]==6) {
							
		    			if(FuLuSum.get(i)>193) {
					    	
					    	maps[posY-2][posX] = 49;
					        maps[posY-1][posX] = 1;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
					    	fuluNums-=2;
					    }
					    
					    else{
					    	
					    	maps[posY-2][posX] = 50;
					        maps[posY-1][posX] = 1;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums--;
						
					    } 
						
					    maps[posY-2][posX] = FuLuColor.get(i);
						posY--;
						
							
					} else if(maps[posY-1][posX]+maps[posY-2][posX]==FuLuSum.get(i) && maps[posY][posX]==7) {
							
						if(FuLuSum.get(i)>193) {
					    	
					    	maps[posY-2][posX] = 49;
					        maps[posY-1][posX] = 1;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums-=2;
					    }
					    
					    else{
					    	
					    	maps[posY-2][posX] = 50;
					        maps[posY-1][posX] = 1;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums--;
						
					    } 
						
					    maps[posY-2][posX] = FuLuColor.get(i);
						posY--;
						
					} else if(maps[posY-1][posX]+maps[posY-2][posX]==FuLuSum.get(i)) {
							
						    if(FuLuSum.get(i)>193) {
						    	
						    	maps[posY-2][posX] = 49;
						        maps[posY-1][posX] = 1;
							    maps[posY][posX] = 0;
							    refresh();
							    try{Thread.sleep(timeSpeed);}catch(Exception e){}
							    fuluNums-=2;
						    }
						    
						    else{
						    	
						    	maps[posY-2][posX] = 50;
						        maps[posY-1][posX] = 1;
							    maps[posY][posX] = 0;
							    refresh();
							    try{Thread.sleep(timeSpeed);}catch(Exception e){}
							    fuluNums--;
							
						    } 
							
						    maps[posY-2][posX] = FuLuColor.get(i);
							posY--;
					}}}
		   
           // 主角上推收妖
           else if(94<maps[posY-1][posX] && (45<maps[posY-2][posX] && maps[posY-2][posX]<49)) {
        	   
        	   // 上推收樹妖, 樹妖代碼48, 1-5級符皆能收
        	   if(maps[posY-2][posX]==48) {
	        	   
        		   for(int i=0 ; i<5 ; i++) {
	        		   if(maps[posY-1][posX]== FuLu1.get(i) && maps[posY-2][posX]==48 && maps[posY][posX]==1){
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 1;
	        			   maps[posY][posX] = 0;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        			   
	        		       int fuluMult = (int)Math.pow(2,i); 
	        			   virtue+=(7000*fuluMult);
	        			   lvirtue.setText("" + virtue);
	        		   }
	        		   
	        		   else if(maps[posY-1][posX]== FuLu1.get(i) && maps[posY-2][posX]==48 && maps[posY][posX]==5) {
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 5;
	        			   maps[posY][posX] = 0;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        			   
	        			   
	        		       int fuluMult = (int)Math.pow(2,i); 
	        			   virtue+=(7000*fuluMult);
	        			   lvirtue.setText("" + virtue);
	        			   
	        		   }
	        		   
	        		   else if(maps[posY-1][posX]== FuLu1.get(i) && maps[posY-2][posX]==48 && maps[posY][posX]==6) {
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 5;
	        			   maps[posY][posX] = 4;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        			   
	        			   
	        		       int fuluMult = (int)Math.pow(2,i); 
	        			   virtue+=(7000*fuluMult);
	        			   lvirtue.setText("" + virtue);
	        			   
	        		   }
	        		   
	        		   else if(maps[posY-1][posX]== FuLu1.get(i) && maps[posY-2][posX]==48 && maps[posY][posX]==7) {
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 1;
	        			   maps[posY][posX] = 4;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        			   
	        			   
	        		       int fuluMult = (int)Math.pow(2,i); 
	        			   virtue+=(7000*fuluMult);
	        			   lvirtue.setText("" + virtue);
	        			   
	        		   }}}
        	   
        	   
        	   // 上推收九尾狐, 九尾狐代碼47, 3-5級符皆能收
        	   else if(maps[posY-2][posX]==47) {
        		   
        		   for(int i=2 ; i<5 ; i++) {
	        		   if(maps[posY-1][posX]== FuLu1.get(i) && maps[posY-2][posX]==47 && maps[posY][posX]==1){
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 1;
	        			   maps[posY][posX] = 0;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        			   
	        			   
	        		       int fuluMult = (int)Math.pow(2,(i-2)); 
	        			   virtue+=(50000*fuluMult);
	        			   lvirtue.setText("" + virtue);
	        		   }
	        		   
	        		   else if(maps[posY-1][posX]== FuLu1.get(i) && maps[posY-2][posX]==47 && maps[posY][posX]==5) {
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 5;
	        			   maps[posY][posX] = 0;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        			   
	        			   
	        			   int fuluMult = (int)Math.pow(2,(i-2)); 
	        			   virtue+=(50000*fuluMult);
	        			   lvirtue.setText("" + virtue);
	        			   
	        		   }
	        		   
	        		   else if(maps[posY-1][posX]== FuLu1.get(i) && maps[posY-2][posX]==47 && maps[posY][posX]==6) {
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 5;
	        			   maps[posY][posX] = 4;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        			   
	        			   
	        			   int fuluMult = (int)Math.pow(2,(i-2)); 
	        			   virtue+=(50000*fuluMult);
	        			   lvirtue.setText("" + virtue);
	        			   
	        		   }
	        		   
	        		   else if(maps[posY-1][posX]== FuLu1.get(i) && maps[posY-2][posX]==47 && maps[posY][posX]==7) {
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 1;
	        			   maps[posY][posX] = 4;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        			   
	        			   
	        			   int fuluMult = (int)Math.pow(2,(i-2)); 
	        			   virtue+=(50000*fuluMult);
	        			   lvirtue.setText("" + virtue);
	        			   
	        		   }}}
        	
        	   
        	   // 上推收姥姥, 姥姥代碼46, 5級符能收
               else if(maps[posY-2][posX]==46) {
        		   
        		   if(maps[posY-1][posX]== 99 && maps[posY-2][posX]==46 && maps[posY][posX]==1){
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 1;
	        			   maps[posY][posX] = 0;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        	
	        			   virtue+=300000;
	        			   lvirtue.setText("" + virtue);
	        		   }
	        		   
	        		   else if(maps[posY-1][posX]== 99 && maps[posY-2][posX]==46 && maps[posY][posX]==5) {
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 5;
	        			   maps[posY][posX] = 0;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        			   
	        			   
	        			   virtue+=300000;
	        			   lvirtue.setText("" + virtue);
	        			   
	        		   }
	        		   
	        		   else if(maps[posY-1][posX]== 99 && maps[posY-2][posX]==46 && maps[posY][posX]==6) {
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 5;
	        			   maps[posY][posX] = 4;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        			   
	        			   
	        			   virtue+=300000;
	        			   lvirtue.setText("" + virtue);
	        			   
	        		   }
	        		   
	        		   else if(maps[posY-1][posX]== 99 && maps[posY-2][posX]==46 && maps[posY][posX]==7) {
	        			   
	        			   maps[posY-2][posX] = 55;
	        			   maps[posY-1][posX] = 1;
	        			   maps[posY][posX] = 4;
	        			   
	        			   refresh();
						   try{Thread.sleep(timeSpeed);}catch(Exception e){}
	        			   
	        			   maps[posY-2][posX] = 0;
	        			   
	        			   posY--;
	        			   fuluNums--;
	        			   
	        			   
	        			   virtue+=300000;
	        			   lvirtue.setText("" + virtue);
	        			   
	        		   }}
        	   
           }

		   // 主角上走-基本主角移動判斷
		    else if(maps[posY][posX]==6 && maps[posY-1][posX]==0) {
				
				maps[posY-1][posX] = 5;
				maps[posY][posX] = 4;
				posY--;
				
			} 		    
		    
		    else if(maps[posY][posX]==7 && maps[posY-1][posX]==0) {
				
		    	maps[posY-1][posX] = 5;
				maps[posY][posX] = 4;
				posY--;
			
		    } 
		    
		    else if(maps[posY][posX]==7 && maps[posY-1][posX]==4) {
				
		    	maps[posY-1][posX] = 7;
				maps[posY][posX] = 4;
				posY--;
				
			} 
		    
		    else if(maps[posY][posX]==6 && maps[posY-1][posX]==4) {
				
				maps[posY-1][posX] = 6;
				maps[posY][posX] = 4;
				posY--;
			
			} 
		    
		    // 召喚小倩, 陽氣值全滿(1)
		    else if(maps[posY][posX]==1 && maps[posY-1][posX]==17) {
				
			maps[posY-1][posX] = 18;
			maps[posY][posX] = 1
					;
			refresh();
			try{Thread.sleep(250);}catch(Exception e){}
			
			maps[posY-1][posX] = 1;
			maps[posY][posX] = 0;
			posY--;
			sun = 2000;
			
		    }
		    // 召喚小倩, 陽氣值全滿(5)
		    else if(maps[posY][posX]==5 && maps[posY-1][posX]==17) {
				
			maps[posY-1][posX] = 18;
			maps[posY][posX] = 5;
			refresh();
			try{Thread.sleep(250);}catch(Exception e){}
			
			maps[posY-1][posX] = 5;
			maps[posY][posX] = 0;
			posY--;
			sun = 2000;
			
		    }
		    
		    else if(maps[posY][posX]==1 && maps[posY-1][posX]==0) {
				
				maps[posY-1][posX] = 1;
				maps[posY][posX] = 0;
				posY--;
				
			}
		    
		    else if(maps[posY][posX]==5 && maps[posY-1][posX]==0) {
				
				maps[posY-1][posX] = 5;
				maps[posY][posX] = 0;
				posY--;
				
			}    
		    
		    
		    
		    else if(maps[posY-1][posX]==4) {
				
		    	maps[posY-1][posX] = 6;
				maps[posY][posX] = 0;
				posY--;
				
			}}
		
		// 人物向下走 (數字鍵2)
		else if(kB==KeyEvent.VK_DOWN) {
			
			//下推-單推五種符籙FuLu1
			 if(94<maps[posY+1][posX] && maps[posY+2][posX]==0) {
			    	
			    	for(int i=0 ; i<FuLu1.size() ; i++) {
			    		
			    		if(maps[posY+1][posX]==FuLu1.get(i) && maps[posY+2][posX]==0 && maps[posY][posX]==6) {
			    			
			    			maps[posY+2][posX] = FuLu1.get(i);
			    			maps[posY+1][posX] = 5;
			    			maps[posY][posX] = 4;
			    			posY++;
			    			
			    		} else if(maps[posY+1][posX]==FuLu1.get(i) && maps[posY+2][posX]==0 && maps[posY][posX]==7) {
			    			
			    			maps[posY+2][posX] = FuLu1.get(i);
			    			maps[posY+1][posX] = 1;
			    			maps[posY][posX] = 4;
			    			posY++;
			    			
			    		} else if(maps[posY+1][posX]==FuLu1.get(i) && maps[posY+2][posX]==0) {
			    			
			    			maps[posY+2][posX] = FuLu1.get(i);
			    			maps[posY+1][posX] = 1;
			    			maps[posY][posX] = 0;
			    			posY++;
			    		}}}

		     // 下推-合成符籙FuLu系統
			 else if(94<maps[posY+1][posX] && 94<maps[posY+2][posX]) {
				
		    	for(int i=0 ; i<9 ; i++) {
		    		
		    		if(maps[posY+1][posX]+maps[posY+2][posX]==FuLuSum.get(i) && maps[posY][posX]==6) {
							
                       if(FuLuSum.get(i)>193) {
					    	
					    	maps[posY+2][posX] = 49;
					        maps[posY+1][posX] = 5;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums-=2;
					    }
					    
					    else{
					    	
					    	maps[posY+2][posX] = 50;
					        maps[posY+1][posX] = 5;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums--;
						
					    } 
						
					    maps[posY+2][posX] = FuLuColor.get(i);
						posY++;
							
					} else if(maps[posY+1][posX]+maps[posY+2][posX]==FuLuSum.get(i) && maps[posY][posX]==7) {
							
						if(FuLuSum.get(i)>193) {
					    	
					    	maps[posY+2][posX] = 49;
					        maps[posY+1][posX] = 1;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums-=2;
					    }
					    
					    else{
					    	
					    	maps[posY+2][posX] = 50;
					        maps[posY+1][posX] = 1;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums--;
						
					    } 
						
					    maps[posY+2][posX] = FuLuColor.get(i);
						posY++;
						
					} else if(maps[posY+1][posX]+maps[posY+2][posX]==FuLuSum.get(i)) {
							
						 if(FuLuSum.get(i)>193) {
						    	
						    	maps[posY+2][posX] = 49;
						        maps[posY+1][posX] = 1;
							    maps[posY][posX] = 0;
							    refresh();
							    try{Thread.sleep(timeSpeed);}catch(Exception e){}
							    fuluNums-=2;
						    }
						    
						    else{
						    	
						    	maps[posY+2][posX] = 50;
						        maps[posY+1][posX] = 1;
							    maps[posY][posX] = 0;
							    refresh();
							    try{Thread.sleep(timeSpeed);}catch(Exception e){}
							    fuluNums--;
							
						    } 
							
						    maps[posY+2][posX] = FuLuColor.get(i);
							posY++;
					}}
		    	}
			
			// 主角下推收妖
	           else if(94<maps[posY+1][posX] && (45<maps[posY+2][posX] && maps[posY+2][posX]<49)) {
	        	   
	        	   // 下推收樹妖, 樹妖代碼48, 1-5級符皆能收
	        	   if(maps[posY+2][posX]==48) {
		        	   
	        		   for(int i=0 ; i<5 ; i++) {
		        		   if(maps[posY+1][posX]== FuLu1.get(i) && maps[posY+2][posX]==48 && maps[posY][posX]==1){
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 1;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        		   }
		        		   
		        		   else if(maps[posY+1][posX]== FuLu1.get(i) && maps[posY+2][posX]==48 && maps[posY][posX]==5) {
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 5;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY+1][posX]== FuLu1.get(i) && maps[posY+2][posX]==48 && maps[posY][posX]==6) {
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 5;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY+1][posX]== FuLu1.get(i) && maps[posY+2][posX]==48 && maps[posY][posX]==7) {
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 1;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }}}
	        	   
	        	   
	        	   // 下推收九尾狐, 九尾狐代碼47, 3-5級符皆能收
	        	   else if(maps[posY+2][posX]==47) {
	        		   
	        		   for(int i=2 ; i<5 ; i++) {
		        		   if(maps[posY+1][posX]== FuLu1.get(i) && maps[posY+2][posX]==47 && maps[posY][posX]==1){
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 1;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        		   }
		        		   
		        		   else if(maps[posY+1][posX]== FuLu1.get(i) && maps[posY+2][posX]==47 && maps[posY][posX]==5) {
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 5;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        			   
		        			   
		        			   int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY+1][posX]== FuLu1.get(i) && maps[posY+2][posX]==47 && maps[posY][posX]==6) {
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 5;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        			   
		        			   
		        			   int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY+1][posX]== FuLu1.get(i) && maps[posY+2][posX]==47 && maps[posY][posX]==7) {
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 1;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        			   
		        			   
		        			   int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }}}
	        	
	        	   
	        	   // 下推收姥姥, 姥姥代碼46, 5級符能收
	               else if(maps[posY+2][posX]==46) {
	        		   
	        		   if(maps[posY+1][posX]== 99 && maps[posY+2][posX]==46 && maps[posY][posX]==1){
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 1;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        	
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        		   }
		        		   
		        		   else if(maps[posY+1][posX]== 99 && maps[posY+2][posX]==46 && maps[posY][posX]==5) {
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 5;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        			   
		        			   
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY+1][posX]== 99 && maps[posY+2][posX]==46 && maps[posY][posX]==6) {
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 5;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        			   
		        			   
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY+1][posX]== 99 && maps[posY+2][posX]==46 && maps[posY][posX]==7) {
		        			   
		        			   maps[posY+2][posX] = 55;
		        			   maps[posY+1][posX] = 1;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY+2][posX] = 0;
		        			   
		        			   posY++;
		        			   fuluNums--;
		        			   
		        			   
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }}}
			
		    // 主角下走-基本主角移動判斷
			 else if(maps[posY][posX]==6 && maps[posY+1][posX]==0) {
				
				maps[posY+1][posX] = 5;
				maps[posY][posX] = 4;
				posY++;
				
			} else if(maps[posY][posX]==7 && maps[posY+1][posX]==0) {
				
				maps[posY+1][posX] = 5;
				maps[posY][posX] = 4;
				posY++;
				
			} else if(maps[posY][posX]==6 && maps[posY+1][posX]==4) {
				
				maps[posY+1][posX] = 6;
				maps[posY][posX] = 4;
				posY++;
				
			} else if(maps[posY][posX]==7 && maps[posY+1][posX]==4) {
				
				maps[posY+1][posX] = 7;
				maps[posY][posX] = 4;
				posY++;
				
			} else if(maps[posY+1][posX]==4) {
				
				maps[posY+1][posX] = 7;
				maps[posY][posX] = 0;
				posY++;
				
			} else if(maps[posY][posX]==1 && maps[posY+1][posX]==0) {
				
				maps[posY+1][posX] = 1;
				maps[posY][posX] = 0;
				posY++;
				
			}
			else if(maps[posY][posX]==5 && maps[posY+1][posX]==0) {
				
				maps[posY+1][posX] = 5;
				maps[posY][posX] = 0;
				posY++;
				
			}}
		
		// 人物向左走 (數字鍵4)
		else if(kB==KeyEvent.VK_LEFT) {
			
			//左推-單推五種符籙FuLu1
			 if(94<maps[posY][posX-1] && maps[posY][posX-2]==0) {
			    	
			    	for(int i=0 ; i<FuLu1.size() ; i++) {
			    		
			    		if(maps[posY][posX-1]==FuLu1.get(i) && maps[posY][posX-2]==0 && maps[posY][posX]==6) {
			    			
			    			maps[posY][posX-2] = FuLu1.get(i);
			    			maps[posY][posX-1] = 5;
			    			maps[posY][posX] = 4;
			    			posX--;
			    			
			    		} else if(maps[posY][posX-1]==FuLu1.get(i) && maps[posY][posX-2]==0 && maps[posY][posX]==7) {
			    			
			    			maps[posY][posX-2] = FuLu1.get(i);
			    			maps[posY][posX-1] = 5;
			    			maps[posY][posX] = 4;
			    			posX--;
			    			
			    		} else if(maps[posY][posX-1]==FuLu1.get(i) && maps[posY][posX-2]==0) {
			    			
			    			maps[posY][posX-2] = FuLu1.get(i);
			    			maps[posY][posX-1] = 5;
			    			maps[posY][posX] = 0;
			    			posX--;
			    		}}}

		     // 左推-合成符籙FuLu系統
			 else if(94<maps[posY][posX-1] && 94<maps[posY][posX-2]) {
				
		    	for(int i=0 ; i<9 ; i++) {
		    		
		    		if(maps[posY][posX-1]+maps[posY][posX-2]==FuLuSum.get(i) && maps[posY][posX]==6) {
							
                        if(FuLuSum.get(i)>193) {
					    	
					    	maps[posY][posX-2] = 49;
					        maps[posY][posX-1] = 5;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums-=2;
					    }
					    
					    else{
					    	
					    	maps[posY][posX-2] = 50;
					        maps[posY][posX-1] = 5;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums--;
						
					    } 
						
					    maps[posY][posX-2] = FuLuColor.get(i);
						posX--;
							
					} else if(maps[posY][posX-1]+maps[posY][posX-2]==FuLuSum.get(i) && maps[posY][posX]==7) {
							
                       if(FuLuSum.get(i)>193) {
					    	
					    	maps[posY][posX-2] = 49;
					        maps[posY][posX-1] = 5;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums-=2;
					    }
					    
					    else{
					    	
					    	maps[posY][posX-2] = 50;
					        maps[posY][posX-1] = 5;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums--;
						
					    } 
						
					    maps[posY][posX-2] = FuLuColor.get(i);
						posX--;
						
					} else if(maps[posY][posX-1]+maps[posY][posX-2]==FuLuSum.get(i)) {
							
						if(FuLuSum.get(i)>193) {
					    	
					    	maps[posY][posX-2] = 49;
					        maps[posY][posX-1] = 5;
						    maps[posY][posX] = 0;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums-=2;
					    }
					    
					    else{
					    	
					    	maps[posY][posX-2] = 50;
					        maps[posY][posX-1] = 5;
						    maps[posY][posX] = 0;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums--;
						
					    } 
						
					    maps[posY][posX-2] = FuLuColor.get(i);
						posX--;
					}}
		    	}
			 
			
			 // 主角左推收妖
	           else if(94<maps[posY][posX-1] && (45<maps[posY][posX-2] && maps[posY][posX-2]<49 && 2<posX)) {
	        	   
	        	   // 左推收樹妖, 樹妖代碼48, 1-5級符皆能收
	        	   if(maps[posY][posX-2]==48) {
		        	   
	        		   for(int i=0 ; i<5 ; i++) {
		        		   if(maps[posY][posX-1]== FuLu1.get(i) && maps[posY][posX-2]==48 && maps[posY][posX]==1){
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 1;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-2] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        		   }
		        		   
		        		   else if(maps[posY][posX-1]== FuLu1.get(i) && maps[posY][posX-2]==48 && maps[posY][posX]==5) {
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 5;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-2] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX-1]== FuLu1.get(i) && maps[posY][posX-2]==48 && maps[posY][posX]==6) {
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 5;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-2] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX-1]== FuLu1.get(i) && maps[posY][posX-2]==48 && maps[posY][posX]==7) {
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 1;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-2] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }}}
	        	   
	        	   
	        	   // 左推收九尾狐, 九尾狐代碼47, 3-5級符皆能收
	        	   else if(maps[posY][posX-2]==47) {
	        		   
	        		   for(int i=2 ; i<5 ; i++) {
		        		   if(maps[posY][posX-1]== FuLu1.get(i) && maps[posY][posX-2]==47 && maps[posY][posX]==1){
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 1;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-2] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        		   }
		        		   
		        		   else if(maps[posY][posX-1]== FuLu1.get(i) && maps[posY][posX-2]==47 && maps[posY][posX]==5) {
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 5;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-2] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        			   
		        			   
		        			   int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX-1]== FuLu1.get(i) && maps[posY][posX-2]==47 && maps[posY][posX]==6) {
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 5;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-2] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        			   
		        			   
		        			   int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX-1]== FuLu1.get(i) && maps[posY][posX-2]==47 && maps[posY][posX]==7) {
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 1;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-2] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        			   
		        			   
		        			   int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }}}
	        	
	        	   
	        	   // 左推收姥姥, 姥姥代碼46, 5級符能收
	               else if(maps[posY][posX-2]==46) {
	        		   
	        		   if(maps[posY][posX-1]== 99 && maps[posY][posX-2]==46 && maps[posY][posX]==1){
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 1;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-2] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        	
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        		   }
		        		   
		        		   else if(maps[posY][posX-1]== 99 && maps[posY][posX-2]==46 && maps[posY][posX]==5) {
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 5;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-2] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        			   
		        			   
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX-1]== 99 && maps[posY][posX-2]==46 && maps[posY][posX]==6) {
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 5;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-2] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        			   
		        			   
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX-1]== 99 && maps[posY][posX-2]==46 && maps[posY][posX]==7) {
		        			   
		        			   maps[posY][posX-2] = 55;
		        			   maps[posY][posX-1] = 1;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX-1] = 0;
		        			   
		        			   posX--;
		        			   fuluNums--;
		        			   
		        			   
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }}}
			
			 // 主角左走-基本主角移動判斷 
			 else if(maps[posY][posX]==6 && maps[posY][posX-1]==0) {
				
				maps[posY][posX-1] = 5;
				maps[posY][posX] = 4;
				posX--;
				
			} else if(maps[posY][posX]==7 && maps[posY][posX-1]==0) {
				
				maps[posY][posX-1] = 5;
				maps[posY][posX] = 4;
				posX--;
			
			  	
			} 
			    // 召喚小倩, 陽氣值全滿
			  else if(maps[posY][posX]==5 && maps[posY][posX-1]==17) {
				
				maps[posY][posX-1] = 18;
				maps[posY][posX] = 5;
				refresh();
				try{Thread.sleep(250);}catch(Exception e){}
				
				maps[posY][posX-1] = 5;
				maps[posY][posX] = 0;
				posX--;
				sun = 2000;
				
				
			} else if(maps[posY][posX-1]==0) {
				
				maps[posY][posX-1] = 5;
				maps[posY][posX] = 0;
				posX--;
				
			} else if(maps[posY][posX-1]==4) {
				
				maps[posY][posX-1] = 6;
				maps[posY][posX] = 0;
				posX--;
				
			}  
		    }
		
		// 人物向右走 (數字鍵6)
		
		else if(kB == KeyEvent.VK_RIGHT) {
			
			 //右推-單推五種符籙FuLu1
			 if(94<maps[posY][posX+1] && maps[posY][posX+2]==0) {
			    	
			    	for(int i=0 ; i<FuLu1.size() ; i++) {
			    		
			    		if(maps[posY][posX+1]==FuLu1.get(i) && maps[posY][posX+2]==0 && maps[posY][posX]==6) {
			    			
			    			maps[posY][posX+2] = FuLu1.get(i);
			    			maps[posY][posX+1] = 1;
			    			maps[posY][posX] = 4;
			    			posX++;
			    			
			    		} else if(maps[posY][posX+1]==FuLu1.get(i) && maps[posY][posX+2]==0 && maps[posY][posX]==7) {
			    			
			    			maps[posY][posX+2] = FuLu1.get(i);
			    			maps[posY][posX+1] = 1;
			    			maps[posY][posX] = 4;
			    			posX++;
			    			
			    		} else if(maps[posY][posX+1]==FuLu1.get(i) && maps[posY][posX+2]==0) {
			    			
			    			maps[posY][posX+2] = FuLu1.get(i);
			    			maps[posY][posX+1] = 1;
			    			maps[posY][posX] = 0;
			    			posX++;
			    		}}}

		     // 右推-合成符籙FuLu系統
			 else if(94<maps[posY][posX+1] && 94<maps[posY][posX+2]) {
				
		    	for(int i=0 ; i<9 ; i++) {
		    		
		    		if(maps[posY][posX+1]+maps[posY][posX+2]==FuLuSum.get(i) && maps[posY][posX]==6) {
							
                        if(FuLuSum.get(i)>193) {
					    	
					    	maps[posY][posX+2] = 49;
					        maps[posY][posX+1] = 1;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums-=2;
					    }
					    
					    else{
					    	
					    	maps[posY][posX+2] = 50;
					        maps[posY][posX+1] = 1;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums--;
						
					    } 
						
					    maps[posY][posX+2] = FuLuColor.get(i);
						posX++;
							
					} else if(maps[posY][posX+1]+maps[posY][posX+2]==FuLuSum.get(i) && maps[posY][posX]==7) {
							
                        if(FuLuSum.get(i)>193) {
					    	
					    	maps[posY][posX+2] = 49;
					        maps[posY][posX+1] = 1;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums-=2;
					    }
					    
					    else{
					    	
					    	maps[posY][posX+2] = 50;
					        maps[posY][posX+1] = 1;
						    maps[posY][posX] = 4;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums--;
						
					    } 
						
					    maps[posY][posX+2] = FuLuColor.get(i);
						posX++;
						
					} else if(maps[posY][posX+1]+maps[posY][posX+2]==FuLuSum.get(i)) {
							
                       if(FuLuSum.get(i)>193) {
					    	
					    	maps[posY][posX+2] = 49;
					        maps[posY][posX+1] = 1;
						    maps[posY][posX] = 0;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums-=2;
					    }
					    
					    else{
					    	
					    	maps[posY][posX+2] = 50;
					        maps[posY][posX+1] = 1;
						    maps[posY][posX] = 0;
						    refresh();
						    try{Thread.sleep(timeSpeed);}catch(Exception e){}
						    fuluNums--;
						
					    } 
						
					    maps[posY][posX+2] = FuLuColor.get(i);
						posX++;
					}}}
			 
			 
			// 主角右推收妖
	           else if(94<maps[posY][posX+1] && (45<maps[posY][posX+2] && maps[posY][posX+2]<49 && posX<19)) {
	        	   
	        	   // 右推收樹妖, 樹妖代碼48, 1-5級符皆能收
	        	   if(maps[posY][posX+2]==48) {
		        	   
	        		   for(int i=0 ; i<5 ; i++) {
		        		   if(maps[posY][posX+1]== FuLu1.get(i) && maps[posY][posX+2]==48 && maps[posY][posX]==1){
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 1;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+2] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        		   }
		        		   
		        		   else if(maps[posY][posX+1]== FuLu1.get(i) && maps[posY][posX+2]==48 && maps[posY][posX]==5) {
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 5;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+2] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX+1]== FuLu1.get(i) && maps[posY][posX+2]==48 && maps[posY][posX]==6) {
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 5;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+2] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX+1]== FuLu1.get(i) && maps[posY][posX+2]==48 && maps[posY][posX]==7) {
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 1;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+2] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,i); 
		        			   virtue+=(7000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }}}
	        	   
	        	   
	        	   // 右推收九尾狐, 九尾狐代碼47, 3-5級符皆能收
	        	   else if(maps[posY][posX+2]==47 && posX!=20) {
	        		   
	        		   for(int i=2 ; i<5 ; i++) {
		        		   if(maps[posY][posX+1]== FuLu1.get(i) && maps[posY][posX+2]==47 && maps[posY][posX]==1){
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 1;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+2] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        			   
		        			   
		        		       int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        		   }
		        		   
		        		   else if(maps[posY][posX+1]== FuLu1.get(i) && maps[posY][posX+2]==47 && maps[posY][posX]==5) {
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 5;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+2] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        			   
		        			   
		        			   int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX+1]== FuLu1.get(i) && maps[posY][posX+2]==47 && maps[posY][posX]==6) {
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 5;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+2] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        			   
		        			   
		        			   int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX+1]== FuLu1.get(i) && maps[posY][posX+2]==47 && maps[posY][posX]==7) {
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 1;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+2] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        			   
		        			   
		        			   int fuluMult = (int)Math.pow(2,(i-2)); 
		        			   virtue+=(50000*fuluMult);
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }}}
	        	
	        	   
	        	   // 右推收姥姥, 姥姥代碼46, 5級符能收
	               else if(maps[posY][posX+2]==46 && posX!=20) {
	        		   
	        		   if(maps[posY][posX+1]== 99 && maps[posY][posX+2]==46 && maps[posY][posX]==1){
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 1;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+2] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        	
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        		   }
		        		   
		        		   else if(maps[posY][posX+1]== 99 && maps[posY][posX+2]==46 && maps[posY][posX]==5) {
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 5;
		        			   maps[posY][posX] = 0;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+2] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        			   
		        			   
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX+1]== 99 && maps[posY][posX+2]==46 && maps[posY][posX]==6) {
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 5;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+2] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        			   
		        			   
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }
		        		   
		        		   else if(maps[posY][posX+1]== 99 && maps[posY][posX+2]==46 && maps[posY][posX]==7) {
		        			   
		        			   maps[posY][posX+2] = 55;
		        			   maps[posY][posX+1] = 1;
		        			   maps[posY][posX] = 4;
		        			   
		        			   refresh();
							   try{Thread.sleep(timeSpeed);}catch(Exception e){}
		        			   
		        			   maps[posY][posX+1] = 0;
		        			   
		        			   posX++;
		        			   fuluNums--;
		        			   
		        			   
		        			   virtue+=300000;
		        			   lvirtue.setText("" + virtue);
		        			   
		        		   }}}
			

		     // 主角右走-基本主角移動判斷
			 else if(maps[posY][posX]==6 && maps[posY][posX+1]==0) {
				
				maps[posY][posX+1] = 1;
				maps[posY][posX] = 4;
				posX++;
				
			} else if(maps[posY][posX]==7 && maps[posY][posX+1]==0) {
				
				maps[posY][posX+1] = 1;
				maps[posY][posX] = 4;
				posX++;
				
			} else if(maps[posY][posX+1] == 0) {
				
				maps[posY][posX+1] = 1;
				maps[posY][posX] = 0;
				posX++;
				
				
											
			} else if(maps[posY][posX+1] == 4) {
				
				maps[posY][posX+1] = 7;
				maps[posY][posX] = 0;
				posX++;
			
			} 
		}
		
		// 按鍵 Space 讓(角落)符可重製
		else if(kB==KeyEvent.VK_SPACE) {
					       
			if(94<maps[posY-1][posX]) {
							
				maps[posY-1][posX] = 56;
				refresh();
				try{Thread.sleep(150);}catch(Exception e){}
				maps[posY-1][posX] = 0;
				fuluNums--;
				
			}
			
			else if(94<maps[posY+1][posX]) {
				
				maps[posY+1][posX] = 56;
				refresh();
				try{Thread.sleep(150);}catch(Exception e){}
				maps[posY+1][posX] = 0;
				fuluNums--;
				
			}
			
			else if(94<maps[posY][posX-1]) {
				
				maps[posY][posX-1] = 56;
				refresh();
				try{Thread.sleep(150);}catch(Exception e){}
				maps[posY][posX-1] = 0;
				fuluNums--;
					
			} 
			
			else if(94<maps[posY][posX+1]) {
				
				maps[posY][posX+1] = 56;
				refresh();
				try{Thread.sleep(150);}catch(Exception e){}
				maps[posY][posX+1] = 0;
				fuluNums--;
					
			}
			
			
			}
		
		// 按鍵 Q | q <返回人間>
		else if(kB == 'Q' | kB == 'q') {
			
			System.out.println("Debug, 是個好消遣!");
			System.exit(0);
		}
		
		// 匯入自然生成符籙FuLu系統
		fuluBorn();
		
		
		// KeyEvent壓軸Refresh
		refresh();
		
	}	
	
	public void keyReleased(KeyEvent situ) {
		
	}
	
	
	public void keyTyped(KeyEvent situ) {
		
	}

}


//**************************************** 主程式進入點 *********************************************

	
	