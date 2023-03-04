package vn.edu.fpt.authentication.config.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vn.edu.fpt.authentication.constant.ResponseStatusEnum;
import vn.edu.fpt.authentication.dto.event.CreateAccountActivityEvent;
import vn.edu.fpt.authentication.dto.event.SendEmailEvent;
import vn.edu.fpt.authentication.exception.BusinessException;

import java.util.UUID;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/12/2022 - 10:48
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
public class CreateAccountActivityProducer extends Producer{

    private static final String TOPIC = "create_account_horo";
    private final ObjectMapper objectMapper;

    @Autowired
    public CreateAccountActivityProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        super(kafkaTemplate);
        this.objectMapper = objectMapper;
    }

    public void sendMessage(CreateAccountActivityEvent event) {
        try {
            String value = objectMapper.writeValueAsString(event);
            super.sendMessage(TOPIC, UUID.randomUUID().toString(), value);
        } catch (JsonProcessingException ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't send message to topic "+ TOPIC+" : "+ex.getMessage());
        }
    }
}
