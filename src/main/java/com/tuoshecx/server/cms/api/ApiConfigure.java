package com.tuoshecx.server.cms.api;

import com.tuoshecx.server.cms.api.interceptor.BaseAuthorizationInterceptor;
import com.tuoshecx.server.cms.api.client.interceptor.ClientAuthorizationInterceptor;
import com.tuoshecx.server.cms.api.manage.interceptor.ManageAuthorizationInterceptor;
import com.tuoshecx.server.cms.api.interceptor.SysAuthorizationInterceptor;
import com.tuoshecx.server.cms.security.token.TokenService;
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

    public ApiConfigure(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ClientAuthorizationInterceptor(tokenService))
                .addPathPatterns("/client/**")
                .excludePathPatterns("/client/login", "/client/register", "/client/wxLogin", "/client/wx/pay/notify");

        registry.addInterceptor(new ManageAuthorizationInterceptor(tokenService))
                .addPathPatterns("/manage/**")
                .excludePathPatterns("/manage/login");

        registry.addInterceptor(new SysAuthorizationInterceptor(tokenService))
                .addPathPatterns("/sys/**")
                .excludePathPatterns("/sys/login");

        registry.addInterceptor(new BaseAuthorizationInterceptor(tokenService))
                .addPathPatterns("/upload", "/district/**");
    }
}
