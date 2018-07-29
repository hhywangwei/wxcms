package com.tuoshecx.server.cms.tencent.map;

import com.tuoshecx.server.cms.tencent.map.client.cache.DistrictCache;
import com.tuoshecx.server.cms.tencent.map.client.impl.TcMapClients;
import com.tuoshecx.server.cms.tencent.configure.properties.TencentProperties;
import com.tuoshecx.server.cms.tencent.map.client.request.DistrictRequest;
import com.tuoshecx.server.cms.tencent.map.client.request.IpRequest;
import com.tuoshecx.server.cms.tencent.map.client.request.SearchRequest;
import com.tuoshecx.server.cms.tencent.map.client.response.DistrictResponse;
import com.tuoshecx.server.cms.tencent.map.client.response.IpResponse;
import com.tuoshecx.server.cms.tencent.map.client.response.SearchResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.function.Function;

/**
 * QQ地图调用服务
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class QqMapService {
    private TcMapClients clients;
    private TencentProperties properties;
    private DistrictCache cache;

    @Autowired
    public QqMapService(TencentProperties properties, RestTemplate restTemplate, DistrictCache cache){
        this.clients = new TcMapClients(restTemplate);
        this.properties = properties;
        this.cache = cache;
    }

    public DistrictResponse district(Function<DistrictRequest.Builder, DistrictRequest> function){

        DistrictRequest request = function.apply(
                new DistrictRequest.Builder(properties.getMapKey()));

        String id = StringUtils.isBlank(request.getId())? "root": request.getId();

        if(cache != null){
            Optional<DistrictResponse> optional = cache.get(id);
            if(optional.isPresent()){
                return optional.get();
            }
        }

        DistrictResponse response =  clients.getDistrictClient().request(request);
        if(cache != null){
            cache.put(id, response);
        }
        return response;
    }

    public SearchResponse search(Function<SearchRequest.Builder, SearchRequest> function){

        SearchRequest request = function.apply(new SearchRequest.Builder(properties.getMapKey()));
        return clients.getSearchClient().request(request);
    }

    public IpResponse ip(Function<IpRequest.Builder, IpRequest> function){

        IpRequest request = function.apply(new IpRequest.Builder(properties.getMapKey()));
        return clients.getIpClient().request(request);
    }

}
