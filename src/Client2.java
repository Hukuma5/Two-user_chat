import java.io.*;
import java.net.Socket;

public class Client2 {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private int port = 8000;
    private String ipAdr;

    private void downService(){
        try {
            this.socket.close();
            in.close();
            out.close();
        } catch (IOException e){
            System.exit(1);
        }
    }

    public void runClient(String ipAdr){
        this.ipAdr = ipAdr;
        try{
            this.socket = new Socket(this.ipAdr, this.port);
        } catch (IOException e){
            System.err.println("Socket failed");
        }

        try{
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(true){
                String word = in.readLine();
                if(word.equals("stop")) {
                    this.downService();
                    break;
                }
                System.out.println(word + '\n');
                String word2 = inputUser.readLine();
                out.write(word2 + '\n');
                out.flush();
                if (word2.equals("stop")) {
                    out.write("stop" + "\n");
                    this.downService();
                    break;
                }
            }
        } catch (IOException e){
            this.downService();
        }
    }

    public static void main(String[] args){
        Client2 client = new Client2();
        client.runClient("localhost");
    }
}
