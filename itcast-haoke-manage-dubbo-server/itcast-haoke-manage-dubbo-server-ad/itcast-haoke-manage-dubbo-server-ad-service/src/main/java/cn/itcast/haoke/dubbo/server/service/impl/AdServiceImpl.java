package cn.itcast.haoke.dubbo.server.service.impl;

import cn.itcast.haoke.dubbo.server.pojo.Ad;
import cn.itcast.haoke.dubbo.server.service.AdService;
import cn.itcast.haoke.dubbo.server.service.BaseServiceImpl;
import cn.itcast.haoke.dubbo.server.vo.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;

@Service
public class AdServiceImpl extends BaseServiceImpl<Ad> implements AdService {
    @Override
    public PageInfo<Ad> queryAdList(Ad ad, Integer page, Integer pageSize) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("updated");
        queryWrapper.eq("type",ad.getType());
        IPage iPage = super.queryPageList(queryWrapper, page, pageSize);
        return  new PageInfo<>(Long.valueOf(iPage.getTotal()).intValue(),page,pageSize,iPage.getRecords());
    }
}
