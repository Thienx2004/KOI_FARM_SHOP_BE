package com.group2.KoiFarmShop.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FirebaseService {

    public String getImageUrl(String fileName) {

        Bucket bucket = StorageClient.getInstance().bucket();


        Blob blob = bucket.get(fileName);


        if (blob == null) {
            throw new RuntimeException("File không tồn tại trong Firebase Storage");
        }


        return blob.signUrl(365, java.util.concurrent.TimeUnit.DAYS).toString(); // URL tồn tại trong ? ngày
    }

    private final String bucketName = "koi-farm-shop-5212e.appspot.com";

    public List<String> getImageUrls(String folderName) {
        List<String> imageUrls = new ArrayList<>();

        Storage storage = StorageOptions.getDefaultInstance().getService();
        Bucket bucket = storage.get(bucketName);

        for (Blob blob : bucket.list(Storage.BlobListOption.prefix(folderName)).iterateAll()) {
            if (!blob.isDirectory()) {
                // Tạo URL truy cập công khai
                String imageUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, blob.getName());
                imageUrls.add(imageUrl);
            }
        }

        return imageUrls;
    }


   public String uploadImage(MultipartFile file) throws IOException {
       Bucket bucket = StorageClient.getInstance().bucket();
       String blobName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
       Blob blob = bucket.create(blobName, file.getInputStream(),file.getContentType());
       return blob.getMediaLink();
   }

    public String uploadFile(MultipartFile file) throws IOException {
        // Lấy bucket của Firebase
        Bucket bucket = StorageOptions.getDefaultInstance().getService().get("koi-farm-shop-5212e.appspot.com");


        // Tạo tên file unique để tránh trùng lặp
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Upload file lên Firebase Storage
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

        // Trả về URL để truy cập file đã upload
        return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), fileName);

    }
}