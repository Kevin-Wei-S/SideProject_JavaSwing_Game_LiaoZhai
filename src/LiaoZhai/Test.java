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
import java.time.LocalDate;
import java.io.*;


public class Test extends JFrame implements KeyListener {

	ArrayList<String> nameRank;
	ArrayList<String> scoreRank;
	ArrayList<String> dateRank;
	ArrayList<String> rankTitle;
	
	
	public Test() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setBounds(400, 10, 780, 810);
		setTitle("Liao-Zhai SkyRank");
		getContentPane().setBackground(Color.PINK);
		addKeyListener(this);
		
		// JFrame-隱去標題欄
		setUndecorated(true);
		
		// 天榜 - ArrayList
		nameRank = new ArrayList<String>(); 
		scoreRank = new ArrayList<String>();
		dateRank = new ArrayList<String>();
		rankTitle = new ArrayList<String>();
		
		// 讀入天榜-CSV
		try {
			
			InputStreamReader isr = new InputStreamReader(new FileInputStream("SkyRank.csv"));
			BufferedReader br = new BufferedReader(isr);
		
			String line = null;
			int i=0;
			
			while((line=br.readLine())!=null){
				
				String[] item = line.split(",");
				
				
				nameRank.add(item[0]);
				scoreRank.add(item[1]);
				dateRank.add(item[2]);
				i++;
				
			}
			// 功德值排序-降冪
			for(int x=1 ; x<scoreRank.size() ; x++) {
				for(int y=x+1 ; y<scoreRank.size() ; y++) {
					
					
					long lead = Long.parseLong(scoreRank.get(x));
					long follow = Long.parseLong(scoreRank.get(y));
					
					if(lead<follow) {
						
						String tempsr = scoreRank.get(x);
						String tempnr= nameRank.get(x);
						String tempdr = dateRank.get(x);
						
						scoreRank.set(x, scoreRank.get(y));
						nameRank.set(x, nameRank.get(y));
						dateRank.set(x, dateRank.get(y));
						
						scoreRank.set(y, tempsr);
						nameRank.set(y, tempnr);
						dateRank.set(y, tempdr);
					
					}
	
				}
			}
			
			// 設定JLabel
			// JLabel - Title
			JLabel jTitle = new JLabel("聊齋 - 天榜");
			jTitle.setBounds(230, 30, 500, 60);
			jTitle.setFont(new java.awt.Font("標楷體", 1, 52));
			jTitle.setForeground(Color.WHITE);
			add(jTitle);
			
			// JLabel - ScoreTitle
			 JLabel[] nLabel = new JLabel[10]; 
			 String[] nContent = {"<狀元>", "<榜眼>", "<探花>", "<阿肆>", "<老伍>", 
					              "<摟窟>", "<囍門>", "<阿里>", "<陳零>", "<孫山>"};
			 
			 for(int n=0 ; n<nLabel.length; n++) {
				 
				 nLabel[n] = new JLabel(nContent[n]);
				 nLabel[n].setBounds(20, 135+68*n, 100, 30);
				 nLabel[n].setFont(new java.awt.Font("標楷體", 1, 32));
				 nLabel[n].setForeground(Color.WHITE);
				 add(nLabel[n]);
			 }
			 
			 //JLabel - PlayerName
			 JLabel[] pnLabel = new JLabel[10];
			 for(int n=0 ; n<10 ; n++) {
				 
				 pnLabel[n] = new JLabel(nameRank.get(n+1), SwingConstants.CENTER);
				 pnLabel[n].setBounds(140, 135+68*n, 135, 30);
				 pnLabel[n].setFont(new java.awt.Font("標楷體", 1, 32));
				 pnLabel[n].setForeground(Color.BLACK);
				 add(pnLabel[n]);
				 
			 }
			 
			//JLabel - PlayerScore
			 JLabel[] psLabel = new JLabel[10];
			 for(int n=0 ; n<10 ; n++) {
				 
				 psLabel[n] = new JLabel(scoreRank.get(n+1), SwingConstants.RIGHT);
				 psLabel[n].setBounds(295, 135+68*n, 250, 30);
				 psLabel[n].setFont(new java.awt.Font("Aviral", 1, 34));
				 psLabel[n].setForeground(Color.YELLOW);
				 add(psLabel[n]);
				 
			 }
			 
			 // JLabel - PlayerDate
			 JLabel[] pdLabel = new JLabel[10];
			 for(int n=0 ; n<10 ; n++) {
				 
				 pdLabel[n] = new JLabel(dateRank.get(n+1), SwingConstants.RIGHT);
				 pdLabel[n].setBounds(495, 135+68*n, 250, 30);
				 pdLabel[n].setFont(new java.awt.Font("Aviral", 1, 28));
				 pdLabel[n].setForeground(Color.WHITE);
				 add(pdLabel[n]);
				 
			 }
			 
			// Press - Q | q to Exit
			 
			JLabel playerExit = new JLabel("<html>按鍵Q<br>完成查詢</html>");
			playerExit.setBounds(620, 30, 130, 65);
			
				
		    Font fExit = new Font("標楷體" ,Font.BOLD, 28);
			playerExit.setFont(fExit);
			playerExit.setOpaque(true);
			playerExit.setForeground(Color.CYAN); 
			playerExit.setBackground(Color.PINK);
			    
			add(playerExit);
			
		    setVisible(true);
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void	keyPressed(KeyEvent press) {
		
		int kb = press.getKeyCode();
        
		if(kb == 'Q' | kb == 'q') {
			
			dispose();
		}
		
	}
	
	public void keyReleased(KeyEvent press) {}
	
	
	public void keyTyped(KeyEvent press) {}
	
}   
	

