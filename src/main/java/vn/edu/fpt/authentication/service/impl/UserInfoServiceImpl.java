package vn.edu.fpt.authentication.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import vn.edu.fpt.authentication.dto.cache.UserInfo;
import vn.edu.fpt.authentication.exception.BusinessException;
import vn.edu.fpt.authentication.service.UserInfoService;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 21/11/2022 - 07:55
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public UserInfo getUserInfo(String accountId) {
        try {
            String userInfoStr = redisTemplate.opsForValue().get(String.format("userinfo:%s", accountId));
            return objectMapper.readValue(userInfoStr, UserInfo.class);
        }catch (Exception ex){
            log.error("Can't get userinfo in redis: {}", ex.getMessage());
            return null;
        }
    }
}
