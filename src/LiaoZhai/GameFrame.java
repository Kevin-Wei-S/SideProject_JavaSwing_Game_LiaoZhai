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
	
	//�a��ø�s
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
	
	// �a�ϸ�Ʀ���
	
	// �a�� �P Frame ���Z
	public int top = 80, left = 9;
	
	// �H���y��
	public int posX = 8, posY = 1;
	
	// �Ϥ�����   //69
	public int M = 69;
	
	// �X���Φ���-�ɶ��Ƚw����, �ثe����75���|�y���ͩǯ��� 
	// �X���ŤΦ����Ϥ��ഫ�𺢷|�ֿn, �y���ͩǨt�ί���, �p�G�ͲŨt�Χ���, �Ʀr�i��ݤU��
	int timeSpeed = 75;
	
	// ��-Monster�y��
	public int mposX = 17;
	public int mposY = 5;
	
	// ��S-Monster�y��
	public int mSposX = 18;
	public int mSposY = 6;
	
	// �H����- (i) �p��ثe�a�ϤW�����`��, �B�����s��
	// �H����- (ii) ���s���]�w Y�b�y��
	// �H����- (iii) ���s���]�w X�b�y��
	// �H����- (iv) �𧯼ƶq
	// �H����- (v) �𧯽s��
	public ArrayList <Integer> monsterNums;
	//public ArrayList <Integer> monsterYaxis;
	//public ArrayList <Integer> monsterXaxis;
	public int nums = 0;
	//public int numsID = -1;
	
	//�H���ͦ�FuLu����
	public int fuluNums = 9;
	
	// JProgressBar-����Ȧ���
	public JProgressBar jb; 
	public JLabel l0, l1 ;
	public int sun = 2000;
	
	// JLabel-���w�Ȧ���
	public JLabel lvirtue ;
	public JLabel lvirtueTitle ;
	public int virtue = 0;
	
	// JLabel-���^�Ȧ���
	public JLabel lguilt ;
	public JLabel lguiltTitle ;
	public int guilt = 0;
	
	// JLabel-�_��������
	public JLabel lExit;
	
	// �U�����
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
	
	
	
	// ����FuLu����, �����Ť���, EX: FuLu1-����1�H�W������FuLu
	public ArrayList <Integer> FuLu1; 
	public ArrayList <Integer> FuLuSum; 
	public ArrayList <Integer> FuLuColor; 
	
	
	
	// LiaoZhai-Score
	public static int score;
	
	// ���a�W��
	ScoreBulletin sb;

	
//*********************************�H�U�� GameFrame-�غc�l******************************************	
	
	// GameFrame �غc�l
	public GameFrame() {
		
		// LMusic - �I���C������
		LM = new LMusic("Music/LMusic.wav");
		LM.start();

		
		// JFrame-maps �򥻳]�w
		setTitle("Liao-Zhai");
		//1600, 900
		setBounds(0, 0, 1600, 900);
		setLayout(null); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		// JFrame-���h���D��
		setUndecorated(true);
		
		
		
		// JFrame-JFrame�I������]���¦�
		getContentPane().setBackground(Color.BLACK);
		
		
		// �����-Sun �b�غc�l���� - ��W�����
		sunThr = new Thread() {
			public void run() {
				Sun();}
			};
		
		sunThr.start();
		
		
		// ���w��-virtue �b�غc�l���� - ��W�����
		virtueThr = new Thread() {
			public void run() {
				Virtue();}
			};
				
		virtueThr.start();
		
		
		// ���^��-guilt �b�غc�l���� - ��W�����
		guiltThr = new Thread() {
			public void run() {
				Guilt();}
			};
				
		guiltThr.start();
		
	
		// <��^�H��>-Exit �b�غc�l����
		Exit();
	
		addKeyListener(this);
		setVisible(true);

	}

//*********************************�H�W��GameFrame-�غc�l******************************************	
	
	// �N�������Ų�, �K�[��������, �����Ť���, EX: FuLu1-����1�H�W������FuLu
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
		
	
	// �����-Sun
	public void Sun() {
		
		jb=new JProgressBar(5,2000); 
		jb.setStringPainted(true);
		jb.setForeground(Color.yellow);
		jb.setBackground(Color.CYAN);
		jb.setString("");
		jb.setBounds(185, 13, 280, 50); 
				
		jb.setStringPainted(true);
		l0 = new JLabel("�����");
		l0.setBounds(45, 2, 170, 80);
		l0.setFont(new java.awt.Font("�з���", 1, 36));
		l0.setForeground(Color.MAGENTA);
		add(l0);
		add(jb);
				
		while(0<=sun){
			
			jb.setValue(sun); 
			sun-=5;         
			
			//ProgressBarŪ���t��150
			try{Thread.sleep(150);}catch(Exception e){}    
			
			if(sun==0) {
		
				  UIManager.put("OptionPane.messageFont", new Font("�з���", Font.BOLD, 20));
				  UIManager.put("OptionPane.buttonFont", new Font("�з���", Font.PLAIN, 20));
				  UIManager.put("OptionPane.yesButtonText", "���N���");
				  UIManager.put("OptionPane.noButtonText", "�^��gCode");
				  UIManager.put("OptionPane.cancelButtonText", "�d�\�Ѻ]");
				  
				  score = virtue - guilt;
				  System.out.println("virtue�@:�@" + virtue);
				  System.out.println("guilt�@:�@" + guilt);
				  System.out.println("score�@:�@" + score);
				  
				  int result = JOptionPane.showConfirmDialog(this, "      ������: " + sb.scoreName + 
						                                    "\n" + "      ���w��: " + virtue + 
									                        "\n" + "      ���^��: " + guilt +
									                        "\n" + "      �\�w��: " + score +
									                        "\n" + " �Ыe���Ѻ], �d�߬O�_�W�]", "�\�L�F", 1);
				  
				  
				  
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
	
	// ���w��-Virtue
	
	public void Virtue() {
		
		lvirtueTitle = new JLabel("���w�� :");
		lvirtue = new JLabel();
		
		lvirtueTitle.setBounds(540, 2, 170, 80);
		lvirtueTitle.setFont(new java.awt.Font("�з���", 1, 36));
		lvirtueTitle.setForeground(Color.WHITE);
		
		
		lvirtue.setBounds(705, 5, 300, 70);
		lvirtue.setText(""+virtue);
		lvirtue.setFont(new java.awt.Font("Arial", 1, 46));
		lvirtue.setForeground(Color.WHITE);
		
		add(lvirtueTitle);
		add(lvirtue);
			
	}
	
	// ���^��-Guilt
	public void Guilt() {
		
		lguiltTitle = new JLabel("���^�� :");
		lguilt = new JLabel();
			
		lguiltTitle.setBounds(940, 2, 170, 80);
		lguiltTitle.setFont(new java.awt.Font("�з���", 1, 36));
		lguiltTitle.setForeground(Color.GRAY);
			
		
		lguilt.setBounds(1105, 5, 300, 70);
		lguilt.setText(""+guilt);
		lguilt.setFont(new java.awt.Font("Arial", 1, 46));			
		lguilt.setForeground(Color.GRAY);
			
		add(lguiltTitle);			
		add(lguilt);
	}
	
	
	// Q | q <��^�H��>-Exit
	public void Exit() {
		
		lExit = new JLabel("<html>����Q<br>��^�H��</html>");
		lExit.setBounds(1390, 6, 110, 65);
	
		
		Font fExit = new Font("�з���" ,Font.BOLD, 26);
	    lExit.setFont(fExit);
	    lExit.setOpaque(true);
	    lExit.setForeground(Color.BLACK); 
	    lExit.setBackground(Color.RED);
	    
	    add(lExit);
		
	}
	
	
//******************************** �H�ULiaoZhai�϶פJ�@�ϰ� ************************************	
	
	
		// �j���@�Ϲw�ư�
		public void refresh() {
			
			Graphics gPen = this.getGraphics();
			
			
			// �פJ�a�ϤΨ���Ϥ� & �������� �� > �� > �C > �� > ��
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
			
			// �� maps�N�X ���۹�������
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

//****************************************�H�ULiaoZhai�@�Ϩ�{��*************************************************			
	
	// �@�Ϩ�{��
	public void paint(Graphics g) {
		
		super.paint(g);
		 refresh();
		 		 
		 //�۵M�ͦ��s��- Akali
		 mGThrA = new Thread() {
			 public void run() {
				 try {
					 
					 monsterGroup();
					 
				 }catch(Exception e) {System.out.println("mGThrA�����������Q����.");}
			 }
		 };
		 mGThrA.start();
		
		 
		 
		 //�۵M�ͦ��s�� - Bebe
		 mGThrB = new Thread() {
			 public void run() {
				 try {
					 
					 monsterGroup();
					 
				 }catch(Exception e) {System.out.println("mGThrB�����������Q����.");}
			 }
		 };
		 mGThrB.start();
		
		 
		 
		 //�۵M�ͦ��s��- Cycle
		 mGThrC = new Thread() {
			 public void run() {
				 try {
					 
					 monsterGroup();
					 
				 }catch(Exception e) {System.out.println("mGThrC�����������Q����.");}
			 }
		 };
		 
		 mGThrC.start();
		 
		 
		 
		 //�۵M�ͦ��s�� - Debug
		 mGThrD = new Thread() {
			 public void run() {
				 try {
					 
					 monsterGroup();
					 
				 }catch(Exception e) {System.out.println("mGThrD�����������Q����.");}
			 }
		 };
		 mGThrD.start();
		
		 
		 
		 //�۵M�ͦ��s��- Eagle
		 mGThrE = new Thread() {
			 public void run() {
				 try {
					 
					 monsterGroup();
					 
					 
				 }catch(Exception e) {System.out.println("mGThrE�����������Q����.");}
			 }
		 };
		 mGThrE.start();
		 

	}
	
//*********************************** �H�U�s�����ʧ@�Ϲw�ư� ****************************************
//*****************************MonsterGroupID - �۵M�ͦ��s��ID**********************************

	public void monsterGroup() {
		
		
		int numsID = -1;
		
		//numsID
		ArrayList<Integer> monsterYaxis = new ArrayList<Integer>();
		ArrayList<Integer> monsterXaxis = new ArrayList<Integer>();
		
		
		// �s���Ƥ���5, ���͸s��ID: (i)�s�� (ii)Y�b�y�� (iii)X�b�y��
		while(nums<5) {
			
			
			
			int randomMG = (int)(Math.random()*10); 
			int monsterKd = (int)(Math.random()*100);
			

				// �X�𧯾��v
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
					
					// �j��-�𧯳q�D
					maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
					refresh();
					try{Thread.sleep(180);}catch(Exception e){}
					
					// ����-�𧯲{��
					maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 35;
					refresh();
					try{Thread.sleep(180);}catch(Exception e){}
					
					// �B�ξ�ID - �ͦ���
					maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 48;
					refresh();
					
					
	                // �𧯳̲ץؼЦa - ������� - �𧯦]���ͩʨϵM, ���|��h.
					while(0<monsterXaxis.get(numsID)) {
						
						// �s�y 0-100 ����ƶü�, ���𧯲��ʨ��ܤƩ�.
						int randomM = (int)(Math.random()*100);
						
						// �q�խ߬ɲ{�ͥ��V����(�o�˧ڤ���nCoding)
						if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)==21 ) {
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 48;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
						    int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
							
						}
						
						// �W�e�U-����
						else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48){
							
							
							// �𧯲��� �B�ζüƼW�[�����ܤƩ� ���e30% ���W35% ���U35% (randomM��0~99�ü�)
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
						
						// �W�e-����
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
						
						// �e�U-����
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
			//****************** �U�W���� & �e���H�ɳq�D *******************
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
						//******************�e���H�ɳq�D *******************
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
						
						// �U�W-����
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
						
						// �W-����
						else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48) {
							
							maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 48;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY--;
						    monsterYaxis.set(numsID, mY);
												    
					        
						}
						
						// �e-����
						else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)!=21 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48) {
							
							maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 48;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mX = monsterXaxis.get(numsID);
						    mX--;
						    monsterXaxis.set(numsID, mX);
					        
						}
						
						// �U-����
						else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==48) {
							
							maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 48;
						    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
						    int mY = monsterYaxis.get(numsID);
						    mY++;
						    monsterYaxis.set(numsID, mY);
					        
						}
						
						//�𧯳Q��, �y�йϧאּ0, �𧯰�����]����]�w�L�k����, �]�w�����X�j�����, �s���ƶq-1
	                    else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==0) {
	                    	int mX = monsterXaxis.get(numsID);
	                    	mX = 0;
	                    	monsterXaxis.set(numsID, mX);
	                    	nums--;
	                    	
						}
				
						try{Thread.sleep(350);}catch(Exception e) {}
						refresh();
						
					}}}
			   
			
			    
			// �X�E�������v
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
				
				// �j��-�E�����q�D
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
				refresh();
				try{Thread.sleep(180);}catch(Exception e){}
				
				// ����-�E�����{��
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 35;
				refresh();
				try{Thread.sleep(180);}catch(Exception e){}
				
				// �B�θs��ID - �ͦ��E����
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 47;
				refresh();
				
				
                // �E�����̲ץؼЦa - ������� - �E�����]���ͩʨϵM, ���|��h.
				while(0<monsterXaxis.get(numsID)) {
					
					// �s�y 0-100 ����ƶü�, ���E�������ʨ��ܤƩ�.
					int randomM = (int)(Math.random()*100);
					
					// �q�խ߬ɲ{�ͥ��V����(�o�˧ڤ���nCoding)
					if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)==21 ) {
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 47;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
					    int mX = monsterXaxis.get(numsID);
					    mX--;
					    monsterXaxis.set(numsID, mX);
						
					}
					
					// �W�e�U-����
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
					
					// �W�e-����
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
					
					// �e�U-����
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
		//****************** �U�W���� & �e���H�ɳq�D *******************
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
					//******************�e���H�ɳq�D *******************
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
					
					
					// �U�W-����
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
					
					// �W-����
					else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47) {
						
						maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 47;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mY = monsterYaxis.get(numsID);
					    mY--;
					    monsterYaxis.set(numsID, mY);
											    
				        
					}
					
					// �e-����
					else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)!=21 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47) {
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 47;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mX = monsterXaxis.get(numsID);
					    mX--;
					    monsterXaxis.set(numsID, mX);
				        
					}
					
					// �U-����
					else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==47) {
						
						maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 47;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mY = monsterYaxis.get(numsID);
					    mY++;
					    monsterYaxis.set(numsID, mY);
				        
					}
					
					// �E�����Q��, �y�йϧאּ0, �E����������]����]�w�L�k����, �]�w�����X�j�����, �s���ƶq-1
                    else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==0) {
                    	int mX = monsterXaxis.get(numsID);
                    	mX = 0;
                    	monsterXaxis.set(numsID, mX);
                    	nums--;
                    	 	
					}
			
					try{Thread.sleep(280);}catch(Exception e) {}
					refresh();
					
				}}}
			
			
			// �X�������v
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
				
				// �j��-�����q�D
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
				refresh();
				try{Thread.sleep(180);}catch(Exception e){}
				
				// ����-�����{��
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 35;
				refresh();
				try{Thread.sleep(180);}catch(Exception e){}
				
				// �B�θs��ID - �ͦ�����
				maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 46;
				refresh();
				
				
                // �����̲ץؼЦa - ������� - �����]���ͩʨϵM, ���|��h.
				while(0<monsterXaxis.get(numsID)) {
					
					// �s�y 0-100 ����ƶü�, ���������ʨ��ܤƩ�.
					int randomM = (int)(Math.random()*100);
					
					// �q�խ߬ɲ{�ͥ��V����(�o�˧ڤ���nCoding)
					if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)==21 ) {
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 46;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 36;
					    int mX = monsterXaxis.get(numsID);
					    mX--;
					    monsterXaxis.set(numsID, mX);
						
					}
					
					// �W�e�U-����
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
					
					// �W�e-����
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
					
					// �e�U-����
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
		//****************** �U�W���� & �e���H�ɳq�D *******************
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
					
					//******************�e���H�ɳq�D *******************
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
					
					// �U�W-����
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
					
					// �W-����
					else if(maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46) {
						
						maps[monsterYaxis.get(numsID)-1][monsterXaxis.get(numsID)] = 46;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mY = monsterYaxis.get(numsID);
					    mY--;
					    monsterYaxis.set(numsID, mY);
											    
				        
					}
					
					// �e-����
					else if(maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1]==0 && monsterXaxis.get(numsID)!=21 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46) {
						
						maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)-1] = 46;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mX = monsterXaxis.get(numsID);
					    mX--;
					    monsterXaxis.set(numsID, mX);
				        
					}
					
					// �U-����
					else if(maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)]==0 && maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)]==46) {
						
						maps[monsterYaxis.get(numsID)+1][monsterXaxis.get(numsID)] = 46;
					    maps[monsterYaxis.get(numsID)][monsterXaxis.get(numsID)] = 0;
					    int mY = monsterYaxis.get(numsID);
					    mY++;
					    monsterYaxis.set(numsID, mY);
				        
					}
					
					//�����Q��, �y�йϧאּ0, ����������]����]�w�L�k����, �]�w�����X�j�����, �s���ƶq-1
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

//************************************FuLu�����ͦ��t��***********************************************
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
	

	
//*****************************Monster - ��(�ثe�w����)*************************************	
	public void monster() {
		
		
		maps[mposY][mposX]= 48;
		refresh();
		nums++;
		
		// �𧯳̲ץؼЦa - ������� - �𧯦]���ͩʨϵM, ���|��h.
		while(0<mposX) {
			
			// �s�y 0-100 ����ƶü�, ���𧯲��ʨ��ܤƩ�.
			int randomM = (int)(Math.random()*100);
			
			// �W�e�U-����
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
			
			// �W�e-����
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
			
			// �e�U-����
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
//****************** �U�W���� & �e���H�ɳq�D *******************
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
			
			// �U�W-����
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
			
			// �W-����
			else if(maps[mposY-1][mposX]==0) {
				
				maps[mposY-1][mposX] = 48;
			    maps[mposY][mposX] = 0;
		        mposY--;
		        
			}
			
			// �e-����
			else if(maps[mposY][mposX-1]==0) {
				
				maps[mposY][mposX-1] = 48;
			    maps[mposY][mposX] = 0;
		        mposX--;
		        
			}
			
			// �U-����
			else if(maps[mposY+1][mposX]==0) {
				
				maps[mposY+1][mposX] = 48;
			    maps[mposY][mposX] = 0;
		        mposY++;
		        
			}
			
			
	
	
			try{Thread.sleep(350);}catch(Exception e) {}
			refresh();
			
		}
		
	}
	
	
	
//*****************************MonsterS - �W�ž�(�ثe�w����)*************************************
	
	public void monsterS() {
	
		maps[mSposY][mSposX]= 48;
		refresh();
		nums++;
		
		// �𧯳̲ץؼЦa - ������� - �𧯦]���ͩʨϵM, ���|��h.
		while(0<mSposX) {
			
			// �s�y 0-100 ����ƶü�, ���𧯲��ʨ��ܤƩ�.
			int randomM = (int)(Math.random()*100);
			
			// �W�e�U-����
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
			
			// �W�e-����
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
			
			// �e�U-����
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
			// �W�U���� & �e�謰�H���q�D
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
			
			// �U�W-����
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
			
			// �W-����
			else if(maps[mSposY-1][mSposX]==0) {
				
				maps[mSposY-1][mSposX] = 48;
			    maps[mSposY][mSposX] = 0;
		        mSposY--;
		        
			}
			
			// �e-����
			else if(maps[mSposY][mSposX-1]==0) {
				
				maps[mSposY][mSposX-1] = 48;
			    maps[mSposY][mSposX] = 0;
		        mSposX--;
		        
			}
			
			// �U-����
			else if(maps[mSposY+1][mSposX]==0) {
				
				maps[mSposY+1][mSposX] = 48;
			    maps[mSposY][mSposX] = 0;
		        mSposY++;
		        
			}
	
	
			try{Thread.sleep(200);}catch(Exception e) {}
			refresh();
			
		}
		
	}
	
	
	
	
	
	
	

//************************************* �D�����ʧP�_�t�� *********************************************
	public void	keyPressed(KeyEvent situ) {
		
		int kB = situ.getKeyCode();
		
		// �פJ���� FuLu-ArrayList ���U��P�_
		FuLu();
		
		
		// �H���V�W��
		if(kB==KeyEvent.VK_UP) {
			
			//�W��-������ز���FuLu1
			
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
		    
		    // �W��-�X������FuLu�t��
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
		   
           // �D���W������
           else if(94<maps[posY-1][posX] && (45<maps[posY-2][posX] && maps[posY-2][posX]<49)) {
        	   
        	   // �W������, �𧯥N�X48, 1-5�ŲŬүব
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
        	   
        	   
        	   // �W�����E����, �E�����N�X47, 3-5�ŲŬүব
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
        	
        	   
        	   // �W��������, �����N�X46, 5�Ųůব
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

		   // �D���W��-�򥻥D�����ʧP�_
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
		    
		    // �l��p��, ����ȥ���(1)
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
		    // �l��p��, ����ȥ���(5)
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
		
		// �H���V�U�� (�Ʀr��2)
		else if(kB==KeyEvent.VK_DOWN) {
			
			//�U��-������ز���FuLu1
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

		     // �U��-�X������FuLu�t��
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
			
			// �D���U������
	           else if(94<maps[posY+1][posX] && (45<maps[posY+2][posX] && maps[posY+2][posX]<49)) {
	        	   
	        	   // �U������, �𧯥N�X48, 1-5�ŲŬүব
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
	        	   
	        	   
	        	   // �U�����E����, �E�����N�X47, 3-5�ŲŬүব
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
	        	
	        	   
	        	   // �U��������, �����N�X46, 5�Ųůব
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
			
		    // �D���U��-�򥻥D�����ʧP�_
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
		
		// �H���V���� (�Ʀr��4)
		else if(kB==KeyEvent.VK_LEFT) {
			
			//����-������ز���FuLu1
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

		     // ����-�X������FuLu�t��
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
			 
			
			 // �D����������
	           else if(94<maps[posY][posX-1] && (45<maps[posY][posX-2] && maps[posY][posX-2]<49 && 2<posX)) {
	        	   
	        	   // ��������, �𧯥N�X48, 1-5�ŲŬүব
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
	        	   
	        	   
	        	   // �������E����, �E�����N�X47, 3-5�ŲŬүব
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
	        	
	        	   
	        	   // ����������, �����N�X46, 5�Ųůব
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
			
			 // �D������-�򥻥D�����ʧP�_ 
			 else if(maps[posY][posX]==6 && maps[posY][posX-1]==0) {
				
				maps[posY][posX-1] = 5;
				maps[posY][posX] = 4;
				posX--;
				
			} else if(maps[posY][posX]==7 && maps[posY][posX-1]==0) {
				
				maps[posY][posX-1] = 5;
				maps[posY][posX] = 4;
				posX--;
			
			  	
			} 
			    // �l��p��, ����ȥ���
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
		
		// �H���V�k�� (�Ʀr��6)
		
		else if(kB == KeyEvent.VK_RIGHT) {
			
			 //�k��-������ز���FuLu1
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

		     // �k��-�X������FuLu�t��
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
			 
			 
			// �D���k������
	           else if(94<maps[posY][posX+1] && (45<maps[posY][posX+2] && maps[posY][posX+2]<49 && posX<19)) {
	        	   
	        	   // �k������, �𧯥N�X48, 1-5�ŲŬүব
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
	        	   
	        	   
	        	   // �k�����E����, �E�����N�X47, 3-5�ŲŬүব
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
	        	
	        	   
	        	   // �k��������, �����N�X46, 5�Ųůব
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
			

		     // �D���k��-�򥻥D�����ʧP�_
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
		
		// ���� Space ��(����)�ťi���s
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
		
		// ���� Q | q <��^�H��>
		else if(kB == 'Q' | kB == 'q') {
			
			System.out.println("Debug, �O�Ӧn����!");
			System.exit(0);
		}
		
		// �פJ�۵M�ͦ�����FuLu�t��
		fuluBorn();
		
		
		// KeyEvent���bRefresh
		refresh();
		
	}	
	
	public void keyReleased(KeyEvent situ) {
		
	}
	
	
	public void keyTyped(KeyEvent situ) {
		
	}

}


//**************************************** �D�{���i�J�I *********************************************

	
	