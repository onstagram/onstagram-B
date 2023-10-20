package com.onstagram.search.service;

import com.onstagram.Member.domain.MemberDto;

import java.util.List;

public interface SearchService {

    public List<MemberDto> search(String str);
}
