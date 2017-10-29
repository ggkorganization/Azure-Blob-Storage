package com.example.demo;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

@SpringBootApplication
public class AzureBlobApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzureBlobApplication.class, args);
	}
}




/*@RequestMapping("/hell")
@ResponseBody
public String h() {

	try {
		CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
		CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
		CloudBlobContainer container = blobClient.getContainerReference("mycontainer");
		container.createIfNotExists();
		BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
		containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
		container.uploadPermissions(containerPermissions);

		final String filePath = "H:\\E\\Dangal 2016 Hindi 720p Blu-Ray x264 AAC ESubs { TaRa }\\Dangal_Poster.jpg";

		CloudBlockBlob blob = container.getBlockBlobReference("myimage.jpg");
		File source = new File(filePath);
		blob.upload(new FileInputStream(source), source.length());

	} catch (Exception e) {
		e.printStackTrace();
	}

	return "hello";
}*/