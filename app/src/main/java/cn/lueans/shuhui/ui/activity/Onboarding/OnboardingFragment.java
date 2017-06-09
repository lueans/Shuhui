package cn.lueans.shuhui.ui.activity.Onboarding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cn.lueans.shuhui.R;

/**
 * Created by 24277 on 2017/6/7.
 */

public class OnboardingFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private AppCompatTextView sectionLabel;
    private AppCompatTextView sectionIntro;
    private ImageView sectionImg;

    private int page = 0;

    public static final String title[] = {
            "鼠绘，一个只属于属于的自己的漫画！",
            "汉化组，汉化不是我的天赋，而是我的本性",
            "马上开启只属于你的鼠绘！"
    };

    public static final String body[] = {
            "热血漫画 在线漫画 海贼王 火影忍者 妖精的尾巴 进击的巨人 美食的俘虏鼠绘漫画网",
            "汉化！只因为你是漫迷",
            "GO! Go! Go!"
    };

    public static final int imgId[] = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };



    public static OnboardingFragment getInstance(int pageIndex){
        OnboardingFragment onboardingFragment = new OnboardingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, pageIndex);
        onboardingFragment.setArguments(bundle);
        return onboardingFragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        page = bundle.getInt(ARG_SECTION_NUMBER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        sectionImg = (ImageView) view.findViewById(R.id.iv_section);
        sectionLabel = (AppCompatTextView) view.findViewById(R.id.section_label);
        sectionIntro = (AppCompatTextView) view.findViewById(R.id.section_intro);
        sectionImg.setBackgroundResource(imgId[page]);
        sectionLabel.setText(title[page]);
        sectionIntro.setText(body[page]);
    }
}
