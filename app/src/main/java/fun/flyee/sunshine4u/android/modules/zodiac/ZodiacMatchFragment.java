package fun.flyee.sunshine4u.android.modules.zodiac;


import android.app.Fragment;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZodiacMatchFragment extends BaseFragment {

    public static ZodiacMatchFragment newInstance(){
        ZodiacMatchFragment fragment = new ZodiacMatchFragment();
        return fragment;
    }

    public ZodiacMatchFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutId(){
        return R.layout.fragment_list;
    }

}
