package com.tuoshecx.server.cms.security.authenticate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.tuoshecx.server.cms.security.authenticate.matcher.AntMatcher;
import com.tuoshecx.server.cms.security.authenticate.matcher.AuthenticateMatcher;
import com.tuoshecx.server.configure.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 认证业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class AuthenticateService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateService.class);
	
	private final List<AuthenticateMatcher> matchers;

	@Autowired
	public AuthenticateService(SecurityProperties properties){
		LOGGER.info("Local security auth size is {}, matcher is {}",
                properties.getAuthentications().size(), properties.getMatcher());
		
		matchers = properties.getAuthentications().stream().flatMap(e -> Arrays.stream(e.getPatterns())
				.map(p -> new AntMatcher(p, Arrays.asList(e.getRoles()))).collect(Collectors.toList()).stream()
		).collect(Collectors.toList());
	}
	
	public boolean authorization(String url, List<String> roles){
		return matchers.stream().anyMatch(e -> e.authorization(url, roles));
	}
}
