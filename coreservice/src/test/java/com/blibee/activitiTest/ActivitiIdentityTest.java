package com.blibee.activitiTest;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
public class ActivitiIdentityTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiIdentityTest.class);

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	@Deployment(resources = "my-processIdentity.bpmn20.xml")
	public void test() {
		ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process");

		IdentityService identityService = activitiRule.getIdentityService();
		User user1 = identityService.newUser("user1");
		user1.setEmail("user1@163.com");
		User user2 = identityService.newUser("user2");
		user2.setEmail("user2@163.com");
		identityService.saveUser(user1);
		identityService.saveUser(user2);

		Group group1 = activitiRule.getIdentityService().newGroup("group1");
		identityService.saveGroup(group1);
		Group group2 = activitiRule.getIdentityService().newGroup("group2");
		identityService.saveGroup(group2);

		identityService.createMembership(user1.getId(), group1.getId());
		identityService.createMembership(user2.getId(), group1.getId());
		identityService.createMembership(user1.getId(), group2.getId());

		List<User> users = identityService.createUserQuery().memberOfGroup("group1").listPage(0, 99);
		for (User user: users
			 ) {
			LOGGER.info("user = {}",ToStringBuilder.reflectionToString(user,ToStringStyle.JSON_STYLE));
		}
	}
}
