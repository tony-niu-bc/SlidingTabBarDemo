package com.wzhnsc.slidingtabbardemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private CategoryTabStrip       mTabBar;
    private ViewPager              mvpArea;
    private CategoryNineBlockStyle mCategoryNineBlockStyle;

    private List<String> mCategroyList = new ArrayList<>();
    private List<CategoryListFragment> mvpFragmentList = new ArrayList<>();

    public static class CategoryListFragment extends Fragment {
        private static final String ARG_CATEGORY_NAME = "ARG_CATEGORY_NAME";

        public static CategoryListFragment newInstance(String categoryName) {
            Bundle b = new Bundle();
            b.putString(ARG_CATEGORY_NAME, categoryName);

            CategoryListFragment f = new CategoryListFragment();
            f.setArguments(b);

            return f;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            FrameLayout fl = new FrameLayout(getContext());
            fl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;

            TextView tv = new TextView(getContext());
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tv.setText(getArguments().getString(ARG_CATEGORY_NAME));
            tv.setTextColor(getResources().getColor(R.color.colorAccent));

            fl.addView(tv);

            return fl;
        }
    }

    public class CategoryAdapter extends FragmentPagerAdapter {
        CategoryAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public String getPageTitle(int position) {
            return mCategroyList.get(position);
        }

        @Override
        public int getCount() {
            return mCategroyList.size();
        }

        @Override
        public Fragment getItem(int position) {
            // 通过如下日志可以看出一开始只创建 0 和 1 两个 Fragment，当滑动到 1 时，创建 2 ，依次类推
            // 从 2 滑动回 1 时不会再重复创建
            //Log.e("slidingtabbardemo", "CategoryAdapter - getItem - position: " + position);
            return mvpFragmentList.get(position);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTabBar = (CategoryTabStrip)findViewById(R.id.cts_bar);

        ImageView tabMore = (ImageView)findViewById(R.id.iv_tab_more);
        tabMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((null != mvpArea)
                 && (null != mCategroyList)
                 && (0 < mCategroyList.size())) {
                    mCategoryNineBlockStyle.setVisibility(View.VISIBLE,
                                                          mCategroyList,
                                                          mvpArea.getCurrentItem());
                }
            }
        });

        mvpArea = (ViewPager)findViewById(R.id.vp_area);
        mvpArea.setAdapter(new CategoryAdapter(getSupportFragmentManager()));

        mCategoryNineBlockStyle = (CategoryNineBlockStyle)findViewById(R.id.nbs_choice_category);
        mCategoryNineBlockStyle.setOnClickListener(new CategoryNineBlockStyle.OnClickListener() {
            @Override
            public void onClick(int position) {
                if ((0 <= position)
                 && (mCategroyList.size() > position)) {
                    mTabBar.setCurrentItem(position);
                }
            }
        });

        updateTabBar();
    }

    // 生成测试数据
    private void updateTabBar() {
        mCategroyList.clear();
        mCategroyList.add("栏目01");
        mCategroyList.add("栏目02");
        mCategroyList.add("栏目03");
        mCategroyList.add("栏目04");
        mCategroyList.add("栏目05");
        mCategroyList.add("栏目06");
        mCategroyList.add("栏目07");
        mCategroyList.add("栏目08");
        mCategroyList.add("栏目09");
        mCategroyList.add("栏目11");
        mCategroyList.add("栏目12");
        mCategroyList.add("栏目13");
        mCategroyList.add("栏目14");
        mCategroyList.add("栏目15");
        mCategroyList.add("栏目16");
        mCategroyList.add("栏目17");
        mCategroyList.add("栏目18");
        mCategroyList.add("栏目19");
        mCategroyList.add("栏目21");
        mCategroyList.add("栏目22");
        mCategroyList.add("栏目23");
        mCategroyList.add("栏目24");
        mCategroyList.add("栏目25");
        mCategroyList.add("栏目26");
        mCategroyList.add("栏目27");
        mCategroyList.add("栏目28");
        mCategroyList.add("栏目29");

        mvpFragmentList.clear();
        for (int i = 0; i < mCategroyList.size(); ++i) {
            mvpFragmentList.add(CategoryListFragment.newInstance(mCategroyList.get(i)));
        }

        if ((null != mvpArea)
         && (null != mvpArea.getAdapter())) {
            mvpArea.getAdapter().notifyDataSetChanged();

            mTabBar.setViewPager(mvpArea);
        }
    }
}
