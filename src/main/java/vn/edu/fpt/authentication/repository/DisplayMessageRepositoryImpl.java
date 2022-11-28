package vn.edu.fpt.authentication.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.edu.fpt.authentication.constant.AppConstant;
import vn.edu.fpt.authentication.constant.ResponseStatusEnum;
import vn.edu.fpt.authentication.entity.DisplayMessage;
import vn.edu.fpt.authentication.exception.BusinessException;

import java.util.Objects;
import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 05/11/2022 - 22:46
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
public class DisplayMessageRepositoryImpl implements DisplayMessageRepository{

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<DisplayMessage> findByCodeAndLanguage(String code, String language) {
        if (Objects.isNull(language)){
            language = AppConstant.DEFAULT_LANGUAGE;
        }

        String displayMessageStr = redisTemplate.opsForValue().get(String.format("%s:%s", code, language));
        try {
            return Optional.of(objectMapper.convertValue(displayMessageStr, DisplayMessage.class));
        }catch (Exception ex) {
            return Optional.empty();
        }
    }
}
