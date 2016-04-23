package xzy.zzia.com.landscaping.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.banner.anim.select.ZoomInEnter;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.flyco.dialog.widget.base.BottomBaseDialog;

import xzy.zzia.com.landscaping.R;
import xzy.zzia.com.landscaping.banner.SimpleImageBanner;
import xzy.zzia.com.landscaping.util.DataProvider;
import xzy.zzia.com.landscaping.util.ToastUtils;
import xzy.zzia.com.landscaping.util.ViewFindUtils;

/**
 * Created by roy on 16/4/20.
 */
public class BannerDialog extends BottomBaseDialog {

    private SimpleImageBanner sib;
    private Class<? extends ViewPager.PageTransformer> transformerClass;

    public BannerDialog(Context context, View animateView) {
        super(context, animateView);
    }

    public BannerDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        View inflate = View.inflate(mContext, R.layout.dialog_banner, null);
        sib = ViewFindUtils.find(inflate, R.id.sib);

        return inflate;
    }

    public BannerDialog transformerClass(Class<? extends ViewPager.PageTransformer> transformerClass) {
        this.transformerClass = transformerClass;
        return this;
    }

    @Override
    public void setUiBeforShow() {
        sib();
    }

    private void sib() {
        sib.setIndicatorGap(8)
                .setSelectAnimClass(ZoomInEnter.class)
                .setBarColor(Color.parseColor("#88000000"))
                .setSource(DataProvider.getList())
                .setTransformerClass(transformerClass)
                .startScroll();

        sib.setOnItemClickL(new BaseBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showShort(mContext, "position---->" + position);
            }
        });
    }
}
