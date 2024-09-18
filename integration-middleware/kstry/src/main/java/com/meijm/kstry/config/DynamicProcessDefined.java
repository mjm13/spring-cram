package com.meijm.kstry.config;

import cn.kstry.framework.core.component.bpmn.link.ProcessLink;
import cn.kstry.framework.core.component.bpmn.link.StartProcessLink;
import cn.kstry.framework.core.component.dynamic.creator.DynamicProcess;
import cn.kstry.framework.core.component.jsprocess.transfer.JsonProcessParser;
import com.meijm.kstry.service.OutBoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class DynamicProcessDefined implements DynamicProcess {
    @Override
    public Optional<ProcessLink> getProcessLink(String startId) {
//        JsonProcessParser parser = new JsonProcessParser(startId,"");
        StartProcessLink processLink = StartProcessLink.build("mjm");
        processLink
                .nextService(OutBoundService::outBound).build()
                .nextService(OutBoundService::outBound2).build()
                .end();
        return Optional.of(processLink);
    }
}