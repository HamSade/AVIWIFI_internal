package logger.logit;

import android.support.v7.app.ActionBarActivity;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;


public class Activity0 extends ActionBarActivity implements AnimationListener {

	private ImageView imageView1;
	private TextView textView1;
	private Animation mAnimation1;
	private Animation mAnimation2;
	private Animation mAnimation3;
	private Animation mAnimation4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity0);

		imageView1= (ImageView) findViewById(R.id.activity0_logit_logo1);		
		textView1 = (TextView) findViewById(R.id.activity0_textv_info1);
		
        mAnimation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        mAnimation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        mAnimation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_2);
        mAnimation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein_2);
 
        mAnimation1.setAnimationListener(this);
        mAnimation2.setAnimationListener(this);
        mAnimation3.setAnimationListener(this);
        mAnimation4.setAnimationListener(this);
 
        imageView1.startAnimation(mAnimation2); 
        textView1.startAnimation(mAnimation1);

	}
		
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		if (animation == mAnimation1){
			imageView1.startAnimation(mAnimation2);
		}
		if (animation == mAnimation2){
			imageView1.startAnimation(mAnimation3);
		}
		if (animation == mAnimation3){
			imageView1.startAnimation(mAnimation4);
		}
		if (animation == mAnimation4) {
			Intent toActivity1 = new Intent(getApplicationContext(),Activity1.class);
			startActivity(toActivity1);
			this.finish();			
		}		
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub
		
	}

	

	
}
