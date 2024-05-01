import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread
{
    private Socket connectionToPlayer = null;
    private ServerSocket server = null;
    //private int port;
    private DataInputStream in = null;


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

}
