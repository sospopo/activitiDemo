package com.blibee.activitiTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.assertj.core.util.Lists;
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
public class ActivitiUser1Test {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiUser1Test.class);


	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	@Deployment(resources = "my-processUser2.bpmn20.xml")
	public void test() {
		Map<String, Object> variables = Maps.newHashMap();
		ArrayList<String> assigneeUserList = Lists.newArrayList();
		assigneeUserList.add("user1");
		assigneeUserList.add("user2");
		assigneeUserList.add("user3");
		variables.put("assigneeUsers", assigneeUserList);
		ProcessInstance processInstance = activitiRule.getRuntimeService()
				.startProcessInstanceByKey("my-process", variables);

		TaskService taskService = activitiRule.getTaskService();
		List<Task> list = taskService.createTaskQuery().list();
		for (Task task: list
			 ) {
			LOGGER.info("task:{}", task);
//			taskService.complete(task.getId());
		}



	}
}
