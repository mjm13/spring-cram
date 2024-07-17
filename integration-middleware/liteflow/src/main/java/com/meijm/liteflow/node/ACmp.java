package com.meijm.liteflow.node;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component("a")
public class ACmp extends NodeComponent {
	@Resource
	private FlowExecutor flowExecutor;
	@Override
	public void process() {
		log.info("a");
		
	}
}
