package com.tuoshecx.server.wx.small.client;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.common.utils.DateUtils;
import com.tuoshecx.server.cms.site.domain.SiteWxToken;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.wx.component.token.ComponentTokenService;
import com.tuoshecx.server.wx.configure.properties.WxComponentProperties;
import com.tuoshecx.server.wx.small.client.impl.WxSmallClients;
import com.tuoshecx.server.wx.small.client.request.*;
import com.tuoshecx.server.wx.small.client.response.*;
import com.tuoshecx.server.wx.small.session.RedisSessionDao;
import com.tuoshecx.server.wx.small.session.SessionDao;
import com.tuoshecx.server.wx.small.utils.WxSmallUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 微信小程API接口业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class WxSmallClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxSmallClientService.class);

    private final SiteWxService wxService;
    private final SessionDao sessionDao;
    private final WxSmallClients clients;
    private final WxComponentProperties properties;
    private final ComponentTokenService componentTokenService;

    @Autowired
    public WxSmallClientService(RestTemplate restTemplate,
                                SiteWxService wxService,
                                StringRedisTemplate redisTemplate,
                                WxComponentProperties properties,
                                ComponentTokenService componentTokenService) {

        this.wxService = wxService;
        this.sessionDao = new RedisSessionDao(redisTemplate);
        this.clients = new WxSmallClients(restTemplate);
        this.properties = properties;
        this.componentTokenService = componentTokenService;
    }

    /**
     * 微信小程序用户登陆
     *
     * @param appid 调用登陆微信appid
     * @param code 登陆code
     * @return
     */
    public Optional<String> login(String appid, String code){
        String token = componentTokenService.get(properties.getAppid()).orElseThrow(() -> new BaseException("微信Token不存在"));
        LoginRequest request = new LoginRequest(appid, properties.getAppid(), token, code);
        LoginResponse response =  clients.loginClient().request(request);

        if(!response.isOk()){
            return Optional.empty();
        }

        LOGGER.debug("Login openid {} sessionKey {}", response.getOpenid(), response.getSessionKey());
        boolean ok = sessionDao.saveKey(response.getOpenid(), response.getSessionKey());
        return ok? Optional.of(response.getOpenid()): Optional.empty();
    }

    /**
     * 发送模板消息
     *
     * @param appid appid
     * @param func 构建模板消息请求对象
     * @return {@link WxSmallResponse}
     */
    public WxSmallResponse sendTmpMsg(String appid, Function<String, SendTemplateMsgRequest> func){
        String token = getAccessToken(appid);
        return clients.sendTmpMsgClient().request(func.apply(token));
    }

    /**
     *  发送客服消息
     *
     * @param appid 微信appid
     * @param func  构造客服消息请求
     * @return {@link WxSmallResponse}
     */
    public WxSmallResponse sendCustomMsg(String appid, Function<String, SendCustomMsgRequest> func){
        String token = getAccessToken(appid);
        SendCustomMsgRequest request = func.apply(token);

        return clients.sendCustomMsgClient().request(request);
    }

    /**
     * 判读数据签名是否正确
     *
     * @param openid    用户openid
     * @param rawData   明文
     * @param signature 签名
     * @return true:签名正确
     */
    public boolean isSignature(String openid, String rawData, String signature){
        return sessionDao.getKey(openid)
                .map(e ->{
                    LOGGER.debug("isSignature Openid {} sessionKey {}", openid, e);
                    return WxSmallUtils.isSignature(rawData, e, signature);
                })
                .orElseGet(() -> {
                    LOGGER.warn("isSignature Openid {} sessionKey not exist");
                    return false;
                });
    }

    /**
     * 解密微信加密数据
     *
     * @param openid         用户openid
     * @param encryptedData  明文
     * @param vi             加密算法的初始向量
     * @return 解密字符串
     */
    public Optional<String> decrypt(String openid, String encryptedData, String vi){
        return sessionDao.getKey(openid)
                .flatMap(e -> WxSmallUtils.decrypt(encryptedData, e, vi));
    }

    /**
     * 添加微信消息模板
     *
     * @param appid      微信appid
     * @param id         微信的模板编号
     * @param keywordIds 关键字编号
     * @return {@link MessageTemplateAddResponse}
     */
     public MessageTemplateAddResponse addMessageTemplate(String appid, String id, List<Integer> keywordIds){

         String token = getAccessToken(appid);
         MessageTemplateAddRequest request = new MessageTemplateAddRequest(token, id, keywordIds);

         return clients.messageTemplateAddClient().request(request);
     }

    /**
     * 删除微信消息模板
     *
     * @param appid       微信appid
     * @param templateId  微信模板编号
     * @return {@link WxSmallResponse}
     */
     public WxSmallResponse delMessageTemplate(String appid, String templateId){
         String token = getAccessToken(appid);
         MessageTemplateDelRequest request = new MessageTemplateDelRequest(token, templateId);

         return clients.messageTemplateDelClient().request(request);
     }

    /**
     * 查询自己的消息模板
     *
     * @param appid   微信appid
     * @param offset  offset和count用于分页，表示从offset开始，拉取count条记录，offset从0开始，count最大为20
     * @param count   offset和count用于分页，表示从offset开始，拉取count条记录，offset从0开始，count最大为20
     * @return {@link MessageTemplateQueryResponse}
     */
     public MessageTemplateQueryResponse queryMessageTemplate(String appid, int offset, int count){
         String token = getAccessToken(appid);
         MessageTemplateQueryRequest request = new MessageTemplateQueryRequest(token, offset, count);

         return clients.messageTemplateQueryClient().request(request);
     }

    /**
     * 日趋势
     *
     * @param appid appid
     * @param day   日期
     * @return {@link VisitTrendAnalysisResponse}
     */
     public VisitTrendAnalysisResponse dailyVisitTrendAnalysis(String appid, Date day){
         String date = DateUtils.format("yyyyMMdd", day);
         AnalysisRequest request = new AnalysisRequest(getAccessToken(appid), date, date);
         return clients.dailyVisitTrendAnalysisClient().request(request);
     }

    /**
     * 周趋势
     *
     * @param appid     appid
     * @param beginDate 周开始日期
     * @param endDate   周结束日期
     * @return {@link VisitTrendAnalysisResponse}
     */
     public VisitTrendAnalysisResponse weekVisitTrendAnalysis(String appid, Date beginDate, Date endDate){
         AnalysisRequest request = new AnalysisRequest(getAccessToken(appid),
                 DateUtils.format("yyyyMMdd", beginDate), DateUtils.format("yyyyMMdd", endDate));
         return clients.weekVisitTrendAnalysisClient().request(request);
     }

    /**
     * 月趋势
     *
     * @param appid     appid
     * @param beginDate 月开始日期
     * @param endDate   月结束日期
     * @return {@link VisitTrendAnalysisResponse}
     */
     public VisitTrendAnalysisResponse monthVisitTrendAnalysis(String appid, Date beginDate, Date endDate){
         AnalysisRequest request = new AnalysisRequest(getAccessToken(appid),
                 DateUtils.format("yyyyMMdd", beginDate), DateUtils.format("yyyyMMdd", endDate));
         return clients.monthVisitTrendAnalysisClient().request(request);
     }

    /**
     * 概况趋势
     *
     * @param appid appid
     * @param day   日期
     * @return {@link SummaryTrendAnalysisResponse}
     */
     public SummaryTrendAnalysisResponse summaryTrendAnalysis(String appid, Date day){
         String date = DateUtils.format("yyyyMMdd", day);
         AnalysisRequest request = new AnalysisRequest(getAccessToken(appid), date, date);
         return clients.summaryTrendAnalysisHttpClient().request(request);
     }

    /**
     * 访问页面
     *
     * @param appid appid
     * @param day   日期
     * @return {@link VisitPageResponse}
     */
     public VisitPageResponse visitPage(String appid, Date day){
         String date = DateUtils.format("yyyyMMdd", day);
         AnalysisRequest request = new AnalysisRequest(getAccessToken(appid), date, date);
         return clients.visitPageClient().request(request);
     }

    /**
     * 删除微信用户登陆session_key
     *
     * @param openid 用户openid
     */
    public void removeSessionKey(String openid){
        sessionDao.remove(openid);
    }

    private String getAccessToken(String appid){
        Optional<SiteWxToken> optional = wxService.getToken(appid);
        return optional.orElseThrow(() -> new BaseException(5001, "微信公众号Token失效")).getAccessToken();
    }
}