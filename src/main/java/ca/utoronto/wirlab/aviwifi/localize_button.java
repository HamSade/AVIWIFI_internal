package ca.utoronto.wirlab.aviwifi;

import android.widget.TextView;
import android.widget.Button;
import android.view.View;

/**
 * Created by hamed on 11/21/16.
 */

public class localize_button {

    // On normal click
    protected void click(Button localize_me_button, final TextView location_text) {

        localize_me_button.setOnClickListener(
                new Button.OnClickListener() {

                    public void onClick(View v) {
                        location_text.setText("You are @");

                    }

                }
        );

    }

    //On long click!
    protected void long_click(Button localize_me_button, final TextView location_text) {
        localize_me_button.setOnLongClickListener(
                new Button.OnLongClickListener() {
                    public boolean onLongClick(View v) {
//                        location_text.setText("Ahhh! Long click!");
//                        Localizer_engine LE=new Localizer_engine();
//                        Location=LE.localize(); //and then print location instead of start

                        location_text.setText("Start");
                        return false;
                    }
                }
        );

    }

}
