package cn.itcast.haoke.dubbo.api.service;

import cn.itcast.haoke.dubbo.api.vo.Pagination;
import cn.itcast.haoke.dubbo.api.vo.TableResult;
import cn.itcast.haoke.dubbo.server.api.ApiHouseResourcesService;
import cn.itcast.haoke.dubbo.server.pojo.HouseResources;
import cn.itcast.haoke.dubbo.server.vo.PageInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class HouseResourcesService {
    @Reference(version = "1.0.0")
    private ApiHouseResourcesService apiHouseResourcesService;

    public boolean save(HouseResources houseResources) {
        int result =
                this.apiHouseResourcesService.saveHouseResources(houseResources);
        return result == 1;
    }

    public TableResult<HouseResources> queryList(HouseResources houseResources, Integer currentPage, Integer pageSize) {
        PageInfo<HouseResources> houseResourcesPageInfo = this.apiHouseResourcesService.queryHouseResourcesList(currentPage, pageSize, houseResources);
        return new TableResult<HouseResources>(houseResourcesPageInfo.getRecords(),new Pagination(currentPage,pageSize,houseResourcesPageInfo.getTotal()));
    }
    public HouseResources queryHouseResourceById(Long id) {
        return this.apiHouseResourcesService.queryHouseResourcesById(id);
    }

    public Boolean update(HouseResources houseResources) {
        return apiHouseResourcesService.updataHouseResources(houseResources);
    }
}