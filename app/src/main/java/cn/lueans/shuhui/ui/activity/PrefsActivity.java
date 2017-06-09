package cn.lueans.shuhui.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.ui.fragment.prefs.AboutFragment;
import cn.lueans.shuhui.ui.fragment.prefs.LicensesFragment;
import cn.lueans.shuhui.ui.fragment.prefs.SettingFragment;

public class PrefsActivity extends AppCompatActivity {

    public static final String EXTRA_FLAG= "EXTRA_FLAG";
    public static final int FLAG_SETTINGS = 0, FLAG_ABOUT = 1, FLAG_LICENSES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);
        initBar();
        initPrefs();
    }

    private void initPrefs() {
        Intent intent = getIntent();
        if (intent.getIntExtra(EXTRA_FLAG, 0) == FLAG_SETTINGS) {
            getSupportActionBar().setTitle("设置");
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container,new SettingFragment())
                    .commit();
        } else if (intent.getIntExtra(EXTRA_FLAG, 0) == FLAG_ABOUT){
            getSupportActionBar().setTitle("关于");
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container, new AboutFragment())
                    .commit();
        } else if (intent.getIntExtra(EXTRA_FLAG, 0) == FLAG_LICENSES) {
            getSupportActionBar().setTitle("开源许可");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container,new LicensesFragment())
                    .commit();
        } else {
            throw new RuntimeException("Please set flag when launching PrefsActivity.");
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void initBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

}
