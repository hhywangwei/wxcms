package com.tuoshecx.server.cms.api.manage.interceptor;

import com.tuoshecx.server.cms.api.interceptor.BaseAuthorizationInterceptor;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.security.authenticate.AuthenticateService;
import com.tuoshecx.server.cms.security.token.TokenService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 站点管理端权限认证拦截器
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ManageAuthorizationInterceptor extends BaseAuthorizationInterceptor {
    private final AuthenticateService authenticateService;

    public ManageAuthorizationInterceptor(TokenService service, AuthenticateService authenticateService) {
        super(service);
        this.authenticateService = authenticateService;
    }

    @Override
    protected boolean authorization(HttpServletRequest request, Credential credential) {
        return StringUtils.equals(credential.getType(), "manager") &&
                authenticateService.authorization(request.getRequestURI(), credential.getRoles());
    }
}
