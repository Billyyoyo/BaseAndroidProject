package fun.flyee.sunshine4u.android.modules.mall;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.models.Merchandise;
import fun.flyee.sunshine4u.android.utils.ImageLoader;

public class MerchandiseHoder extends RecyclerView.ViewHolder {

    @BindView(R.id.pic_view)
    public ImageView picView;
    @BindView(R.id.desc_view)
    public TextView descView;
    @BindView(R.id.price_view)
    public TextView priceView;
    @BindView(R.id.saled_view)
    public TextView saledView;

    private Merchandise merchandise;

    public MerchandiseHoder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindData(Merchandise mer) {
        this.merchandise = mer;
        ImageLoader.squareImage(picView, merchandise.pic, R.mipmap.ic_launcher);
        descView.setText(merchandise.description);
        priceView.setText("￥" + merchandise.salesPrice);
        saledView.setText("销量 " + merchandise.saled);
    }
}
