import java.io.IOException;
import java.net.Socket;
import java.lang.Thread;
import java.util.Date;

public class Server {
	public static void main(String args[]) throws IOException {
		tools.CreateServer();
		System.out.println(tools.df.format(new Date())+" �������гɹ�������\n");
		while(true){
			Socket client=tools.GetClientSocket();
			System.out.println(tools.df.format(new Date())+" �пͻ�������   ip: "+client.getInputStream().toString());
			new Thread(new ServerThread(client)).start();
		}
	}

}
