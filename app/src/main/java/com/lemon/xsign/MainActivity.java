package com.lemon.xsign;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lemon.xsign.client.ClientHandler;
import com.lemon.xsign.client.NettyClient;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends Activity {
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        //初始化状态栏
        View root = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        ButterKnife.bind(this, root);
        initTopBar();
        setContentView(root);

        //获取全局上下文  发送广播需要
      //  ClientHandler.signContext = getApplicationContext();



    }



    private void initTopBar() {
      //  mTopBar.setBackgroundColor(ContextCompat.getColor(this, R.color.app_color_theme_4));
//        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
//            }
//        });

        mTopBar.setTitle("XSign服务");
    }


    public void onClickCloseLink(View view) {

                 //   Intent intent = new Intent("getXsign");
            // 广播带参数
//            intent.putExtra("t", t);
//            intent.putExtra("api", api);
//            intent.putExtra("data", data);

         //  this.sendBroadcast(intent);

//        NettyClient.closeLink();
//        findViewById(R.id.button_linkserver).setEnabled(true);
//        findViewById(R.id.button_closelink).setEnabled(false);
    }


    public void onClickLinkServer(View view) {
       // Toast.makeText(MainActivity.this, "测试方法", Toast.LENGTH_SHORT).show();

//        EditText editTextHost =findViewById(R.id.edittext_host);
//       String host =editTextHost.getText().toString();
//        EditText editTextPort =findViewById(R.id.edittext_port);
//        int port =Integer.parseInt(editTextPort.getText().toString());
//
//
//
//        findViewById(R.id.button_linkserver).setEnabled(false);
//        findViewById(R.id.button_closelink).setEnabled(true);
//



        //   TBXsign.hookTBXsign(TBXsign.classLoader);

//        new QMUIDialog.MessageDialogBuilder(this)
//                .setTitle("QMUI对话框标题")
//                .setMessage("这是QMUI框架对话框的内容")
//                .addAction("取消", new QMUIDialogAction.ActionListener() {
//                    @Override
//                    public void onClick(QMUIDialog dialog, int index) {
//                        dialog.dismiss();
//                        Toast.makeText(MainActivity.this, "点击了取消按钮", Toast.LENGTH_SHORT).show();

//                    }
//                })
//                .addAction("确定", new QMUIDialogAction.ActionListener() {
//                    @Override
//                    public void onClick(QMUIDialog dialog, int index) {
//                        dialog.dismiss();
//                        Toast.makeText(MainActivity.this, "点击了确定按钮", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .show();
    }



}




