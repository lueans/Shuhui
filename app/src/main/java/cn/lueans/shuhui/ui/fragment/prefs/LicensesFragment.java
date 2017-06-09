package cn.lueans.shuhui.ui.fragment.prefs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import cn.lueans.shuhui.R;

/**
 * Created by 24277 on 2017/6/6.
 */

public class LicensesFragment extends Fragment {

    private static final String TAG = "LicensesFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_licenses, container, false);
        WebView webView = (WebView) view.findViewById(R.id.webview);
        String path = "file:///android_asset/license.html";
        WebSettings webSettings = webView.getSettings();
        webView.loadUrl(path);
        return view;
    }
}
