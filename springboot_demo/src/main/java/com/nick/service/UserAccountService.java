package com.nick.service;

import com.nick.entity.UserAccount;
import com.nick.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author nick.liu
 * @create 2019/3/14 15:20
 */
@Component
public class UserAccountService {

    private static final Logger log = LoggerFactory.getLogger(UserAccountService.class);

    @Autowired
    private UserAccountRepository userAccountRepository;

    public void findUsers() {
        log.info("retrieve users by page......");
        Pageable pageable = PageRequest.of(0, 20);
        Page<UserAccount> userPage = userAccountRepository.findByIsActive(Boolean.TRUE, pageable);
        List<UserAccount> userAccountList = userPage.getContent();
        log.info("Count of users: " + userPage.getTotalElements());

//        log.info("retrieve users by slice......");
//        Sort sort = Sort.by(Sort.Order.by("loginName"));
//        Slice<UserAccount> userSlice = userAccountRepository.findByIsActive(Boolean.TRUE, sort);
//        List<UserAccount> userAccounts = userSlice.getContent();
//        log.info("Count of users: " + userSlice.getNumber());
    }

}
