package zj.config;

import zj.controller.IconController;
import zj.controller.IndexController;
import zj.controller.LogController;
import zj.controller.OnlineController;
import zj.controller.ResourceController;
import zj.controller.RoleController;
import zj.controller.UserController;
import zj.interceptor.GlobalInterceptor;
import zj.jfinalExt.PropertyConfig;
import zj.model.Ticon;
import zj.model.Tlog;
import zj.model.Tonline;
import zj.model.Tresource;
import zj.model.Trole;
import zj.model.Tuser;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

public class MyJFinalConfig extends JFinalConfig {

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载数据库配置文件
		PropertyConfig.me().loadPropertyFile("config.properties");
		// 设定为开发者模式
		me.setDevMode(PropertyConfig.me()
				.getPropertyToBoolean("devMode", false));
		me.setViewType(ViewType.FREE_MARKER);
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/",IndexController.class);
		me.add("/index",IndexController.class);
		me.add("/icon", IconController.class);
		me.add("/log", LogController.class);
		me.add("/role", RoleController.class);
		me.add("/online", OnlineController.class);
		me.add("/resource", ResourceController.class);
		me.add("/user", UserController.class);
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		PropertyConfig config = PropertyConfig.me();
		String jdbcUrl = config.getProperty("jdbcUrl");
		String user = config.getProperty("user");
		String password = config.getProperty("password");
		C3p0Plugin c3p0Plugin = new C3p0Plugin(jdbcUrl, user, password);
		me.add(c3p0Plugin);
		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		arp.addMapping("ticon", Ticon.class); // 映射xx 表到 XX.class模型
		arp.addMapping("tlog", Tlog.class); 
		arp.addMapping("tonline", Tonline.class); 
		arp.addMapping("trole", Trole.class); 
		arp.addMapping("tuser", Tuser.class); 
		arp.addMapping("tresource", Tresource.class); 
	}
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new GlobalInterceptor());
	}
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
	}

}