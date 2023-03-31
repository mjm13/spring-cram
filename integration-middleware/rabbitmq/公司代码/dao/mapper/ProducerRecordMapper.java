package com.prolog.rdc.outbound.mq.dao.mapper;

import com.prolog.product.rabbitmqreliable.producer.entity.Producer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProducerRecordMapper {

    List<Producer> selectProducerByMessageId(@Param("applicationId") String applicationId,
                                             @Param("messageIds") List<String> messageIds);
}
