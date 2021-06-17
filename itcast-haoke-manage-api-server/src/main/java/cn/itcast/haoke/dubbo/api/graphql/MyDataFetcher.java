package cn.itcast.haoke.dubbo.api.graphql;

import graphql.schema.DataFetchingEnvironment;

public interface MyDataFetcher {
    String fileName();
    Object dataFetcher(DataFetchingEnvironment environment);
}
