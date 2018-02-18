package edu.iastate.cs.proj_309_vc_b_4.game.User;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.iastate.cs.proj_309_vc_b_4.game.Activities.ProfileImageAdapter;
import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.FriendServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.MessageServerReqs;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.PlayerReportServReq;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.SharedPrefManager;

/**
 * This class describes a user in a broader range.
 * It gives all stats necessary to display it.
 *
 * Created by johan on 22.10.2017.
 */

public class User {
    //variables...
    private final String username;
    private final String message;
    private final int PlayerID;
    private final int noWins;
    private final int noLoss;
    private final int imageID;

    /**
     * Constructor, straigth forward.
     * @param username
     * @param message
     * @param noWins
     * @param noLoss
     * @param imageID
     * @param playerID
     */
    public User(String username, String message, int noWins, int noLoss, int imageID,int playerID){
        this.username = username;
        this.message = message;
        this.noWins = noWins;
        this.noLoss = noLoss;
        this.imageID = imageID;
        this.PlayerID = playerID;
    }

    /**
     * This creates and returns a dialog ( POP UP ) to show the user.
     * There is an option to display it differently if he is a friend or not ( Parameter isFriend )
     * It will have all the needed things to be dismissed etc.
     * TODO add report option
     * @param context the context of our application
     * @param isFriend true if the user is a friend of this user
     * @return the dialog created ( not shown yet )
     */
        public Dialog createDialog(Context context, boolean isFriend){
            final Context ctx = context;

            final Dialog dialog = new Dialog(context);

            final Boolean res;
            if(isFriend) {
                dialog.setContentView(R.layout.friend_pop);
                Button rmFriend = (Button) dialog.findViewById(R.id.removefriend);
                rmFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean rm = removeFriends(PlayerID,ctx);
                        dialog.dismiss();
                        return;
                    }
                });
                //here manage the message button.
                Button sendMessage = (Button) dialog.findViewById(R.id.sendMessageTo);
                sendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog d = sendMessageDialog(context, PlayerID);
                        d.show();
                    }
                });


            }else{
                dialog.setContentView(R.layout.user_pop);
                Button addFriend = (Button) dialog.findViewById(R.id.addFriend);
                addFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        addFriend(PlayerID,ctx);
                        dialog.dismiss();
                        return;
                    }
                });
            }
            dialog.setTitle("User");
            TextView text = (TextView) dialog.findViewById(R.id.username);
            text.setText("Username : "+username+" ID : " +PlayerID);
            text = (TextView) dialog.findViewById(R.id.noWins);
            text.setText("Number of wins : \n "+noWins);
            text = (TextView) dialog.findViewById(R.id.noLoss);
            text.setText("Number of loss : \n "+noLoss);
            text = (TextView) dialog.findViewById(R.id.personalMessage);
            text.setText("Message : \n \""+message+"\"");
            //....etc....
            ImageView img = (ImageView) dialog.findViewById(R.id.profilePicture);
            img.setImageResource(chooseImage());

            Button closePop = (Button) dialog.findViewById(R.id.closeFriend);
            closePop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });



            Button report = (Button) dialog.findViewById(R.id.reportUser);
            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean rep = PlayerReportServReq.reportPlayer(PlayerID);
                    Toast.makeText(ctx,"Player Reported!",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    return;
                }
            });




            return dialog;

        }

    /**
     * This creates a dialog that will allow to send messages to the user (PlayerID) from the user of the app ( the one stored in sharedPreferences)
     * @param PlayerID the id of the player to who it is going to be sent.
     * @param context the current context.
     */
    public static Dialog sendMessageDialog(Context context, int PlayerID) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.send_message_popup);
        EditText edit = (EditText) dialog.findViewById(R.id.messageContent);
        //Configure confirm and cancel
        Button confirm = (Button)dialog.findViewById(R.id.sendConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send the message.
                SharedPrefManager spm = SharedPrefManager.getInstance(context);
                int fromID = spm.getLocalPlayer().getId();
                String mail = edit.getText().toString();
                boolean send = MessageServerReqs.sendMessageTo(fromID,PlayerID,mail);
                if(send){
                    Toast.makeText(context, "Sent successfully !", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Sorry there was an error !", Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.sendCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);


        return dialog;




    }

    private int chooseImage() {
        return ProfileImageAdapter.mThumbsIds[imageID];
    }

    /**
     * removes a friend.
     *
     * @param playerID id of friend to be removed
     * @param context the current context
     * @return true iff sucess
     */
    private boolean removeFriends(int playerID, Context context) {
        SharedPrefManager sp = SharedPrefManager.getInstance(context);
        String pwd = sp.getLocalPassword();
        String username = sp.getLocalPlayer().getUsername();
//        String username = "test";
//        String pwd = "1234";
        //we have the username and pwd. all is ready for a nice request.
        if(FriendServerReqs.removeFriends(playerID,username,pwd)){
            Toast.makeText(context,"Successfully removed! ", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(context,"Error please try again later. ", Toast.LENGTH_SHORT).show();
            return false;

        }
    }

    /**
     * Add a friend. Careful it is not safe to use it as is.
     * You need to check if the user exists before hand otherwise you might end up with unwanted result.
     *
     * @param id id of the user oyu want to add
     * @param context current context
     */
    public static void addFriend(int id,Context context) {
        SharedPrefManager sp = SharedPrefManager.getInstance(context);
        String pwd = sp.getLocalPassword();
        String username = sp.getLocalPlayer().getUsername();
//        String username = "test";
//        String pwd = "1234";
        //we have the username and pwd. all is ready for a nice request.
        if(FriendServerReqs.addFriend(id,username,pwd)){
            Toast.makeText(context,"Successfully added ! ", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Error please try again later. ", Toast.LENGTH_SHORT).show();

        }





    }

    /**
     * pretty print it for debugging purpose
     * @return pretty print
     */
    @Override
    public String toString(){
        return "UserID: "+PlayerID+","+username+", "+message;
    }

    /**
     *
     * @return this username.
     */
    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public int getNoLoss() {
        return noLoss;
    }

    public int getNoWins() {
        return noWins;
    }

    public int getImageID() {
        return imageID;
    }

    public int getPlayerID() {
        return PlayerID;
    }
}
