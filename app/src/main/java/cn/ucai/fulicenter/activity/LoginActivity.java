package cn.ucai.fulicenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = LoginActivity.class.getSimpleName();
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

    }

    private void initView() {

    }

    @OnClick({R.id.btnLogin, R.id.btnFreeRegister})
    public void onClick(View view) {
        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();
        switch (view.getId()) {
            case R.id.btnLogin:
                checkInput();
                login();
                break;
            case R.id.btnFreeRegister:
                startActivityForResult(new Intent(this,RegisterActivity.class), I.REQUEST_CODE_REGISTER);
                break;
        }
    }

    private void login() {
        NetDao.login(this, userName, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                if(result==null){
                    Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                }else{
                    if(result.isRetMsg()){
                        Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                        //拿到用户信息
                        //UserAvatar user = (UserAvatar) result.getRetData();
                        //L.e(TAG,user.toString());
                        //MFGT.finish(LoginActivity.this);
                    }else {
                        if(result.getRetCode()==I.MSG_LOGIN_ERROR_PASSWORD){
                            Toast.makeText(LoginActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
                        }else if(result.getRetCode()==I.MSG_LOGIN_UNKNOW_USER){
                            Toast.makeText(LoginActivity.this, "该用户不存在!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onError(String error) {
                Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                L.e(error);
            }
        });
    }

    private void checkInput() {
        if(TextUtils.isEmpty(userName)){
            etUserName.setError("用户名不能为空");
            etUserName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            etPassword.setError("密码不能为空");
            etPassword.requestFocus();
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==I.REQUEST_CODE_REGISTER && resultCode==RESULT_OK){
            String userName = data.getStringExtra("userName");
            etUserName.setText(userName);
        }
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
