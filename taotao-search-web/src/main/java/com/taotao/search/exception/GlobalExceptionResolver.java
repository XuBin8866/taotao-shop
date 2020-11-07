package com.taotao.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xxbb
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    Logger logger=LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //写日志文件
        logger.error("系统发生异常",e);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("message","系统发生异常，请重试");
        modelAndView.setViewName("/error/exception");
        return modelAndView;
    }
}
