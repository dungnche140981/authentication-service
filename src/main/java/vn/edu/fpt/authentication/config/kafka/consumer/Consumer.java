package vn.edu.fpt.authentication.config.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/11/2022 - 09:21
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Slf4j
public abstract class Consumer {

    protected void listen(String value,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        log.info("Listen data: value:{} received_topic:{} received_message_key:{}", value, topic, key);
    }
}
