package com.tuoshecx.server.cms.api;

import com.tuoshecx.server.BaseException;
import com.tuoshecx.server.cms.api.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * BaseException拦截处理
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@ControllerAdvice(annotations = RestController.class)
public class CmsExceptionAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(CmsExceptionAdvice.class);

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    @SuppressWarnings("unused")
    public ResultVo<String> handleBaseException(BaseException e){
        LOGGER.error("Controller error code {} message {}", e.getCode(), e.getMessage());
        return ResultVo.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(ConversionFailedException.class)
    @ResponseBody
    @SuppressWarnings("unused")
    public ResultVo<String> handlerConversionFailedException(ConversionFailedException e){
        LOGGER.error("Parameter conversion fail, error is {}", e.getMessage());
        return ResultVo.error(100, "参数错误");
    }
}
