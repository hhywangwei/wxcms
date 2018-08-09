package com.tuoshecx.server.wx.small.client.response;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VisitPageResponse extends WxSmallResponse {
    private final List<VisitPage> visitPages;

    @SuppressWarnings("unchecked")
    public VisitPageResponse(Map<String, Object> data){
        super(data);
        visitPages = ((List<Map<String,Object>>)data.getOrDefault("list", Collections.emptyList()))
                .stream().map(VisitPage::new).collect(Collectors.toList());
    }

    public List<VisitPage> getVisitPages() {
        return visitPages;
    }

    public static class VisitPage {
        private final String pagePath;
        private final Integer pageVisitPv;
        private final Integer pageVisitUv;
        private final Integer pageStayTimePv;
        private final Integer entryPagePv;
        private final Integer exitPagePv;
        private final Integer pageSharePv;
        private final Integer pageShareUv;

        public VisitPage(Map<String, Object> map){
            pagePath = (String)map.getOrDefault("page_path", "");
            pageVisitPv = (Integer)map.getOrDefault("page_visit_pv", 0);
            pageVisitUv = (Integer)map.getOrDefault("page_visit_uv", 0);
            pageStayTimePv = (Integer)map.getOrDefault("page_staytime_pv", 0);
            entryPagePv = (Integer)map.getOrDefault("entrypage_pv", 0);
            exitPagePv = (Integer)map.getOrDefault("exitpage_pv", 0);
            pageSharePv = (Integer)map.getOrDefault("page_share_pv", 0);
            pageShareUv = (Integer)map.getOrDefault("page_share_pv", 0);
        }

        public String getPagePath() {
            return pagePath;
        }

        public Integer getPageVisitPv() {
            return pageVisitPv;
        }

        public Integer getPageVisitUv() {
            return pageVisitUv;
        }

        public Integer getPageStayTimePv() {
            return pageStayTimePv;
        }

        public Integer getEntryPagePv() {
            return entryPagePv;
        }

        public Integer getExitPagePv() {
            return exitPagePv;
        }

        public Integer getPageSharePv() {
            return pageSharePv;
        }

        public Integer getPageShareUv() {
            return pageShareUv;
        }
    }
}
