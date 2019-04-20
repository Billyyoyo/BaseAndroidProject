package fun.flyee.sunshine4u.android.modules.mall;


import android.app.Fragment;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;
import fun.flyee.sunshine4u.android.models.Merchandise;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchandiseCommentFragment extends BaseFragment {

    private Merchandise merchandise;

    public static MerchandiseCommentFragment newInstance(Merchandise mer){
        MerchandiseCommentFragment fragment = new MerchandiseCommentFragment();
        fragment.merchandise = mer;
        return fragment;
    }

    public MerchandiseCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutId(){
        return R.layout.fragment_merchandise_buy;
    }

}
