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
        for(int i = board.length - 1; i >= 0; i--) //first condition is not right?????*************
                                                            //only want to be decreasing the height, not the width
        {
            if(board[i][col] == 0)
            {
                //place piece
                //board[i][col] = piece.currentPlayer

                //switch player
            }
        }
    }


    // X X X X X X X
    // X X X X X X X
    // X X X 0 X X X
    // X X X X X X X
    // X X X X X X X


    // public bool checkWin(row, column){ 
    //      if(checkLeft(row, column) || checkUpLeft(row, column) || checkUp(row, column) || checkUpRight(row, column)){
    //           return True;
    //      }
    //          return False;
    //}

    // public bool checkLeft(row, column)
    // bool win = 

    // 4 directions
    // go in one direction until (off the board OR not your color)
    //          if(counter == 4)
    //              win = true
    //              break
    //          if(is your color)
    //              Add dimisions for next in in order
    //              counter++



    public void checkHorizonalWin()
    {
        //for loop iterating through rows
            //for loop iterating through columns 
                //if current x spot = 1 and x+1 = 1 and x+2 = 1 and x+3 = 1
                    //player one wins
                //if current x spot = 2 and x+1 = 2 and x+2 = 2 and x+3 = 2
                    //player two wins
    }

    public void checkVerticalWin()
    {
        //for loop iterating through rows
            //for loop iterating through columns 
                //if current y spot = 1 and y+1 = 1 and y+2 = 1 and y+3 = 1
                    //player one wins
                //if current y spot = 2 and y+1 = 2 and y+2 = 2 and y+3 = 2
                    //player two wins
    }

    public void checkDiagonalWin()
    {
        //for loop iterating through rows
            //for loop iterating through columns 
                //if (current x and y = 1) and (x+1 and y+1 = 1) and (x+2 and y+2 = 1) and (x+3 and y+3 = 1)
                    //player one wins
                //if (current x and y = 2) and (x+1 and y+1 = 2) and (x+2 and y+2 = 2) and (x+3 and y+3 = 2)
                    //player two wins
                //if (current x and y = 1) and (x-1 and y+1 = 1) and (x-2 and y+2 = 1) and (x-3 and y+3 = 1)
                    //player one wins
                //if (current x and y = 2) and (x-1 and y+1 = 2) and (x-2 and y+2 = 2) and (x-3 and y+3 = 2)
                    //player two wins
                //if (current x and y = 1) and (x+1 and y-1 = 1) and (x+2 and y-2 = 1) and (x+3 and y-3 = 1)
                    //player one wins
                //if (current x and y = 2) and (x+1 and y-1 = 2) and (x+2 and y-2 = 2) and (x+3 and y-3 = 2)
                    //player two wins
                //if (current x and y = 1) and (x-1 and y-1 = 1) and (x-2 and y-2 = 1) and (x-3 and y-3 = 1)
                    //player one wins
                //if (current x and y = 2) and (x-1 and y-1 = 2) and (x-2 and y-2 = 2) and (x-3 and y-3 = 2)
                    //player two wins
    }
}
