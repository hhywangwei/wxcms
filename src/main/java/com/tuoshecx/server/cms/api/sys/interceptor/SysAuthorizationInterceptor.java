package com.tuoshecx.server.cms.api.sys.interceptor;

import com.tuoshecx.server.cms.api.interceptor.BaseAuthorizationInterceptor;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.security.authenticate.AuthenticateService;
import com.tuoshecx.server.cms.security.token.TokenService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * 系统管理端认证处理
 *
 * @author WangWei
 */
public class SysAuthorizationInterceptor extends BaseAuthorizationInterceptor {
    private final AuthenticateService authenticateService;

    public SysAuthorizationInterceptor(TokenService service, AuthenticateService authenticateService) {
        super(service);
        this.authenticateService = authenticateService;
    }

    @Override
    protected boolean authorization(HttpServletRequest request,  Credential credential) {
        return StringUtils.equals(credential.getType(), "sys")
                && authenticateService.authorization(request.getRequestURI(), credential.getRoles());
    }
}
