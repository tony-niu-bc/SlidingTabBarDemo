package com.wzhnsc.slidingtabbardemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CategoryTabStrip extends HorizontalScrollView {

    private LinearLayout.LayoutParams mDefaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                                                                              LayoutParams.MATCH_PARENT);

    private LayoutInflater mLayoutInflater;

    // 滑动过程中矩形高亮区域的上下左右位置
    private Drawable mIndicator;
    private Rect     mIndicatorRect = new Rect();
    private int      mIndicatorHeight = 2; // 像素转成dp

    private int mScrollOffset = 10;
    private int mLastScrollX = 0;

    private LinearLayout mTabsContainer;
    private int          mTabCount = 0;

    private ViewPager mViewPager;
    private int       mLastPosition    = -1;
    private int       mCurrentPosition = 0;
    private float     mCurrentPositionOffset = 0f;

    private Drawable mRightEdge;

    public CategoryTabStrip(Context context) {
        this(context, null);
    }

    public CategoryTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CategoryTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mLayoutInflater = LayoutInflater.from(context);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScrollOffset = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mScrollOffset, dm);
        mIndicatorHeight = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mIndicatorHeight, dm);;

        // 绘制高亮区域作为滑动分页指示器
        mIndicator = getResources().getDrawable(R.drawable.category_indicator_bg);

        mRightEdge = getResources().getDrawable(R.drawable.icon_category_right_edge);

        // true - 拉伸当前滚动视图的内容宽度以填充可视范围
        setFillViewport(true);

        // 没有执行 onDraw() 方法，
        // 那么你需要在你直接或者间接继承 View 的类的构造函数中加入下面的语句且参数置为 false
        setWillNotDraw(false);

        mTabsContainer = new LinearLayout(context);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        mTabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(mTabsContainer);
    }

    // 绑定与 CategoryTabStrip 控件对应的 ViewPager 控件，实现联动
    public void setViewPager(ViewPager viewPager) {
        // 先确保传入的 ViewPager 对象是绑定有适配器的！
        if (null == viewPager.getAdapter()) {
            throw new IllegalStateException("ViewPager have not the instance of adapter!");
        }

        mViewPager = viewPager;

        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            // 此方法是在页面滑动的时候调用，在滑动被停止之前，此方法会一直被调用
            @Override
            public void onPageScrolled(int   position,               // 当前页面，左滑为当前页面，右滑为目标页面，滑动造成页面改变则此值也会变
                                       float positionOffset,         // 当前页面偏移的百分比，滑动前和结束时，为零(0.0)值
                                       int   positionOffsetPixels) { // 当前页面偏移的像素位置，滑动前和结束时，为零(0)值
                // CategoryTabStrip - onPageScrolled - position: 0 positionOffset: 0.0 positionOffsetPixels: 0
                // CategoryTabStrip - onPageScrolled - position: 0 positionOffset: 0.004166667 positionOffsetPixels: 3
                //Log.e("slidingtabbardemo",
                //      "CategoryTabStrip - onPageScrolled - position: " + position +
                //      " positionOffset: " + positionOffset +
                //      " positionOffsetPixels: " + positionOffsetPixels);

                mCurrentPosition       = position;
                mCurrentPositionOffset = positionOffset;

                scrollToChild();

                invalidate();
            }

            // 此方法是在状态改变的时候调用
            @Override
            public void onPageScrollStateChanged(int state) {
                // state = 0 时表示什么都没做，= 1 时表示正在滑动，= 2 时表示滑动完毕了!
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (0 == mViewPager.getCurrentItem()) {
                        // 滑动到最左边
                        scrollTo(0, 0);
                    }
                    else if ((mTabCount - 1) == mViewPager.getCurrentItem()) {
                        //Log.e("slidingtabbardemo",
                        //      "CategoryTabStrip - onPageScrollStateChanged - getScrollRange(): " + getScrollRange());

                        // 滑动到最右边
                        scrollTo(getScrollRange(), 0);
                    }
                    else {
                        scrollToChild();
                    }
                }
            }

            // 此方法是页面跳转完后被调用
            @Override
            public void onPageSelected(int position) { // 当前选中的页面的位置编号
                if (0 < mTabsContainer.getChildCount()) {
                    // 先修改当前选中标签的颜色
                    ViewGroup vgTab      = (ViewGroup)mTabsContainer.getChildAt(position);
                    TextView  tvCategory = (TextView)vgTab.findViewById(R.id.category_text);

                    tvCategory.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));

                    // 再修改上个选中标签的颜色
                    if (-1 != mLastPosition) {
                        vgTab      = (ViewGroup)mTabsContainer.getChildAt(mLastPosition);
                        tvCategory = (TextView)vgTab.findViewById(R.id.category_text);
                        tvCategory.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                }

                mLastPosition = position;
            }
        });

        if (0 < mViewPager.getAdapter().getCount()) {
            mViewPager.setCurrentItem(0);
            mLastPosition = 0;
        }

        notifyDataSetChanged();
    }

    // 当附加在 ViewPager 适配器上的数据发生变化时，应该调用该方法通知 CategoryTabStrip 刷新数据
    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();

        mTabCount = mViewPager.getAdapter().getCount();

        for (int i = 0; i < mTabCount; ++i) {
            addTab(i, mViewPager.getAdapter().getPageTitle(i).toString());
        }
    }

    // 指定位置的标签为当前选中
    public void setCurrentItem(final int position) {
        // 先确保传入的 ViewPager 对象是绑定有适配器的！
        if ((null != mViewPager)
         && (null != mViewPager.getAdapter())
         && (0 <= position)
         && (mViewPager.getAdapter().getCount() > position)) {
            mViewPager.setCurrentItem(position);
        }
    }

    private void addTab(final int position, String title) {
        ViewGroup vgTab = (ViewGroup)mLayoutInflater.inflate(R.layout.layout_category_tab, this, false);

        TextView tvCategory = (TextView)vgTab.findViewById(R.id.category_text);
        tvCategory.setText(CommonUitils.formatTabName(title, 4)); // 超过4个字符截取加省略号(...)
        tvCategory.setGravity(Gravity.CENTER);
        tvCategory.setSingleLine();
        tvCategory.setFocusable(true);

        if (mViewPager.getCurrentItem() == position) {
            tvCategory.setTextColor(getResources().getColor(R.color.colorPrimaryGreen));
        }
        else {
            tvCategory.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        vgTab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position);
            }
        });

        mTabsContainer.addView(vgTab, position, mDefaultTabLayoutParams);
    }

    // 计算滑动过程中矩形高亮区域的上下左右位置
    private void calculateIndicatorRect(Rect rect) {
        ViewGroup vgCurrentTab = (ViewGroup)mTabsContainer.getChildAt(mCurrentPosition);
        TextView  tvCategory   = (TextView)vgCurrentTab.findViewById(R.id.category_text);

        // 算出相对水平滑动容器控件中的线性布局容器左边的当前选项标签文本控件左边和右边位置
        //Log.e("slidingtabbardemo",
        //      "CategoryTabStrip - calculateIndicatorRect - " +
        //      "vgCurrentTab.getLeft(): " + vgCurrentTab.getLeft() +
        //      " tvCategory.getLeft(): " + tvCategory.getLeft() +
        //      " tvCategory.getWidth(): " + tvCategory.getWidth());

        float left  = (float)(vgCurrentTab.getLeft() + tvCategory.getLeft());
        float width = ((float)tvCategory.getWidth()) + left;

        // 当前页面偏移的百分比大于零，且不是最后一个选项标签
        if ((0f < mCurrentPositionOffset)
         && ((mTabCount - 1) > mCurrentPosition)) {
            ViewGroup vgNextTab      = (ViewGroup)mTabsContainer.getChildAt(mCurrentPosition + 1);
            TextView  tvNextCategory = (TextView)vgNextTab.findViewById(R.id.category_text);

            float nextLeft  = (float)(vgNextTab.getLeft() + tvNextCategory.getLeft());
            float nextWidth = ((float)tvNextCategory.getWidth()) + nextLeft;

            float difference = 1.0f - mCurrentPositionOffset;

            left  = left  * difference + mCurrentPositionOffset * nextLeft;
            width = width * difference + mCurrentPositionOffset * nextWidth;
        }

        //Log.e("slidingtabbardemo",
        //      "CategoryTabStrip - calculateIndicatorRect - " +
        //      "getPaddingLeft(): " + getPaddingLeft() +
        //      " getPaddingTop(): " + getPaddingTop());

        int top = getPaddingTop() + vgCurrentTab.getTop() + tvCategory.getTop();

        // 此区域大小则为当前选项标签文本控件的大小，但位置是随滑动改变的
        rect.set(getPaddingLeft() + ((int)left), // 左边要加上水平滑动容器的左边填充宽度
                 top,
                 ((int)width) + vgCurrentTab.getPaddingRight(),
                 top + tvCategory.getHeight());
    }

    // 计算显示最右边末尾内容需滚动的范围，即向左移出可见区域的部分宽度
    private int getScrollRange() {
        //Log.e("slidingtabbardemo",
        //      "CategoryTabStrip - getScrollRange - " +
        //      " getChildAt(0).getWidth(): " + getChildAt(0).getWidth() +
        //      " getWidth(): " + getWidth() +
        //      " getPaddingLeft(): " + getPaddingLeft() +
        //      " getPaddingRight(): " + getPaddingRight());

        return getChildCount() > 0 ? Math.max(0,
                                              // 选项标签所在的线性布局容器的宽度 - 水平滑动容器的宽度的差，
                                              // 再加上水平滑动容器的左右填充宽度
                                              (getChildAt(0).getWidth() - getWidth()) +
                                              getPaddingLeft() + getPaddingRight()) :
                                     0;
    }

    private void scrollToChild() {
        // 无选项标签直接退出
        if (0 == mTabCount) {
            return;
        }

        calculateIndicatorRect(mIndicatorRect);

        int newScrollX = mLastScrollX;

        // 左/右移动水平滑动容器的内容，以便让选项标签文本显示出来
        if ((getScrollX() + mScrollOffset) > mIndicatorRect.left) {
            newScrollX = mIndicatorRect.left - mScrollOffset;
        }
        else if ((getScrollX() + getWidth() - mScrollOffset) < mIndicatorRect.right) {
            newScrollX = mIndicatorRect.right - getWidth() + mScrollOffset;
        }

        //Log.e("slidingtabbardemo",
        //      "CategoryTabStrip - scrollToChild - getScrollX(): " + getScrollX() +
        //      " newScrollX: " + newScrollX +
        //      " mLastScrollX: " + mLastScrollX +
        //      " mIndicatorRect.left: " + mIndicatorRect.left +
        //      " mScrollOffset: " + mScrollOffset);

        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // 无选项标签直接退出
        if (0 == mTabCount) {
            return;
        }

        calculateIndicatorRect(mIndicatorRect);

        // 画文字背后的背景
        if (null != mIndicator) {
            int tmpTop = mIndicatorRect.top;
            mIndicatorRect.top = mIndicatorRect.bottom - mIndicatorHeight;
            mIndicator.setBounds(mIndicatorRect);
            mIndicator.draw(canvas);

            mIndicatorRect.top = tmpTop;
        }

        // 如下代码是在水平滑动容器右边画个阴影
        if (null == mRightEdge) {
            return; // 没有阴影可画就跳出
        }

        int i = canvas.save();

        int scrollX = getScrollX();
        canvas.translate((float)scrollX, 0.0f);

        // 移动到最右边时不用再画右边阴影了
        if (getScrollRange() <= scrollX) {
            canvas.restoreToCount(i);
            return;
        }

        int height  = getHeight();
        int width   = getWidth();

        if (null != mRightEdge) {
            mRightEdge.setBounds((width - mRightEdge.getIntrinsicWidth()),
                                 0,
                                 width,
                                 height - mIndicatorHeight);
            mRightEdge.draw(canvas);
        }

        canvas.restoreToCount(i);
    }
}
