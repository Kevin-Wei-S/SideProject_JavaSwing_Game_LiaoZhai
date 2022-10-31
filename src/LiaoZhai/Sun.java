package LiaoZhai;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;



public class Sun extends JFrame{    
	
	JProgressBar jb; 
	JLabel l0, l1 ;
	JButton plusY;
	int i=2000;

	Sun(){   
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		jb=new JProgressBar(20,2000); 
		jb.setStringPainted(true);
		jb.setForeground(Color.yellow);
		jb.setBackground(Color.CYAN);
		jb.setString("");
		jb.setBounds(50, 200, 300, 50);         
	
		//jb.setValue();    
		jb.setStringPainted(true);
		l0 = new JLabel();
		l1 = new JLabel("陽氣值");
		l0.setBounds(85, 120, 250, 120);
		l0.setFont(new java.awt.Font("標楷體", 1, 36));
		l0.setForeground(Color.MAGENTA);
		l1.setBounds(84, 120, 250, 120);
		l1.setFont(new java.awt.Font("標楷體", 1, 37));
		l1.setForeground(Color.ORANGE);
		
		plusY = new JButton("UP!");
		plusY.setBounds(170, 300, 70, 50);
		plusY.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (i<=1880)
				i+=120;
				
			}});
	
		add(l0);
		add(l1);
		add(jb); 
		add(plusY);
		
		
		setSize(450, 500);    
		setLayout(null); 
		setVisible(true);    
	    iterate();    
		
		
		}    
	public void iterate(){    
	while(20<=i){    
	  jb.setValue(i);    
	  i-=20; 
	  l0.setText("陽氣值 : " + i);
	  if(i<=0) {
		  
		  
		  UIManager.put("OptionPane.messageFont", new Font("標楷體", Font.BOLD, 20));
		  UIManager.put("OptionPane.buttonFont", new Font("標楷體", Font.PLAIN, 20));
		  UIManager.put("OptionPane.yesButtonText", "投胎求功名");
		  UIManager.put("OptionPane.noButtonText", "回魂寫Code");
		  UIManager.put("OptionPane.cancelButtonText", "查閱功名榜");
		  JOptionPane.showConfirmDialog(this, "   陽氣已盡, 尚未官居一品\n        乘願重返陽間?", "幽冥地府", 1);
	  }
	  try{Thread.sleep(150);}catch(Exception e){}    
	}    
	}
	
	}

