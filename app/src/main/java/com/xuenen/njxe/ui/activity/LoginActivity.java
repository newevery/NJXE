package com.xuenen.njxe.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xuenen.njxe.MainActivity;
import com.xuenen.njxe.R;
import com.xuenen.njxe.bean.Check;
import com.xuenen.njxe.common.BaseActivity;
import com.xuenen.njxe.constant.C;
import com.xuenen.njxe.utils.DialogProgressUtil;
import com.xuenen.njxe.utils.LogUtils;
import com.xuenen.njxe.utils.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private Dialog dialog;
    private EditText account, password;
    private CheckBox rem_pw, auto_login;
    private Button btn_login;
    private Button btnRegist;
    private String accountValue, passwordValue;
    private SharedPreferences sp;
    private int userId;
    private String userName;
    private ArrayList<Map<String, String>> lst = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //去除标题
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        //获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        account = (EditText) findViewById(R.id.login_name_et);
        password = (EditText) findViewById(R.id.login_psss_et);
        rem_pw = (CheckBox) findViewById(R.id.login_passMem_cb);
        rem_pw.setChecked(false);
        auto_login = (CheckBox) findViewById(R.id.login_autoLogin_cb);
        btn_login = (Button) findViewById(R.id.login_login_btn);
        btnRegist = (Button) findViewById(R.id.login_regist_btn);

        //判断记住密码多选框的状态
        if (sp.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            rem_pw.setChecked(true);
            account.setText(sp.getString("ACCOUNT", ""));
            password.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if (sp.getBoolean("AUTO_ISCHECK", false)) {
                //设置默认是自动登录状态
                auto_login.setChecked(true);
                //跳转界面
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        }

        // 登录监听事件  现在默认为用户名为：liu 密码：123
        btn_login.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                accountValue = account.getText().toString();
                passwordValue = password.getText().toString();
                if (TextUtils.isEmpty(accountValue) || TextUtils.isEmpty(passwordValue)) {
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    dologin(accountValue, passwordValue);
                }
            }
        });

        //监听记住密码多选框按钮事件
        rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rem_pw.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                } else {

                    auto_login.setChecked(false);
                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        //监听自动登录多选框事件
        auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (auto_login.isChecked()) {

                    //设置默认是记录密码状态
                    rem_pw.setChecked(true);

                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });

        btnRegist.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistActivity.class));

            }
        });

    }

    private void dologin(String name, String pass) {
        Map<String, String> map = new HashMap<>();
        map.put("account", name);
        map.put("password", pass);
        showDialog();
        OkHttpUtil.doPost(C.URL.loginURL, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                http://192.168.3.3:8080/android/
                SystemClock.sleep(3000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeDialog();
                        Toast.makeText(LoginActivity.this, "连接服务器失败", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                登录成功和记住密码框为选中状态才保存用户信息
                SystemClock.sleep(3000);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    LogUtils.w(jsonObject.toString());
                    if ((0 + "").equals(jsonObject.getString("requestcode"))) {
                        String data = jsonObject.getString("data");
                        System.out.println(data);
                        userId = new JSONObject(data).getInt("id");
                        userName = new JSONObject(data).getString("username");
                        Editor editor = sp.edit();
                        editor.putString("ACCOUNT", accountValue);
                        if (rem_pw.isChecked()) {
                            //记住用户名、密码、
                            editor.putString("PASSWORD", passwordValue);
                        }
                        editor.putString("USERNAME", userName);
                        editor.putInt("USERID", userId);
                        editor.commit();
                        System.out.println(sp.getInt("USERID", 100));
                        System.out.println("userName" + userName);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                closeDialog();
                                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
                            }
                        });
                        //跳转界面
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                        //finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                closeDialog();
                                Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    /**
     * 显示Dialog
     */
    private void showDialog() {
        if (dialog == null) {
            dialog = DialogProgressUtil.createLoadingDialog(this, "正在加载中...");
            dialog.show();
        }
    }

    /**
     * 关闭Dialog
     */
    public void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}