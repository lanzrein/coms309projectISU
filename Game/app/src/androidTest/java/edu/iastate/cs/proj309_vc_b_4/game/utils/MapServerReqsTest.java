package edu.iastate.cs.proj309_vc_b_4.game.utils;


import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.Map;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType;
import edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.Player;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.MapServerReqs;

import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType.GRASS;
import static edu.iastate.cs.proj_309_vc_b_4.game.Gameplay.GameMap.TerrainType.PATH;

/**
 * Created by johan on 17.10.2017.
 */
@RunWith(AndroidJUnit4.class)
public class MapServerReqsTest {

    //just a dummy test to make sure we have all working for test.
    @Test
    public void dummyTest(){
        Assert.assertTrue(true);
        Assert.assertEquals(2,1+1);
    }
    //test the method to grab metadata by id.
    @Test
    public void mapReqByID(){
        for(int i = 1; i < 3 ; i++){
            Map map = MapServerReqs.fetchMetaDataByID1(i);
            checkIthMap(i, map);

        }
    }

    private void checkIthMap(int i, Map map) {
        System.out.println(map.toString());
        Assert.assertEquals(i, map.getID());
        Assert.assertEquals(i,map.getAuthor());
        switch (i) {
            case 1 :
                Assert.assertEquals("Death valley",map.getName());
                Assert.assertEquals("This map is very deadly",map.getMapDescription());
                break;
            case 2:
                Assert.assertEquals("Canyon of Dwarf",map.getName());
                Assert.assertEquals("Dwarfs like it there",map.getMapDescription());
                break;
            case 3:
                Assert.assertEquals("Simple road",map.getName());
                Assert.assertEquals("This map is for noobs",map.getMapDescription());
                break;
            default :
                break;

        }
    }



    @Test
    public void getAllMaps1(){
        List<Map> xs = MapServerReqs.getAllMapMeta1();
        int i = 1;
        for(Map m : xs){

            checkIthMap(i,m);
            i++;
        }


    }


    @Test
    public void getDataForMap1(){
        List<Map> xs = MapServerReqs.getAllMapMeta1();

        for(Map m : xs ){
            Assert.assertTrue(MapServerReqs.getMapData(m));
            Log.d("HEY",m.getData().toString());
        }



    }

    //tests for map creation...
    @Test
    public void createMap(){
        TerrainType[][] dummy = new TerrainType[Map.HEIGHT][Map.WIDTH];
        for(int i = 0; i < Map.HEIGHT; i++){
            for(int j = 0 ; j < Map.WIDTH; j++){
                int choice = (int)(Math.random()*100) % 10;
                if(choice > 2) {
                    dummy[i][j] = GRASS;
                }else{
                    dummy[i][j] = PATH;

                }

            }
        }



        Map dummyMap = new Map("Hallo", "This rocks", 0, 9);

        Player p = new Player(9,"hallo", "Hey man");

        Map m = MapServerReqs.createNewMap(dummyMap, dummy, p);

        Assert.assertEquals(m.getAuthor(), p.getId());
        TerrainType[][] res = m.getData();

        for(int i = 0; i < Map.HEIGHT; i++){
            for(int j = 0 ; j < Map.WIDTH; j++){
                TerrainType x = res[i][j];
                Assert.assertEquals(dummy[i][j],x);

            }
        }
        Assert.assertEquals(m.getMapDescription() ,dummyMap.getMapDescription());
        Assert.assertEquals(m.getName() ,dummyMap.getName());




    }


}
