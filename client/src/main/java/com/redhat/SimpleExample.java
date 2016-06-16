package com.redhat;

import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

import java.util.LinkedList;
import java.util.List;

public class SimpleExample {
	private static final String ENDPOINT = "http://kie-app-cruyff-rds-training.apps.latest.xpaas/kie-server/services/rest/server";
	private static final String USERNAME = "kieserver";
	private static final String PASSWORD = "yyvVpk6!";

	public static void main(String[] args) {
		KieServicesConfiguration config = KieServicesFactory.newRestConfiguration(ENDPOINT, USERNAME, PASSWORD);
		config.setMarshallingFormat(MarshallingFormat.JSON);
		config.setTimeout(300_000L);

		KieServicesClient client = KieServicesFactory.newKieServicesClient(config);
		RuleServicesClient rules = client.getServicesClient(RuleServicesClient.class);

		KieCommands cmdFactory = KieServices.Factory.get().getCommands();

		List<Command<?>> commands = new LinkedList<>();
		commands.add(cmdFactory.newInsert("Tomas Schlosser"));
		commands.add(cmdFactory.newFireAllRules());

		ServiceResponse<ExecutionResults> response = rules.executeCommandsWithResults("DemoContainer", cmdFactory.newBatchExecution(commands, "DemoProjectSession"));
		System.out.println(response.getMsg());
	}
}
