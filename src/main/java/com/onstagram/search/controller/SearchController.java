package com.onstagram.search.controller;

import com.onstagram.search.domain.searchResult;
import com.onstagram.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search/{str}") //회원 검색
    public searchResult search(@PathVariable("str") String str) {
        log.info("str : " + str);
//        String encodedStr = URLEncoder.encode(searchStr, StandardCharsets.UTF_8.toString()); 혹시모르는 회원검색
        return new searchResult(HttpStatus.OK.value(), true, searchService.search(str));
    }


}
