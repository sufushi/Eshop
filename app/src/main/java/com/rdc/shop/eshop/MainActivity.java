package com.rdc.shop.eshop;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.rdc.shop.eshop.R;
import com.rdc.shop.eshop.base.BaseActivity;
import com.rdc.shop.eshop.fragment.ClassifyFragment;
import com.rdc.shop.eshop.fragment.HomeFragment;
import com.rdc.shop.eshop.fragment.PersonFragment;
import com.rdc.shop.eshop.fragment.TrolleyFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_home)
    TextView mTvHome;
    @BindView(R.id.tv_classify)
    TextView mTvClassify;
    @BindView(R.id.tv_trolley)
    TextView mTvTrolley;
    @BindView(R.id.tv_person)
    TextView mTvPerson;

    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private ClassifyFragment mClassifyFragment;
    private TrolleyFragment mTrolleyFragment;
    private PersonFragment mPersonFragment;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mFragmentManager = getSupportFragmentManager();
        mTvHome.performClick();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.tv_home, R.id.tv_classify, R.id.tv_trolley, R.id.tv_person})
    public void onViewClicked(View view) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.tv_home:
                hideFragments(fragmentTransaction);
                setSelectNone();
                mTvHome.setSelected(true);
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance(getString(R.string.string_home));
                    fragmentTransaction.add(R.id.fl_content, mHomeFragment);
                } else {
                    fragmentTransaction.show(mHomeFragment);
                }
                break;
            case R.id.tv_classify:
                hideFragments(fragmentTransaction);
                setSelectNone();
                mTvClassify.setSelected(true);
                if (mClassifyFragment == null) {
                    mClassifyFragment = ClassifyFragment.newInstance(getString(R.string.string_classify));
                    fragmentTransaction.add(R.id.fl_content, mClassifyFragment);
                } else {
                    fragmentTransaction.show(mClassifyFragment);
                }
                break;
            case R.id.tv_trolley:
                hideFragments(fragmentTransaction);
                setSelectNone();
                mTvTrolley.setSelected(true);
                if (mTrolleyFragment == null) {
                    mTrolleyFragment = TrolleyFragment.newInstance(getString(R.string.string_trolley));
                    fragmentTransaction.add(R.id.fl_content, mTrolleyFragment);
                } else {
                    fragmentTransaction.show(mTrolleyFragment);
                }
                break;
            case R.id.tv_person:
                hideFragments(fragmentTransaction);
                setSelectNone();
                mTvPerson.setSelected(true);
                if (mPersonFragment == null) {
                    mPersonFragment = PersonFragment.newInstance(getString(R.string.string_person));
                    fragmentTransaction.add(R.id.fl_content, mPersonFragment);
                } else {
                    fragmentTransaction.show(mPersonFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    private void setSelectNone() {
        mTvHome.setSelected(false);
        mTvClassify.setSelected(false);
        mTvTrolley.setSelected(false);
        mTvPerson.setSelected(false);
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mClassifyFragment != null) {
            fragmentTransaction.hide(mClassifyFragment);
        }
        if (mTrolleyFragment != null) {
            fragmentTransaction.hide(mTrolleyFragment);
        }
        if (mPersonFragment != null) {
            fragmentTransaction.hide(mPersonFragment);
        }
    }
}
