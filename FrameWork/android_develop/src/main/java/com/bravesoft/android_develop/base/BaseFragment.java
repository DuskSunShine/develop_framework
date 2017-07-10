package com.bravesoft.android_develop.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**fragment 基类
 * Created by SCY on 2017/7/4 13:38.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
        private Context context;
        protected View rootView;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if (rootView == null) {
                rootView = inflater.inflate(getResource(), container, false);
            }
            //rootViewに複数のparentをつけないように処理
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            this.context = context;
        }


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            beforeInitView();
            initView(rootView);
            initData();
        }


        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {

            super.setUserVisibleHint(isVisibleToUser);
        }

        protected <T extends View> T findViewByIdNoCast(int id) {
            return rootView == null ? null : (T) rootView.findViewById(id);
        }

        protected abstract int getResource();

        protected abstract void beforeInitView();

        protected abstract void initView(View rootView);

        protected abstract void initData();

        /**
         * 通过ID设置点击
         *
         * @param ids
         */
        protected void setOnClick(int... ids) {

            for (int id : ids) {
                if (id != -1)
                    findViewByIdNoCast(id).setOnClickListener(this);
            }

        }

        public void setOnClick(View... views) {
            for (View view : views)
                view.setOnClickListener(this);

        }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
