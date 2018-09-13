package com.tuoshecx.server.cms.api;

import com.tuoshecx.server.cms.api.interceptor.BaseAuthorizationInterceptor;
import com.tuoshecx.server.cms.api.client.interceptor.ClientAuthorizationInterceptor;
import com.tuoshecx.server.cms.api.manage.interceptor.ManageAuthorizationInterceptor;
import com.tuoshecx.server.cms.api.sys.interceptor.SysAuthorizationInterceptor;
import com.tuoshecx.server.cms.security.authenticate.AuthenticateService;
import com.tuoshecx.server.cms.security.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Api接口配置
 *
 * @author WangWei
 */
@Configuration
public class ApiConfigure implements WebMvcConfigurer {
    private final TokenService tokenService;
    private final AuthenticateService authenticateService;

    @Autowired
    public ApiConfigure(TokenService tokenService, AuthenticateService authenticateService) {
        this.tokenService = tokenService;
        this.authenticateService = authenticateService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BaseAuthorizationInterceptor(tokenService))
                .addPathPatterns("/upload", "/district/**");

        registry.addInterceptor(new ClientAuthorizationInterceptor(tokenService))
                .addPathPatterns("/client/**")
                .excludePathPatterns("/client/login", "/client/wxLogin","/client/register");

        registry.addInterceptor(new ManageAuthorizationInterceptor(tokenService, authenticateService))
                .addPathPatterns("/manage/**")
                .excludePathPatterns("/manage/login");

        registry.addInterceptor(new SysAuthorizationInterceptor(tokenService, authenticateService))
                .addPathPatterns("/sys/**")
                .excludePathPatterns("/sys/login");
    }
}
