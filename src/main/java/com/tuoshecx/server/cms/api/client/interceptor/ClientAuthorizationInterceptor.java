package com.tuoshecx.server.cms.api.client.interceptor;

import com.tuoshecx.server.cms.api.interceptor.BaseAuthorizationInterceptor;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.security.token.TokenService;
import org.apache.commons.lang3.StringUtils;

/**
 * 小程序端权限认证拦截器
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class ClientAuthorizationInterceptor extends BaseAuthorizationInterceptor {

    public ClientAuthorizationInterceptor(TokenService service) {
        super(service);
    }

    @Override
    protected boolean authorization(Credential credential){
        return StringUtils.equals(credential.getType(), "user") ||
                StringUtils.equals(credential.getType(), "temp");
    }
}
