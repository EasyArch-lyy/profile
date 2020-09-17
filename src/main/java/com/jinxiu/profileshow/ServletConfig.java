//package com.jinxiu.profileshow;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.Arrays;
//
//@WebFilter
//public class ServletConfig implements Filter {
//    private static final Logger logger = LoggerFactory.getLogger(ServletConfig.class);
//
//    /** 所有发送到 "/SPL" 的请求转发到 "/" 目录下
//     * @param request
//     * @param response
//     * @param filterChain
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest)request;
//        String uri = httpRequest.getRequestURI();
//        StringBuilder str = new StringBuilder();
//        httpRequest.getParameterMap().forEach((k,v)->{
//            String arrStr = Arrays.toString(v);
//            str.append(k).append(":").append(arrStr.substring(1, arrStr.length()-1)).append("; ");
//        });
//        if (uri != null && ("/SPL".equals(uri) || uri.startsWith("/SPL/"))) {
//            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(uri.substring(4));
//            if (dispatcher != null) {
//                dispatcher.forward(request, response);
//            }
//        } else {
//            filterChain.doFilter(request, response);
//        }
//    }



//}
