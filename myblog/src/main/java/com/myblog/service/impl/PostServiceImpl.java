package com.myblog.service.impl;

import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;
import jdk.dynalink.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
Post  post   = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);

       PostDto dto = mapToDto(savedPost);
       return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository deleById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
     Post post =    postRepository.findById(id).orElseThrow(
             ()->new ResourceNotFound("postnot found with id:"+id)
     );
     post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(post.getDescription());
        Post updatePost = postRepository.save(post);
        PostDto dto = mapToDto(updatePost);
        return dto;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFound("postnot found with id:" + id)
        );
     PostDto dto =    mapToDto(post);
     return dto;
    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
     Sort sort =   sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();
        Pageable pageable  = PageRequest.of(pageNo,pageSize,Sort.by(sortBy));
        Page<Post> pagePosts = postRepository.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDto(postDtos);
        postResponse.setPageNo(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements((pagePosts.getTotalElements()));
        postResponse.setLast(pagePosts.isLast());
        postResponse.setTotalPages((pagePosts.getTotalPages()));
        return postResponse;

    }

    PostDto mapToDto(Post post){
        PostDto dto = new PostDto();
        dto.setId(Post.getId());
        dto.setTitle(Post.getTitle());
        dto.setDescription(Post.getDescription());
        dto.setContent(Post.getContent());
        return dto;
    }
   Post mapToEntity(PostDto postDto){
       Post post = new Post();
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());
        return post;
    }
}
