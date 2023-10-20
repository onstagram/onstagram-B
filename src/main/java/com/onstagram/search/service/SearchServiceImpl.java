package com.onstagram.search.service;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.entity.MemberEntity;
import com.onstagram.exception.OnstagramException;
import com.onstagram.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;

    @Override
    public List<MemberDto> search(String str) {
        List<MemberDto> list = new ArrayList<>();
        try {
            List<MemberEntity> members = searchRepository.findByMembers(str);

            for(MemberEntity member : members) {
                list.add(MemberDto.builder().memberEntity(member).build());
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
            throw new OnstagramException(HttpStatus.BAD_REQUEST.value(), "계정검색 싶패");
        }

    }
}
