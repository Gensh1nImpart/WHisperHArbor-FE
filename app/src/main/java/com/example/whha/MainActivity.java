package com.example.whha;

import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.whha.utils.TokenTools;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tencent.mmkv.MMKV;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    TokenTools tokentools;
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TokenTools.mmkv.removeValueForKey("token");

        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.main_vp);
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bnv);
        initData();
        MainActivityAdapter adapter = new MainActivityAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_write);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.bottom_me);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_home) {
                    viewPager.setCurrentItem(0);
                }else if(item.getItemId() == R.id.bottom_write) {
                    viewPager.setCurrentItem(1);
                }else if(item.getItemId() == R.id.bottom_me){
                    viewPager.setCurrentItem(2);
                }
                return true;
            }
        });
    }
    void initData(){
        HomeFragment homeFragment = HomeFragment.newInstance("首页", "");
        fragmentList.add(homeFragment);
        WriteFragment writeFragment = WriteFragment.newInstance("写作","");
        fragmentList.add(writeFragment);
        InformationFragment informationFragment = InformationFragment.newInstance("我的", "");
        fragmentList.add(informationFragment);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}