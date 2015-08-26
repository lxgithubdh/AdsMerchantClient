package com.whut.presenter;

import java.util.ArrayList;

import com.whut.data.model.VipDetailModel;
import com.whut.data.model.VipRecordModel;
import com.whut.interfaces.IBaseView;

/**
 * 负责vip进店记录页面的信息交互
 * @author lx
 */
public class VipListPresenter {

	//视图层接口
	private IBaseView iView;
	
	
	public VipListPresenter(IBaseView itf){
		this.iView = itf;
	}
	
	public void setInfo(){
		String imageUrl = "http://115.28.9.186:8899/store/service/201/file-repo//uploads/2015-07-14/a9c03c30"
				+ "-7c34-46f0-97cf-39b627600592.png";
		ArrayList<VipRecordModel> list = new ArrayList<VipRecordModel>();
		
		VipRecordModel model1 = new VipRecordModel();
		model1.setRecordDate("08-10");
		ArrayList<VipDetailModel> details = new ArrayList<VipDetailModel>();
		VipDetailModel detail1 = new VipDetailModel();
		detail1.setPortraitImageUrl(imageUrl);
		detail1.setVipUserName("普天小王子");
		detail1.setVipTag("男  80后  牛仔裤");
		detail1.setEnterTime("10:32");
		VipDetailModel detail2 = new VipDetailModel();
		detail2.setPortraitImageUrl(imageUrl);
		detail2.setVipUserName("普天一哥");
		detail2.setVipTag("男  00后  唐装/民族/舞台服装");
		detail2.setEnterTime("10:30");
		VipDetailModel detail3 = new VipDetailModel();
		detail3.setPortraitImageUrl(imageUrl);
		detail3.setVipUserName("宝宝-宝哥");
		detail3.setVipTag("男  90后  轮滑/滑板/极限运动");
		detail3.setEnterTime("09:32");
		VipDetailModel detail4 = new VipDetailModel();
		detail4.setPortraitImageUrl(imageUrl);
		detail4.setVipUserName("信曾哥，得永生");
		detail4.setVipTag("");
		detail4.setEnterTime("08:32");
		details.add(detail1);
		details.add(detail2);
		details.add(detail3);
		details.add(detail4);
		model1.setVipList(details);
		
		VipRecordModel model2 = new VipRecordModel();
		model2.setRecordDate("08-09");
		ArrayList<VipDetailModel> details2 = new ArrayList<VipDetailModel>();
		VipDetailModel detail5 = new VipDetailModel();
		detail5.setPortraitImageUrl(imageUrl);
		detail5.setVipUserName("贼QQ");
		detail5.setVipTag("女  65后  考试/教材/论文");
		detail5.setEnterTime("14:32");
		details2.add(detail5);
		model2.setVipList(details2);
		
		VipRecordModel model3 = new VipRecordModel();
		model3.setRecordDate("08-07");
		ArrayList<VipDetailModel> details3 = new ArrayList<VipDetailModel>();
		VipDetailModel detail6 = new VipDetailModel();
		detail6.setPortraitImageUrl(imageUrl);
		detail6.setVipUserName("俞善浚");
		detail6.setVipTag("女  90后  绘画/美食");
		detail6.setEnterTime("19:32");
		details3.add(detail6);
		model3.setVipList(details3);
		
		list.add(model1);
		list.add(model2);
		list.add(model3);
		
		iView.setInfo(list,0);
	}
}
