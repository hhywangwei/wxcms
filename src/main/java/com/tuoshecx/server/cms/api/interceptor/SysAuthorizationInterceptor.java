package com.tuoshecx.server.cms.api.interceptor;

import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.security.token.TokenService;
import org.apache.commons.lang3.StringUtils;

/**
 * 系统管理端认证处理
 *
 * @author WangWei
 */
public class SysAuthorizationInterceptor extends BaseAuthorizationInterceptor {

    public SysAuthorizationInterceptor(TokenService service) {
        super(service);
    }

    @Override
    protected boolean authorization(Credential credential) {
        return StringUtils.equals(credential.getType(), "sys");
    }
}
