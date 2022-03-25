package com.nevesti.www.bairroseguro.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nevesti.www.bairroseguro.fragment.MinhasDenunciasFragment;
import com.nevesti.www.bairroseguro.fragment.NaturezaFragment;

public class TabDenunciaAdapter extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"NATUREZAS", "MINHAS DENÚNCIAS"};

    public TabDenunciaAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new NaturezaFragment();
                break;
            case 1:
                fragment = new MinhasDenunciasFragment();
                break;
        }

        return fragment;

    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}
