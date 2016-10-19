package cn.ucai.fulicenter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by 11039 on 2016/10/19.
 */
public abstract class BaseActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initView();
        initData();
        setListener();
    }
    protected abstract void initView();
    protected abstract void initData();
    protected abstract void setListener();

}
