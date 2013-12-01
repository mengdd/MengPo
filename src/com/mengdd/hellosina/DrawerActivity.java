package com.mengdd.hellosina;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DrawerActivity extends Activity {

    private Button mOpenButton = null;
    private DrawerLayout mDrawerLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_layout);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mOpenButton = (Button) findViewById(R.id.open_button);
        mOpenButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }
}
