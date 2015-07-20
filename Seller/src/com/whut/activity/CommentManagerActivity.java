package com.whut.activity;

import com.whut.fragment.HasReadCommentFragment;
import com.whut.fragment.UnReadCommentFragment;
import com.whut.seller.R;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class CommentManagerActivity extends Activity implements OnClickListener {
	private FragmentManager fm;
	private UnReadCommentFragment unreadfragment;
	private HasReadCommentFragment hasreadfragment;

	private TextView unread_tv;
	private TextView hasread_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_manager);
		initView();
		setTabView(0);
	}

	private void setTabView(int i) {
		// TODO Auto-generated method stub
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		switch (i) {
		case 0:
			unread_tv.setTextColor(getResources().getColor(R.color.white));
			unread_tv.setBackgroundColor(getResources().getColor(R.color.blue));
			hasread_tv.setTextColor(getResources().getColor(R.color.blue));
			hasread_tv.setBackgroundColor(getResources()
					.getColor(R.color.white));

			if (hasreadfragment != null) {
				transaction.show(hasreadfragment);
			} else {
				hasreadfragment = new HasReadCommentFragment();
				transaction.add(R.id.comment_mng_content, hasreadfragment);
			}
			break;
		case 1:
			hasread_tv.setTextColor(getResources().getColor(R.color.white));
			hasread_tv
					.setBackgroundColor(getResources().getColor(R.color.blue));
			unread_tv.setTextColor(getResources().getColor(R.color.blue));
			unread_tv
					.setBackgroundColor(getResources().getColor(R.color.white));

			if (unreadfragment != null) {
				transaction.show(unreadfragment);
			} else {
				unreadfragment = new UnReadCommentFragment();
				transaction.add(R.id.comment_mng_content, unreadfragment);
			}
			break;
		}
		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (unreadfragment != null)
			transaction.hide(unreadfragment);
		if (hasreadfragment != null)
			transaction.hide(hasreadfragment);
	}

	private void initView() {
		// TODO Auto-generated method stub
		fm = getFragmentManager();
		unread_tv = (TextView) findViewById(R.id.unread_comment_tab);
		hasread_tv = (TextView) findViewById(R.id.hasread_comment_tab);

		unread_tv.setOnClickListener(this);
		hasread_tv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.comment_mng_back_layout:
			finish();
			break;
		case R.id.unread_comment_tab:
			setTabView(0);
			break;
		case R.id.hasread_comment_tab:
			setTabView(1);
			break;

		}
	}
}
