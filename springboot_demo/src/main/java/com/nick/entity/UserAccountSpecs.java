package com.nick.entity;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

/**
 * @author nick.liu
 * @create 2019/3/18 9:33
 */
public class UserAccountSpecs {

    public static Specification<UserAccount> isLoginThisWeek() {
        return new Specification<UserAccount>() {
            @Override
            public Predicate toPredicate(Root<UserAccount> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                LocalDate date = LocalDate.now().minusWeeks(5);
//                return criteriaBuilder.lessThan(root.get(_UserAccount.lastLoginTime), date);
                return null;
            }
        };
    }
}
