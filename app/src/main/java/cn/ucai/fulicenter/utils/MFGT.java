package cn.ucai.fulicenter.utils;

import android.app.Activity;
import android.content.Intent;

<<<<<<< HEAD
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.activity.MainActivity;
public class MFGT {
    //Activity结束时的动画
=======
import cn.ucai.fulicenter.MainActivity;
import cn.ucai.fulicenter.R;


public class MFGT {
>>>>>>> origin/master
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
<<<<<<< HEAD
    //跳转到MainActivity的动画
=======
>>>>>>> origin/master
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }
}
