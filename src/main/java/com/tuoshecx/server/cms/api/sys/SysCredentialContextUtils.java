package com.tuoshecx.server.cms.api.sys;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.security.CredentialContextUtils;

import java.util.Optional;

/**
 * 系统管理认证用户工具类型
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SysCredentialContextUtils {

    /**
     * 获取用户认证信息
     *
     * @return 认证信息
     */
    public static Credential getCredential(){
        Optional<Credential> optional = CredentialContextUtils.getCredential();
        return optional.orElseThrow(() -> new BaseException("认证错误，非法操作"));
    }
}
