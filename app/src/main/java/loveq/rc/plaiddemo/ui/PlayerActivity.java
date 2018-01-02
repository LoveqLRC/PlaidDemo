package loveq.rc.plaiddemo.ui;

import android.app.Activity;
import android.os.Bundle;

import loveq.rc.plaiddemo.R;

public class PlayerActivity extends Activity {
    public static final String EXTRA_PLAYER = "EXTRA_PLAYER";
    public static final String EXTRA_PLAYER_NAME = "EXTRA_PLAYER_NAME";
    public static final String EXTRA_PLAYER_ID = "EXTRA_PLAYER_ID";
    public static final String EXTRA_PLAYER_USERNAME = "EXTRA_PLAYER_USERNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
    }
}
