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

public class ScoreBulletin {
	
	public static String scoreName;
	
	
	public ScoreBulletin() {
		
		JFrame scorejf = new JFrame("�Ѻ] - �^���j�W"); 
		
		JLabel namejl = new JLabel("�п�J�����̦W��:");
		namejl.setBounds(107,50,300,30);
		namejl.setFont(new java.awt.Font("�з���", 1, 32));
		namejl.setForeground(Color.BLACK);
		
		JLabel noticejl = new JLabel("");
		noticejl.setBounds(125,170,300,30);
		noticejl.setFont(new java.awt.Font("�з���", 1, 26));
		noticejl.setForeground(Color.WHITE);
		
		JTextField namejt = new JTextField();
		namejt.setBounds(120,110,250,45);
		namejt.setHorizontalAlignment(JTextField.CENTER);
		namejt.setFont(new java.awt.Font("�з���", 1, 30));
		
		JButton confirmjb = new JButton("����");
		confirmjb.setBounds(90,220,120,50);
		confirmjb.setFont(new java.awt.Font("�з���", 1, 28));
		confirmjb.setBackground(Color.BLACK);
		confirmjb.setForeground(Color.WHITE);
		
		JButton canceljb = new JButton("����");
		canceljb.setBounds(285,220,120,50);
		canceljb.setFont(new java.awt.Font("�з���", 1, 28));
		canceljb.setBackground(Color.BLACK);
		canceljb.setForeground(Color.WHITE);
		
		
		scorejf.add(namejl);
		scorejf.add(namejt);
		scorejf.add(noticejl);
		scorejf.add(confirmjb);
		scorejf.add(canceljb);
		
		scorejf.getContentPane().setBackground(Color.RED);
		scorejf.setUndecorated(true);
		scorejf.setBounds(0, 0, 500, 300);
		scorejf.setLayout(null); 
		scorejf.setLocationRelativeTo(null);
		scorejf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scorejf.setVisible(true);
		
		confirmjb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(namejt.getText().length()==0) {
					
					noticejl.setText("���a�W�٤��i���ť�");
					
				} else if(namejt.getText().length()>7) {
					
					noticejl.setText("���a�W�ٳ̦h�C�Ӧr");
					
				} else {
					
					scoreName = namejt.getText();
					System.out.println(scoreName);
					new GameFrame();
					scorejf.dispose();
				}
			}
		});
		
		canceljb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scorejf.dispose();
			}
		});
		
	}
}
