import java.io.*;
import java.net.*;

public class Server {
    public static int port = 8000;
    private Socket[] client = new Socket[2];
    private BufferedReader[] in = new BufferedReader[2];
    private BufferedWriter[] out = new BufferedWriter[2];

    private void downService() throws IOException {
        for (int i = 0; i < 2; i++){
            client[i].close();
            in[i].close();
            out[i].close();
        }

    }

    public void runServer() throws IOException {
        ServerSocket server = new ServerSocket(port);
        System.out.println("Server Started");

        try{
            for (int i = 0; i < 2; i++){
                this.client[i] = server.accept();
                in[i] = new BufferedReader(new InputStreamReader(client[i].getInputStream()));
                out[i] = new BufferedWriter(new OutputStreamWriter(client[i].getOutputStream()));
                System.out.println("Connected");
            }
            while (true){
                String word1 = in[0].readLine();
                System.out.println("Client1 writes " + word1);
                out[1].write(word1 + '\n');
                out[1].flush();
                if(word1.equals("stop")) {
                    this.downService();
                    break;
                }

                String word2 = in[1].readLine();
                System.out.println("Client2 writes " + word2);
                out[0].write(word2 + '\n');
                out[0].flush();
                if(word2.equals("stop")) {
                    this.downService();
                    break;
                }

            }

        } finally {
            server.close();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.runServer();
    }
}
