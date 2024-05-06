import java.net.*; 
import java.io.*; 
//the client provides functionality for the players

//the client will tell the server that a player has made a move and the cloumn they have chosen
//**read server for details 
//the client recieves the updated board and switches to the other player

public class Client 
{
    private Socket socket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private String address;
    private int port;

    public Client(String address, int port)
    {
        this.address = address;
        this.port = port;

        try
        {
            socket = new Socket(address, port);
            in = new DataInputStream(System.in);
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        String line = "";

        while(!line.equals("Over"))
        {
            try
            {
                in.close();
                out.close();
                socket.close();
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args)
    {
        Client client = new Client("127.0.0.1", 5000);
    }
}