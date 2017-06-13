package com.wzj.learn.autotest.mapper;

import com.wzj.learn.autotest.domain.TestTableDO;
import com.wzj.learn.autotest.domain.TestTableQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 参考:https://examples.javacodegeeks.com/enterprise-java/testng/testng-spring-integration-example/
 */
public interface TestTableMapper {

    TestTableDO getById(Long id);

    Integer countTestTable(@Param("testTableQuery") TestTableQuery testTableQuery);

    List<TestTableDO> listTestTable(@Param("testTableQuery") TestTableQuery testTableQuery);

    List<TestTableDO> listTestTable(
            @Param("testTableQuery") TestTableQuery testTableQuery,
            @Param("offset") Integer offset,
            @Param("offset") Integer limit);

    Integer update(TestTableDO testTableDO);

    Integer deleteById(@Param("id") Long id);

    Long save(TestTableDO testTableDO);

}
