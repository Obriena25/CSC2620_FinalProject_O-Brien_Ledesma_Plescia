import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//the server provides functionality for the board

//the server recieves the move made by the client(player) and check for the first empty spot in the chosen cloumn
//also checks if the player that just made a move, has won
//sends the updated board to the client
public class Server extends Thread 
{
    private Socket connectionToPlayer = null;
    private ServerSocket server = null;
    //private int port;
    private DataInputStream in = null;
    int[][] board = new int[6][7];


    public static void main(String[] args)
    {
        Server server = new Server(5000);
    }

    public Server(int port)
    {
        //this.port = port;
        try
        {
            server = new ServerSocket(port);
            //System.out.println("Waiting for player");
            connectionToPlayer = server.accept();
            in = new DataInputStream(new BufferedInputStream(connectionToPlayer.getInputStream()));

            String line = "";

            while(!line.equals("Game Over!"))
            {
                try
                {
                    line = in.readUTF();
                    System.out.println(line);
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
        }
        catch(IOException ex)
        {

        }
       
    }

    public void placePiece(int col)
    {
        int height = 6;
        int width = 7;
        for(int i = board[height-1][width]; i >= 0; i--)
        {
            if(board[i][col] == 0)
            {
                //place piece
                //board[i][col] = 
                //switch player
            }
        }
    }

    public void checkHorizonalWin()
    {

    }

    public void checkVerticalWin()
    {

    }

    public void checkDiagonalWin()
    {

    }
}
