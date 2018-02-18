package edu.iastate.cs.proj309_vc_b_4.game.utils;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.iastate.cs.proj_309_vc_b_4.game.User.User;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.FriendServerReqs;

/**
 * Created by johan on 26.10.2017.
 */
@RunWith(AndroidJUnit4.class)
public class FriendsServerReqstest {
    //dummy player username : test password : 1234 id : 7
    String username = "test";
    String password = "1234";
    @Test
    public void dummy(){
        Assert.assertEquals(1,1);
    }
    @Test
    public void getFriends(){
        List<User> friends = FriendServerReqs.getFriends(username,password);
        for(User u : friends){
            Log.d("Friend ",u.toString());
        }

    }

    @Test
    public void addFriend(){
//        int id = (int) Math.random() % 30;
        //try to add number id 4
        Assert.assertTrue(FriendServerReqs.addFriend(4,username,password));

    }

    @Test
    public void removeFriend(){
//        addFriend();
        //need to add the friend with id 4 before. doing this test.
        Assert.assertTrue(FriendServerReqs.removeFriends(4,username,password));

    }


    @Test
    public void existence(){
        Assert.assertTrue(FriendServerReqs.existsPlayer("Alex", 4));
        Assert.assertFalse(FriendServerReqs.existsPlayer("Dummy", 99));
    }


    @Test
    public void getUserByID(){

    }
}
