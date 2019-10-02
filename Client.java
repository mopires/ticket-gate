
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    public static void main(String[] args) throws IOException {

        Control control = new Control();

        while (true) {

            System.out.println("Right to get in | Left get out ");


            try {

                System.out.println("Connecting...");
                Socket socket = new Socket("localhost", 5555);
                System.out.println("Connected.");

                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                String request = "ticket"; //ingresso

                output.writeUTF(request);
                output.flush();
                System.out.println("Submiting...");
                boolean response = input.readBoolean();

                if (!response) {
                    System.out.println("\n * Access Denied * \n");
                } else
                    System.out.println("\n * Access Granted * \n");

                input.close();
                output.close();
                socket.close();

            } catch (IOException ex) {
                System.out.println("Erro no cliente: " + ex.getMessage());
            }
        }

    }

}