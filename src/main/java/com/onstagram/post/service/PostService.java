package com.onstagram.post.service;

import com.onstagram.Member.domain.MemberDto;
import com.onstagram.Member.domain.ModifyDto;
import com.onstagram.comment.domain.CommentDto;
import com.onstagram.post.domain.PostDto;
import com.onstagram.post.domain.PostModifyDto;
import com.onstagram.post.entity.PostEntity;

import java.util.List;

public interface PostService {
    
    public PostEntity findById(Long id); //게시물등록페이지에서 사용할 로그인한 회원 정보
    public PostEntity AddPost(PostEntity postEntity); //게시물 정보 등록
    public PostDto postInfo(Long postId); //게시물 정보
    public List<PostDto> postList(Long userId); //회원의 모든 게시물의 정보(본인 제외)
    public PostDto postModify(PostModifyDto postModifyDto); //게시물 수정
    public void postDelete(Long postId); //게시물 삭제
    public PostEntity postAndUserInfo(Long postId); //게시물 정보와 작성자 정보
    public List<CommentDto> commemtList(Long postId); //게시물의 댓글 정보(리스트)

}