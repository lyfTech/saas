import org.junit.Test;
import org.junit.runner.RunWith;
import org.saas.dao.domain.SysUser;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-mybatis.xml"})
public class TestMybatis {

    public static final Logger logger = LoggerFactory.getLogger(TestMybatis.class);

    @Autowired
    private SysUserService userService;

    @Test
    public void test1(){
        SysUser user = userService.getUserByName("root");
//        String s = JSONUtils.toJSONString(user);
        logger.info("========"+user.toString());
        System.out.println("========"+user.getUserName());
    }
}
