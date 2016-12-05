import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
//import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import java.io.*;

public class ServerThread implements Runnable {
	Socket s = null;
	DataInputStream br = null;
	DataOutputStream bw = null;

	public ServerThread(Socket s) throws IOException {
		this.s = s;
		br = new DataInputStream(new BufferedInputStream(s.getInputStream()));
		bw = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
	}

	public void run() {
		try {
			String content = br.readUTF();
			String[] infor = content.split("/");
			if (infor[0].equals("login")) {
				System.out.println(tools.df.format(new Date())+"  客户端请求登陆...\n帐号："+infor[1]+"\n密码："+infor[2]);
				if (tools.login(infor[1], infor[2])) {
					bw.writeUTF("true");
					System.out.println(tools.df.format(new Date())+"  登陆成功\n");
				} else {
					bw.writeUTF("false");
					System.out.println(tools.df.format(new Date())+"  登录失败\n");
				}
				bw.flush();
				s.close();
			}
			else if (infor[0].equals("homeworknum")) {
				System.out.println(tools.df.format(new Date())+"  客户端请求查看作业列表，班次："+infor[1]);
				int fileNum = tools.getHomeworkNum(infor[1],infor[2]);
				bw.writeUTF(""+fileNum);
				bw.flush();
				System.out.println(tools.df.format(new Date())+"  查看完成\n");
				s.close();
			}
			else if (infor[0].equals("testnum")){
				System.out.println(tools.df.format(new Date())+"  客户端请求查看试卷列表,班次："+infor[1]);
				int fileNum = tools.getHomeworkNum(infor[1],infor[2]);
				bw.writeUTF(""+fileNum);
				bw.flush();
				System.out.println(tools.df.format(new Date())+"  查看完成\n");
				s.close();
			}
			else if(infor[0].equals("questionnum")){
				System.out.println(tools.df.format(new Date())+"  客户端请求查看问卷列表,班次："+infor[1]);
				int fileNum = tools.getHomeworkNum(infor[1],infor[2]);
				bw.writeUTF(""+fileNum);
				bw.flush();
				System.out.println(tools.df.format(new Date())+"  查看完成\n");
				s.close();
			}
			else if (infor[0].equals("homework")) {
				System.out.println(tools.df.format(new Date())+"  客户端请求下载文件.."+infor[1]);
				tools.downloadfile(infor[1],infor[2],bw);
				s.close();
				System.out.println(tools.df.format(new Date())+"  下载完成\n");
			}
			else if (infor[0].equals("uploadfile")) {
				System.out.println(tools.df.format(new Date())+"  客户端请求上传文件: "+infor[1]+"-"+infor[3]+"."+infor[2]);
				String time = tools.df.format(new Date());
				String codePtime=time+"-"+infor[3];
				String path=tools.uploadfile( infor[1], infor[2], infor[3], infor[4] ,codePtime, br);
				if(infor[2].equals("txt")){
					DB.record(path,infor[1],infor[3],time);
				}
				System.out.println(tools.df.format(new Date())+"  上传完成\n");
			}
			else if(infor[0].equals("modify")){
				System.out.println(tools.df.format(new Date())+"  客户端请求修改密码，帐号： "+infor[1]);
				String username = infor[1];
				String password = infor[2];
				DB.modify(username,password);
				bw.writeUTF("ok");
				bw.flush();
				s.close();
				System.out.println(tools.df.format(new Date())+"  密码修改成功。。");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
