package cn.ucai.fulicenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.fragment.FragmentBoutique;
import cn.ucai.fulicenter.fragment.FragmentCart;
import cn.ucai.fulicenter.fragment.FragmentCategory;
import cn.ucai.fulicenter.fragment.FragmentMe;
import cn.ucai.fulicenter.fragment.FragmentNewGoods;

public class MainActivity extends AppCompatActivity {
    FragmentNewGoods mFragmentNewGoods;
    FragmentBoutique mFragmentBoutique;
    FragmentCategory mFragmentCategory;
    FragmentCart mFragmentCart;
    FragmentMe mFragmentMe;
    List<Fragment> mFragmentList;
    int index = 0;//默认fragment的下标
    @BindView(R.id.fragmentContainer)
    FrameLayout mfragmentContainer;
    @BindView(R.id.rbNewGoods)
    RadioButton mrbNewGoods;
    @BindView(R.id.rbBoutique)
    RadioButton mrbBoutique;
    @BindView(R.id.rbCategory)
    RadioButton mrbCategory;
    @BindView(R.id.rbCart)
    RadioButton mrbCart;
    @BindView(R.id.tvCartHint)
    TextView mtvCartHint;
    @BindView(R.id.rbPersonalCenter)
    RadioButton mrbMe;
    RadioButton[] rbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initRadioButtonStatus() {
        rbs=new RadioButton[5];
        rbs[0]=mrbNewGoods;
        rbs[1]=mrbBoutique;
        rbs[2]=mrbCategory;
        rbs[3]=mrbCart;
        rbs[4]=mrbMe;
        for(int i=0;i<rbs.length;i++){
            if(i==index){
                rbs[i].setChecked(true);
            }else{
                rbs[i].setChecked(false);
            }
        }
    }

    private void initView() {
        mFragmentNewGoods = new FragmentNewGoods();
        mFragmentBoutique = new FragmentBoutique();
        mFragmentCategory = new FragmentCategory();
        mFragmentCart = new FragmentCart();
        mFragmentMe = new FragmentMe();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mFragmentNewGoods);
        mFragmentList.add(mFragmentBoutique);
        mFragmentList.add(mFragmentCategory);
        mFragmentList.add(mFragmentCart);
        mFragmentList.add(mFragmentMe);
        //设置默认显示的fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragmentContainer, mFragmentNewGoods).show(mFragmentNewGoods).commit();
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.rbNewGoods:
                index = 0;
                break;
            case R.id.rbBoutique:
                index = 1;
                break;
            case R.id.rbCategory:
                index = 2;
                break;
            case R.id.rbCart:
                index = 3;
                break;
            case R.id.rbPersonalCenter:
                index = 4;
                break;
        }
        initRadioButtonStatus();
        switchFragment(index);
    }
    //跳转Fragment
    int currentIndex = 0;//记录当前的Fragment下标
    private void switchFragment(int index) {
        if (index == currentIndex) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = mFragmentList.get(index);
        if (!fragment.isAdded()) {
            ft.add(R.id.fragmentContainer, fragment);
        }
        ft.show(fragment).hide(mFragmentList.get(currentIndex)).commit();
        currentIndex = index;
    }
}
