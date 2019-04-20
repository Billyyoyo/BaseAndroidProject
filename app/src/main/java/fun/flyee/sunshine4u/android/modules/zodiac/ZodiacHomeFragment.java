package fun.flyee.sunshine4u.android.modules.zodiac;


import android.app.Fragment;

import fun.flyee.sunshine4u.android.R;
import fun.flyee.sunshine4u.android.fragments.BaseFragment;
import fun.flyee.sunshine4u.android.models.Merchandise;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZodiacHomeFragment extends BaseFragment {

    public static ZodiacHomeFragment newInstance(){
        ZodiacHomeFragment fragment = new ZodiacHomeFragment();
        return fragment;
    }

    public ZodiacHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutId(){
        return R.layout.fragment_list;
    }

}
