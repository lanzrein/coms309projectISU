package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities;

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
import android.view.SurfaceHolder;

import java.util.ArrayList;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.ZoomingSurfaceView;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.buyables.TurretBuyable;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Projectile;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Turret;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

import static edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.MainThread.canvas;

/**
 * Created by Alex on 10/2/2017.
 * This class displays an instance of the game on screen and handles any touch events on the screen.
 */
public final class GamePanel extends ZoomingSurfaceView {

    private GameLogic game;

    private final int playerID;
    private final int adversaryID = 1;//Opponent.opponent.getPlayerID();


    private Bitmap background;
    private ArrayList<Bitmap> textures;
    private Bitmap enemySprite, turretSprite, projectileSprite;
    private Bitmap goblin_burrow_Sprite, goblin_flying_Sprite, goblin_ground_Sprite;
    private Bitmap human_ground_Sprite, human_burrow_Sprite, human_flying_Sprite;
    private Bitmap dwarf_ground_Sprite, dwarf_burrow_Sprite, dwarf_flying_Sprite;

    /**
     * Constructs a new GamePanel
     * @param context context of the Activity
     * @param attributeSet attributes from the layout xml file
     */
    public GamePanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        playerID = SharedPrefManager.getInstance(context).getLocalPlayer().getId();
        getHolder().addCallback(this);
    }

    /**
     * Sets the instance of the game that this class will display
     * @param gameLogic
     */
    public void setArgs(GameLogic gameLogic) {
        this.game = gameLogic;
    }

    private void initTextures(int scalex, int scaley) {
        textures = new ArrayList<>();

        //these must be in the order they are listed in the enum
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.grass_texture), scalex, scaley, false));
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.path_texture), scalex, scaley, false));
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.water_texture), scalex, scaley, false));
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rock_texture), scalex, scaley, false));
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.base_texture), scalex, scaley, false));
        textures.add(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.path_texture), scalex, scaley, false));

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    /**
     * Called when this surface view is created
     * @param holder the holder of this surface view
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        int scalex = getWidth() / game.getMap().WIDTH;
        int scaley = getHeight() / game.getMap().HEIGHT;

        //loads all the textures
        initTextures(scalex, scaley);

        background = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvasMap = new Canvas(background);
        createMapBitmap(canvasMap, game.getMap(), scalex, scaley);

        generate_sprites(scalex, scaley);

    }

    private void generate_sprites(int scalex, int scaley) {
        enemySprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_enemy), scalex, scaley, false);
        turretSprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.tower), scalex, scaley, false);
        projectileSprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.placeholder_projectile), scalex, scaley, false);
        dwarf_ground_Sprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dwarf_ground), scalex, scaley, false);
        dwarf_burrow_Sprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dwarf_flying), scalex, scaley, false);
        dwarf_flying_Sprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dwarf_burrowing), scalex, scaley, false);

        human_ground_Sprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.human_ground), scalex, scaley, false);
        human_burrow_Sprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.human_burrowing), scalex, scaley, false);
        human_flying_Sprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.human_flying), scalex, scaley, false);


        goblin_ground_Sprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.goblin_ground), scalex, scaley, false);
        goblin_burrow_Sprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.goblin_burrowing), scalex, scaley, false);
        goblin_flying_Sprite = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.goblin_flying), scalex, scaley, false);


    }

    /**
     * Called whenver there is a touch event on this surface view
     * @param event the motion event that occured
     * @return true if this event was handled
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int scalex = getWidth() / game.getMap().WIDTH;
        int scaley = getHeight() / game.getMap().HEIGHT;

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //this will eventually be used to get the position of towers that are bought and placed
                //translate coordinates of touch event according to any zooming / panning that has been done
                float[] point = {event.getX(), event.getY()};
                Matrix inverseMatrix = new Matrix();
                getmMatrix().invert(inverseMatrix);
                inverseMatrix.mapPoints(point);

                game.placeTurret(new Position((int) (point[0] / scalex), (int) (point[1] / scaley)));

                Log.d("GAME_PANEL", "RELEASED at: " + point[0] / scalex + ", " + point[1] / scaley);
                break;
        }

        //zooming and panning done in the super method
        return super.onTouchEvent(event);
    }

    /**
     * When called, the canvas is redrawn. The map is drawn first and then units, towers, and projectiles are drawn.
     * @param canvas the canvas to draw on
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void draw(Canvas canvas) {

        super.draw(canvas);
        preDraw(canvas);

        canvas.drawBitmap(background, 0, 0, null);


        //scales map coordinates to screen size
        float scalex = getWidth() / Map.WIDTH;
        float scaley = getHeight() / Map.HEIGHT;

        if (game.getSpawnedUnits() != null) {
            for (Unit t : game.getSpawnedUnits()) {
                if (t.isAlive()) {
                    float x, y;
                    x = t.getPosition().getX() * scalex;
                    y = t.getPosition().getY() * scaley;

                    if (t.ID == 0) {
                        canvas.drawBitmap(dwarf_flying_Sprite, x, y, null);
                    } else if (t.ID == 1) {
                        canvas.drawBitmap(dwarf_ground_Sprite, x, y, null);
                    } else if (t.ID == 2) {
                        canvas.drawBitmap(dwarf_burrow_Sprite, x, y, null);
                    } else if (t.ID == 3) {
                        canvas.drawBitmap(goblin_flying_Sprite, x, y, null);
                    } else if (t.ID == 4) {
                        canvas.drawBitmap(goblin_ground_Sprite, x, y, null);
                    } else if (t.ID == 5) {
                        canvas.drawBitmap(goblin_burrow_Sprite, x, y, null);
                    } else if (t.ID == 6) {
                        canvas.drawBitmap(human_flying_Sprite, x, y, null);
                    } else if (t.ID == 7) {
                        canvas.drawBitmap(human_ground_Sprite, x, y, null);
                    } else if (t.ID == 8) {
                        canvas.drawBitmap(human_burrow_Sprite, x, y, null);

                    } else {
                        canvas.drawBitmap(goblin_ground_Sprite, x, y, null);
                    }

                }
            }
        }


        for (
                Turret t : game.getTurrets())

        {
            canvas.drawBitmap(turretSprite, t.getPosition().getX() * scalex, t.getPosition().getY() * scaley, null);
        }

        for (
                Projectile t : game.getProjectiles())

        {
            if (t.isAlive()) {
                float x, y;
                x = t.getPosition().getX() * scalex;
                y = t.getPosition().getY() * scaley;
                canvas.drawBitmap(projectileSprite, t.getPosition().getX() * scalex, t.getPosition().getY() * scaley, null);
            }
        }

        postDraw(canvas);

    }

    private void createMapBitmap(Canvas canvas, Map map, int scalex, int scaley) {
        TerrainType[][] terrainMap = map.getTerrainMap();
        for (int x = 0; x < game.getMap().WIDTH; x++) {
            for (int y = 0; y < game.getMap().HEIGHT; y++) {
                canvas.drawBitmap(textures.get(terrainMap[y][x].ordinal()), x * scalex, y * scaley, null);
            }
        }
    }

    /**
     * Changes the game instance that this view is displaying
     * @param gameLogic a new game instance to display
     */
    public void setGameLogic(GameLogic gameLogic) {
        game = gameLogic;
    }

    /**
     * Returns the local player's id
     * @return the local player's id
     */
    @Deprecated
    public int getPlayerID() {
        return playerID;
    }
}