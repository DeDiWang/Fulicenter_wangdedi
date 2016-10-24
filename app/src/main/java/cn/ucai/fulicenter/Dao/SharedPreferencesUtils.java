package cn.ucai.fulicenter.Dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 11039 on 2016/10/24.
 */
public class SharedPreferencesUtils {
    private static SharedPreferencesUtils instance;
    private static final String SHARE_NAME="saveUserInfo";
    private static final String SHARE_KEY_USER_NAME="share_key_user_name";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private SharedPreferencesUtils(Context context){
        sp=context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    public static SharedPreferencesUtils getInstance(Context context){
        if(instance==null){
            instance=new SharedPreferencesUtils(context);
        }
        return instance;
    }
    public void saveUser(String userName){
        editor.putString(SHARE_KEY_USER_NAME,userName);
        editor.commit();
    }
    public String getUser(){
        return sp.getString(SHARE_KEY_USER_NAME, null);
    }
    public void removeUser(){
        editor.remove(SHARE_KEY_USER_NAME);
        editor.commit();
    }
}
