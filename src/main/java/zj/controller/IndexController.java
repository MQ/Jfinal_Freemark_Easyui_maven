package zj.controller;

import com.jfinal.core.Controller;


public class IndexController extends Controller{
	
	public void index(){
		render("/ht/login.html");
	}
	public void login(){
		render("/ht/index.html");
	}
	public void welcome(){
		render("/ht/welcome.html");
	}
	public void href(){
		setAttr("sessionInfo", getSessionAttr("sessionInfo"));
		render(getPara("url"));
	}

}
