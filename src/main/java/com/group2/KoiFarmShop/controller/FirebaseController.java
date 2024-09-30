package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RequestMapping("/api/upload")
@RestController
public class FirebaseController {
    @Autowired
    private FirebaseService firebaseService;



//    @GetMapping("/image/{fileName}")
//    public String getImageUrl(@PathVariable String fileName) {
//        return firebaseService.getImageUrl(fileName);
//    }
//
//    @GetMapping("/image-gallery")
//    public String getImageGallery(Model model) {
//        List<String> imageUrls = firebaseService.getImageUrls("KoiAsagi/");
//        model.addAttribute("imageUrls", imageUrls);
//        return "images";
//    }

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = firebaseService.uploadFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

}