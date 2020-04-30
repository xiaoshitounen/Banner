package swu.xl.banner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import swu.xl.pagecontrollerlibrary.PagerController;

public class MainActivity extends AppCompatActivity {

    //图片资源
    private int[] resources = {R.drawable.pic_1,R.drawable.pic_2,R.drawable.pic_3};
    private String[] texts = {"桥边美景","万千大厦","火影鸣人"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ViewPager
        final ViewPager viewPager = findViewById(R.id.pager);
        //PageController
        final PagerController pagerController = findViewById(R.id.page_controller);

        //设置总共的个数
        pagerController.setNumberOfPage(resources.length);

        //设置动画
        pagerController.setPageChangeAnimation(new PagerController.PageChangeAnimation() {
            @Override
            public void changeAnimation(View last_dot, View current_dot) {
                //上一个点不做动画

                //针对当前点的动画
                ObjectAnimator scale = ObjectAnimator.ofFloat(current_dot, "scaleX", 1.0f, 1.2f, 1.0f);
                scale.setDuration(500);
                scale.start();
            }
        });

        //监听页面切换
        pagerController.setPageChangeListener(new PagerController.PageChangeListener() {
            @Override
            public void pageHasChange(int currentPage) {

                //获得真正的position
                int realPosition = viewPager.getCurrentItem() % resources.length;

                //判断左滑还是右滑
                if ((currentPage == 0 && realPosition == resources.length-1)
                        || (currentPage > realPosition && !(currentPage == resources.length-1 && realPosition == 0))) {
                    //右滑
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1,true);
                }else {
                    //左滑
                    viewPager.setCurrentItem(viewPager.getCurrentItem()-1,true);
                }

            }
        });

        //设置适配器
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                if (resources.length == 1){
                    return 1;
                }

                return 500;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return object == view;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                //获得真正的position
                int realPosition = position % resources.length;

                //设置布局
                View inflate = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pager_layout, null);
                ImageView imageView = inflate.findViewById(R.id.image_view);
                imageView.setImageResource(resources[realPosition]);
                TextView textView = inflate.findViewById(R.id.text);
                textView.setText(texts[realPosition]);

                //添加视图
                container.addView(inflate);

                return inflate;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });

        //设置当前显示的页面
        viewPager.setCurrentItem(240);

        //设置页面监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //获得真正的position
                int realPosition = position % resources.length;

                //页面控制器滑动
                pagerController.setCurrentPage(realPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
