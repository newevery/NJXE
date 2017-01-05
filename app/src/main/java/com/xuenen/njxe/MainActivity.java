package com.xuenen.njxe;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.xuenen.njxe.bean.User;
import com.xuenen.njxe.ui.activity.CateActivity;
import com.xuenen.njxe.ui.activity.DetialActivity;
import com.xuenen.njxe.ui.activity.LoginActivity;
import com.xuenen.njxe.ui.activity.BorrowActivity;
import com.xuenen.njxe.utils.OkHttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("主页");
        setSupportActionBar(toolbar);
//        dologin();


        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("http://www.njby.net/");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            DialogProgressUtil.createLoadingDialog(this, "DDDDDDDDD").show();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_borrow) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.nav_gallery) {

            View inflate = MainActivity.this.getLayoutInflater().inflate(R.layout.dialog_jiejue, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("选择操作");
            builder.setView(inflate);
            final AlertDialog dialog = builder.create();
            dialog.show();
            inflate.findViewById(R.id.btn_dalydj).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, BorrowActivity.class));
                    dialog.dismiss();
                }
            });
            inflate.findViewById(R.id.btn_dalyjl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, CateActivity.class));
                    dialog.dismiss();
                }
            });

        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(this, BorrowActivity.class));
        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void doLogout() {
        OkHttpUtil.doGet("http://192.168.3.3:8080/jyyy/mobile/logout.do", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(response.headers().values("Set-Cookie"));

            }
        });
    }

    private void doRegist() {
        User user = new User();
        user.setAccount("assc");
        user.setPassword("qq123");
        user.setUsername("sdsds");
        user.setKind("6");
        user.setState(0);
        user.setPhone("111111111");
        user.setEmail("11111@qqqq");
        user.setOrgid("DQ14");
        Map<String, String> map = new HashMap<>();
        map.put("account", user.getAccount());
        map.put("username", user.getUsername());
        map.put("king", user.getKind());
        map.put("state", user.getState() + "");
        map.put("phone", user.getPassword());
        map.put("email", user.getEmail());
        map.put("ordid", user.getOrgid());

        OkHttpUtil.doPost("http://192.168.3.3:8080/jyyy/mobile/add.do", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                http://192.168.3.3:8080/android/
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("SSSSSSS  " + response.toString());
            }
        });
    }


}
