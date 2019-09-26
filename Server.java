
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.*;

public class Server {

    private ServerSocket serverSocket;

    private void CreateServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private Socket Wait() throws IOException{
        Socket socket = serverSocket.accept();
        return socket;
    }

    private void ProcessRequest(Socket socket){

        String request, response;

        try{
            ObjectOutputStream output =
                    new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input =
                    new ObjectInputStream(socket.getInputStream());

            request = input.readUTF();
            System.out.println("Processing request...");

            output.writeBoolean(IsOpen());
            output.flush();


        }
        catch (IOException ex){

            System.out.println(ex.getMessage());

        }

    }

    private boolean IsOpen(){

        //verifica disponibilidade
        boolean[] spots;
        GetList();

        /*for (int i = 0; i < spots.length; i++){
            if (!spots[i]){
                spots[i] = true;
                return true;
            }
        }*/

        return false;

    }

    private void GetList(){

        JSONObject json = new JSONObject("spot.json");
        //ArrayList<Boolean> list = new ArrayList<Boolean>();


        /*
        listTicket = new boolean[list.length()];
        for (int i = 0; i < list.length();i++){
            listTicket[i] = list.getBoolean(i);
        }
        */


    }

    private boolean CheckIn(String Request){

        //verificar o ingresso valido

        return true;
    }


    public static void main(String argv[]){

        Server server = new Server();
        try {
            server.CreateServer(5555);
        }catch (Exception ex){
            System.out.println(" \n Exception: \n " + ex.getMessage());
        }

            while (true){
                try {
                    System.out.println("Waiting connection...");
                    Socket clientRequest = server.Wait();
                    System.out.println("Client connected.");
                    server.ProcessRequest(clientRequest);
                }
                catch (Exception ex){
                    System.out.println(" \n Exception: \n " + ex.getMessage());
                }
            }





    }

}
