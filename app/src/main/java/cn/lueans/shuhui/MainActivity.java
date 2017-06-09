package cn.lueans.shuhui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

import cn.lueans.shuhui.constant.AppConstant;
import cn.lueans.shuhui.entity.User;
import cn.lueans.shuhui.ui.activity.LoginActivity;
import cn.lueans.shuhui.ui.activity.PersonActivity;
import cn.lueans.shuhui.ui.activity.PrefsActivity;
import cn.lueans.shuhui.ui.activity.SearchActivity;
import cn.lueans.shuhui.ui.fragment.ComicFragment;
import cn.lueans.shuhui.utils.ClassifyIdUtils;
import cn.lueans.shuhui.utils.ShareUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private ArrayList<Fragment> mFragment;
    private static int index = 0;
    private static int currentTabIndex = 0;

    private ImageView ivHeadIcon;
    private TextView tvHeadName;
    private TextView tvHeadEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(ClassifyIdUtils.comicTitle[index]);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initHead(navigationView);
        initFragment();

    }

    private void initHead( NavigationView navigationView) {
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ivHeadIcon = (ImageView) view.findViewById(R.id.iv_head_icon);
        tvHeadName = (TextView) view.findViewById(R.id.tv_head_name);
        tvHeadEmail = (TextView) view.findViewById(R.id.tv_head_email);
        upState();
        this.ivHeadIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                User instance = User.getInstance(MainActivity.this);
                if (instance.isLogin()){
                    //进入个人中心
                    Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                    startActivity(intent);

                }else{
                    // 登陆操作
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void initFragment() {
        mFragment =  new ArrayList<Fragment>();
        String[] comicTitle = ClassifyIdUtils.comicTitle;
        ComicFragment instance;

        for (String str : comicTitle) {
            instance = ComicFragment.getInstance(str);
            mFragment.add(instance);
        }
        //显示第一个fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_main, mFragment.get(0))
                .show(mFragment.get(0)).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, PrefsActivity.class);
            intent.putExtra(PrefsActivity.EXTRA_FLAG, PrefsActivity.FLAG_SETTINGS);
            startActivity(intent);
        }else if(id == R.id.action_search){
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            changeFragmentIndex(item, 0);
        } else if (id == R.id.nav_gallery) {
            changeFragmentIndex(item, 1);
        } else if (id == R.id.nav_slideshow) {
            changeFragmentIndex(item, 2);
        } else if (id == R.id.nav_manage) {
            changeFragmentIndex(item, 3);
        }else if(id == R.id.nav_setting){
            Intent intent = new Intent(MainActivity.this, PrefsActivity.class);
            intent.putExtra(PrefsActivity.EXTRA_FLAG, PrefsActivity.FLAG_SETTINGS);
            startActivity(intent);
            return true;
        }else if (id == R.id.nav_share) {
            ShareUtils.shareText(MainActivity.this, AppConstant.FROM_TYPE+"\n来自「鼠绘」");

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this, PrefsActivity.class);
            intent.putExtra(PrefsActivity.EXTRA_FLAG, PrefsActivity.FLAG_ABOUT);
            startActivity(intent);
            return true;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /**
     *
     * 切换Fragment的下标
     *
     */
    private void changeFragmentIndex(MenuItem item, int currentIndex) {
        index = currentIndex;
        switchFragment();
        item.setChecked(true);
        getSupportActionBar().setTitle(ClassifyIdUtils.comicTitle[currentIndex]);
    }

    private void switchFragment() {
        FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
        trx.hide(mFragment.get(currentTabIndex));

        if (!mFragment.get(index).isAdded()) {
            trx.add(R.id.fl_main, mFragment.get(index));
        }
        trx.show(mFragment.get(index)).commit();
        currentTabIndex = index;
    }

    @Override
    protected void onStart() {
        super.onStart();
        upState();
    }

    private void upState(){
        User instance = User.getInstance(MainActivity.this);
        tvHeadName.setText("鼠绘");
        String imageUrl = instance.getImageUrl();
        if (!TextUtils.isEmpty(imageUrl)) {

            Glide.with(MainActivity.this)
                    .load("http://www.ishuhui.net"+imageUrl)
                    .asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)  //占位符
                    .error(R.mipmap.ic_launcher).into(new BitmapImageViewTarget(this.ivHeadIcon) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(MainActivity.this.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    ivHeadIcon.setImageDrawable(circularBitmapDrawable);
                }
            });
        }

        if (instance.isLogin()){
            tvHeadEmail.setText(instance.getEmail());
            tvHeadName.setText(TextUtils.isEmpty(instance.getNickName())?"鼠绘":instance.getNickName());
        }else{
            tvHeadEmail.setText("登陆");
        }
    }

}
