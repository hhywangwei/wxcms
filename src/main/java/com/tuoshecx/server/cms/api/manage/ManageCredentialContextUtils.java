package com.tuoshecx.server.cms.api.manage;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.security.Credential;
import com.tuoshecx.server.cms.security.CredentialContextUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * 站点认证用户工具类型
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ManageCredentialContextUtils {
    public static final String SITE_ID_KEY = "site_id";

    /**
     * 获取用户认证信息
     *
     * @return 认证信息
     */
    public static Credential getCredential(){
        Optional<Credential> optional = CredentialContextUtils.getCredential();
        return optional.orElseThrow(() -> new BaseException("认证错误，非法操作"));
    }

    /**
     * 获取用户所属站点编号
     *
     * @return 站点编号
     */
    public static String currentSiteId(){
        Credential t = getCredential();
        Optional<String> optional = t.getAttaches().stream()
                .filter(e -> StringUtils.equals(SITE_ID_KEY, e.getKey()))
                .map(Credential.Attach::getValue)
                .findFirst();

        return optional.orElseThrow(() -> new BaseException("认证错误，非法操作"));
    }
}
