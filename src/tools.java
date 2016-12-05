//import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.io.*;

import javax.swing.JOptionPane;

public class tools {
	private static ServerSocket severSocket=null;
	private final static int port=30000;
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
	
	public static void CreateServer() throws IOException{
		severSocket=new ServerSocket(port);
	}
	
	public static Socket GetClientSocket() throws IOException{
		return severSocket.accept();
	}
	
	public static boolean login(String username,String password) throws Exception{
		if(DB.check(username,password)) return true;
		return false;
	}
	
	public static int getHomeworkNum(String course,String level){
		String path="D:\\homework\\";
		if(level.equals("question")) path = path + "question";
		else if(level.equals("test")) path = path + course + "\\test";
		else path = path + course + "\\homework";
		File file = new File(path);
		if(!file.exists()) file.mkdirs();
		return file.list().length;
	}
	
	public static String uploadfile(String username,String type,String course,String code,String codePtime,DataInputStream br) throws IOException{
		String path="D:\\feedback";
		if(code.charAt(0)=='q') path=path+"\\question\\"+username+"-"+codePtime+"."+type; 
		else if(code.charAt(0)=='t') path=path+"\\test\\"+course+"\\"+username+"-"+codePtime+"."+type;
		else path=path+"\\homework\\"+course+"\\"+code+"\\"+username+"-"+codePtime+"."+type;
		File file = new File(path);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		DataOutputStream dos = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream(path)));
		byte[] bu = new byte[1024 * 8];
		while (true) {
			int read = 0;
			if (br != null) {
				read = br.read(bu);
			}
			if (read == -1)
				break;
			dos.write(bu, 0, read);
		}
		dos.close();
		return path;
	}
	
	public static void downloadfile(String course,String code,DataOutputStream bw) throws IOException{
		String path="D:\\homework";
		if(code.charAt(0)=='q') path=path+"\\question\\"+code+".txt";
		else if(code.charAt(0)=='t')path=path+"\\"+course+"\\test\\"+code+".txt";
		else path = path + "\\" + course + "\\homework\\" + code+ ".txt";
		BufferedReader fis=new BufferedReader(new FileReader(path));
		byte[] buf=new byte[1024*8];
		while(true){
			String str=null;
			if(fis!=null){
				str=fis.readLine();
			}
			if(str==null) break;
			str=str+"\n";
			buf=str.getBytes("utf-8");
			bw.write(buf,0,buf.length);
		}
		bw.flush();
		fis.close();
	}

}
