package vn.edu.fpt.authentication.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:35
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@CacheConfig(cacheNames = "displayMessage")
public interface DisplayMessageService {
    @Cacheable
    String getDisplayMessage(String code);


    @Cacheable
    String getDisplayMessage(String code, String language);

}
