package cn.lueans.shuhui.ui.fragment.prefs;

/**
 * Created by 24277 on 2017/6/6.
 */

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.ui.activity.PrefsActivity;
import cn.lueans.shuhui.utils.BrowserUtils;

import static android.content.Context.CLIPBOARD_SERVICE;

public class AboutFragment extends PreferenceFragment{

    private Preference prefVersion,  //版本
            prefDeveloper,  //开发者
            prefLicenses,  //开源
            prefHugeterry,  //鸣谢LHugeterry
            prefLufficc,    //鸣谢 ufficc
            prefSourceCode,  //github 开源
            prefSendAdvices, //反馈
            prefDonate;     //捐赠


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs_about);
        initPrefs();
        initData();
        initListener();
    }

    private void initListener() {
        prefDeveloper.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String weibo = "http://weibo.com/5599619390/profile?rightmod=1&wvr=6&mod=personinfo&retcode=6102&is_all=1";
                BrowserUtils.openInBrowser(getActivity(),weibo);
                return true;
            }
        });

        prefLicenses.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), PrefsActivity.class);
                intent.putExtra(PrefsActivity.EXTRA_FLAG, PrefsActivity.FLAG_LICENSES);
                startActivity(intent);
                return true;
            }
        });

        prefHugeterry.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String hugeterry = "https://github.com/hugeterry/coderfun";
                BrowserUtils.openInBrowser(getActivity(),hugeterry);
                return true;
            }
        });

        prefLufficc.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String lufficc ="https://github.com/lufficc/iShuiHui";
                BrowserUtils.openInBrowser(getActivity(),lufficc);
                return true;
            }
        });

        prefSourceCode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String sourceCode = "https://github.com/lueans/Shuhui";
                BrowserUtils.openInBrowser(getActivity(),sourceCode);
                return true;
            }
        });

        prefSendAdvices.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String weibo = "http://weibo.com/5599619390/profile?rightmod=1&wvr=6&mod=personinfo&retcode=6102&is_all=1";
                BrowserUtils.openInBrowser(getActivity(),weibo);
                return true;
            }
        });

        prefDonate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog dialog =new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("支付宝");
                dialog.setMessage("支付宝账号将被复制到剪贴板, 打开支付宝并粘贴账号即可完成捐赠。感谢你的支持!");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // add the alipay account to clipboard
                        ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("text","18826237642");
                        manager.setPrimaryClip(clipData);
                        dialog.dismiss();
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });


    }

    private void initPrefs() {
        prefVersion = findPreference("app_version");
        prefDeveloper = findPreference("app_developer");
        prefLicenses = findPreference("licenses");
        prefHugeterry = findPreference("hugeterry");
        prefLufficc = findPreference("lufficc");
        prefSourceCode = findPreference("source_code");
        prefSendAdvices = findPreference("feedback");
        prefDonate = findPreference("donate");

    }

    private void initData()
    {
        prefVersion.setSummary("1.0debug");
    }
}
