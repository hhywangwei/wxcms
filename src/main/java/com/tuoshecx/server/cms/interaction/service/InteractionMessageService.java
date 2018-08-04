package com.tuoshecx.server.cms.interaction.service;

import com.tuoshecx.server.cms.interaction.domain.Interaction;
import com.tuoshecx.server.cms.user.domain.User;
import com.tuoshecx.server.cms.user.service.UserService;
import com.tuoshecx.server.wx.small.message.sender.SmallTemplateMessage;
import com.tuoshecx.server.wx.small.message.sender.WxTemplateMessageSender;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 互动消息推送服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class InteractionMessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(InteractionMessageService.class);
    private static final String INTERACTION_CALL_KEY = "interaction_notify";

    private final WxTemplateMessageSender sender;
    private final UserService userService;

    @Autowired
    public InteractionMessageService(WxTemplateMessageSender sender,  UserService userService) {
        this.sender = sender;
        this.userService = userService;
    }

    public void send(Interaction t){
        try{
            User u = userService.get(t.getUserId());
            SmallTemplateMessage m = new SmallTemplateMessage
                    .Builder(u.getOpenid(), u.getAppid(),INTERACTION_CALL_KEY, t.getFormId())
                    .addContentItem("keyword1", t.getTitle())
                    .addContentItem("keyword2", StringUtils.left(t.getReply(),30) + (StringUtils.length(t.getReply()) > 30? "..." : ""))
                    .addContentItem("keyword3", t.getState() == Interaction.State.REFUSE ? "拒绝": "回复")
                    .build();
             sender.send(m);
        }catch (Exception e){
            LOGGER.error("Send interaction message fail, error is {}", e.getMessage());
        }
    }
}
