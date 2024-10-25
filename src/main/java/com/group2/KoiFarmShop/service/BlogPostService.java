package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.request.BlogCreationRequest;
import com.group2.KoiFarmShop.dto.response.BlogResponse;
import com.group2.KoiFarmShop.dto.response.PaginReponse;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.BlogPost;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogPostService implements BlogPostServiceImp {

    @Autowired
    private BlogPostRepository blogPostRepository;
    @Autowired
    private FirebaseService firebaseService;

    @Override
    public BlogPost addBlogPost(BlogCreationRequest blogPost) throws IOException {

        Account account = new Account();
        account.setAccountID(blogPost.getAccountId());

        BlogPost newBlogPost = new BlogPost();
        newBlogPost.setTitle(blogPost.getTitle());
        newBlogPost.setSubTitle(blogPost.getSubTitle());
        newBlogPost.setContent(blogPost.getContent());
        newBlogPost.setBlogImg(firebaseService.uploadImage(blogPost.getBlogImg()));
        newBlogPost.setPostDate(new Date());
        newBlogPost.setAccount(account);
        newBlogPost.setStatus(blogPost.getStatus());

        blogPostRepository.save(newBlogPost);

        return newBlogPost;
    }

    @Override
    public BlogResponse updateBlogPost(int blogId, BlogCreationRequest blogPost) throws IOException {

        BlogPost existedBlog = blogPostRepository.findBlogPostByBlogPostID(blogId)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_FOUND));

        existedBlog.setTitle(blogPost.getTitle());
        existedBlog.setSubTitle(blogPost.getSubTitle());
        existedBlog.setContent(blogPost.getContent());
        existedBlog.setPostDate(existedBlog.getPostDate());
        existedBlog.setAccount(existedBlog.getAccount());
        if (blogPost.getBlogImg() != null) {
            existedBlog.setBlogImg(firebaseService.uploadImage(blogPost.getBlogImg()));
        } else {
            existedBlog.setBlogImg(existedBlog.getBlogImg());
        }
        existedBlog.setStatus(blogPost.getStatus());
        blogPostRepository.save(existedBlog);

        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setBlogId(existedBlog.getBlogPostID());
        blogResponse.setTitle(existedBlog.getTitle());
        blogResponse.setSubTitle(existedBlog.getSubTitle());
        blogResponse.setContent(existedBlog.getContent());
        blogResponse.setPostDate(existedBlog.getPostDate());
        blogResponse.setBlogImg(existedBlog.getBlogImg());
        blogResponse.setAccountId(existedBlog.getAccount().getAccountID());
        blogResponse.setAccountName(existedBlog.getAccount().getFullName());
        blogResponse.setStatus(existedBlog.getStatus());

        return blogResponse;
    }

    @Override
    public String deleteBlogPost(int blogId) {
        BlogPost blogPost = blogPostRepository.findBlogPostByBlogPostID(blogId)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_FOUND));
        blogPostRepository.delete(blogPost);
        return "Đã xoá thành công blog id: " + blogId;
    }

    @Override
    public PaginReponse<BlogResponse> getAllBlogPosts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("blogPostID").descending());
        Page<BlogPost> blogPosts = blogPostRepository.findAll(pageable);
        List<BlogResponse> blogResponses = new ArrayList<>();
        for (BlogPost blogPost : blogPosts.getContent()) {
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setBlogId(blogPost.getBlogPostID());
            blogResponse.setTitle(blogPost.getTitle());
            blogResponse.setSubTitle(blogPost.getSubTitle());
            blogResponse.setContent(blogPost.getContent());
            blogResponse.setPostDate(blogPost.getPostDate());
            blogResponse.setBlogImg(blogPost.getBlogImg());
            blogResponse.setAccountId(blogPost.getAccount().getAccountID());
            blogResponse.setAccountName(blogPost.getAccount().getFullName());
            blogResponse.setStatus(blogPost.getStatus());

            blogResponses.add(blogResponse);
        }
        PaginReponse<BlogResponse> paginReponse = new PaginReponse<>();
        paginReponse.setPageNum(pageNo);
        paginReponse.setPageSize(pageSize);
        paginReponse.setContent(blogResponses);
        paginReponse.setTotalElements(blogPosts.getTotalElements());
        paginReponse.setTotalPages(blogPosts.getTotalPages());
        return paginReponse;

    }

    @Override
    public BlogResponse getBlogPostDetail(int blogId) {
        BlogPost blogPost = blogPostRepository.findBlogPostByBlogPostID(blogId)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_FOUND));

        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setBlogId(blogPost.getBlogPostID());
        blogResponse.setTitle(blogPost.getTitle());
        blogResponse.setSubTitle(blogPost.getSubTitle());
        blogResponse.setContent(blogPost.getContent());
        blogResponse.setPostDate(blogPost.getPostDate());
        blogResponse.setBlogImg(blogPost.getBlogImg());
        blogResponse.setAccountId(blogPost.getAccount().getAccountID());
        blogResponse.setAccountName(blogPost.getAccount().getFullName());
        blogResponse.setStatus(blogPost.getStatus());

        return blogResponse;
    }

    @Override
    public String changeStatus(int blogId, int status) {
        boolean blogStatus = status == 1;
        BlogPost blogPost = blogPostRepository.findBlogPostByBlogPostID(blogId)
                .orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_FOUND));
        blogPost.setStatus(blogStatus);
        blogPostRepository.save(blogPost);

        return "Thay đổi status blog id: " + blogId + " thành công";
    }
}
