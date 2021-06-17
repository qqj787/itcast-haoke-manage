package cn.itcast.haoke.dubbo.api.graphql;

import cn.itcast.haoke.dubbo.api.service.MongoHouseService;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapHouseDataFetcher implements MyDataFetcher {
    @Autowired
    private MongoHouseService mongoHouseService;
    @Override
    public String fileName() {
        return "MapHouseData";
    }

    @Override
    public Object dataFetcher(DataFetchingEnvironment environment) {
        Float lng = ((Double)environment.getArgument("lng")).floatValue();
        Float lat = ((Double)environment.getArgument("lat")).floatValue();
        Integer zoom = environment.getArgument("zoom");
        System.out.println("lng = " + lng + ",lat = " + lat + ",zoom = " + zoom);
        return this.mongoHouseService.queryHouseData(lng,lat,zoom);
    }
}
