package gg.rich.me.fuzo;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class SplashActivity extends Activity  {

    Context context;
    private static int WELCOME_TIMEOUT = 800;
    private TextView tv, tv1, tv2, tv3;
    private ImageView iv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SplashActivity.this;
        setContentView(R.layout.activity_splash);
        ///////////////////////////////////
        tv = (TextView) findViewById(R.id.tv);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        iv = (ImageView)findViewById(R.id.iv);
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv.startAnimation(myAnim);
        tv1.startAnimation(myAnim);
        tv2.startAnimation(myAnim);
        tv3.startAnimation(myAnim);
        iv.startAnimation(myAnim);

        ///////////////////////////////////

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goToNextActivity(R.anim.fade_in, R.anim.fade_out);
            }
        },WELCOME_TIMEOUT );

    }


    private void goToNextActivity(int animationIn, int animationOut) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(animationIn, animationOut);
    }

}
