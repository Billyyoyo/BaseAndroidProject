package fun.flyee.sunshine4u.android.modules.zodiac;


import android.app.Fragment;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZodiacSearchFragment extends BaseFragment {

    public static ZodiacSearchFragment newInstance(){
        ZodiacSearchFragment fragment = new ZodiacSearchFragment();
        return fragment;
    }

    public ZodiacSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutId(){
        return R.layout.fragment_list;
    }

}
