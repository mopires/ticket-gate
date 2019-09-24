import java.io.*;
import java.net.*;
import org.json.*;


public class Server {

    private int CurrentCapacity, MAX_CAPACITY = 1000;

    public static void main(String args[]) throws Exception {

        int porta = 9876;
        int numConn = 1;

        DatagramSocket serverSocket = new DatagramSocket(porta);

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while (true) {

            DatagramPacket receivePacket = new DatagramPacket(receiveData,
                    receiveData.length);
            System.out.println("Esperando por datagrama UDP na porta " + porta);
            serverSocket.receive(receivePacket);
            System.out.print("Datagrama UDP [" + numConn + "] recebido...");

            String sentence = new String(receivePacket.getData());
            System.out.println(sentence);

            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            String capitalizedSentence = sentence.toUpperCase();

            sendData = capitalizedSentence.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData,
                    sendData.length, IPAddress, port);

            System.out.print("Enviando " + capitalizedSentence + "...");

            serverSocket.send(sendPacket);
            System.out.println("OK\n");
        }
    }

    private boolean IsOpen(){

        if (CurrentCapacity() < Capacity()){
            return true;
        }
        else {
            return false;
        }

    }

    public int CurrentCapacity(){
        return CurrentCapacity;
    }

    public int Capacity(){
        return MAX_CAPACITY;
    }

    public void CheckIn(String Id){
        JSONObject list = new JSONObject("list-ticket.json");

        System.out.println(list.get(Id));
    }


}
