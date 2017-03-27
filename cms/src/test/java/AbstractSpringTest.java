
import org.activiti.engine.*;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.logging.Logger;

//@ContextConfiguration("classpath:activiti/activiti-context.xml")
@ContextConfiguration(locations = {"classpath:activiti/activiti-context.xml"})
public abstract class AbstractSpringTest extends AbstractTransactionalJUnit4SpringContextTests {

    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(AbstractSpringTest.class.getName());

    @SuppressWarnings("unused")
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected TaskService taskService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected ManagementService managementService;

    protected String deploymentId;

    public AbstractSpringTest() {
        super();
    }

    @Before
    public void initialize() throws Exception {
        beforeTest();
    }

    @After
    public void clean() throws Exception {
        afterTest();
    }

    protected abstract void beforeTest() throws Exception;

    protected abstract void afterTest() throws Exception;
}