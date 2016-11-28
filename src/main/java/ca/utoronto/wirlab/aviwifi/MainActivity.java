package ca.utoronto.wirlab.aviwifi;


import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;
import android.content.Intent;






public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Localize me button
        Button localize_me_button = (Button) findViewById(R.id.localize_me_id);
        final TextView location_text = (TextView) findViewById(R.id.location_id);
        localize_button localize_button_object = new localize_button();
        localize_button_object.click(localize_me_button, location_text);
        localize_button_object.long_click(localize_me_button, location_text);

////////////////////////////////////////////////////////////////////////////////////////////////////
        //Google map button

        //Extracted from Nigel's code

        Button google_map_button = (Button) findViewById(R.id.google_map_button_id);
        google_map_button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent google_map_intent = new Intent(MainActivity.this,
                        MapsActivity.class);
                startActivity(google_map_intent);
            }
        });

                   // Intent map_activity_intent = new Intent(this, MapsActivity.class);



////////////////////////////////////////////////////////////////////////////////////////////////////


    }




}