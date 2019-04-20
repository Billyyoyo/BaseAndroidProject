package fun.flyee.sunshine4u.android.modules.mall;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;
import fun.flyee.sunshine4u.android.models.Merchandise;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchandiseBuyFragment extends BaseFragment {

    private Merchandise merchandise;

    public static MerchandiseBuyFragment newInstance(Merchandise mer){
        MerchandiseBuyFragment fragment = new MerchandiseBuyFragment();
        fragment.merchandise = mer;
        return fragment;
    }

    public MerchandiseBuyFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutId(){
        return R.layout.fragment_merchandise_buy;
    }

}
