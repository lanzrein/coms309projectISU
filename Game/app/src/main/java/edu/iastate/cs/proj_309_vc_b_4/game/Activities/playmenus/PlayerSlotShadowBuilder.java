package edu.iastate.cs.proj_309_vc_b_4.game.Activities.playmenus;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by Joseph on 10/1/2017.
 *
 * Creates shadows for LobbySlotViews when they are dragged.
 */
public class PlayerSlotShadowBuilder extends View.DragShadowBuilder{

    //the shadow that will be drawn
    private static Drawable shadow;

    public PlayerSlotShadowBuilder(View view) {
        //stores the View parameter passed in
        super(view);

        //creates a draggable image that will fill the canvas provided by the system
        shadow = new ColorDrawable(Color.LTGRAY);
    }


    @Override
    public void onDrawShadow(Canvas canvas) {
        //draw the shadow
        shadow.draw(canvas);
    }

    @Override
    public void onProvideShadowMetrics(Point outShadowSize, Point outShadowTouchPoint) {
        /*
        // Defines local variables
        int width, height;

        // Sets the width of the shadow to half the width of the original View
        width = getView().getWidth() / 2;

        // Sets the height of the shadow to half the height of the original View
        height = getView().getHeight() / 2;

        // The drag shadow is a ColorDrawable. This sets its dimensions to be the same as the
        // Canvas that the system will provide. As a result, the drag shadow will fill the
        // Canvas.
        shadow.setBounds(0, 0, width, height);

        // Sets the size parameter's width and height values. These get back to the system
        // through the size parameter.
        outShadowSize.set(width, height);

        // Sets the touch point's position to be in the middle of the drag shadow
        outShadowTouchPoint.set(width / 2, height / 2);
        */
    }
}
