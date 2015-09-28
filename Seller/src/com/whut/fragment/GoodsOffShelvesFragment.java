package com.whut.fragment;

import java.util.ArrayList;

import com.whut.activity.GoodsDetailActivity;
import com.whut.config.RequestParam;
import com.whut.data.model.GoodsModel;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.GoodsListPresenter;
import com.whut.seller.R;
import com.whut.util.GoodsListUtil;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 商品下架列表Fragment
 * @author lx
 */
public class GoodsOffShelvesFragment extends Fragment implements IBaseView{

	private Context context;
	private ListView list;
	private ArrayList<GoodsModel> models = null;
	private GoodsListPresenter presenter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.goods_list_view, container,false);
		list = (ListView)view.findViewById(R.id.goods_list_view);
		presenter = new GoodsListPresenter(this);
		presenter.request(RequestParam.REQUEST_QUERY);
		return view;
	}

	
	/**
	 * 设置上下文
	 * @param context
	 */
	public void setContext(Context context){
		this.context = context;
	}
	
	
	@Override
	public Object getInfo(int code) {
		return null;
	}

	@Override
	public void setInfo(Object obj,int code) {
		String result = (String)obj;
		GoodsListUtil util = new GoodsListUtil(context, result);
		BaseAdapter adapter = util.getAdapter();
		models = util.getGoodsList();
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context, GoodsDetailActivity.class);
				intent.putExtra("goods", models.get(position));        //将全部信息传至详情页
				startActivity(intent);
			}
		});
	}
}
