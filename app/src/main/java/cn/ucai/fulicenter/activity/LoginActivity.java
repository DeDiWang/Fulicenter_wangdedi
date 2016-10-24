package cn.ucai.fulicenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.Dao.SharedPreferencesUtils;
import cn.ucai.fulicenter.Dao.UserDao;
import cn.ucai.fulicenter.FuLiCenterApplication;
import cn.ucai.fulicenter.I;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.bean.Result;
import cn.ucai.fulicenter.bean.UserAvatar;
import cn.ucai.fulicenter.net.NetDao;
import cn.ucai.fulicenter.utils.CommonUtils;
import cn.ucai.fulicenter.utils.L;
import cn.ucai.fulicenter.utils.MFGT;
import cn.ucai.fulicenter.utils.OkHttpUtils;
import cn.ucai.fulicenter.utils.ResultUtils;

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
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        mContext=this;
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
                if(checkInput()){
                    login();
                }
                break;
            case R.id.btnFreeRegister:
                startActivityForResult(new Intent(this,RegisterActivity.class), I.REQUEST_CODE_REGISTER);
                break;
        }
    }

    private void login() {
        NetDao.login(this, userName, password, new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String str) {
                Result result = ResultUtils.getResultFromJson(str, UserAvatar.class);
                if(result.toString()==null){
                    CommonUtils.showShortToast(getResources().getString(R.string.login_fail));
                }else{
                    if(result.isRetMsg()){
                        CommonUtils.showLongToast(getResources().getString(R.string.login_success));
                        //得到用户信息
                        UserAvatar user = (UserAvatar) result.getRetData();
                        L.e("用户信息："+user.toString());
                        //将用户信息保存在数据库中
                        UserDao userDao = new UserDao(LoginActivity.this);
                        boolean isSuccess = userDao.saveUser(user);
                        if(isSuccess){
                            //将登陆成功后的用户名保存在首选项中
                            SharedPreferencesUtils.getInstance(mContext).saveUser(user.getMuserName());
                            //将登陆成功后的用户信息保存在内存中
                            FuLiCenterApplication.setUser(user);
                            //跳转回MainActivity
                            setResult(RESULT_OK);
                            MFGT.finish(LoginActivity.this);
                        }else {
                            CommonUtils.showShortToast(getResources().getString(R.string.user_database_error));
                        }
                    }else {
                        if(result.getRetCode()==I.MSG_LOGIN_ERROR_PASSWORD){
                            CommonUtils.showShortToast(getResources().getString(R.string.error_password));
                        }else if(result.getRetCode()==I.MSG_LOGIN_UNKNOW_USER){
                            CommonUtils.showShortToast(getResources().getString(R.string.unknow_user));
                        }else {
                            CommonUtils.showShortToast(getResources().getString(R.string.login_fail));
                        }
                    }
                }
            }
            @Override
            public void onError(String error) {
                CommonUtils.showShortToast(getResources().getString(R.string.login_fail));
                L.e(error);
            }
        });
    }

    private boolean checkInput() {
        if(TextUtils.isEmpty(userName)){
            etUserName.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(password)){
            etPassword.setError(getResources().getString(R.string.password_connot_be_empty));
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==I.REQUEST_CODE_REGISTER && resultCode==RESULT_OK){
            String userName = data.getStringExtra(I.User.USER_NAME);
            etUserName.setText(userName);
            if(!etPassword.getText().toString().isEmpty()){
                etPassword.setText(null);
            }
            etPassword.requestFocus();
        }
    }

    @OnClick(R.id.ivBack)
    public void onClick() {
        MFGT.finish(this);
    }
}
