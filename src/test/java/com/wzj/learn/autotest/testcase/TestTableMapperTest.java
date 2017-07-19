package com.wzj.learn.autotest.testcase;


import com.wzj.learn.autotest.domain.TestTableDO;
import com.wzj.learn.autotest.mapper.TestTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Date;

@ContextConfiguration(locations = "classpath:spring-config-dao-test.xml")
public class TestTableMapperTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private TestTableMapper testTableMapper;

    @BeforeMethod
    protected void setUp() throws Exception {
    }

    @Test
    @Transactional()
    public void testSave() {
        TestTableDO testTableDO = new TestTableDO();
        testTableDO.setType(1);
        testTableDO.setGmtCreate(new Date());
        testTableDO.setGmtModified(new Date());
        testTableDO.setMarkup("asdfasdfasdfasdfasd");
        testTableDO.setName("wanglu test");
        testTableDO.setStatus(2);
        Long id = testTableMapper.save(testTableDO);
        System.out.println("Id: " + id);
        Assert.assertNotNull(id);
    }
}


