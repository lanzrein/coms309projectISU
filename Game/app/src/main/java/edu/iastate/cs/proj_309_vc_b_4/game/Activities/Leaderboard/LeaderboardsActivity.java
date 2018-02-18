package edu.iastate.cs.proj_309_vc_b_4.game.Activities.Leaderboard;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.User.User;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.FriendServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.LeaderboardTempStorage;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

/**
 * This activity handles the global and local tab that allows players to view current
 * tandings of players on Most game played, fastest time, and win ratio.
 * Created by JeremyC on 11/5/2017.
 */

public class LeaderboardsActivity extends AppCompatActivity
{
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    LeaderboardTempStorage temp = new LeaderboardTempStorage();
    SharedPrefManager spm = SharedPrefManager.getInstance(this);
    String username = spm.getLocalPlayer().getUsername();
    String password = spm.getLocalPassword();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_leaderboard, menu);
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * a menu selection where it allows player to decide which criteria
     * they wished to see.
     * @param item criteria they player wishes to see.
     * @return true
     */
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ( item.getItemId())
        {
            case R.id.action_search:
                final Dialog d = addFriendPopUp();
                d.show();

                break;

            case R.id.win_ratio:
                String[] inputStr1 = {"Pos.","Player ID", "Name", "Wins Ratio"};
                temp.setGlobal_url("/game/leaderboardNetwork/leaderboard_stats.php");
                temp.setLocal_url("/game/leaderboardNetwork/LocalLeader_winRatio.php"+"?username="+username+"&password="+password);
                temp.setHeader(inputStr1);
                temp.setStatus(0);
                finish();
                startActivity(getIntent());
                break;

            case R.id.fastest_time:
                String[] inputStr2 = {"Pos.","Player ID", "Name", "Fastest Time"};
                temp.setGlobal_url("/game/leaderboardNetwork/leaderboard_fastestTime.php");
                temp.setLocal_url("/game/leaderboardNetwork/LocalLeader_fastestTime.php"+"?username="+username+"&password="+password);
                temp.setHeader(inputStr2);
                temp.setStatus(1);
                finish();
                startActivity(getIntent());
                break;

            case R.id.most_games_play:
                String[] inputStr3 = {"Pos.","Player ID", "Name", "Most Game Played"};
                temp.setGlobal_url("/game/leaderboardNetwork/leaderboard_mostPlayed.php");
                temp.setLocal_url("/game/leaderboardNetwork/LocalLeader_mostPlayed.php"+"?username="+username+"&password="+password);
                temp.setHeader(inputStr3);
                temp.setStatus(2);
                finish();
                startActivity(getIntent());
                break;
        }

        return true;
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position)
            {
                case 0:
                    Global global_tab = new Global();
                    return global_tab;
                case 1:
                    Local local_tab = new Local();
                    return local_tab;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Global";
                case 1:
                    return "Local";
            }
            return null;
        }
    }

    /**
     * This creates a pop up to offer the possibility to add a friend.
     * It only creates it and then return it the user has to then call show() on the return dialog to show it.
     *Created by Johan.
     * @return the dialog created
     */
    private Dialog addFriendPopUp() {
        final Dialog dialog = new Dialog(this);
        final Context ctx = this;
        dialog.setContentView(R.layout.addfriend_pop_up);
        //our fields for username and id
        final EditText editUsername = (EditText) dialog.findViewById(R.id.editUsername);
        final EditText editID = (EditText) dialog.findViewById(R.id.editID);

        Button addFriend = (Button) dialog.findViewById(R.id.confirm);

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editUsername.getText().toString();
                int id = -1;
                try {
                    id = Integer.parseInt(editID.getText().toString());
                }catch(NumberFormatException nfe){
                    Toast.makeText(ctx,"Enter a valid id and username",Toast.LENGTH_LONG).show();
                    return;
                }
                if(id < 0 || username.isEmpty()){
                    Toast.makeText(ctx,"Enter a valid id and username",Toast.LENGTH_LONG).show();
                }

                if(FriendServerReqs.existsPlayer(username,id)){
                    User.addFriend(id, ctx);
                    dialog.dismiss();
                    //refresh the activity.
                    finish();
                    startActivity(getIntent());
                }else{
                    Toast.makeText(ctx,"Player does not exist !", Toast.LENGTH_LONG).show();
                }
                return;
            }
        });

        Button close = (Button) dialog.findViewById(R.id.cancel);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        return dialog;
    }

}
