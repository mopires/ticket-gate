
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
        /*boolean[] list = GetList();
        if (list == null) {
            System.out.println("Error: Fail to retrieve the list of tickets");
            return false;
        }else{

            for (int i = 0; i < list.length; i++){
                if (!list[i]){
                    System.out.println(list[i]);
                    return true;
                }
                else
                    return false;
            }

        }*/
        return true;
    }

    private boolean[] GetList() {
        boolean[] list = null;
        try {

            InputStream file = new FileInputStream("/home/matheus/IdeaProjects/Server/src/spots.json");
            JSONObject json = new JSONObject(file);
            JSONArray jsonArray = json.getJSONArray("");
            list = new boolean[jsonArray.length()];
            for (int i = 0; i < jsonArray.length();i++){
                list[i] = jsonArray.getBoolean(i);
            }

        }catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }

        return list;
    }

    /*private boolean CheckIn(String Request){

        //verificar o ingresso valido

        return true;
    }*/


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
