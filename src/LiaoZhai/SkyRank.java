package LiaoZhai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;

public class SkyRank{
	
	ScoreBulletin sb;
	GameFrame gf;
	
	
	ArrayList<String> nameRank;
	ArrayList<String> scoreRank;
	ArrayList<String> dateRank;
	
	
	
	public SkyRank() {
		
		nameRank = new ArrayList<String>(); 
		scoreRank = new ArrayList<String>();
		dateRank = new ArrayList<String>();
		LocalDate date = LocalDate.now();
		
		try {
			
		    BufferedWriter bw = new BufferedWriter(new FileWriter("txt/SkyRank.csv",true));
			bw.newLine();
		    bw.write(sb.scoreName + "," + gf.score + "," + date);
			System.out.println(sb.scoreName + "," + gf.score + "," + date);
			bw.close();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}   
	

