package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.request.BlogCreationRequest;
import com.group2.KoiFarmShop.dto.response.BlogResponse;
import com.group2.KoiFarmShop.dto.response.PaginReponse;
import com.group2.KoiFarmShop.entity.BlogPost;

import java.io.IOException;
import java.util.List;

public interface BlogPostServiceImp {

    BlogPost addBlogPost(BlogCreationRequest blogPost) throws IOException;
    BlogResponse updateBlogPost(int blogId, BlogCreationRequest blogPost) throws IOException;
    String deleteBlogPost(int blogId);
    PaginReponse<BlogResponse> getAllBlogPosts(int pageNo, int pageSize);
    BlogResponse getBlogPostDetail(int blogId);
    String changeStatus(int blogId, int status);
}
