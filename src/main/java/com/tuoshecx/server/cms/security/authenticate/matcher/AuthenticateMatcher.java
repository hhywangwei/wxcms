package com.tuoshecx.server.cms.security.authenticate.matcher;

import java.util.List;

/**
 * 访问匹配接口
 * 
 * @author WangWei
 */
public interface AuthenticateMatcher {

	/**
	 * 判断访问是否合法
	 * 
	 * @param url 访问URL
	 * @param roles 用户角色
	 * @return true:认证成功，false:认证失败
	 */
	boolean authorization(String url, List<String> roles);
	
}
