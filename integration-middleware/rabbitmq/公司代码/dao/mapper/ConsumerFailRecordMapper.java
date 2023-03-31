package com.prolog.rdc.outbound.mq.dao.mapper;

import com.prolog.framework.dao.mapper.BaseMapper;
import com.prolog.rdc.outbound.mq.entity.ConsumerFailRecord;
import com.prolog.rdc.outbound.mq.entity.ConsumerFailRecordQueryBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConsumerFailRecordMapper extends BaseMapper<ConsumerFailRecord> {

    List<ConsumerFailRecord> selectMessageIdByIds(List<String> ids);

    List<ConsumerFailRecord> selectRecordList(ConsumerFailRecordQueryBO bo);

    long deleteRecordByMessageIds(@Param("applicationId") String applicationId,
                                 @Param("messageIds") List<String> messageIds);

    long saveRecord(ConsumerFailRecord consumerFailRecord);
}
