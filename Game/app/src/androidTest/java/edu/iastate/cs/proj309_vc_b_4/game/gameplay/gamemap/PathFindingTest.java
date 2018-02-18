package edu.iastate.cs.proj309_vc_b_4.game.gameplay.gamemap;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.junit.Test;

import java.util.ArrayList;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.AStar;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Graph;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.gameobjects.Position;

import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType.BASE;
import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType.GRASS;
import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType.PATH;
import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType.SPAWN;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Joe on 10/22/2017.
 */

public class PathFindingTest {

    private TerrainType[][] testMap1 = {
        {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
        {SPAWN,PATH,PATH,PATH,PATH,GRASS,GRASS,GRASS,GRASS,GRASS},
        {PATH,GRASS,GRASS,PATH,PATH,GRASS,GRASS,GRASS,GRASS,GRASS},
        {PATH,GRASS,GRASS,PATH,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
        {PATH,PATH,GRASS,PATH,PATH,PATH,PATH,PATH,PATH,BASE},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS}};
    private final String pathSolution1 = "{0.0,1.0}{1.0,1.0}{2.0,1.0}{3.0,1.0}{3.0,2.0}{3.0,3.0}{3.0,4.0}{4.0,4.0}{5.0,4.0}{6.0,4.0}{7.0,4.0}{8.0,4.0}{9.0,4.0}";

    private TerrainType[][] testMap2 = {
            {GRASS,GRASS,SPAWN,GRASS,GRASS,GRASS,GRASS,PATH,PATH,PATH},
            {PATH,PATH,PATH,PATH,PATH,GRASS,GRASS,PATH,GRASS,PATH},
            {PATH,GRASS,GRASS,PATH,PATH,GRASS,GRASS,PATH,GRASS,PATH},
            {PATH,GRASS,GRASS,PATH,GRASS,GRASS,GRASS,PATH,GRASS,PATH},
            {PATH,PATH,PATH,PATH,PATH,PATH,PATH,PATH,GRASS,PATH},
            {GRASS,GRASS,GRASS,PATH,GRASS,GRASS,GRASS,GRASS,GRASS,PATH},
            {GRASS,GRASS,GRASS,PATH,PATH,GRASS,GRASS,GRASS,GRASS,PATH},
            {GRASS,GRASS,GRASS,GRASS,PATH,PATH,PATH,PATH,PATH,PATH},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,PATH},
            {GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,GRASS,BASE}};
    private final String pathSolution2 = "{2.0,0.0}{2.0,1.0}{3.0,1.0}{3.0,2.0}{3.0,3.0}{3.0,4.0}{3.0,5.0}{3.0,6.0}{4.0,6.0}{4.0,7.0}{5.0,7.0}{6.0,7.0}{7.0,7.0}{8.0,7.0}{9.0,7.0}{9.0,8.0}{9.0,9.0}";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Test
    public void findPath1(){
        Map map = new Map("test1", "this map is for testing", 0, 0);
        map.setTerrainMapManually(testMap1);
        String calculatedPath = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Graph g = map.getPathGraph();
            AStar astar = new AStar(g,map.getSpawns().get(0).getPosition(),map.getBase().getPosition());
            ArrayList<Position> path = astar.findPath();
            for(int i =0;i<path.size();i++){
                calculatedPath += path.get(i).toString();
            }
        }
        assertEquals(calculatedPath, pathSolution1);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Test
    public void findPath2(){
        Map map = new Map("test1", "this map is for testing", 0, 0);
        map.setTerrainMapManually(testMap2);
        String calculatedPath = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Graph g = map.getPathGraph();
            AStar astar = new AStar(g,map.getSpawns().get(0).getPosition(),map.getBase().getPosition());
            ArrayList<Position> path = astar.findPath();
            for(int i =0;i<path.size();i++){
                calculatedPath += path.get(i).toString();
            }
        }
        assertEquals(calculatedPath, pathSolution2);
    }

}
