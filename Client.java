import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) {

        try {

            System.out.println("Connecting...");
            Socket socket = new Socket("localhost",5555);
            System.out.println("Connected.");

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            String request = "ticket"; //ingresso

            output.writeUTF(request);
            output.flush();
            System.out.println("Submiting...");
            boolean response = input.readBoolean();

            if (!response){
                System.out.println("\n * Access Denied * \n");
            }else
                System.out.println("\n * Access Granted * \n");



            input.close();
            output.close();
            socket.close();

        } catch (IOException ex) {
            System.out.println("Erro no cliente: "+ ex.getMessage());
            //Logger.getLogger(Client.class.getName().log(Level.SEVERE,null, ex);
        }

    }
}