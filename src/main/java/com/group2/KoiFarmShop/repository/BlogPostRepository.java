package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogPostRepository extends JpaRepository<BlogPost,Integer> {

    public Optional<BlogPost> findBlogPostByBlogPostID(int blogPostID);
}
