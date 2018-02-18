package edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing.EditMapActivity;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing.MapHelper;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.User.User;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.FriendServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.MapServerReqs;

import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType.*;

/**
 * Created by Joseph on 9/21/2017.
 */

/**
 * This class represents a game map.
 */
public class Map {

    /**
     * The width of all maps
     */
    public static final int WIDTH = 32;
    /**
     * The height of all maps
     */
    public static final int HEIGHT = 18;

    /**
     * An empty map
     */
    @SuppressLint("NewApi")
    public static final Map empty = new Map("","",0,0);

    //this holds a terrainMap of the terrain
    //for now it is hardcoded in but in the future it should be loaded from the server or
    //from a local cache
    
	//When the map is first loaded it is this one always Then we update it ( Using the methode from MapSeverReqs )
    //and it changes to the correct one.

	private TerrainType[][] terrainMap = {//[HEIGHT][WIDTH]
            {GRASS,PATH,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,PATH,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,PATH,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,PATH,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,PATH,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,PATH,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,BASE},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS}
    };


    private final int mapID;
    private final String mapName;
    private final String mapDescription;
    private final int author;



    //Location of the base
    private Cell base;

    //made public for testing
    //list of pathCells cells, NOTE: this isn't the path units will take
    private ArrayList<Cell> pathCells;

    //array list of cells which are valid spawn points
    private ArrayList<Cell> spawns;

    //graph of the pathCells through the map
    private Graph pathGraph;

    //given a spawn point, you can get a path to the enemy base
    private HashMap<Position,ArrayList<Position>> paths;


    /**
     * Creates a new instance of a map
     * @param mapName the name of this map
     * @param mapDescription the description for this map
     * @param mapID the map id
     * @param author the id of the author
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Map(String mapName, String mapDescription, int mapID, int author) {
        this.mapID = mapID;
        this.mapName = mapName;
        this.mapDescription = mapDescription;
        this.author = author;
        parseTerrainMap();
        buildPaths();
    }

    /**
     * Returns the TerrainType of a given location
     * @param x x coordinate
     * @param y y coordinate
     * @return the terrain type at the given location
     */
    public TerrainType getTerrainAt(int x, int y) {
        return terrainMap[y][x];
    }

	/**
     * Given a JSON String return a map.
     * @param s the JSON string
     * @return a map from this string
     */
    public static Map createMapFromJSON(String s){
        Gson gson = new Gson();
		return gson.fromJson(s,Map.class);
    }

    private void parseTerrainMap(){
        //parse the terrainMap

        //clearEnemyWave pathCells
        pathCells = new ArrayList<>();
        spawns = new ArrayList<>();

        for(int y = 0; y<HEIGHT;y++){
            for(int x = 0; x<WIDTH;x++){
                Cell cell;
                switch(terrainMap[y][x]){
                    case GRASS:
                        break;
                    case ROCK:
                        break;
                    case SPAWN:
                    case PATH:
                        //if the pathCells is on the edge and on the left side of the map, units can spawn from it
                        if(isMapEdge(x,y) && x < WIDTH/2) {
                            cell = new Cell(x, y, TerrainType.SPAWN);
                            pathCells.add(cell);
                            spawns.add(cell);
                        }
                        else {
                            cell = new Cell(x, y, terrainMap[y][x]);
                            pathCells.add(cell);
                        }
                        break;
                    case WATER:
                        break;
                    case BASE:
                        base = new Cell(x ,y , TerrainType.BASE);
                        pathCells.add(base);
                        break;
                }
            }
        }
    }

    //if the x,y coordinate is on the edge of the map, return true;
    private boolean isMapEdge(int x, int y){
        if(x==WIDTH-1 || x==0)
            return true;
        if(y==HEIGHT-1 || y == 0){
            return true;
        }
        return false;
    }

    //generates a graph which represents the pathCells on the map
    //not very efficient at the moment I (joe) will make it better eventually
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildPaths() {
        pathGraph = new Graph();

        for (int i = 0; i < pathCells.size(); i++) {
            Cell from = pathCells.get(i);
            for(int t = 0; t< pathCells.size(); t++) {
                Cell to = pathCells.get(t);
                if ((Math.abs(from.getX() - to.getX()) == 1 && from.getY() - to.getY() == 0 )||
                        (Math.abs(from.getY() - to.getY()) == 1 && from.getX()-to.getX() == 0)) {
                    pathGraph.addEdge(from.getPosition(),to.getPosition(), 1);
                }
            }
        }

        paths = new HashMap<>();

        for (Cell c : spawns) {
            AStar pathFinder = new AStar(pathGraph, c.getPosition(), base.getPosition());
            paths.put(c.getPosition(), pathFinder.findPath());
        }
    }

    /**
     * Returns a graph representation of this map
     * @return
     */
    public Graph getPathGraph(){
        return pathGraph;
    }

    /**
     * Returns a path to the base that originates from the given position.
     * @param start the starting position
     * @return Path originating from the starting positiong
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Position> getPath(Position start){
//        return paths.getOrDefault(start, new ArrayList<>());
        return paths.get(start);
    }

    /**
     * Returns all the spawn positions for this map
     * @return ArrayList of all spawn positions
     */
    public ArrayList<Cell> getSpawns(){
        return spawns;
    }

    /**
     * Returns the location of the base for this map
     * @return location of the base
     */
    public Cell getBase(){
        return base;
    }
    

    /**
     * Using the current instance create a json string.
     * @return the json string.
     */
    public String createJSONfromMap(){
        //we use this.
        Gson gson = new Gson();
        return gson.toJson(this);
    }


    /**
     * Returns a Bitmap thumbnail of this map
     * @param context The context of the current activity
     * @return A Bitmap image of this map
     */
    public Bitmap getMapThumbnail(Context context){
        Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        //each of the resource files I'm currently using are a single pixel so there isn't any scaling needed.
        //once we add better resources this method will have to be updated to account for that.
        for(int y = 0;y < HEIGHT;y++) {
            for(int x = 0 ;x< WIDTH;x++) {
                switch (terrainMap[y][x]) {
                    case GRASS:
                        canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.grasstile),x,y, null);
                        break;
                    case ROCK:
                        canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.rocktile),x,y, null);
                        break;
                    case PATH:
                    case SPAWN:
                        canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.pathtile),x,y, null);
                        break;
                    case WATER:
                        canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.watertile),x,y, null);
                        break;
                    case BASE:
                        canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.basetile),x,y,null);
                        break;
                }
            }
        }
        return bitmap;
    }

    /**
     * Returns the terrain map
     * @return 2d array of terrain types
     */
    public TerrainType[][] getTerrainMap(){
        return terrainMap;
    }

    /**
     * Returns the name of this map
     * @return name of this map
     */
    public String getName(){
        return mapName;
    }

    /**
     * Returns the description of this map
     * @return description of this map
     */
    public String getMapDescription(){
        return mapDescription;
    }

    /**
     * Returns this map's id
     * @return map id
     */
    public int getID() {
        return mapID;
    }

    /**
     * Changes this map to reflect the new terrainType map given
     * @param data 2d array of terrain types
     * @return true if the map was set
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean setData(TerrainType[][] data) {
        if(data == null) return false;
        this.terrainMap = data;
        parseTerrainMap();
        buildPaths();
        return true;
    }

    /**
     * Debugging method to make a pretty string...
     * @return
     */
    @Override
    public String toString() {
        return "Map number "+mapID+" made by player with ID "+ author+ "the name of the map is : "+mapName+"." +
                "\n Here is the description : \n "+mapDescription;

    }

    /**
     * Returns the id of the map author
     * @return id of map author
     */
    public int getAuthor() {
        return author;
    }

    /**
     * Returns the terrain type map of this map
     * @return 2d array of terrain types
     */
    public TerrainType[][] getData() {
        return terrainMap;
    }

    //this method is strictly for testing purposes

    /**
     * updates the map to reflect the given 2d array of terrain types. Functionally the same as the setData() method but less safe
     * @param map 2d array of terrain types
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setTerrainMapManually(TerrainType[][] map){
        terrainMap = map;
        parseTerrainMap();
        buildPaths();
    }

    /**
     * Creates a dialog for the map.
     * This is a "pop up" that shows most of the information for the map.
     * @param context the current context.
     * @return the dialog created.
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Dialog createDialog(Context context){
        final Context ctx = context;
        final Dialog dialog = new Dialog(context);
        final Map m = this;
        dialog.setContentView(R.layout.dialog_map_popup);
        //now set the content of the edit text.
        dialog.setTitle("Map");
        TextView text = (TextView)dialog.findViewById(R.id.name_of_map);
        text.setText("Name of the map : \n " +mapName);
        text = (TextView) dialog.findViewById(R.id.descriptionOfMap);
        text.setText("Description of the map : \n "+mapDescription);
        //Get the user
        User u = FriendServerReqs.getSingleUser(author);
        text = (TextView)dialog.findViewById(R.id.authorName);
        text.setText("Author is : \n "+u.getUsername()+", ID : "+author);
        //Now set the bit map..

        ImageView iv = (ImageView)dialog.findViewById(R.id.thumbnail_of_map);
        iv.setBackground(new BitmapDrawable(ctx.getResources(),getMapThumbnail(ctx)));
        iv.getBackground().setFilterBitmap(false);


        //now the button.
        Button delete = (Button)dialog.findViewById(R.id.delete_map);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MapServerReqs.deleteMap(mapID,author)){
                    dialog.dismiss();
                    Toast.makeText(ctx,"Map deleted.", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(ctx,"Sorry error occured. Try again", Toast.LENGTH_LONG).show();
                }
            }
        });


        Button editMap = (Button) dialog.findViewById(R.id.edit_map);
        editMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapHelper.set(m);
                Intent intent = new Intent(ctx, EditMapActivity.class);
                ctx.startActivity(intent);


            }
        });

        //the preview..
        Button preview = (Button) dialog.findViewById(R.id.close_map_dialog);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        return dialog;
    }






    @Override
    public boolean equals(Object o){
        if(o instanceof Map){
            Map om = (Map) o;

            boolean mapAndDescr = (om.mapDescription.equals(this.mapDescription)&&om.mapName.equals(this.mapName));
            boolean ids = (om.mapID == this.mapID && om.author == this.author);
            return ids && mapAndDescr;

        }
        return false;
    }


}
