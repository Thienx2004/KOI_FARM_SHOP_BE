package com.group2.KoiFarmShop.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FirebaseService {

    public String getImageUrl(String fileName) {

        Bucket bucket = StorageClient.getInstance().bucket();


        Blob blob = bucket.get(fileName);


        if (blob == null) {
            throw new RuntimeException("File không tồn tại trong Firebase Storage");
        }


        return blob.signUrl(30, java.util.concurrent.TimeUnit.DAYS).toString(); // URL tồn tại trong 30 ngày
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


}


