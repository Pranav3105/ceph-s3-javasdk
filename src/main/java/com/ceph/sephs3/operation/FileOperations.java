package com.ceph.sephs3.operation;

import java.io.File;

import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ceph.sephs3.connection.ConnectionUtility;

@Component
public class FileOperations {

	public void uploadFile(String bucketName, String key, File file){
		AmazonS3 conn = ConnectionUtility.getConnection();
		if(!conn.doesBucketExistV2(bucketName)){
			conn.createBucket(bucketName);
		}
		conn.putObject(new PutObjectRequest(bucketName, key, file));
	}
	
	public void downloadFile(String bucketName, String key, File destinationPath){
		AmazonS3 conn = ConnectionUtility.getConnection();
		conn.getObject(new GetObjectRequest(bucketName, key), destinationPath);
	}
	
	public void listAllBuckets(){
		AmazonS3 conn = ConnectionUtility.getConnection();
		conn.listBuckets().forEach(bucket->{
			System.out.println(bucket.getName()+" "+bucket.getCreationDate());
		});
	}
}
