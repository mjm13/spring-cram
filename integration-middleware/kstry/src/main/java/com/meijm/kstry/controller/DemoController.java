package com.meijm.kstry.controller;

import cn.kstry.framework.core.engine.StoryEngine;
import cn.kstry.framework.core.engine.facade.ReqBuilder;
import cn.kstry.framework.core.engine.facade.StoryRequest;
import cn.kstry.framework.core.engine.facade.TaskResponse;
import com.meijm.kstry.data.dto.OutBoundParamDTO;
import com.meijm.kstry.data.dto.OutBoundResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private StoryEngine storyEngine;
    
    @GetMapping("/test")
    public OutBoundResultDTO test() {
        OutBoundParamDTO paramDTO = new OutBoundParamDTO();
        paramDTO.setCount(111);
        paramDTO.setOrderNo("m-test");
        StoryRequest<OutBoundResultDTO> fireRequest = ReqBuilder.returnType(OutBoundResultDTO.class)
                .request(paramDTO).startId("mjm")
                .build();
        TaskResponse<OutBoundResultDTO> result = storyEngine.fire(fireRequest);
        return result.getResult();
    }
}
