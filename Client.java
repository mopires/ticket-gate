import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) {
        try {
            /*1.Estabelecer conexão com o servidor
             * 2. Trocar mensagens com o servidor
             */
            // cria a conexão entre o cliente e o servidor.
            System.out.println("Estabelecendo conexão...");
            Socket socket = new Socket("localhost",5555);
            System.out.println("Conexão estabelecida.");

            // criação dos streams de entrada e saída
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            System.out.println("Enviando mensagem...");
            String msg = "HELLO";
            output.writeUTF(msg);
            output.flush(); //libera buffer para envio


            System.out.println("Mensagem "+msg+" enviada.");

            msg = input.readUTF();
            System.out.println("Resposta: " + msg);

            input.close();
            output.close();
            socket.close();


        } catch (IOException ex) {
            System.out.println("Erro no cliente: "+ ex.getMessage());
            //Logger.getLogger(Client.class.getName().log(Level.SEVERE,null, ex);
        }

    }
}