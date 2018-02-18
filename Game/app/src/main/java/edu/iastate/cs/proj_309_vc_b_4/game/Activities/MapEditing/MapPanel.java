package edu.iastate.cs.proj_309_vc_b_4.game.Activities.MapEditing;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.ZoomingSurfaceView;
import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.MainThread;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Cell;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Player;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.MapServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType.*;

/**
 *
 * This surface views will display the background of a map.
 *
 * Created by johan on 12.11.2017.
 */

public final class MapPanel extends ZoomingSurfaceView {
    //this is the map object.
    private MapBuilder mapBuilder = new MapBuilder();
    private Context ctx;
    private ArrayList<Bitmap> textures;
    private Matrix mMatrix = new Matrix();
    private TerrainType currTerrainType = BASE;
    private boolean editing = true;

    public MapPanel(Context ctx, AttributeSet attributeSet){
       super(ctx, attributeSet);
       this.ctx = ctx;
       this.getHolder().addCallback(this);
    }


    /**
     * Draws the map on the canvas
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
//        preDraw(canvas);

        //pretty straight forward we just draw the map..
        //zooming and panning stuff
        int saveCount = canvas.getSaveCount();
        canvas.save();
        canvas.concat(mMatrix);
        int scalex = getWidth()/Map.WIDTH;
        int scaley = getHeight()/Map.HEIGHT;



        TerrainType[][] terrainMap = mapBuilder.terrain;
        for(int x = 0;x<Map.WIDTH;x++){
            for(int y = 0;y<Map.HEIGHT;y++){
                canvas.drawBitmap(textures.get(terrainMap[y][x].ordinal()),x*scalex,y*scaley,null);
            }
        }

//        postDraw(canvas);


    }

    //from GamePanel
    private void initTextures(int scalex,int scaley){
        textures = new ArrayList<>();

        //these must be in the order they are listed in the enum
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.grass_texture),scalex,scaley,false));
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.path_texture),scalex,scaley,false));
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.water_texture),scalex,scaley,false));
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rock_texture),scalex,scaley,false));
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.base_texture),scalex,scaley,false));
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.path_texture),scalex,scaley,false));

    }


    /**
     * Takes care of touch events.
     * We want to only take care of events when player want to "draw" on the map to
     * change it.
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        refreshCanvas();

        if(editing) {
            Log.d("position : ", motionEvent.getX() + " : " + motionEvent.getY());
            int caseX = (int) (motionEvent.getX() / getWidth() * Map.WIDTH);
            int caseY = (int) (motionEvent.getY() / getHeight() * Map.HEIGHT);
            Log.d("Boxes : ", caseX + " , " + caseY);

            boolean valid = mapBuilder.setTileTo(caseX, caseY, currTerrainType);
            if (!valid) {
                Toast.makeText(ctx, "Not a valid placement ! ", Toast.LENGTH_SHORT).show();
            }


        }
        //this does the zooming/panning
        return super.onTouchEvent(motionEvent);
    }

    /**
     * Save the edited map as a map that will be sent to the database and saved.
     * This method does NOT take in charge to verifiy if the map is valid.
     * @return the created map.
     */
    public Map saveMap(){
        return mapBuilder.build();
    }

    /**
     * Check if the map is valid ( there is a path from base to spawn )
     * @return true iff the map is valid
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isValidMap(){
        return mapBuilder.isValidmap();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        int scalex = getWidth()/Map.WIDTH;
        int scaley = getHeight()/Map.HEIGHT;
        initTextures(scalex,scaley);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    /**
     * Refresh the canvas when needed.
     * We only need to do it when it is changed.
     */
    public void refreshCanvas(){
        Canvas c = getHolder().lockCanvas();
        draw(c);
        getHolder().unlockCanvasAndPost(c);

    }

    /**
     * Change the current type of terrain being drawn.
     * This can be seen as what type of "brush" the user has to draw on the canvas.
     * @param currentTerrainType the id (in the order of the enum TerrainType) of the terrain type
     */
    public void setCurrentTerrainType(int currentTerrainType) {
        this.currTerrainType = TerrainType.values()[currentTerrainType];
    }

    /**
     * Set the name of the map being built.
     * @param name the name
     */
    public void setName(String name){
        mapBuilder.setName(name);
    }

    /**
     * Set the description of the map being built.
     * @param description
     */
    public void setDescription(String description){
        mapBuilder.setDescription(description);
    }

    /**
     * This is used for the preview. We set the map already but disable all editing.
     *
     * @param map the map
     * @param editing whether or not thius is editing or not
     */
    public void setMap(Map map,boolean editing) {
        this.mapBuilder.terrain = map.getTerrainMap();
        this.mapBuilder.name = map.getName();
        this.mapBuilder.description = map.getMapDescription();
        this.editing = editing;

    }

    /**
     * Class to allow for building maps.
     * It is a builder pattern
     */
    private class MapBuilder{
        //contains the data for the map. on creation its an "empty" map of only grass.
        private TerrainType[][] terrain = new TerrainType[Map.HEIGHT][Map.WIDTH];
        private String name;
        private String description;
        private MapBuilder(){
            //init...
            for(int i = 0; i < Map.WIDTH; i++ ){
                //start with just a big patch of grass.
//                terrain[i] = (TerrainType[]) Collections.nCopies(Map.HEIGHT, GRASS).toArray();
                for(int j = 0; j < Map.HEIGHT; j++){
                    terrain[j][i] = GRASS;
                }
            }
        }


        private Map build(){

            Player p = SharedPrefManager.getInstance(ctx).getLocalPlayer();
            return MapServerReqs.createNewMap(this.toMap(p.getId()),terrain, p);
        }

        private void setDescription(String description){
            this.description = description;
        }

        private void setName(String name){
            this.name = name;
        }

        @TargetApi(Build.VERSION_CODES.N)
        private Map toMap(int authorID){
            Map m = new Map(name,description,-1,authorID);
            m.setData(terrain);
            return m;
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        private boolean isValidmap(){
            //check for only one base
            try {
                if (!exactlyOneBase()) {
                    Log.d("MapERROR", "Base amount not correct");
                    return false;
                }
                Map dummy = mapBuilder.toMap(-1);

                for (Cell spawn : dummy.getSpawns()) {
                    //we only need one legal path..
                    ArrayList path = dummy.getPath(spawn.getPosition());
                    Log.d("MapERROR", "Path null" + (path != null) + "path empty : " + path.isEmpty());

                    if (!path.isEmpty()) {

                        return true;
                    }
                }
            }catch(Exception e){
                return false;
            }
            //havent found a single legal path..
            return false;



        }

        private boolean exactlyOneBase() {
            int count = 0;
            for(TerrainType[] line : terrain){
                for(TerrainType bloc : line){
                    if(bloc == BASE){
                        count++;
                    }
                }
            }
            return count ==  1;
        }

        private boolean setTileTo(int x, int y, TerrainType type){

            if(type == BASE){
                if(x < Map.WIDTH/2){return false;}//we want the base to always be in the right side of the map.
            }

            if(y>=0&&y<Map.HEIGHT && x>=0&&x< Map.WIDTH)
                terrain[y][x] = type;

            return true;


        }



    }

}
