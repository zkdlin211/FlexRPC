package com.cliffe.flex.server.boot;


import com.cliffe.flex.server.registry.FlexRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Cliffe
 * @Date: 2023-01-08 4:52 下午
 */
@Component
public class FlexServerRunner {

	@Autowired
	private FlexRegistry flexRegistry;

	@Autowired
	private FlexServer flexServer;


	public void run(){
		flexRegistry.registryService();
		new Thread(() -> {
			flexServer.start();
		});
	}


}
