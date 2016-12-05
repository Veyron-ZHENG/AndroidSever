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
				System.out.println(tools.df.format(new Date())+"  �ͻ��������½...\n�ʺţ�"+infor[1]+"\n���룺"+infor[2]);
				if (tools.login(infor[1], infor[2])) {
					bw.writeUTF("true");
					System.out.println(tools.df.format(new Date())+"  ��½�ɹ�\n");
				} else {
					bw.writeUTF("false");
					System.out.println(tools.df.format(new Date())+"  ��¼ʧ��\n");
				}
				bw.flush();
				s.close();
			}
			else if (infor[0].equals("homeworknum")) {
				System.out.println(tools.df.format(new Date())+"  �ͻ�������鿴��ҵ�б���Σ�"+infor[1]);
				int fileNum = tools.getHomeworkNum(infor[1],infor[2]);
				bw.writeUTF(""+fileNum);
				bw.flush();
				System.out.println(tools.df.format(new Date())+"  �鿴���\n");
				s.close();
			}
			else if (infor[0].equals("testnum")){
				System.out.println(tools.df.format(new Date())+"  �ͻ�������鿴�Ծ��б�,��Σ�"+infor[1]);
				int fileNum = tools.getHomeworkNum(infor[1],infor[2]);
				bw.writeUTF(""+fileNum);
				bw.flush();
				System.out.println(tools.df.format(new Date())+"  �鿴���\n");
				s.close();
			}
			else if(infor[0].equals("questionnum")){
				System.out.println(tools.df.format(new Date())+"  �ͻ�������鿴�ʾ��б�,��Σ�"+infor[1]);
				int fileNum = tools.getHomeworkNum(infor[1],infor[2]);
				bw.writeUTF(""+fileNum);
				bw.flush();
				System.out.println(tools.df.format(new Date())+"  �鿴���\n");
				s.close();
			}
			else if (infor[0].equals("homework")) {
				System.out.println(tools.df.format(new Date())+"  �ͻ������������ļ�.."+infor[1]);
				tools.downloadfile(infor[1],infor[2],bw);
				s.close();
				System.out.println(tools.df.format(new Date())+"  �������\n");
			}
			else if (infor[0].equals("uploadfile")) {
				System.out.println(tools.df.format(new Date())+"  �ͻ��������ϴ��ļ�: "+infor[1]+"-"+infor[3]+"."+infor[2]);
				String time = tools.df.format(new Date());
				String codePtime=time+"-"+infor[3];
				String path=tools.uploadfile( infor[1], infor[2], infor[3], infor[4] ,codePtime, br);
				if(infor[2].equals("txt")){
					DB.record(path,infor[1],infor[3],time);
				}
				System.out.println(tools.df.format(new Date())+"  �ϴ����\n");
			}
			else if(infor[0].equals("modify")){
				System.out.println(tools.df.format(new Date())+"  �ͻ��������޸����룬�ʺţ� "+infor[1]);
				String username = infor[1];
				String password = infor[2];
				DB.modify(username,password);
				bw.writeUTF("ok");
				bw.flush();
				s.close();
				System.out.println(tools.df.format(new Date())+"  �����޸ĳɹ�����");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
