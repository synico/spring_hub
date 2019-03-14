package com.nick.repository;

import com.nick.entity.UserAccount;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author nick.liu
 * @create 2019/3/14 14:59
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {

    Page<UserAccount> findByIsActive(Boolean isActive, Pageable pageable);

    Slice<UserAccount> findByIsActive(Boolean isActive, Sort sort);

//    List<UserAccount> findByIsActive(Boolean isActive, Sort sort);

    @Async
    Future<UserAccount> findByIsActive(Boolean isActive);

    @Async
    CompletableFuture<UserAccount> findByIsLocked(Boolean isActive);

    @Async
    ListenableFuture<UserAccount> findByIsSiteAdmin(Boolean isSiteAdmin);
}
