
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

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

        return true;

    }

    private boolean CheckIn(String Request){

        //verificar o ingresso valido

        return true;
    }


    public static void main(String argv[]){

        try {
            Server server = new Server();
            server.CreateServer(5555);
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
