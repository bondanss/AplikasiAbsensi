package pnj.uas.bondanss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;



public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        if (Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPutih));
        }

        //------------------------------

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (sharedPreferences.getBoolean("isLogin", false)) {
//                  getLocation();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLogin", true);
                    editor.putString("email","bondansatrio99@gmail.com");
                    editor.putString("nim","4817070443");
                    editor.putString("nama","Bondan Satrio");
                    editor.putString("kelas","TI-6C");
                    editor.commit();
                } else {
//                  getLocation();
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    Splash.this.startActivity(intent);
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);
    }

}
