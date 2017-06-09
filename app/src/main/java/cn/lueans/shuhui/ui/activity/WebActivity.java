package cn.lueans.shuhui.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.io.File;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.db.ChapterDao;

/**
 * Created by 24277 on 2017/5/23.
 */

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "WebActivity";

    public static final String TITLE_KEY = "title";
    public static final String ID_KEY = "id";
    public static final String SORT_KEY = "sort";
    public static final String IMAGE_KEY = "image";


    private String mTitle;
    private String mUrl;
    private String mId;
    private int mSort;
    private String mImage;

    private WebView wvView;
    private FrameLayout fl;


    public static Intent startIntent(Context context,String title,String id,int sort,String image){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(TITLE_KEY,title);
        intent.putExtra(ID_KEY,id);
        intent.putExtra(SORT_KEY,sort);
        intent.putExtra(IMAGE_KEY,image);
        context.startActivity(intent);
        return intent;
    }
    private void parseIntent(){
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(TITLE_KEY);
        mId = intent.getStringExtra(ID_KEY);
        mSort = intent.getIntExtra(SORT_KEY,0);
        mImage = intent.getStringExtra(IMAGE_KEY);
        mUrl = "http://www.ishuhui.net/ComicBooks/ReadComicBooksToIsoV1/"+mId+".html";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        parseIntent();
        initBar();
        iniFL();
        initWebView();
        Log.i(TAG, "onCreate: "+mTitle);
        Log.i(TAG, "onCreate: "+mId);
        Log.i(TAG, "onCreate: "+mUrl);

    }

    private void iniFL() {
        fl = (FrameLayout) findViewById(R.id.fl_webview_load);
        fl.setVisibility(View.VISIBLE);
    }

    private void initBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mTitle);
        //设置返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    private void initWebView() {


        wvView = (WebView) findViewById(R.id.wv_web);



        wvView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    fl.setVisibility(View.GONE);
                    mUrl = wvView.getUrl();
                } else {
                    fl.setVisibility(View.VISIBLE);

                }
            }
        });

        wvView.setWebViewClient(new WebViewClient());
        //添加手势操作
        wvView.getSettings().setJavaScriptEnabled(true);
        wvView.getSettings().setUseWideViewPort(true);
        wvView.getSettings().setBuiltInZoomControls(true);//显示放大缩小 controler
        wvView.getSettings().setSupportZoom(true);
        //隐藏错放条
        wvView.getSettings().setDisplayZoomControls(false);

        //缓存
        wvView.getSettings().setDomStorageEnabled(true);
        wvView.getSettings().setDatabaseEnabled(true);
        //设置缓存模式
        //在LOAD_CACHE_ELSE_NETWORK模式下，无论是否有网络，只要本地有缓存，都使用缓存本地没有缓存时网从网络上获取
        wvView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        String cacheDirPath = getFilesDir().getAbsolutePath();
        cacheDirPath = new File(cacheDirPath,"Shuhui").getPath();
        //设置数据库缓存路径
        wvView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        wvView.getSettings().setAppCachePath(cacheDirPath);
        wvView.getSettings().setAppCacheEnabled(true);
        File baseFile = new File(Environment.getExternalStorageDirectory(), "鼠绘");
        if (!baseFile.exists()) {
            baseFile.mkdir();
        }

        // 判断是否保存到本地，是  加载缓存数据，反之加载网络
        File file = new File(baseFile,mId+".mht");
        if(file.exists()){
            wvView.loadUrl("file://" + file.getAbsolutePath());
            fl.setVisibility(View.GONE);
        }
        wvView.loadUrl(mUrl);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_download:
                // 下载文件操作
                Log.i(TAG, "onOptionsItemSelected:  + 下载文件");
                saveHtml();
                break;
            case R.id.action_share:
                //  分享操作
                Log.i(TAG, "onOptionsItemSelected:  + 分享");



                break;
        }
        return true;

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (wvView.canGoBack()) {
                    wvView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        wvView.setVisibility(View.GONE);
    }

    @Override
    public void finish(){
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

    public void saveHtml(){
        File baseFile = new File(Environment.getExternalStorageDirectory(), "鼠绘");
        if (!baseFile.exists()) {
            baseFile.mkdir();
        }
        File file = new File(baseFile,mId+".mht");
        Log.i(TAG, "saveHtml: " +file.getAbsolutePath());

        Log.i(TAG, "saveHtml: 是否存在 " + ChapterDao.isEmpty(Integer.parseInt(mId)));

        if (ChapterDao.isEmpty(Integer.parseInt(mId))){
            Log.i(TAG, "saveHtml:   新建下载");
            wvView.saveWebArchive(file.getAbsolutePath());
            ChapterDao.saveChapter(
                    Integer.parseInt(mId),
                    mTitle,
                    "2017-01-06",
                    mImage
            );


        }
        Snackbar.make(wvView,"已经下载",Snackbar.LENGTH_SHORT).show();
    }



}
