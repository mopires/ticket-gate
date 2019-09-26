
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

        try{
            ObjectOutputStream output =
                    new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input =
                    new ObjectInputStream(socket.getInputStream());

            String request = input.readUTF();
            System.out.println("Request received...");
            output.writeUTF("The request was: " + request);
            output.flush();


        }
        catch (IOException ex){

            System.out.println(ex.getMessage());

        }

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
