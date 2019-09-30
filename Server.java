
import java.io.*;
import java.net.*;
import java.util.HashMap;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

public class Server {

    private ServerSocket serverSocket;

    private void CreateServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private Socket Wait() throws IOException {
        Socket socket = serverSocket.accept();
        return socket;
    }

    private void ProcessRequest(Socket socket) {

        String request, response;

        try {
            ObjectOutputStream output =
                    new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input =
                    new ObjectInputStream(socket.getInputStream());

            request = input.readUTF();
            System.out.println("Processing request...");

            output.writeBoolean(IsOpen());
            output.flush();


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private boolean IsOpen() {

        //verifica disponibilidade o TAMANHO DO ARRAY SE MAIOR QUE MIL TRANCA
        boolean[] list = GetList();
        boolean rt = false;

        for (int i = 0; i < list.length; i++) {

            if (!list[i]) {
                list[i] = true; // fill the spot
                CheckIn(list);
                rt = true; //return true  to allow entrance
                break;
            }
        }
        return rt;
    }

    private boolean[] GetList() {
        boolean[] list = null;
        try {

            JSONObject json;
            //Cria o parse de tratamento
            JSONParser parser = new JSONParser();

            json = (JSONObject) parser.parse(
                    new FileReader("/home/matheus/IdeaProjects/Server/src/spots.json"));

            JSONArray jsonArray = (JSONArray) json.get("ticket");

            list = new boolean[jsonArray.size()];

            for (int i = 0; i < list.length; i++) {
                list[i] = Boolean.valueOf(jsonArray.get(i).toString());
            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void CheckIn(boolean[] list) {

        //write on spots.json new customer
        JSONObject json;
        //Cria o parse de tratamento
        JSONParser parser = new JSONParser();

        try {

            json = (JSONObject) parser.parse(
                    new FileReader("/home/matheus/IdeaProjects/Server/src/spots.json"));

            //arrumar aqui para escrever certo
            HashMap<String, boolean[]> updateList = new HashMap<>();

            updateList.put("ticket",list);

            json.putAll(updateList);
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(json);


            FileWriter file =
                    new FileWriter("/home/matheus/IdeaProjects/Server/src/spots.json");

            file.write(jsonArray.toString());//ele escreve isso no lugar Z@5f2050f6
            file.flush();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String argv[]) {

        Server server = new Server();
        try {
            server.CreateServer(5555);
        } catch (Exception ex) {
            System.out.println(" \n Exception: \n " + ex.getMessage());
        }

        while (true) {
            try {
                System.out.println("Waiting connection...");
                Socket clientRequest = server.Wait();
                System.out.println("Client connected.");
                server.ProcessRequest(clientRequest);
            } catch (Exception ex) {
                System.out.println(" \n Exception: \n " + ex.getMessage());
            }
        }


    }

}
