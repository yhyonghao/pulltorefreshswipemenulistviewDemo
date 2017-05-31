package com.yh.testswipmeunlistview;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenu;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.bean.SwipeMenuItem;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnMenuItemClickListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.OnSwipeListener;
import edu.swu.pulltorefreshswipemenulistview.library.swipemenu.interfaces.SwipeMenuCreator;
import edu.swu.pulltorefreshswipemenulistview.library.util.RefreshTime;

public class MainActivity extends AppCompatActivity {
  private PullToRefreshSwipeMenuListView listView;
    private List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    private int[] imgId={R.drawable.apple_pic,R.drawable.banana_pic,R.drawable.cherry_pic,
            R.drawable.grape_pic,R.drawable.mango_pic,R.drawable.orange_pic,R.drawable.pear_pic,
            R.drawable.pineapple_pic,R.drawable.strawberry_pic,R.drawable.watermelon_pic};
    private String[] names={"apple","banana","cherry","grape","mango","orange","pear","pineapple",
            "strawberry","watermelon"};
      private   SimpleAdapter adapter;
    private Handler mHandler;
    private ScrollView scorllview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        listView = (PullToRefreshSwipeMenuListView) findViewById(R.id.listview);
       // scorllview = (ScrollView) findViewById(R.id.scorllview);
        adapter = new SimpleAdapter(this,list, R.layout.fruit_item,
                new String[]{"img","name"},new int[]{R.id.iv,R.id.tv});
        listView.setAdapter(adapter);
        //下拉刷新监听
        listView.setPullRefreshEnable(true);
        //上拉刷新监听
        listView.setPullLoadEnable(true);
        //listView.setXListViewListener(this);
        mHandler = new Handler();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String clickData=list.get(position).get("name").toString();
                Toast.makeText(MainActivity.this, clickData, Toast.LENGTH_SHORT).show();
            }
        });
        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)));
                // set item width
                //openItem.setWidth(dp2px(90));
                openItem.setWidth(150);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xe9, 0x6F, 0x95)));
                // set item width
               // deleteItem.setWidth(dp2px(90));
                deleteItem.setWidth(150);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);

                // create "delete" item
                SwipeMenuItem deleteItem1 = new SwipeMenuItem(getApplicationContext());
                // set item background
                deleteItem1.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // set item width
                // deleteItem.setWidth(dp2px(90));
                deleteItem1.setWidth(150);
                // set a icon
                deleteItem1.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem1);
            }
        };
        // set creator
        listView.setMenuCreator(creator);


        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                //ApplicationInfo item = list.get(position);
                switch (index) {
                    case 0:
                        // open
                        //open(item);
                        Toast.makeText(MainActivity.this, "打开", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        // delete
                        // delete(item);
                        Toast.makeText(MainActivity.this, "删除", Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        adapter.notifyDataSetChanged();

                        break;
                }
            }
        });



        // set SwipeListener
        listView.setOnSwipeListener(new OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // other setting
        // listView.setCloseInterpolator(new BounceInterpolator());
        // test item long click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });}
        private void onLoad() {
            initFruits();
            listView.setRefreshTime(
                    RefreshTime.getRefreshTime(getApplicationContext()));

            listView.stopRefresh();

            listView.stopLoadMore();

        }

        public void onRefresh() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault());
                    RefreshTime.setRefreshTime(getApplicationContext(), df.format(new Date()));
                    onLoad();
                }
            }, 2000);
        }

        public void onLoadMore() {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onLoad();
                }
            }, 2000);
        }



//    private void open(ApplicationInfo item) {
//        // open app
//        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
//        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//        resolveIntent.setPackage(item.packageName);
//        List<ResolveInfo> resolveInfoList = getPackageManager().queryIntentActivities(resolveIntent, 0);
//        if (resolveInfoList != null && resolveInfoList.size() > 0) {
//            ResolveInfo resolveInfo = resolveInfoList.get(0);
//            String activityPackageName = resolveInfo.activityInfo.packageName;
//            String className = resolveInfo.activityInfo.name;
//
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//            ComponentName componentName = new ComponentName(activityPackageName, className);
//
//            intent.setComponent(componentName);
//            startActivity(intent);
//        }
//    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


    private void initFruits(){
        for(int i=0;i<imgId.length;i++){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("img",imgId[i]);
            map.put("name",names[i]);
            list.add(map);
        }
    }
}
