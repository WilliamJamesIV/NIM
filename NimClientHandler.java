/**
 * ClientHandler.java
 *
 * This class handles communication between the client
 * and the server.  It runs in a separate thread but has a
 * link to a common list of sockets to handle broadcast.
 *

  Program was a multithreaded chat and now is a multithreaded game of nim


 */
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class NimClientHandler implements Runnable
{
	private Socket connectionSock = null;
	private ArrayList<Socket> socketList;
	private NimGame nim;

	NimClientHandler(Socket sock, ArrayList<Socket> socketList, NimGame nim)
	{
		this.connectionSock = sock;
		this.socketList = socketList;	// Keep reference to master list
		this.nim = nim;
	}

	//takes the strings and puts them into arrays(by parsing) so it can be added to gameboard
	private int[] stringToArray(String[] nums){
		int[] num = new int[nums.length];
		for (int i = 0; i < nums.length;i++){

			num[i] = Integer.parseInt(nums[i]);

		}
		num[0] = num[0];
		return num;
	}

	public void run(){
        		// Get data from a client and send it to everyone else
		try{
			System.out.println("Connection made with socket " + connectionSock);
			BufferedReader clientInput = new BufferedReader(
				new InputStreamReader(connectionSock.getInputStream()));
			while (true){
				// Get data sent from a client
				nim.printGameBoard();

				String clientText = clientInput.readLine();
				if (clientText != null){
					System.out.println("Received: " + clientText);
					String[] stringNumbers = clientText.split(" ");
					int[] numbers = stringToArray(stringNumbers);
					clientText = (numbers[1]+" Sticks were removed from row: "+numbers[0]);

					if (nim.moveChecker(numbers[0],numbers[1])){
							nim.moveMaker(numbers[0],numbers[1]);

							if(nim.winChecker()){

								if(nim.getTurn()){
									System.out.println("Player 1 wins!");
								}
								else{
									System.out.println("Player 2 wins!");
								}
							}

							if(nim.getTurn()){
								clientText += "\nPlayer 1 wins!";
							}
							else{
								clientText += "\nPlayer 2 wins!";
							}
					}

					else{
						clientText += "\nInvalid Text Input";
						continue;
					}
					// Turn around and output this data
					// to all other clients except the one
					// that sent us this information
					for (Socket s : socketList){
						if (s != connectionSock){
							DataOutputStream clientOutput = new DataOutputStream(s.getOutputStream());
							clientOutput.writeBytes(clientText + "\n");
							//need for here
						}
					}
				}
				else{
				  // Connection was lost
				  System.out.println("Closing connection for socket " + connectionSock);
				   // Remove from arraylist
				   socketList.remove(connectionSock);
				   connectionSock.close();
				   break;
				}
			}
		}
		catch (Exception e){
			System.out.println("Error: " + e.toString());
			// Remove from arraylist
			socketList.remove(connectionSock);
		}
	}
} // ClientHandler for MTServer.java
