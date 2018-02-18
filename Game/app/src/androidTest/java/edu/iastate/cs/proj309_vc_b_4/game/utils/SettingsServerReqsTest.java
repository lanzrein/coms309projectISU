package edu.iastate.cs.proj309_vc_b_4.game.utils;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Set;

import edu.iastate.cs.proj_309_vc_b_4.game.utils.SettingsServerReqs;

/**
 * Created by johan on 02.11.2017.
 */

@RunWith(AndroidJUnit4.class)

public final class SettingsServerReqsTest {

    private String username = "test";
    private String password = "1234";
    @Test
    public void editMessage(){
        Assert.assertTrue(SettingsServerReqs.changeMessageTo("Goodbye world",username,password));
        //check manually in db if change.
    }


    @Test
    public void sendEmail(){
        Assert.assertTrue(SettingsServerReqs.sendEmailToTeam("Hey Johan", username, 0));
        //check my email.
    }

    @Test
    public void deleteAccount(){
        Assert.assertTrue(SettingsServerReqs.deleteAccount(username,password));
        //check db
    }
    @Test
    public void modifyPicture(){
        Assert.assertTrue(SettingsServerReqs.changeImageTo(5,username,password));
        //check db
    }
}
