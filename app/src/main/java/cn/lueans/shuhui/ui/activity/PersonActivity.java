package cn.lueans.shuhui.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import java.util.ArrayList;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.entity.User;
import cn.lueans.shuhui.ui.adapter.BaseViewPagerAdapter;
import cn.lueans.shuhui.ui.fragment.MyDownloadFragment;
import cn.lueans.shuhui.ui.fragment.SubscribeListFragment;

/**
 * Created by 24277 on 2017/5/24.
 *
 */

public class PersonActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager vpContent;
    private ImageView ivAvator;

    private ArrayList<Fragment> fragments;
    private String title[] = {
            "我的订阅",
            "我的下载"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        initHead();
        initFragment();
        initViewPager();
        initBar();
    }

    private void initHead() {
        /*ivAvator = (ImageView) findViewById(R.id.iv_person_avator);
        User instance = User.getInstance(PersonActivity.this);
        String imageUrl = instance.getImageUrl();
        Glide.with(PersonActivity.this)
                .load("http://www.ishuhui.net"+imageUrl)
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)  //占位符
                .error(R.mipmap.ic_launcher).into(new BitmapImageViewTarget(this.ivAvator) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(PersonActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                ivAvator.setImageDrawable(circularBitmapDrawable);
            }
        });*/

    }

    /**
     * 初始化fragment
     */
    private void initFragment(){
        fragments = new ArrayList<>();
        fragments.add(new SubscribeListFragment());
        fragments.add(new MyDownloadFragment());
    }

    private void initViewPager() {
        vpContent = (ViewPager) findViewById(R.id.vp_content);
        BaseViewPagerAdapter baseViewPagerAdapter =
                new BaseViewPagerAdapter(getSupportFragmentManager(), fragments, title);
        vpContent.setOffscreenPageLimit(fragments.size());
        vpContent.setAdapter(baseViewPagerAdapter);
    }

    private void initBar() {
        //设置ActionBar
        User instance = User.getInstance(PersonActivity.this);
        String email = instance.getEmail();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle(email);
        //设置tabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        //设置标签的字体
        tabLayout.setupWithViewPager(vpContent);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId){
            case android.R.id.home:
                finish();
                break;
        }
        return true;

    }
}
