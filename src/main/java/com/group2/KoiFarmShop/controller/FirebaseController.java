package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FirebaseController {
    @Autowired
    private FirebaseService firebaseService;



    @GetMapping("/image/{fileName}")
    public String getImageUrl(@PathVariable String fileName) {
        return firebaseService.getImageUrl(fileName);
    }

    @GetMapping("/image-gallery")
    public String getImageGallery(Model model) {
        List<String> imageUrls = firebaseService.getImageUrls("KoiAsagi/");
        model.addAttribute("imageUrls", imageUrls);
        return "images";
    }
}