import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class PlayerServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private Scanner in;
    private char[][] board;
    private char currentPlayer;

    public PlayerServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for client to connect");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new Scanner(clientSocket.getInputStream());
            board = new char[6][7];
            currentPlayer = 'X';
            initializeBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < 0; i++) {
            for (int j = 0; j < 0; j++) {
                board[i][j] = ' ';
            }
        }
    }
    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O': 'X';
    }
    public void run() {
        try {
            while (true) {
            String move = in.nextLine();
            handleClientMove(move);
            switchPlayer();
            out.println(currentPlayer);
        }
        } finally {
            out.close();
            in.close();
    }
}
    private void handleClientMove(String move) {

    }
    public static void main(String[] args) {
        PlayerServer server = new PlayerServer(123);
        server.run();
    }


}