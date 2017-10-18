package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

@Controller
public class IndexController {

	public static final String storageConnectionString = "DefaultEndpointsProtocol=https;"
			+ "AccountName=springbootstorage;" + "AccountKey=r5jZAhx/ut9ND24hNxW93pE3MKafPCBjARZatC83udFg0B/"
			+ "rS3jb5IT6A2kLOkPdtC36SRTJ2pBiDIy24jZWYg==;" + "EndpointSuffix=core.windows.net";

	@Autowired
	CustomerService customerService;

	@RequestMapping("/")
	public String index() {
		return "upload.html";
	}

	@RequestMapping("/getCount")
	@ResponseBody
	public String getCount() {
		List<Customer> listOfCustomers = customerService.getAllCustomers();
		System.out.println(listOfCustomers.size());
		return Integer.toString(listOfCustomers.size());
	}

	@RequestMapping("/uploadImage")
	@ResponseBody
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		try {

			if (file.isEmpty()) {
				redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
				return "hello";
			}

			CloudBlobContainer container = createContainerAndSetPermissions();
			CloudBlockBlob blob = container.getBlockBlobReference(file.getOriginalFilename());
			File convertedFile = convert(file);

			blob.upload(new FileInputStream(convertedFile), convertedFile.length());

		} catch (Exception e) {
			e.printStackTrace();
			return "Upload Failed";
		}
		return "Uploaded Successfully";
	}

	public CloudBlobContainer createContainerAndSetPermissions()
			throws StorageException, InvalidKeyException, URISyntaxException {

		CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
		CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
		CloudBlobContainer container = blobClient.getContainerReference("mycontainer");
		container.createIfNotExists();
		BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
		containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
		container.uploadPermissions(containerPermissions);
		return container;
	}

	public File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

}