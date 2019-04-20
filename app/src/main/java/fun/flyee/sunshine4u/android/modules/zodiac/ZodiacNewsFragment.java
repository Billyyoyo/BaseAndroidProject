package fun.flyee.sunshine4u.android.modules.zodiac;


import android.app.Fragment;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZodiacNewsFragment extends BaseFragment {

    public static ZodiacNewsFragment newInstance(){
        ZodiacNewsFragment fragment = new ZodiacNewsFragment();
        return fragment;
    }

    public ZodiacNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutId(){
        return R.layout.fragment_list;
    }

}
