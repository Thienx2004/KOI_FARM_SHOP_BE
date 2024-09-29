package com.group2.KoiFarmShop.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    public String getImageUrl(String fileName) {

        Bucket bucket = StorageClient.getInstance().bucket();


        Blob blob = bucket.get(fileName);


        if (blob == null) {
            throw new RuntimeException("File không tồn tại trong Firebase Storage");
        }


        return blob.signUrl(30, java.util.concurrent.TimeUnit.DAYS).toString(); // URL tồn tại trong 14 ngày
    }


}


