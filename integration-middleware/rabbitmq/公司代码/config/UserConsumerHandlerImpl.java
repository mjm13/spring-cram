package com.prolog.rdc.outbound.mq.config;

import com.esotericsoftware.minlog.Log;
import com.prolog.product.core.framework.util.RequestUtils;
import com.prolog.product.rabbitmqreliable.comsumer.support.UserConsumerHandler;
import org.springframework.stereotype.Component;

@Component
public class UserConsumerHandlerImpl implements UserConsumerHandler {

    @Override
    public void consume(String userKey) {
        Log.info("...........:{}", userKey);
        RequestUtils.initHeader(userKey);
    }

}
