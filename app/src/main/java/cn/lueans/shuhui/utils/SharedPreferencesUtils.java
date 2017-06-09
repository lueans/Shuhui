package cn.lueans.shuhui.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by 24277 on 2017/5/8.
 *
 *  SharedPreferences 的封装
 */
public class SharedPreferencesUtils {

    private SharedPreferences sharedPreferences;
    private static SharedPreferencesUtils instance;

    private SharedPreferencesUtils(Context context) {
        sharedPreferences = context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtils getInstance(Context context){
        if (instance == null){
            synchronized(SharedPreferencesUtils.class){
                if (instance == null){
                    instance = new SharedPreferencesUtils(context);
                }
            }
        }
        return instance;
    }


    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public PreferencesEditor start() {
        return new PreferencesEditor(sharedPreferences.edit());
    }

    public String getString(String key, String defValue) {
        return (String)(sharedPreferences.getString(key, defValue));
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return sharedPreferences.getFloat(key, defValue);
    }

    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return sharedPreferences.getStringSet(key, defValue);
    }


    public class PreferencesEditor {
        private SharedPreferences.Editor editor;

        public PreferencesEditor(SharedPreferences.Editor editor) {
            this.editor = editor;
        }

        public PreferencesEditor put(String key, String value) {
            editor.putString(key, value);
            return this;
        }

        public PreferencesEditor put(String key, boolean value) {
            editor.putBoolean(key, value);
            return this;
        }
        public  PreferencesEditor remove(String key){
            editor.remove(key);
            return this;
        }

        public PreferencesEditor put(String key, int value) {
            editor.putInt(key, value);
            return this;
        }

        public PreferencesEditor put(String key, long value) {
            editor.putLong(key, value);
            return this;
        }

        public PreferencesEditor put(String key, float value) {
            editor.putFloat(key, value);
            return this;
        }

        public PreferencesEditor put(String key, Set<String> value) {
            editor.putStringSet(key, value);
            return this;
        }

        public boolean commit() {
            return editor.commit();
        }

        public void apply(){
            editor.apply();
        }
    }
}
