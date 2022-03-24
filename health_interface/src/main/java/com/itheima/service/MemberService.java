package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

/**
 * @ author He
 * @ create 2022-03-14 15:02
 */
public interface MemberService {

    Member findByTelephone(String telephone);

    void add(Member member);

    List<Integer> findByMonths(List<String> months);

}
