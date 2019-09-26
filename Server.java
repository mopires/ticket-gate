
import java.io.*;
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
        try {

            boolean[] list = GetList();

            for (int i = 0; i < list.length; i++){
                if (!list[i]){
                    list[i] = true;
                    //write on json file
                    return true;
                }
            }
        }catch (IOException ex){
            System.out.println(ex.getStackTrace());
        }


        return false;

    }

    private boolean[] GetList() throws FileNotFoundException {

        InputStream file = new FileInputStream("/home/matheus/IdeaProjects/Server/src/spots.json");
        JSONObject json = new JSONObject(file);
        //ArrayList<Boolean> list = new ArrayList<Boolean>();
        JSONArray jsonArray = json.names();


        boolean[] list = new boolean[jsonArray.length()];
        for (int i = 0; i < jsonArray.length();i++){
            list[i] = Boolean.valueOf(jsonArray.getString(i));
        }


        return list;


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
