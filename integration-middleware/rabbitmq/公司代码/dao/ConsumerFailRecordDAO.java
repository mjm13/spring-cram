package com.prolog.rdc.outbound.mq.dao;

import com.prolog.framework.core.pojo.Page;
import com.prolog.product.core.base.pojo.RequestPage;
import com.prolog.rdc.outbound.mq.entity.ConsumerFailRecord;
import com.prolog.rdc.outbound.mq.entity.ConsumerFailRecordQueryBO;

import java.util.List;

/**
 * 数据访问层
 */
public interface ConsumerFailRecordDAO {

    /**
     * 保存记录
     */
    void saveRecord(ConsumerFailRecord consumerFailRecord);

    /**
     * 查询消费失败记录
     */
    Page<ConsumerFailRecord> pageRecord(RequestPage<ConsumerFailRecordQueryBO> bo);

    /**
     * 通过ID批量删除
     */
    void deleteRecordByMessageIds(String applicationId, List<String> ids);

    List<ConsumerFailRecord> selectMessageIdByIds(List<String> ids);
}
