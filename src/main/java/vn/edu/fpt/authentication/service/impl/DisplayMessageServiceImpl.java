package vn.edu.fpt.authentication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.authentication.entity.DisplayMessage;
import vn.edu.fpt.authentication.repository.DisplayMessageRepository;
import vn.edu.fpt.authentication.service.DisplayMessageService;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/09/2022 - 18:03
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class DisplayMessageServiceImpl implements DisplayMessageService {

    private final DisplayMessageRepository displayMessageRepository;

    @Override
    public String getDisplayMessage(String code) {
        return getDisplayMessage(code, LocaleContextHolder.getLocale().getLanguage());
    }

    @Override
    public String getDisplayMessage(String code, String language) {
        DisplayMessage displayMessage = displayMessageRepository.findByCodeAndLanguage(code, language)
                .orElse(null);
        return displayMessage == null
                ? null
                : displayMessage.getMessage();
    }
}
