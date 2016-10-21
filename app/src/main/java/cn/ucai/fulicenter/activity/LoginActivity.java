package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassword)
    EditText etPassword;
    String userName;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        initView();
        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();
    }

    private void initView() {
        String name = getIntent().getStringExtra("userName");
        if(name!=null){
            etUserName.setText(name);
        }
    }

    @OnClick({R.id.btnLogin, R.id.btnFreeRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                NetDao.login(this, userName, password, new OkHttpUtils.OnCompleteListener<Result>() {
                    @Override
                    public void onSuccess(Result result) {
                        if(result.getRetCode()==0){
                            Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                            //登录成功后跳到个人中心
                            FuLiCenterApplication.setUserName(userName);

                        }
                    }
                    @Override
                    public void onError(String error) {
                        L.e(error);
                    }
                });
                break;
            case R.id.btnFreeRegister:
                MFGT.gotoRegisterActivity(this);
                break;
        }
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
