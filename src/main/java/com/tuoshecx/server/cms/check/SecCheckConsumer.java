package com.tuoshecx.server.cms.check;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuoshecx.server.cms.interaction.domain.Interaction;
import com.tuoshecx.server.cms.interaction.service.InteractionService;
import com.tuoshecx.server.cms.site.service.SiteWxService;
import com.tuoshecx.server.cms.sns.domain.Comment;
import com.tuoshecx.server.cms.sns.service.CommentService;
import com.tuoshecx.server.wx.small.client.WxSmallClientService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * 消费文字内容检查
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class SecCheckConsumer implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecCheckConsumer.class);

    private final SecCheckOperator operator;
    private final Map<String, Consumer<String>> checkHandlers;
    private final ExecutorService executorService;
    private final AtomicBoolean shutdownHookRegistered;
    private final AtomicBoolean stop;

    @Autowired
    public SecCheckConsumer(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper,
                            InteractionService interactionService, CommentService commentService,
                            SiteWxService wxService, WxSmallClientService clientService){

        this.operator = new SecCheckOperator(redisTemplate, objectMapper);
        this.checkHandlers = initCheckHandlers(interactionService, commentService,wxService, clientService);
        this.executorService = Executors.newSingleThreadExecutor();
        this.shutdownHookRegistered = new AtomicBoolean(false);
        this.stop = new AtomicBoolean(false);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        executorService.execute(() -> {
            while (!stop.get()){
                check();
            }
        });

        if(shutdownHookRegistered.compareAndSet(false, true)){
            registerShutdownHook(new Thread(() -> {
                stop.compareAndSet(false, true);
                executorService.shutdown();
                LOGGER.info("Sec check to shutdown...");
            }));
        }

        LOGGER.info("Start sec check consumer ...");
    }

    private void check(){
        try{
            Optional<SecCheck> optional = operator.pop();
            optional.ifPresent(e -> {
                Consumer<String> handler = checkHandlers.get(e.getType());
                if(handler == null){
                    LOGGER.error("Handler sec check type {} not exist", e.getType());
                    return ;
                }
                handler.accept(e.getId());
            });
        }catch (Exception e){
            LOGGER.error("Sec check content fail, error is {}", e.getMessage());
        }
    }

    private void registerShutdownHook(Thread thread) {
        Runtime.getRuntime().addShutdownHook(thread);
    }

    private Map<String, Consumer<String>> initCheckHandlers(InteractionService interactionService, CommentService commentService,
                                                            SiteWxService wxService, WxSmallClientService clientService){

        Map<String, Consumer<String>> handlers = new HashMap<>();

        handlers.put("interaction", e -> {
            Interaction t = interactionService.get(e);
            wxService.getAppid(t.getSiteId()).ifPresent(appid -> {
                boolean  ok =clientService.msgSecCheck(appid, t.getContent());
                interactionService.updateSecCheck(t.getId(), ok);
            });
        });

        handlers.put("comment", e -> {
            Comment t = commentService.get(e);
            wxService.getAppid(t.getSiteId()).ifPresent(appid -> {
                boolean  ok =clientService.msgSecCheck(appid, t.getContent());
                commentService.updateSecCheck(t.getId(), ok);
            });
        });

        return handlers;
    }
}
