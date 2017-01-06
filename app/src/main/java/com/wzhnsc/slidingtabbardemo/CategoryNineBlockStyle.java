package com.wzhnsc.slidingtabbardemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CategoryNineBlockStyle extends FrameLayout {
    private View mContentView;
    private List<String> mCategoryList = new ArrayList<>();

    class TopicCategoryNineBlockViewHolder {
        // 第一行
        LinearLayout mllCategoryAreaRow1Col1;
        TextView     mtvCategoryNameRow1Col1;

        LinearLayout mllCategoryAreaRow1Col2;
        TextView     mtvCategoryNameRow1Col2;

        LinearLayout mllCategoryAreaRow1Col3;
        TextView     mtvCategoryNameRow1Col3;

        // 第二行
        LinearLayout mllCategoryAreaRow2Col1;
        TextView     mtvCategoryNameRow2Col1;

        LinearLayout mllCategoryAreaRow2Col2;
        TextView     mtvCategoryNameRow2Col2;

        LinearLayout mllCategoryAreaRow2Col3;
        TextView     mtvCategoryNameRow2Col3;

        // 第三行
        LinearLayout mllCategoryAreaRow3Col1;
        TextView     mtvCategoryNameRow3Col1;

        LinearLayout mllCategoryAreaRow3Col2;
        TextView     mtvCategoryNameRow3Col2;

        LinearLayout mllCategoryAreaRow3Col3;
        TextView     mtvCategoryNameRow3Col3;
    }

    public interface OnClickListener {
        void onClick(int position); // 点击项的位置
    }

    private ViewPager mvpCategoryNineBlockArea;

    private CategoryNineBlockStyle.OnClickListener mtOnClickListener;

    private int mCurrentPosition = 0;

    private void clickCategoryItem(int clickPosition) {
        // 参数合法且需要触发点击事件
        if ((mCategoryList.size() > clickPosition)
         && (0 <= clickPosition)
         && (null != mtOnClickListener)) {
            mtOnClickListener.onClick(clickPosition);
        }

        setVisibility(GONE);
    }

    private View createPageView(final int position) {
        View contentView = inflate(getContext(), R.layout.layout_category_nine_block_area, null);

        TopicCategoryNineBlockViewHolder viewHolder = new TopicCategoryNineBlockViewHolder();

        // 第一行
        viewHolder.mllCategoryAreaRow1Col1 = (LinearLayout)contentView.findViewById(R.id.ll_category_nine_block_area_row1_col1);
        viewHolder.mllCategoryAreaRow1Col1.setVisibility(INVISIBLE);
        viewHolder.mllCategoryAreaRow1Col1.setOnClickListener(null);

        viewHolder.mtvCategoryNameRow1Col1 = (TextView)contentView.findViewById(R.id.tv_category_nine_block_area_row1_col1_name);

        viewHolder.mllCategoryAreaRow1Col2 = (LinearLayout)contentView.findViewById(R.id.ll_category_nine_block_area_row1_col2);
        viewHolder.mllCategoryAreaRow1Col2.setVisibility(INVISIBLE);
        viewHolder.mllCategoryAreaRow1Col2.setOnClickListener(null);

        viewHolder.mtvCategoryNameRow1Col2 = (TextView)contentView.findViewById(R.id.tv_category_nine_block_area_row1_col2_name);

        viewHolder.mllCategoryAreaRow1Col3 = (LinearLayout)contentView.findViewById(R.id.ll_category_nine_block_area_row1_col3);
        viewHolder.mllCategoryAreaRow1Col3.setVisibility(INVISIBLE);
        viewHolder.mllCategoryAreaRow1Col3.setOnClickListener(null);

        viewHolder.mtvCategoryNameRow1Col3 = (TextView)contentView.findViewById(R.id.tv_category_nine_block_area_row1_col3_name);

        // 第二行
        viewHolder.mllCategoryAreaRow2Col1 = (LinearLayout)contentView.findViewById(R.id.ll_category_nine_block_area_row2_col1);
        viewHolder.mllCategoryAreaRow2Col1.setVisibility(INVISIBLE);
        viewHolder.mllCategoryAreaRow2Col1.setOnClickListener(null);

        viewHolder.mtvCategoryNameRow2Col1 = (TextView)contentView.findViewById(R.id.tv_category_nine_block_area_row2_col1_name);

        viewHolder.mllCategoryAreaRow2Col2 = (LinearLayout)contentView.findViewById(R.id.ll_category_nine_block_area_row2_col2);
        viewHolder.mllCategoryAreaRow2Col2.setVisibility(INVISIBLE);
        viewHolder.mllCategoryAreaRow2Col2.setOnClickListener(null);

        viewHolder.mtvCategoryNameRow2Col2 = (TextView)contentView.findViewById(R.id.tv_category_nine_block_area_row2_col2_name);

        viewHolder.mllCategoryAreaRow2Col3 = (LinearLayout)contentView.findViewById(R.id.ll_category_nine_block_area_row2_col3);
        viewHolder.mllCategoryAreaRow2Col3.setVisibility(INVISIBLE);
        viewHolder.mllCategoryAreaRow2Col3.setOnClickListener(null);

        viewHolder.mtvCategoryNameRow2Col3 = (TextView)contentView.findViewById(R.id.tv_category_nine_block_area_row2_col3_name);

        // 第三行
        viewHolder.mllCategoryAreaRow3Col1 = (LinearLayout)contentView.findViewById(R.id.ll_category_nine_block_area_row3_col1);
        viewHolder.mllCategoryAreaRow3Col1.setVisibility(INVISIBLE);
        viewHolder.mllCategoryAreaRow3Col1.setOnClickListener(null);

        viewHolder.mtvCategoryNameRow3Col1 = (TextView)contentView.findViewById(R.id.tv_category_nine_block_area_row3_col1_name);

        viewHolder.mllCategoryAreaRow3Col2 = (LinearLayout)contentView.findViewById(R.id.ll_category_nine_block_area_row3_col2);
        viewHolder.mllCategoryAreaRow3Col2.setVisibility(INVISIBLE);
        viewHolder.mllCategoryAreaRow3Col2.setOnClickListener(null);

        viewHolder.mtvCategoryNameRow3Col2 = (TextView)contentView.findViewById(R.id.tv_category_nine_block_area_row3_col2_name);

        viewHolder.mllCategoryAreaRow3Col3 = (LinearLayout)contentView.findViewById(R.id.ll_category_nine_block_area_row3_col3);
        viewHolder.mllCategoryAreaRow3Col3.setVisibility(INVISIBLE);
        viewHolder.mllCategoryAreaRow3Col3.setOnClickListener(null);

        viewHolder.mtvCategoryNameRow3Col3 = (TextView)contentView.findViewById(R.id.tv_category_nine_block_area_row3_col3_name);

        for (int i = 0; i < 9; ++i) {
            // 无数据可添充立即跳出
            if ((position * 9 + i) >= mCategoryList.size()) {
                break;
            }

            switch (i) {
            // 第一行
            case 0:
                viewHolder.mtvCategoryNameRow1Col1.setText(CommonUitils.formatTabName(mCategoryList.get(position * 9 + i), 5)); // 超过五个字符截取加省略号(...)

                if ((position * 9 + i) == mCurrentPosition) {
                    viewHolder.mtvCategoryNameRow1Col1.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                }
                else {
                    viewHolder.mtvCategoryNameRow1Col1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                viewHolder.mllCategoryAreaRow1Col1.setVisibility(VISIBLE);
                viewHolder.mllCategoryAreaRow1Col1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCategoryItem(position * 9);
                    }
                });
                break;

            case 1:
                viewHolder.mtvCategoryNameRow1Col2.setText(CommonUitils.formatTabName(mCategoryList.get(position * 9 + i), 5)); // 超过五个字符截取加省略号(...)

                if ((position * 9 + i) == mCurrentPosition) {
                    viewHolder.mtvCategoryNameRow1Col2.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                }
                else {
                    viewHolder.mtvCategoryNameRow1Col2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                viewHolder.mllCategoryAreaRow1Col2.setVisibility(VISIBLE);
                viewHolder.mllCategoryAreaRow1Col2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCategoryItem(position * 9 + 1);
                    }
                });
                break;

            case 2:
                viewHolder.mtvCategoryNameRow1Col3.setText(CommonUitils.formatTabName(mCategoryList.get(position * 9 + i), 5)); // 超过五个字符截取加省略号(...)

                if ((position * 9 + i) == mCurrentPosition) {
                    viewHolder.mtvCategoryNameRow1Col3.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                }
                else {
                    viewHolder.mtvCategoryNameRow1Col3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                viewHolder.mllCategoryAreaRow1Col3.setVisibility(VISIBLE);
                viewHolder.mllCategoryAreaRow1Col3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCategoryItem(position * 9 + 2);
                    }
                });
                break;

            // 第二行
            case 3:
                viewHolder.mtvCategoryNameRow2Col1.setText(CommonUitils.formatTabName(mCategoryList.get(position * 9 + i), 5)); // 超过五个字符截取加省略号(...)

                if ((position * 9 + i) == mCurrentPosition) {
                    viewHolder.mtvCategoryNameRow2Col1.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                }
                else {
                    viewHolder.mtvCategoryNameRow2Col1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                viewHolder.mllCategoryAreaRow2Col1.setVisibility(VISIBLE);
                viewHolder.mllCategoryAreaRow2Col1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCategoryItem(position * 9 + 3);
                    }
                });
                break;

            case 4:
                viewHolder.mtvCategoryNameRow2Col2.setText(CommonUitils.formatTabName(mCategoryList.get(position * 9 + i), 5)); // 超过五个字符截取加省略号(...)

                if ((position * 9 + i) == mCurrentPosition) {
                    viewHolder.mtvCategoryNameRow2Col2.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                }
                else {
                    viewHolder.mtvCategoryNameRow2Col2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                viewHolder.mllCategoryAreaRow2Col2.setVisibility(VISIBLE);
                viewHolder.mllCategoryAreaRow2Col2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCategoryItem(position * 9 + 4);
                    }
                });
                break;

            case 5:
                viewHolder.mtvCategoryNameRow2Col3.setText(CommonUitils.formatTabName(mCategoryList.get(position * 9 + i), 5)); // 超过五个字符截取加省略号(...)

                if ((position * 9 + i) == mCurrentPosition) {
                    viewHolder.mtvCategoryNameRow2Col3.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                }
                else {
                    viewHolder.mtvCategoryNameRow2Col3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                viewHolder.mllCategoryAreaRow2Col3.setVisibility(VISIBLE);
                viewHolder.mllCategoryAreaRow2Col3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCategoryItem(position * 9 + 5);
                    }
                });
                break;

            // 第三行
            case 6:
                viewHolder.mtvCategoryNameRow3Col1.setText(CommonUitils.formatTabName(mCategoryList.get(position * 9 + i), 5)); // 超过五个字符截取加省略号(...)

                if ((position * 9 + i) == mCurrentPosition) {
                    viewHolder.mtvCategoryNameRow3Col1.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                }
                else {
                    viewHolder.mtvCategoryNameRow3Col1.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                viewHolder.mllCategoryAreaRow3Col1.setVisibility(VISIBLE);
                viewHolder.mllCategoryAreaRow3Col1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCategoryItem(position * 9 + 6);
                    }
                });
                break;

            case 7:
                viewHolder.mtvCategoryNameRow3Col2.setText(CommonUitils.formatTabName(mCategoryList.get(position * 9 + i), 5)); // 超过五个字符截取加省略号(...)

                if ((position * 9 + i) == mCurrentPosition) {
                    viewHolder.mtvCategoryNameRow3Col2.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                }
                else {
                    viewHolder.mtvCategoryNameRow3Col2.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                viewHolder.mllCategoryAreaRow3Col2.setVisibility(VISIBLE);
                viewHolder.mllCategoryAreaRow3Col2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCategoryItem(position * 9 + 7);
                    }
                });
                break;

            case 8:
                viewHolder.mtvCategoryNameRow3Col3.setText(CommonUitils.formatTabName(mCategoryList.get(position * 9 + i), 5)); // 超过五个字符截取加省略号(...)

                if ((position * 9 + i) == mCurrentPosition) {
                    viewHolder.mtvCategoryNameRow3Col3.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
                }
                else {
                    viewHolder.mtvCategoryNameRow3Col3.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }

                viewHolder.mllCategoryAreaRow3Col3.setVisibility(VISIBLE);
                viewHolder.mllCategoryAreaRow3Col3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCategoryItem(position * 9 + 8);
                    }
                });
                break;
            }
        }

        contentView.setTag(viewHolder);

        return contentView;
    }

    public class TopicCategoryNineBlockPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            int pagerNum;

            if ((null == mCategoryList)
             || (0 >= mCategoryList.size())) {
                pagerNum = 0;
            }
            else if (9 >= mCategoryList.size()) {
                pagerNum = 1;
            }
            else {
                pagerNum = mCategoryList.size() / 9;

                // 多出一页且不足九个
                if (0 != (mCategoryList.size() % 9)) {
                    ++pagerNum;
                }
            }

            return pagerNum;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final View view = createPageView(position);

            container.addView(view);

            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    TopicCategoryNineBlockPagerAdapter mTopicCategoryNineBlockPagerAdapter = new TopicCategoryNineBlockPagerAdapter();

    public CategoryNineBlockStyle(Context context) {
        this(context, null);
    }

    public CategoryNineBlockStyle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryNineBlockStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContentView = inflate(context, R.layout.layout_category_nine_block, this);
    }

    public void setOnClickListener(CategoryNineBlockStyle.OnClickListener tOnClickListener) {
        mtOnClickListener = tOnClickListener;
    }

    private View.OnClickListener mvOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setVisibility(GONE);
        }
    };

    private void referenceUI() {
        ImageView ivClose = (ImageView)mContentView.findViewById(R.id.iv_category_nine_block_close);
        ivClose.setOnClickListener(mvOnClickListener);

        View viewClose = mContentView.findViewById(R.id.v_category_nine_block_close);
        viewClose.setOnClickListener(mvOnClickListener);

        mvpCategoryNineBlockArea = (ViewPager)findViewById(R.id.vp_category_nine_block_area);
        mvpCategoryNineBlockArea.setAdapter(mTopicCategoryNineBlockPagerAdapter);
    }

    // 显示/隐藏本卡片
    public void setVisibility(int          visibility,
                              List<String> categoryList,
                              int          position) {
        // 统一为消失不占空间
        if ((INVISIBLE == visibility)
         || (GONE == visibility)){
            setVisibility(GONE);
            return;
        }

        // 不可识别类型
        if (VISIBLE != visibility) {
            setVisibility(GONE);
            return;
        }

        // 显示本卡片时，如果无数据可显示则不显示
        if ((0 >= categoryList.size())
         || (0 > position)
         || (categoryList.size() <= position)) {
            setVisibility(GONE);
            return;
        }

        mCurrentPosition = position;

        // 注意：要先显示出来，才有显示资源可以操作！
        setVisibility(VISIBLE);

        // 布局方式引用本类，且 android:visibility="gone" 会造成引用的界面元素为无效对象
        referenceUI();

        // 不为消失就是要显示
        mCategoryList.clear();
        mCategoryList.addAll(categoryList);

        mvpCategoryNineBlockArea.getAdapter().notifyDataSetChanged();

        mvpCategoryNineBlockArea.setCurrentItem(mCurrentPosition / 9);
    }
}
