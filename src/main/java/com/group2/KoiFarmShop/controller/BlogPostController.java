package com.group2.KoiFarmShop.controller;


import com.group2.KoiFarmShop.dto.request.BlogCreationRequest;
import com.group2.KoiFarmShop.dto.response.ApiReponse;
import com.group2.KoiFarmShop.dto.response.BlogResponse;
import com.group2.KoiFarmShop.dto.response.PaginReponse;
import com.group2.KoiFarmShop.entity.BlogPost;
import com.group2.KoiFarmShop.service.BlogPostServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/blog")
public class BlogPostController {

    @Autowired
    private BlogPostServiceImp blogPostService;

    @PostMapping("/createBlog")
    public ApiReponse<BlogPost> createBlog(@ModelAttribute BlogCreationRequest blogPost) throws IOException {
        ApiReponse<BlogPost> response = new ApiReponse<>();
        response.setData(blogPostService.addBlogPost(blogPost));
        return response;
    }

    @GetMapping("/getAllBlog/{pageNo}/{pageSize}")
    public ApiReponse<PaginReponse<BlogResponse>> getAllBlog(@PathVariable int pageNo, @PathVariable int pageSize) throws IOException {
        ApiReponse<PaginReponse<BlogResponse>> response = new ApiReponse<>();
        response.setData(blogPostService.getAllBlogPosts(pageNo, pageSize));
        return response;
    }

    @GetMapping("/getBlogDetail/{blogId}")
    public ApiReponse<BlogResponse> getBlogDetail(@PathVariable int blogId) throws IOException {
        ApiReponse<BlogResponse> response = new ApiReponse<>();
        response.setData(blogPostService.getBlogPostDetail(blogId));
        return response;
    }

    @PutMapping("/updateBlog/{blogId}")
    public ApiReponse<BlogResponse> updateBlog(@PathVariable int blogId, @ModelAttribute BlogCreationRequest blogPost) throws IOException {
        ApiReponse<BlogResponse> response = new ApiReponse<>();
        response.setData(blogPostService.updateBlogPost(blogId, blogPost));
        return response;
    }

    @DeleteMapping("/deleteBlog/{blogId}")
    public ApiReponse<String> deleteBlog(@PathVariable int blogId) throws IOException {
        ApiReponse<String> response = new ApiReponse<>();
        response.setData(blogPostService.deleteBlogPost(blogId));
        return response;
    }

    @PutMapping("/changeStatus/{blogId}")
    public ApiReponse<String> changeStatus(@PathVariable int blogId, @RequestParam int status) throws IOException {
        ApiReponse<String> response = new ApiReponse<>();
        response.setData(blogPostService.changeStatus(blogId, status));
        return response;
    }
}
