import org.junit.Test;
import org.junit.runner.RunWith;
import org.saas.common.utils.GsonUtils;
import org.saas.dao.domain.SysUser;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-context.xml"})
public class TestMybatis {

    public static final Logger logger = LoggerFactory.getLogger(TestMybatis.class);

    @Autowired
    private SysUserService userService;

    private int i = 0;

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(new ExecuteThread("root"));
            t.start();
            latch.countDown();
        }
        Thread.sleep(1000);
    }


    private CountDownLatch latch = new CountDownLatch(1000);

    private class ExecuteThread implements Runnable{
        private String userName;
        public ExecuteThread(String userName){
            this.userName = userName;
        }
        public void run() {
            try {
                latch.await();
            } catch (InterruptedException e) {
                logger.error("线程异常：{}", e.getMessage());
            }
            SysUser user = userService.getUserByName(userName);
            if (user != null){
                logger.info("==={}",++i);
            }
        }
    }
}
