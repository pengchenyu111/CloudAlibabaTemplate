package com.pcy.config.feign;

import com.pcy.constant.CommonConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class OAuth2FeignConfig implements RequestInterceptor {

    /**
     * Called for every request. Add data using methods on the supplied {@link RequestTemplate}.
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String header = null;
        if (requestAttributes == null) {
            header = "bearer " + CommonConstant.INSIDE_TOKEN;
        } else {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            // 获取请求上下文的头里面的AUTHORIZATION
            header = request.getHeader(HttpHeaders.AUTHORIZATION);
        }
        if (!StringUtils.isEmpty(header)) {
            template.header(HttpHeaders.AUTHORIZATION, header);
            log.info("本次token传递成功,token的值为:{}", header);
        }
    }
}
