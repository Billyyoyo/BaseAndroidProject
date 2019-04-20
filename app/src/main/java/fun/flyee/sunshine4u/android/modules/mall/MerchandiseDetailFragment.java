package fun.flyee.sunshine4u.android.modules.mall;


import android.app.Fragment;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;
import fun.flyee.sunshine4u.android.models.Merchandise;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchandiseDetailFragment extends BaseFragment {


    private Merchandise merchandise;

    public static MerchandiseDetailFragment newInstance(Merchandise mer){
        MerchandiseDetailFragment fragment = new MerchandiseDetailFragment();
        fragment.merchandise = mer;
        return fragment;
    }

    public MerchandiseDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutId(){
        return R.layout.fragment_merchandise_detail;
    }

}
