package com.tuoshecx.server.configure.properties;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全配置属性
 * 
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ConfigurationProperties(prefix="security")
public class SecurityProperties {
	private String matcher;
	private List<Authentication> authentications;
	
	public String getMatcher() {
		return matcher;
	}

	public void setMatcher(String matcher) {
		this.matcher = matcher;
	}

	public List<Authentication> getAuthentications() {
		return authentications;
	}

	public void setAuthentications(List<Authentication> authentications) {
		this.authentications = authentications;
	}

	public static class Authentication {
		private String[] patterns;
		private String[] roles;

        public String[] getPatterns() {
            return patterns;
        }

        public void setPatterns(String[] patterns) {
            this.patterns = patterns;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }
    }
}
