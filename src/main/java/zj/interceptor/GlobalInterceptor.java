package zj.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

public class GlobalInterceptor implements Interceptor {

	private static final Logger logger = Logger
			.getLogger(GlobalInterceptor.class);

	public void intercept(ActionInvocation ai) {
		String baseUrl = "/";
		Controller controller = ai.getController();
		HttpServletRequest request = controller.getRequest();
		StringBuilder sb = new StringBuilder().append(request.getScheme())
				.append("://").append(request.getServerName());
		if (request.getServerPort() != 80) {
			sb.append(":").append(request.getServerPort());
		}
		sb.append(request.getContextPath());

		baseUrl = sb.toString();

		controller.setAttr("baseUrl", baseUrl);

		ai.invoke();
		logger.info("Before invoking " + ai.getActionKey());
		ai.invoke();
		logger.info("After invoking " + ai.getActionKey());
	}

}
