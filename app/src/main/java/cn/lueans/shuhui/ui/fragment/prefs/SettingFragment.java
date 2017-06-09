package cn.lueans.shuhui.ui.fragment.prefs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import cn.lueans.shuhui.R;
import cn.lueans.shuhui.entity.User;

/**
 * Created by 24277 on 2017/6/6.
 */

public class SettingFragment extends PreferenceFragment{

   /* private ListPreference cachePosition;
    private Preference clearCache;*/
    private Preference logout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs_setting);
        initPrefs();
        initListener();
    }

    private void initPrefs() {
       /* cachePosition = (ListPreference) findPreference("cache_position");
        clearCache = findPreference("Clear_cache");*/
        logout = findPreference("logout");
    }

    private void initListener() {
       /* cachePosition.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Toast.makeText(getActivity(), "" + newValue, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        clearCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity(), "开始清理", Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/

        logout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                final User instance = User.getInstance(getActivity());
                if (!instance.isLogin()){
                    Toast.makeText(getActivity(), "你还没有登陆！请先登录", Toast.LENGTH_SHORT).show();
                    return true;
                }

                AlertDialog dialog =new AlertDialog.Builder(getActivity()).create();
                dialog.setTitle("退出登陆");
                dialog.setMessage("是否要退出当前账号" + instance.getNickName());
                dialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // add the alipay account to clipboard
                        instance.logout(getActivity());
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
}
