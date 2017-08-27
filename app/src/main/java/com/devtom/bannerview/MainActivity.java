package com.devtom.bannerview;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) this.findViewById(R.id.banner);

        BannerViewAdapter adapter = new BannerViewAdapter(this);
        List<String> data = new ArrayList<>();
        data.add("https://img6.bdstatic.com/img/image/smallpic/xiaoqingxbanq.jpg");
        data.add("https://img6.bdstatic.com/img/image/smallpic/fengjingxiaotu.jpg");
        data.add("https://img6.bdstatic.com/img/image/smallpic/touxixiaoqinx.jpg");
        adapter.setData(data);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, new CardPageTransformer());
        adapter.notifyDataSetChanged();
    }

    public static class BannerViewAdapter extends PagerAdapter {

        private Context context;
        private List<String> list = new ArrayList();
        private List<View> views = new ArrayList();

        public BannerViewAdapter(Context context) {
            this.context = context;

        }

        public void setData(List<String> list) {
            this.list = list;
            if (views.isEmpty()) {
                for (int i = 0; i <= list.size() + 1; i++) {
                    views.add(LayoutInflater.from(context).inflate(
                            R.layout.banner_item, null, false));
                }
            }
            notifyDataSetChanged();
        }

        public int getActualSize() {
            return list == null ? 0 : list.size();
        }

        @Override
        public int getCount() {
            return 2000;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int viewIndex = position % views.size();
            final int index = position % list.size();
            View root = views.get(viewIndex);
            if (root.getParent() != null) {
                ((ViewGroup) root.getParent()).removeView(root);
            }
            final String entity = list.get(index);
            final ImageView view = (ImageView) root;

            Glide.with(view.getContext()).load(entity).into(view);
            container.addView(root);
            return root;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }


        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    private static class CardPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {
            ImageView imageView = (ImageView) view;
            float scaleX = 1;
            float scaleY = 1;
            if (position <= 0) {
                scaleX = 0.07f * position + 1;
                scaleY = 0.3f * position + 1;
            } else {
                scaleX = -0.07f * position + 1;
                scaleY = -0.3f * position + 1;
            }

            if (scaleX == 0) {
                imageView.setImageAlpha(153);
            } else {
                imageView.setImageAlpha(255);
            }

            imageView.setScaleX(Math.max(scaleX, 0.8889f));
            imageView.setScaleY(scaleY);
        }
    }
}
