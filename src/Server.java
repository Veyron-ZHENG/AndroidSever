import java.io.IOException;
import java.net.Socket;
import java.lang.Thread;
import java.util.Date;

public class Server {
	public static void main(String args[]) throws IOException {
		tools.CreateServer();
		System.out.println(tools.df.format(new Date())+" 程序运行成功。。。\n");
		while(true){
			Socket client=tools.GetClientSocket();
			System.out.println(tools.df.format(new Date())+" 有客户端连接   ip: "+client.getInputStream().toString());
			new Thread(new ServerThread(client)).start();
		}
	}

}
