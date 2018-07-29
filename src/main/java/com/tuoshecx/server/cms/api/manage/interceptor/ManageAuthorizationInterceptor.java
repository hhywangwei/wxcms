package com.tuoshecx.server.cms.api.manage.interceptor;

import com.tuoshecx.server.cms.api.interceptor.BaseAuthorizationInterceptor;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.security.token.TokenService;
import org.apache.commons.lang3.StringUtils;

/**
 * 站点管理端权限认证拦截器
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ManageAuthorizationInterceptor extends BaseAuthorizationInterceptor {

    public ManageAuthorizationInterceptor(TokenService service) {
        super(service);
    }

    @Override
    protected boolean authorization(Credential credential) {
        return StringUtils.equals(credential.getType(), "manager");
    }
}
