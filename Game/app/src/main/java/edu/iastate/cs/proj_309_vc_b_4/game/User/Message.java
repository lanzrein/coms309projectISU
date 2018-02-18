package edu.iastate.cs.proj_309_vc_b_4.game.User;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import edu.iastate.cs.proj_309_vc_b_4.game.R;
import edu.iastate.cs.proj_309_vc_b_4.game.utils.MessageServerReqs;

/**
 * This class represents a message.
 * Its main use is to facilitate client/server communication with JSON.
 * Created by johan on 03.11.2017.
 */

public final class Message {


    private final int messageID;
    private final int fromID;
    private final String fromUsername;
    private final String message;

    /**
     * Create a new message.
     * @param messageID message id
     * @param senderID id of sender.
     * @param senderUsername username of sender
     * @param msg content of the message.
     */
    public Message(int messageID, int senderID, String senderUsername, String msg){
        this.messageID = messageID;
        this.fromID = senderID;
        this.fromUsername = senderUsername;
        this.message = msg;
    }

    /**
     * creates a dialog of the message and returns it.
     * @param context current context
     * @param username username of current user
     * @param password pw of current user.
     * @param receiverID the id of the author of the message.
     * @return the dialog created
     */
    public Dialog displayMessage(Context context, String username, String password, int receiverID){


        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.message_pop);
        //we have 3 button and need to set the text .
        TextView tv = (TextView)dialog.findViewById(R.id.messageContent);
        tv.setText("From : "+fromUsername+"\n Content : \n "+message);
        //delete button
        Button delete = (Button)dialog.findViewById(R.id.deleteMessage);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send a request to server and dismiss
                boolean delete = MessageServerReqs.deleteMessage(username, password, receiverID, messageID);
                if(delete){
                    Toast.makeText(context,"Successfully deleted!", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(context,"Error try again. ", Toast.LENGTH_SHORT).show();

                }

                dialog.dismiss();
            }
        });


        //reply button
        Button reply = (Button) dialog.findViewById(R.id.replyToMessage);
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we need to open up a new dialog to reply.
                //the same one as the one from user.
                Dialog d = User.sendMessageDialog(context,fromID);
                d.show();
            }
        });

        //closing button.
        Button close = (Button) dialog.findViewById(R.id.closeMessage);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //taken from https://stackoverflow.com/questions/37514116/how-to-specify-correct-dialog-size-in-xml-layout-file-for-android-dialog

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    /**
     *
     * @return get the username of the sender.
     */
    public String getFromUsername() {
        return fromUsername;
    }
}
