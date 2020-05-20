package com.example.assessment_2;

import java.util.HashMap;
public class MainFragmentFactory {

    private static HashMap<Integer, BaseFragment> mSavedFragment = new HashMap<Integer, BaseFragment>();

    //根据position得到对应的fragment
    public static BaseFragment getFragment(int position) {
        BaseFragment fragment = mSavedFragment.get(position);
        if(fragment == null) {
            switch (position) {
                case 0:
                    fragment =  new WXFragment();
                    break;
                case 1:
                    fragment = new BookFragment();
                    break;
                case 2:
                    fragment = new DiscoverFragment();
                    break;
                case 3:
                    fragment = new MeFragment();
                    break;
            }
            //创建之后保存
            mSavedFragment.put(position, fragment);
        }


        return fragment;
    }
    public static void deleteFragment(){
        for (int i = 0 ; i < 4 ; i++){
            mSavedFragment.remove(i);
        }

    }

}

