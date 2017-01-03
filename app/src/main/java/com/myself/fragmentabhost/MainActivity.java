package com.myself.fragmentabhost;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

//1.tabhost的使用
//2.将布局延伸到状态栏,values_v19中增加状态,oncreate中代码设置即可
public class MainActivity extends AppCompatActivity {
    long lastbacktime;
    private FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);
        tabHost = (FragmentTabHost) findViewById(R.id.tab_Host);
        int version = getSDKVersion();
        //判断当前手机的版本
        if (version >= 19) {
            ImageView image = (ImageView) findViewById(R.id.status);
            int height = getStatusHeight(this);
            image.getLayoutParams().height = height;
            image.setBackgroundColor(Color.RED);
        }

        //获取tab的标题
        String[] titles = getResources().getStringArray(R.array.tab_title);
        //背景图
        int[] icons = new int[]{R.drawable.news_selector, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher};
        Class[] classes = new Class[]{EmptyFragment.class, EmptyFragment.class, EmptyFragment.class, EmptyFragment.class, EmptyFragment.class};
        //1 绑定 ->fragment显示的容器
        tabHost.setup(this, getSupportFragmentManager(), R.id.content);

        for (int i = 0; i < titles.length; i++) {
            TabHost.TabSpec tmp = tabHost.newTabSpec("" + i);
            tmp.setIndicator(getEveryView(this, titles, icons, i));
            tabHost.addTab(tmp, classes[i], null);
        }

        //监控这个FragmentTabHost的切换
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.i("it520", "tabId = " + tabId);
            }
        });
        //        //设置选中的页面,tag
        //        tabHost.setCurrentTabByTag("1");
    }


    public int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    public View getEveryView(Context context, String[] titles, int[] icons, int index) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View title_view = inflater.inflate(R.layout.item_title, null);
        TextView title = (TextView) title_view.findViewById(R.id.title);
        ImageView icon = (ImageView) title_view.findViewById(R.id.icon);
        // 设置标签的内容
        title.setText(titles[index]);
        icon.setImageResource(icons[index]);
        return title_view;
    }

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - lastbacktime < 1000) {
            finish();
        } else {
            Toast.makeText(this, "再次点击退出!!!!", Toast.LENGTH_SHORT).show();
        }
        lastbacktime = now;
    }


    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
