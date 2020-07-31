package com.myself.common.util.crossDomain;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 * @title: OriginFilter
 * @projectName gcp-actg
 * @description: TODO
 * @date 2019/9/29 0029下午 15:52
 */
@Component
public class OriginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /**
         * 指定允许其他域名访问
         * 一般用法（*，指定域，动态设置），3是因为*不允许携带认证头和cookies
         */
        response.setHeader("Access-Control-Allow-Origin" , request.getHeader("Origin" ));
        /**
         * 该次的请求方式
         */
        response.setHeader("Access-Control-Allow-Methods" , "POST, GET, OPTIONS, DELETE,PUT" );
        /**
         * 预检结果缓存时间,也就是上面说到的缓存啦
         */
        response.setHeader("Access-Control-Max-Age" , "3600" );
        /**
         * 允许的请求头字段
         */
        response.setHeader("Access-Control-Allow-Headers" , "x-requested-with,Content-Type,Content-Disposition,content-Type" );
        /**
         * 允许cookie跨域
         */
        response.setHeader("Access-Control-Allow-Credentials" , "true" );

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void destroy() {

    }
}
