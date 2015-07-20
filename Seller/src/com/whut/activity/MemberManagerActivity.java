package com.whut.activity;

import com.whut.fragment.ChannelMemberManagerFragment;
import com.whut.fragment.ShopMemberManagerFragment;
import com.whut.seller.R;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MemberManagerActivity extends Activity implements OnClickListener {
	private FragmentManager fm;
	private ChannelMemberManagerFragment cmfragment;
	private ShopMemberManagerFragment smfragment;

	private TextView cm_tv;
	private TextView sm_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_manager);

		initView();
		setTabView(0);
	}

	private void setTabView(int i) {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		switch (i) {
		case 0:
			cm_tv.setTextColor(getResources().getColor(R.color.white));
			cm_tv.setBackgroundColor(getResources().getColor(R.color.blue));
			sm_tv.setTextColor(getResources().getColor(R.color.blue));
			sm_tv.setBackgroundColor(getResources().getColor(R.color.white));

			if (smfragment != null) {
				transaction.show(smfragment);
			} else {
				smfragment = new ShopMemberManagerFragment();
				transaction.add(R.id.member_mng_content, smfragment);
			}
			break;
		case 1:
			sm_tv.setTextColor(getResources().getColor(R.color.white));
			sm_tv.setBackgroundColor(getResources().getColor(R.color.blue));
			cm_tv.setTextColor(getResources().getColor(R.color.blue));
			cm_tv.setBackgroundColor(getResources().getColor(R.color.white));

			if (cmfragment != null) {
				transaction.show(cmfragment);
			} else {
				cmfragment = new ChannelMemberManagerFragment();
				transaction.add(R.id.member_mng_content, cmfragment);
			}
			break;
		}
		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (cmfragment != null)
			transaction.hide(cmfragment);
		if (smfragment != null)
			transaction.hide(smfragment);
	}

	private void initView() {
		// TODO Auto-generated method stub
		fm = getFragmentManager();
		cm_tv = (TextView) findViewById(R.id.channal_member_mng_tab);
		sm_tv = (TextView) findViewById(R.id.shop_member_mng_tab);

		cm_tv.setOnClickListener(this);
		sm_tv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.member_mng_back_layout:
			finish();
			break;
		case R.id.channal_member_mng_tab:
			setTabView(0);
			break;
		case R.id.shop_member_mng_tab:
			setTabView(1);
			break;

		}
	}
}
