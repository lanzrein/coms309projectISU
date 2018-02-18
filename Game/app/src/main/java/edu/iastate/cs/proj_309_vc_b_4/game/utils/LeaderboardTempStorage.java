package edu.iastate.cs.proj_309_vc_b_4.game.utils;

import android.app.Application;

/**
 * This class acts as an temporary storage for different data that is going to be
 * processed in the leaderboard activity.
 * Created by JeremyC on 11/5/2017.
 */

public class LeaderboardTempStorage extends Application
{
    SharedPrefManager spm = SharedPrefManager.getInstance(this);
    String username = spm.getLocalPlayer().getUsername();
    String password = spm.getLocalPassword();

    private String local_url = "/game/leaderboardNetwork/LocalLeader_winRatio.php"+"?username="+username+"&password="+password;
    private String global_url = "/game/leaderboardNetwork/leaderboard_stats.php";

    private static int status = 0; //status for the different criteria of the leaderboard where 1 = Most wins, 2 = Fastest time, 3 = Most games win.
    private static String[] leaderboardHeader = {"Pos.","Player ID", "Name", "Win Ratio"};

    /**
     * Set the local url/link that is going to stored
     * @param link local url/link that is needed to be stored.
     */
    public void setLocal_url(String link)
    {
        this.local_url = link;
    }

    /**
     * Retrieve stored local url/link
     * @return stored local url/link.
     */
    public String getLocal_url()
    {
        return local_url;
    }

    /**
     * Set the global url/link that is going to stored
     * @param link  url/link that is needed to be stored.
     */
    public void setGlobal_url(String link)
    {
        this.global_url = link;
    }

    /**
     * Retrieve stored global url/link.
     * @return  Stored global url/link.
     */
    public String getGlocal_url()
    {
        return global_url;
    }

    /**
     * Stored the current status of an activity
     * @param s status number
     */
    public void setStatus(int s)
    {
        this.status = s;
    }

    /**
     * Retrieve the current status of an activity.
     * @return status number
     */
    public int getStatus()
    {
        return  status;
    }

    /**
     * Set the header for the fragments .
     * @param h header string array.
     */
    public void setHeader(String[] h)
    {
        this.leaderboardHeader = h;
    }

    /**
     * Retreive the header for the fragments.
     * @return header string array.
     */
    public String[] getHeader()
    {
        return leaderboardHeader;
    }
}
