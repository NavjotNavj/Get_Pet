package com.example.myapplication.Moldels;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

public class FullScreen {

    private Activity activity;

    public FullScreen(Activity activity){
        this.activity = activity;
    }

    public void changeStatusBarColor() {

        if (Build.VERSION.SDK_INT >= 21) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
    }

}
