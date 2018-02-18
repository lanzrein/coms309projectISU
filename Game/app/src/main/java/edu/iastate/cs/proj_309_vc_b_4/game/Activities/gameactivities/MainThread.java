package edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.gameactivities.gamemenus.GameSidePanel;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Speed;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Time;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gamelogic.GameLogic;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Unit;
import edu.iastate.cs.proj_309_vc_b_4.game.User.User;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SoloGameAI;

/**
 * Created by Alex on 10/2/2017.
 *
 * This thread handles the game loop. The game loop consists of making draw calls to the GamePanel and updating all of the instances of the games.
 */
public final class MainThread extends Thread {
    /**
     * The target FPS that this thread will run at
     */
    public static final int MAX_FPS = 30;
    private double averageFPS;
    private final SurfaceHolder surfaceHolder;
    private GameActivity hostActivity;
    private GamePanel gamePanel;
    private GameSidePanel gameSidePanel;
    private HashMap<Integer,GameLogic> sessions;
    private boolean running;
    /**
     * The instance of the canvas that will be drawn to
     */
    public static Canvas canvas;
    private User opponent,localPlayer;

    /**
     * Set's whether this thread is running. If false, the thread will not run. If true, the thread will run when run() is called
     * @param running false if this thread should not be running, true if it should be running.
     */
    public void setRunning(boolean running){
        this.running = running;
    }

    /**
     * Construcs a new MainThread object
     * @param host host activity
     * @param surfaceHolder The surface view holder
     * @param gamePanel the surface view that will be drawn
     * @param gameSidePanel the sidepanel
     * @param sessions every session of the game
     * @param localPlayer the local player's User
     * @param opponent the opponents User
     */
    public MainThread(GameActivity host,SurfaceHolder surfaceHolder, GamePanel gamePanel, GameSidePanel gameSidePanel, HashMap<Integer,GameLogic> sessions, User localPlayer, User opponent){
        super();
        this.hostActivity = host;
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
        this.sessions = sessions;
        this.gameSidePanel = gameSidePanel;
        this.opponent = opponent;
        this.localPlayer = localPlayer;
    }

    /**
     * Begins running this thread if this thread is set to run
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run(){

        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    if(totalTime % 100000 == 0) {

                        //-1 is the player id of the AI
                        if(!sessions.keySet().contains(-1)) {
                            //TODO or just do it at the end of a turn.
                            sessions.get(Opponent.opponent.getPlayerID()).poll(localPlayer.getPlayerID(),opponent.getPlayerID());
                            if((totalTime/2)%100000 == 0) {
                                sessions.get(localPlayer.getPlayerID()).push(localPlayer.getPlayerID(), Opponent.opponent.getPlayerID());
                            }

                        }else{
                            GameLogic glAI = sessions.get(-1);
                            GameLogic player = sessions.get(localPlayer.getPlayerID());
                            if(totalTime % 1500000 ==0 ) {
                                ArrayList<Unit> units = SoloGameAI.currAI.spawnWave((int) totalTime / Time.NS_PER_S / Speed.ROUND_LENGTH, player);

                                player.startWave(units);
                                if(Math.random() > 0.7){
                                    SoloGameAI.currAI.addTurrets((int) totalTime / Time.NS_PER_S / Speed.ROUND_LENGTH, glAI );
                                }
                            }
                        }
                    }
                    for(GameLogic gl:sessions.values()){
                        gl.tick();
                        if (gl.hasLost()) {
                            running = false;
                            GameLogic local = sessions.get(localPlayer.getPlayerID());
                            if(gl!=sessions.get(localPlayer.getPlayerID())) {
                                hostActivity.onGameEnd(localPlayer, opponent,gl.getMap().getID(), local.getScore(), local.getUnitsKilled(),totalTime/1000000000);
                            }
                            else {

                                hostActivity.onGameEnd(opponent, localPlayer,gl.getMap().getID(), local.getScore(), local.getUnitsKilled(),totalTime/1000000000);                            }
                        }
                    }
                    //don't try to draw if the view isn't on screen
                    if (canvas != null) {
                        gamePanel.draw(canvas);
                    }
                    gameSidePanel.updateBaseHP();
                    gameSidePanel.updateScore();
                }

            }
            catch (Exception e){
                Log.d("MAIN_THREAD",Log.getStackTraceString(e));}
            finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){Log.d("MAIN_THREAD",Log.getStackTraceString(e));}

                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try{
                if (waitTime > 0){
                    this.sleep(waitTime);
                }

            }
            catch(Exception e){Log.d("MAIN_THREAD",Log.getStackTraceString(e));}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == MAX_FPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime =0;
                Log.d("GAME_MAIN_THREAD", "FPS: "+averageFPS);

            }
        }
    }

    /**
     * Stops this thread from running
     */
    public void pause(){
        setRunning(false);
    }

    /**
     * resumes running
     */
    @SuppressLint("NewApi")
    public void unPause(){
        if(!running) {
            setRunning(true);
            run();
        }
    }

}
