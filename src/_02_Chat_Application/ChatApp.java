package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

/*
 * Using the Click_Chat example, write an application that allows a server computer to chat with a client computer.
 */

public class ChatApp {
	public static void main(String[] args) {
		ChatApp chatapp = new ChatApp();
		chatapp.run();
	}
	public void client() {
		String IPAddress = JOptionPane.showInputDialog("IP Address:");
		String portNumber = JOptionPane.showInputDialog("Port Number:");
		try {
			Socket socket = new Socket(IPAddress, Integer.parseInt(portNumber));
			
			Thread thread = new Thread(() -> write(socket, "client"));
			Thread threadR = new Thread(() -> read(socket));
			thread.start();
			threadR.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void server() {
		try {
			ServerSocket serverSocket = new ServerSocket(8080, 100);
			Socket server = serverSocket.accept();
			
			Thread thread = new Thread(() -> write(server, "client"));
			Thread threadR = new Thread(() -> read(server));
			thread.start();
			threadR.start();
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void write(Socket socket, String string) {
		while(true) {
			DataOutputStream dataoutputstream;
			try {
				dataoutputstream = new DataOutputStream(socket.getOutputStream());
				String message = JOptionPane.showInputDialog("Enter a Message:");
				dataoutputstream.writeUTF(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public void read(Socket socket) {
		while(true) {
		
		try {
			DataInputStream datainputstream;
			datainputstream = new DataInputStream(socket.getInputStream());
			String message = datainputstream.readUTF();
			System.out.println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	public void run() {
		String side = JOptionPane.showInputDialog("Are you on the Client side or the Server side?");
		if(side.equalsIgnoreCase("client")) {
			client();
		} else if(side.equalsIgnoreCase("server")) {
			server();
		}
	}
}
