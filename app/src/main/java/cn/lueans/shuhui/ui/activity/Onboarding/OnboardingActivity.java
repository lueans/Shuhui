package cn.lueans.shuhui.ui.activity.Onboarding;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import cn.lueans.shuhui.MainActivity;
import cn.lueans.shuhui.R;
import cn.lueans.shuhui.utils.SharedPreferencesUtils;

public class OnboardingActivity extends AppCompatActivity {

    private static final String TAG = "OnboardingActivity";

    private static final String IS_INIT = "is_init";
    private CoordinatorLayout mainContent;
    private ViewPager viewPager;
    private ImageButton buttonPre;
    private ImageView[] indicators;
    private AppCompatButton buttonFinish;
    private ImageButton buttonNext;

    private int bgColors[];
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferencesUtils instance = SharedPreferencesUtils.getInstance(OnboardingActivity.this);
        boolean isInit = instance.getBoolean(IS_INIT, false);
        if (isInit) {
            startMain();
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_onboarding);
            assignViews();
            initData();
            initListener();
        }
    }

    private void startMain() {
        Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void assignViews() {
        mainContent = (CoordinatorLayout) findViewById(R.id.main_content);

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(new OnboasdingAdapter(getSupportFragmentManager()));

        buttonPre = (ImageButton) findViewById(R.id.imageButtonPre);
        indicators = new ImageView[3];
        indicators[0] = (ImageView) findViewById(R.id.imageViewIndicator0);
        indicators[1] = (ImageView) findViewById(R.id.imageViewIndicator1);
        indicators[2] = (ImageView) findViewById(R.id.imageViewIndicator2);
        buttonFinish = (AppCompatButton) findViewById(R.id.buttonFinish);
        buttonNext = (ImageButton) findViewById(R.id.imageButtonNext);
    }

    private void initData() {
        bgColors = new int[]{ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.cyan_500),
                ContextCompat.getColor(this, R.color.light_blue_500)};
    }



    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) new ArgbEvaluator().evaluate(positionOffset, bgColors[position], bgColors[position == 2 ? position : position + 1]);
                viewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                updateIndicators(position);
                viewPager.setBackgroundColor(bgColors[position]);
                buttonPre.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                buttonNext.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                buttonFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils instance = SharedPreferencesUtils.getInstance(OnboardingActivity.this);
                instance.start().put(IS_INIT,true).commit();
                startMain();
            }
        });

        buttonPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上一个
                currentPosition -= 1;
                viewPager.setCurrentItem(currentPosition, true);
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下一个
                currentPosition += 1;
                viewPager.setCurrentItem(currentPosition, true);
            }
        });
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.onboarding_indicator_selected : R.drawable.onboarding_indicator_unselected
            );
        }
    }
}
