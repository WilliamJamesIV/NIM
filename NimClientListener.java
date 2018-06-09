/**
 * NimClientListener.java
 *
 * This class runs on the client end and just
 * displays any text received from the server.

  Program was a multithreaded chat and now is a multithreaded game of nim
 *
 */
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class NimClientListener implements Runnable{
	private Socket connectionSock = null;
	//private NimGame nim;

	NimClientListener(Socket sock){
		this.connectionSock = sock;
		//NimGame nim = new NimGame();
	}

	public void run(){
    // Wait for data from the server.  If received, output it.
		try{
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(connectionSock.getInputStream()));
			while (true){
				// Get data sent from the server
				String serverText = serverInput.readLine();
				if (serverInput != null){
					System.out.println(serverText);
					if(serverText.contains("win")){
						System.out.println("Closing connection for socket " + connectionSock);
						connectionSock.close();
						break;
					}
				}
				else{
					// Connection was lost
					System.out.println("Closing connection for socket " + connectionSock);
					connectionSock.close();
					break;
				}
			}
		}
		catch (Exception e){
			System.out.println("Error: " + e.toString());
		}
	}
} // ClientListener for MTClient
