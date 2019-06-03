package Server;

import java.io.IOException;
import java.net.Socket;

import CommonClass.Message;

// Stampa
public class ClientThread extends Thread {
	private ServerModel model;
	private Socket socket;
	private boolean b=false;
	public ClientThread(ServerModel serverModel, Socket s) {
		b=true;
		this.model=serverModel;
		this.socket=s;
		Runnable r=new Runnable() {
			public void run() {
				while(b) {
					Message msgIn= Message.receive(s);
					String message=msgIn.getMessage();
					if(msgIn!=null&&message.equals("ZugEnd")) {
						model.messageIn(msgIn);
					}
				}
			}
		};
		Thread t= new Thread(r, "Client");
		t.start();
	}
	public void send(Message msg) {
		msg.send(socket);
	}
	public void stopClient() {
		try {
			socket.close();
		}catch(IOException e) {

		}
	}

}




