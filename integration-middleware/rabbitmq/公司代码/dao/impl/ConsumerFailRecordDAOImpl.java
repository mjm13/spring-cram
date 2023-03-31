package com.prolog.rdc.outbound.mq.dao.impl;

import cn.hutool.core.lang.Assert;
import com.prolog.framework.core.pojo.Page;
import com.prolog.framework.dao.util.PageUtils;
import com.prolog.product.core.base.pojo.RequestPage;
import com.prolog.product.core.framework.util.mappercustom.MapperCustom;
import com.prolog.rdc.outbound.mq.dao.ConsumerFailRecordDAO;
import com.prolog.rdc.outbound.mq.dao.mapper.ConsumerFailRecordMapper;
import com.prolog.rdc.outbound.mq.entity.ConsumerFailRecord;
import com.prolog.rdc.outbound.mq.entity.ConsumerFailRecordQueryBO;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ConsumerFailRecordDAOImpl implements ConsumerFailRecordDAO {

    @Resource
    private ConsumerFailRecordMapper consumerFailRecordMapper;

    /**
     * 保存记录
     *
     * @param consumerFailRecord 消费失败记录
     */
    @Override
    public void saveRecord(ConsumerFailRecord consumerFailRecord) {
        long save = consumerFailRecordMapper.saveRecord(consumerFailRecord);
        Assert.isTrue(save > 0, "保存消费失败记录失败！");
    }

    /**
     * 查询消费失败记录
     *
     * @param bo 查询参数
     */
    @Override
    public Page<ConsumerFailRecord> pageRecord(RequestPage<ConsumerFailRecordQueryBO> bo) {
        PageUtils.startPage(bo.getPageNum(), bo.getPageSize());
        List<ConsumerFailRecord> byCriteria = MapperCustom.builder(consumerFailRecordMapper, ConsumerFailRecord.class)
                .andEq(ConsumerFailRecord::getBusinessType, bo.getData().getBusinessType())
                .andEq(ConsumerFailRecord::getBusinessNo, bo.getData().getBusinessNo())
                .andIn(ConsumerFailRecord::getId, bo.getData().getIds())
                .findByCriteria();
        return PageUtils.getPage(byCriteria);
    }

    /**
     * 通过ID批量删除
     *
     * @param ids 删除ID
     */
    @Override
    public void deleteRecordByMessageIds(String applicationId, List<String> ids) {
        Assert.notEmpty(applicationId, "应用ID不能为空");
        Assert.notEmpty(ids, "消息ID不能为空");
        consumerFailRecordMapper.deleteRecordByMessageIds(applicationId, ids);
//        Assert.isTrue(b > 0, "删除消费失败记录失败！");
    }

    @Override
    public List<ConsumerFailRecord> selectMessageIdByIds(List<String> ids) {
        List<ConsumerFailRecord> list = consumerFailRecordMapper.selectMessageIdByIds(ids);
        return list;
    }
}
