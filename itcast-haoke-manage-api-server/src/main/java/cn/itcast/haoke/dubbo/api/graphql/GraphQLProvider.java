package cn.itcast.haoke.dubbo.api.graphql;

import cn.itcast.haoke.dubbo.api.service.HouseResourcesService;
import cn.itcast.haoke.dubbo.server.pojo.HouseResources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

@Component
public class GraphQLProvider {
    private GraphQL graphQL;
    @Autowired
    private List<MyDataFetcher> myDataFetchers;
//
//    @Autowired
//    private HouseResourcesService houseResourcesService;

    @PostConstruct
    public void init() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:haoke.graphqls");
        this.graphQL = GraphQL.newGraphQL(buildGraphQLSchema(file)).build();
    }

    private GraphQLSchema buildGraphQLSchema(File file) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(file);
        return new SchemaGenerator().makeExecutableSchema(typeRegistry, buildWiring());

    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("HaokeQuery", builder -> {
                            for (MyDataFetcher myDataFetcher : myDataFetchers) {
                                builder.dataFetcher(myDataFetcher.fileName(), environment -> myDataFetcher.dataFetcher(environment));
                            }
                            return builder;
                        }
//                        builder.dataFetcher("HouseResources", environment -> {
//                            Long id = environment.getArgument("id");
//                            return houseResourcesService.queryHouseResourceById(id);
//                        })
                )
                .build();
    }

    @Bean
    public GraphQL graphQL() {
        return this.graphQL;
    }

}
