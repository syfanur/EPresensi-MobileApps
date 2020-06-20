package com.example.e_presensiadhibeton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager extends FragmentStateAdapter {
    public ViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ListKehadiran();
            case 1:
                return new StatistikKehadiran();
            default:
                return null;

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}