package com.blibee.activitiTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO completion javadoc.
 *
 * @author chuancong.xue
 * @since 05 八月 2018
 */
public class ActivitiUserTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiUserTest.class);


	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	@Deployment(resources = "my-processUser1.bpmn20.xml")
	public void test() {
		ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
		LOGGER.info("构建流程实例:key:{}, name:{}", processInstance.getBusinessKey(), processInstance.getName());

//		Only select tasks for which the given user is a candidate.
		Task task = activitiRule.getTaskService().createTaskQuery().taskCandidateUser("user1").singleResult();
		LOGGER.info("task for user1:{}", task);
		task = activitiRule.getTaskService().createTaskQuery().taskCandidateUser("user2").singleResult();
		LOGGER.info("task for user2:{}", task);
		activitiRule.getTaskService().claim(task.getId(), "user1");

//		候选人是or的关系
		task = activitiRule.getTaskService().createTaskQuery().taskCandidateOrAssigned("user1").singleResult();
		LOGGER.info("user1 what?{}", task);
		task = activitiRule.getTaskService().createTaskQuery().taskCandidateOrAssigned("user2").singleResult();
		LOGGER.info("user2 what?{}", task);

	}
}
