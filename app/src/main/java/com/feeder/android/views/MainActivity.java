package com.feeder.android.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.feeder.android.mvp.IAccountsView;
import com.feeder.android.mvp.ISubscriptionsView;
import com.feeder.android.mvp.MVPPresenter;
import com.feeder.android.presenters.AccountsPresenter;
import com.feeder.android.presenters.SubscriptionsPresenter;

import me.zsr.feeder.R;

/**
 * @description:
 * @author: Match
 * @date: 7/18/16
 */
public class MainActivity extends BaseActivity {
    private MVPPresenter mAccountsPresenter;
    private MVPPresenter mSubscriptionsPresenter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSystemBar();
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initDrawerPanel();
        initDetailsPanel();

        mAccountsPresenter.onCreate();
        mSubscriptionsPresenter.onCreate();
    }

    private void initDrawerPanel() {
        LinearLayout drawerPanel = (LinearLayout) findViewById(R.id.drawer_panel);
        IAccountsView accountsView = new AccountsView(this);
        LinearLayout.LayoutParams accountsViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        drawerPanel.addView(accountsView, accountsViewLp);
        mAccountsPresenter = new AccountsPresenter(accountsView);

        View settingsEntrance = LayoutInflater.from(this).inflate(R.layout.settings_entrance, drawerPanel, false);
        drawerPanel.addView(settingsEntrance);
        ((ImageView) settingsEntrance.findViewById(R.id.settings_img)).setColorFilter(getResources().getColor(R.color.main_grey_normal));
        settingsEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        View aboutEntrance = LayoutInflater.from(this).inflate(R.layout.about_entrance, drawerPanel, false);
        drawerPanel.addView(aboutEntrance);
        ((ImageView) aboutEntrance.findViewById(R.id.about_img)).setColorFilter(getResources().getColor(R.color.main_grey_normal));
        aboutEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });
    }

    private void initDetailsPanel() {
        LinearLayout detailsPanel = (LinearLayout) findViewById(R.id.details_panel);
        ImageButton addSubscriptionButton = (ImageButton) detailsPanel.findViewById(R.id.add_subscription_btn);
        addSubscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddSubscriptionActivity.class));
            }
        });
        ImageButton openDrawerButton = (ImageButton) detailsPanel.findViewById(R.id.open_drawer_btn);
        openDrawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        ISubscriptionsView subscriptionsView = new SubscriptionsView(this);
        LinearLayout.LayoutParams subscriptionsViewLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        detailsPanel.addView(subscriptionsView, subscriptionsViewLp);
        mSubscriptionsPresenter = new SubscriptionsPresenter(subscriptionsView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAccountsPresenter.onStart();
        mSubscriptionsPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAccountsPresenter.onStop();
        mSubscriptionsPresenter.onStop();
    }
}