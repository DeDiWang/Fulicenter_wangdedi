package cn.ucai.fulicenter;

import android.app.Application;
import android.content.Context;

/**
 * Created by 11039 on 2016/10/17.
 */
public class FuLiCenterApplication extends Application {
    public static Context applicationContext;
    private static FuLiCenterApplication instance;
    private static String userName;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        FuLiCenterApplication.userName = userName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext=this;
        instance=this;
    }
    public static FuLiCenterApplication getInstance(){
        if(instance==null){
            instance=new FuLiCenterApplication();
        }
        return instance;
    }
}
