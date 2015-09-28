package com.whut.presenter;

import com.whut.business.LoginAsyncRequest;
import com.whut.config.RequestParam;
import com.whut.data.model.UserModel;
import com.whut.interfaces.IBasePresenter;
import com.whut.interfaces.IBaseView;


/**
 * 登录管理
 * @author lx
 */
public class LoginPresenter implements IBasePresenter {

	
	private IBaseView view;
	
	
	public LoginPresenter(IBaseView v){
		this.view = v;
	}
	
	
	@Override
	public void request(int requestCode) {
		UserModel model = (UserModel)view.getInfo(0);
		//发起请求，参数依次为登录链接，用户名，密码
		new LoginAsyncRequest(this).execute(RequestParam.LOGIN_PATH,model.getUserName(),model.getPassword());
	}

	@Override
	public void response(String data, int respondCode) {
		view.setInfo(data, respondCode);
	}

}
