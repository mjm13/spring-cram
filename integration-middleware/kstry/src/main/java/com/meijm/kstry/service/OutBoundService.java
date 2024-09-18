package com.meijm.kstry.service;

import cn.kstry.framework.core.annotation.*;
import cn.kstry.framework.core.bus.ContextStoryBus;
import cn.kstry.framework.core.bus.StoryBus;
import cn.kstry.framework.core.engine.facade.StoryRequest;
import com.meijm.kstry.data.dto.OutBoundParamDTO;
import com.meijm.kstry.data.dto.OutBoundResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@TaskComponent
public class OutBoundService {

    @NoticeSta(target = "order")
    @TaskService(desc = "出库")
    public OutBoundResultDTO outBound(@ReqTaskParam(reqSelf = true) OutBoundParamDTO dto) {
        // mock return result
        OutBoundResultDTO resultDTO = new OutBoundResultDTO();
        resultDTO.setOrderNo(dto.getOrderNo()+"-out");
        resultDTO.setCount(dto.getCount()+100);
        return resultDTO;
    }

    @NoticeResult
    @TaskService(desc = "出库2")
    public OutBoundResultDTO outBound2(@StaTaskParam("order") OutBoundResultDTO dto) {
        // mock return result
        dto.setOrderNo(dto.getOrderNo()+"-out-2");
        dto.setCount(dto.getCount()+100);
        return dto;
    }
}
