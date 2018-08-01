package com.tuoshecx.server.cms.api.client.interaction.vo;

import com.tuoshecx.server.cms.interaction.domain.Interaction;
import io.swagger.annotations.ApiModelProperty;

/**
 * 政民互动输出VO
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InteractionVo {
    @ApiModelProperty("互动信息")
    private final Interaction info;
    @ApiModelProperty("阅读次数")
    private final Integer readCount;
    @ApiModelProperty("点赞数")
    private final Integer goodCount;

    public InteractionVo(Interaction info, Integer readCount, Integer goodCount) {
        this.info = info;
        this.readCount = readCount;
        this.goodCount = goodCount;
    }

    public Interaction getInfo() {
        return info;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }
}
