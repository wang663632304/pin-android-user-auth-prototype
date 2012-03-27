package com.mindpin.activity.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.mindpin.R;
import com.mindpin.base.activity.MindpinBaseActivity;
import com.mindpin.base.task.MindpinAsyncTask;
import com.mindpin.base.utils.BaseUtils;
import com.mindpin.logic.HttpApi;

public class SignupActivity extends MindpinBaseActivity {
    private String email;
    private String password;
    private String confirm_password;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
    }

    public void signup_button_click(View view) {
        prepare_email_and_password();
        if (is_params_valid()) {
            do_signup();
        }
    }

    // 获取邮箱，密码字符串。作准备。
    private void prepare_email_and_password() {
        EditText email_et = (EditText) findViewById(R.id.email_et);
        EditText password_et = (EditText) findViewById(R.id.password_et);
        EditText name_et = (EditText) findViewById(R.id.name_et);
        EditText confirm_password_et = (EditText) findViewById(R.id.confirm_password_et);
        email = email_et.getText().toString();
        name = name_et.getText().toString(); 
        password = password_et.getText().toString();
        confirm_password = confirm_password_et.getText().toString();
    }
    
    //参数检查
    private boolean is_params_valid(){
        
        //邮箱，用户名，密码不可以空
        if (BaseUtils.is_str_blank(email)) {
            BaseUtils.toast(R.string.signup_email_valid_blank);
            return false;
        }
        
        if (BaseUtils.is_str_blank(name)) {
            BaseUtils.toast(R.string.signup_name_valid_blank);
            return false;
        }

        if (BaseUtils.is_str_blank(password)) {
            BaseUtils.toast(R.string.signup_password_valid_blank);
            return false;
        }
        
        if(BaseUtils.is_str_blank(confirm_password)){
            BaseUtils.toast(R.string.signup_confirm_password_valid_blank);
            return false;
        }
        
        if(!password.equals(confirm_password)){
            BaseUtils.toast(R.string.signup_confirm_password_valid_equals);
            return false;
        }
        return true;
    }
    
    //显示正在注册，并在一个线程中进行注册
    private void do_signup(){        
        new MindpinAsyncTask<String, Void, Void>(this, R.string.now_signup){
            @Override
            public Void do_in_background(String... params) throws Exception {
                String email = params[0];
                String name = params[1];
                String password = params[2];
                HttpApi.user_signup(email, name, password);
                return null;
            }

            @Override
            public void on_success(Void v) {
                restart_to_main();
            }
        }.execute(email, name, password);
    }
}
