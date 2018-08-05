package com.tuoshecx.server.cms.security.authenticate.matcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * URL配置对象
 * 
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class AntMatcher implements AuthenticateMatcher{
	private final String pattern;
	private final Set<String> roles;
	private final PathMatcher pathMatcher;
	
	public AntMatcher(String pattern, List<String> roles){
		this.pattern = pattern;
		this.roles = new HashSet<>(roles);
		this.pathMatcher = new AntPathMatcher();
	}

	@Override
	public boolean authorization(String url, List<String> uRoles) {
		return  pathMatcher.match(pattern, url) && hasRole(roles, uRoles) ;
	}
	
	private boolean hasRole(Set<String> roles, List<String> uRoles){
		return uRoles.stream().anyMatch(roles::contains);
	}
}
