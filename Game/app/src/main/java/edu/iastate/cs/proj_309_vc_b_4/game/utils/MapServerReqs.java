package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Player;

import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map.HEIGHT;
import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map.WIDTH;

/**
 * Created by johan on 16.10.2017.
 * The purpose of this class is to provide methods that help fetch data from the server.
 * It also provides utility to publish the data from a map.
 *
 */

public final class MapServerReqs extends RequestAsync{
    public static final String SERVER_ADDRESS = VolleySingleton.SERVER_ADDRESS+"/game/maps/";
    private MapServerReqs(){};
    /**
     * Given a map id fetch and compute the metadata of the map. ( The name and description )
     * remember : not use this method in a loop to get info about all the maps prefer method getAllMapsMeta
     * @param mapID
     * @return the map(Only the name and description.
     */
    public static Map fetchMetaDataByID1(int mapID){
        String url = SERVER_ADDRESS+"mapInfo_request.php?mapID="+mapID;
        //giving the async task a try..

        AsyncTask<String,Void,String> mdf = asyncTaskGen();
        mdf.execute(url);

        try {
            return turnIntoMap(mdf.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
            return Map.empty;

    }

    /**
     * Turn a string into a map.
     * @param s string
     * @return the map created
     */
    private static Map turnIntoMap(String s){
        //its in JSON so just convert it..
        try {
            Gson gson = new Gson();
            Map map = gson.fromJson(s, Map.class);
            return map;

        }catch(JsonSyntaxException je){
            //in case of network error or any error return a dummy map.
            return Map.empty;
        }
    }

    /**
     * Returns a list of all the maps we have.
     * Use of asynctask
     * @return list of all maps.
     */

    public static List<Map> getAllMapMeta1(){
        String url = SERVER_ADDRESS+"map_allData.php";



        AsyncTask<String,Void,String> mmd = asyncTaskGen();
        mmd.execute(url);
        String res = "ERR";
        try {
            res = mmd.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return processAllMaps(res);
    }

    /**
     * This maps process a string response from the server and converts it into a list of maps.
     *
     * @param response the response
     * @return a list of maps with only the accurate metadata.
     */
    private static List<Map> processAllMaps(String response){
//        got a list of metadata in JSON format.
        List<Map> maps = new ArrayList<>();
        if(!response.contains("ERR")) {
            String[] xs = response.split(Pattern.quote("$"));
            Gson gson = new Gson();
            for (String s : xs) {
                maps.add(gson.fromJson(s, Map.class));
            }
        }

        return maps;
    }

    /**
     *
     * Get the data about the map. At first its set as a dummy field. and then we update it.
     * This allows to counter issues with null pointers.
     * @param map the current map
     * @return true if it was updated, false else.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean getMapData(Map map){
        //we use the same asynctask as before.
        String url = SERVER_ADDRESS + "mapData.php?mapID="+map.getID();

        AsyncTask<String,Void,String> md = asyncTaskGen();
        md.execute(url);
        String response = null;
        try {
            response = md.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.d("Resp", "Response is : "+response);
        if(response!=null) {
            TerrainType[][] tt = processIntoData(response);
            map.setData(tt);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Processes the data oof many maps at once.
     *
     * @param maps the maps to be processed
     * @return true if the masp have been processed correctly
     */
    @SuppressLint("NewApi")
    public static boolean getMapsData(List<Map> maps){
        for(Map m : maps){
            if(!getMapData(m)){
                return false;
            }
        }
                return true;
    }
    private static TerrainType[][] processIntoData(String response) {
        List<Byte> xs = new ArrayList<Byte>();
        TerrainType[][] terrainTypes = new TerrainType[HEIGHT][WIDTH];
        if(response.charAt(0) != '['){
            return parseNonCompressedData(response);
        }
        if(response.charAt(0) == '[' && response.endsWith("]")) {
            response = response.substring(1, response.length() - 1);//we get rid of the braces..
        }
        try {
            String[] bytes = response.split(",");
            for (String s : bytes) {
                xs.add(Byte.parseByte(s.trim()));
            }
        }catch(PatternSyntaxException pe){
            Log.d("Pattern error", "Error on the pattern ! ");
            return null;
        }catch(NumberFormatException ne){
            Log.d("Number format", "Error on the format of the number");
            return null;
        }
        List<Byte> decoded = RunLengthEncoder.decode(xs);

        int decIdx = 0;
        for(int i = 0; i < Map.HEIGHT; i ++){
            for(int j = 0 ; j < Map.WIDTH; j++){
                if(decIdx >= decoded.size()){
                    return null;
                }
                terrainTypes[i][j] =  TerrainType.values()[decoded.get(decIdx)];
                decIdx++;
            }
        }


        return terrainTypes;
    }

    private static TerrainType[][] parseNonCompressedData(String response) {
        TerrainType[][] terrainTypes =new TerrainType[HEIGHT][WIDTH];
        String[] bytes = response.split(",");
        List<Byte> decoded = new ArrayList<>();
        for(String s : bytes){
            decoded.add(Byte.parseByte(s.trim()));
        }


        int decIdx = 0;
        for(int i = 0; i < Map.HEIGHT; i ++){
            for(int j = 0 ; j < Map.WIDTH; j++){
                if(decIdx >= decoded.size()){
                    return null;
                }
                terrainTypes[i][j] =  TerrainType.values()[decoded.get(decIdx)];
                decIdx++;
            }
        }

        return terrainTypes;

    }

    /**
     * This method will ask the server to create a new map based on the terrain given.
     * The map will be remembered as being created by the player who makes the request.
     *
     * @param terrain
     * @param player
     * @return A full map if correclty done else an empty map (with dummy values )
     */
    @SuppressLint("NewApi")
    public static Map createNewMap(Map mapBuilder, TerrainType[][] terrain, Player player) {


        List<Byte> toInteger = new ArrayList<>();
        for(int i = 0; i < terrain.length;i++){
            for(int j = 0; j < terrain[i].length;j++){
                toInteger.add((byte)terrain[i][j].ordinal());
            }
        }

        List<Byte> compress = RunLengthEncoder.encode(toInteger);

        String url = SERVER_ADDRESS+"create_newmap.php?username="+player.getUsername()+"&playerID="+player.getId()+"" +
                "&mapdata="+compress.toString()+"&description="+mapBuilder.getMapDescription()+"&mapName="+mapBuilder.getName();
        AsyncTask<String,Void,String> at = asyncTaskGen();

        at.execute(url);
        //it will return to us either the map in GSOn or error code..
        try {
            String res = at.get();
            if(res.contains("ERR")){
                return Map.empty;
            }else{
                //we do the mapping..
                Gson gson = new Gson();
//                Map map = MapServerReqs.fetchMetaDataByID1(Integer.parseInt(res));
                Map map = Map.createMapFromJSON(res);
                MapServerReqs.getMapData(map);
                return map;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return Map.empty;
    }

    /**
     * Returns all the maps created by the author with authorID
     * @param authorID . authorID we are looking for
     * @return the list of maps created by the author
     */
    public static List<Map> getMapsByAuthorID(int authorID){

        String url = SERVER_ADDRESS+"map_byAuthor.php?authorID="+authorID;



        AsyncTask<String,Void,String> mmd = asyncTaskGen();
        mmd.execute(url);
        String res = "ERR";
        try {
            res = mmd.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return processAllMaps(res);


    }

    /**
     * Delete the map with mapID of author with author ID and
     * @param mapID the mapID
     * @param authorID the authorID
     * @return true iff the map was successfully deleted.
     */
    public static boolean deleteMap(int mapID,int authorID){
        String url = SERVER_ADDRESS+"delete_map.php?authorID="+authorID+"&mapID="+mapID;

        AsyncTask<String,Void,String> deleteMap = asyncTaskGen();
        deleteMap.execute(url);
        return RequestAsync.checkResponse(deleteMap);
    }
}
