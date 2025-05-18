package com.mengnankk.front.filter;

import com.mengnankk.common.utils.RequestUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
         throws IOException, ServletException {
        ResponseWrapper wrapperResponse = new ResponseWrapper((HttpServletResponse) response);
        filterChain.doFilter(request, wrapperResponse);
        byte[] content = wrapperResponse.getContent();
        if (content.length > 0) {
            String str = new String(content, StandardCharsets.UTF_8);
            try {
                HttpServletRequest req = (HttpServletRequest) request;
                str = new ResponseRouter().filter(str, RequestUtil.getUri(req));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ServletOutputStream outputStream = response.getOutputStream();
                if (str.length() > 0) {
                    outputStream.write(str.getBytes());
                    outputStream.flush();
                    outputStream.close();
                    //输出到客户端
                    response.flushBuffer();
                }
            }
        }
    }
}
