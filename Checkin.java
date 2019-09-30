import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Checkin implements Runnable{

    public boolean[] list;

    public Checkin(boolean[] list){
        this.list = list;
    }

    public void run(){
        JSONObject json;
        //write on spots.json new customer
        //Cria o parse de tratamento
        JSONParser parser = new JSONParser();

        try {

            json = (JSONObject) parser.parse(
                    new FileReader("/home/matheus/IdeaProjects/Server/src/spots.json"));

            //transforma o list em um map para poder colocar todas posicoes no json
            HashMap<String, List<Boolean>> updateList = new HashMap<>();
            updateList.put("ticket", new ArrayList<Boolean>());

            for (int i = 0; i < list.length; i++) {
                updateList.get("ticket").add(Boolean.valueOf(list[i]));
            }

            json.putAll(updateList);
            //JSONArray jsonArray = new JSONArray();
            //jsonArray.add(json);

            FileWriter file =
                    new FileWriter("/home/matheus/IdeaProjects/Server/src/spots.json");

            file.write(json.toJSONString());
            file.flush();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
