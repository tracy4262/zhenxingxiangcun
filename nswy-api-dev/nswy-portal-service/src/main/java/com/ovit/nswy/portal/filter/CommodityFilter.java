package com.ovit.nswy.portal.filter;

import com.alibaba.fastjson.JSONObject;
import com.ovit.nswy.portal.service.ShopStorageInfoService;
import com.ovit.nswy.portal.service.UpMarketingBasicInfoService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@WebFilter(filterName = "commodityFilter", urlPatterns = "/shopCommdoity/*")
public class CommodityFilter implements Filter {

    @Autowired
    private UpMarketingBasicInfoService upMarketingBasicInfoService;

    @Autowired
    private ShopStorageInfoService shopStorageInfoService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    //在DispatcherServlet转发之前
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = String.valueOf(request.getRequestURL());
        ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest) {
            requestWrapper = new ReaderReuseHttpServletRequestWrapper(request);
        }
        Reader reader = requestWrapper.getReader();
        // 读取Request Payload数据
        Map<String, Object> paramMap = new HashMap<>();
        if (requestUrl.contains("/shopCommdoity/up") || requestUrl.contains("/shopCommdoity/insertCommodityInfo")) {
            String type = request.getContentType();
            String Payload = IOUtils.toString(reader);
            if (type != null && type.startsWith("application/json")){
                JSONObject jsonObject = JSONObject.parseObject(Payload);
                if (jsonObject != null) {
                    for(Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                        paramMap.put(entry.getKey(), entry.getValue());
                    }
                }
            } else if(type != null && type.startsWith("text/plain")) {
                String[] kvs = Payload.split("&");
                for (String kv : kvs) {
                    String[] lf = kv.split("=");
                    paramMap.put(lf[0], lf[1]);
                }

            }
        }

        filterChain.doFilter(requestWrapper,response);

        if (requestUrl.contains("/shopCommdoity/up") || requestUrl.contains("/shopCommdoity/insertCommodityInfo")) {
            //若是修改商品发布中的数据，则修改该商品数据的审核状态为待审核状态
            //先根据account和commodityId查看shop_product_commitment_info商品承诺表中是否存在数据
            if (requestUrl.contains("/shopCommdoity/insertCommodityInfo")) {
                paramMap.put("commodityId", MapUtils.getString(paramMap, "id"));
            }
            Map<String, Object> commitmentInfo = upMarketingBasicInfoService.getCommitmentInfo(paramMap);
            if (MapUtils.isNotEmpty(commitmentInfo)) {
                //若存在，则更新审核状态
                paramMap.put("approveStatus", "0");
                paramMap.put("storageStatus", "0");
                shopStorageInfoService.updateAuditStatus(paramMap);
            }
        }

    }

    //停止应用后
    @Override
    public void destroy() {
    }

    /**
     * 两个方法都注明方法只能被调用一次，由于RequestBody是流的形式读取，
     * 那么流读了一次就没有了，所以只能被调用一次。
     * 既然是因为流只能读一次的原因，那么只要将流的内容保存下来，就可以实现反复读取了
     *
     */
    public static class ReaderReuseHttpServletRequestWrapper extends HttpServletRequestWrapper {


        private final byte[] body;

        public ReaderReuseHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            body = IOUtils.toString(request.getReader()).getBytes(Charset.forName("UTF-8"));
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream bais = new ByteArrayInputStream(body);
            return new ServletInputStream() {


                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return bais.read();
                }

            };
        }
    }
}
