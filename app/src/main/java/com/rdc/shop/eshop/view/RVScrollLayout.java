package com.rdc.shop.eshop.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.rdc.shop.eshop.utils.CheckRvScrollUtil;

public class RVScrollLayout extends LinearLayout {

    private View mConvertView;
    private RecyclerView mRecyclerView;
    private int mStart;
    private int mEnd;
    private int mLastY;
    private Scroller mScroller;

    public RVScrollLayout(Context context) {
        this(context, null);
    }

    public RVScrollLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RVScrollLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            View view = getChildAt(0);
            view.layout(l, t, r, b);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new RuntimeException(RVScrollLayout.class.getSimpleName() + "只能有一个子控件");
        }
        mConvertView = getChildAt(0);
        if (mConvertView instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) mConvertView;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (! mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                scrollTo(0, (int) ((mLastY - y) * 0.4));
                break;
            case MotionEvent.ACTION_UP:
                mEnd = getScrollY();
                int dScrollY = mEnd - mStart;
                mScroller.startScroll(0, mEnd, 0, -dScrollY, 1000);
                break;
            default:
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mConvertView instanceof RecyclerView) {
                    if (y - mLastY > 0) {
                        if (CheckRvScrollUtil.isRecyclerViewToTop(mRecyclerView)) {
                            return true;
                        }
                    }
                    if (y - mLastY < 0) {
                        if (CheckRvScrollUtil.isRecyclerViewToBottom(mRecyclerView)) {
                            return true;
                        }
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }
}
