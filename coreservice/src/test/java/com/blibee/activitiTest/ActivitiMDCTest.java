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
public class ActivitiMDCTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiMDCTest.class);


	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	@Deployment(resources = "com/blibee/activitiTest/my-processMDC.bpmn20.xml")
	public void test() {
		ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");
		assertNotNull(processInstance);

		Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
		assertEquals(task.getName(), "Activiti is awesome!");
		LOGGER.info("task id:{}, {}", task.getId(), task.getTaskDefinitionKey());
		activitiRule.getTaskService().complete(task.getId());


	}
}
