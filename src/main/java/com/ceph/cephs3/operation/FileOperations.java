package com.ceph.cephs3.operation;

import java.io.File;

import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ceph.cephs3.connection.ConnectionUtility;

/**
 * @author Pranav
 *
 */
@Component
public class FileOperations {

	/**
	 * This method lists all the buckets present in ceph
	 */
	public void listAllBuckets() {
		AmazonS3 conn = ConnectionUtility.getConnection();
		conn.listBuckets().forEach(bucket -> {
			System.out.println(bucket.getName() + " " + bucket.getCreationDate());
		});
	}

	/**
	 * This method uploads a file to a specified bucket with a specified key
	 * 
	 * @param bucketName
	 *            The bucket Name in which file has to be uploaded
	 * @param key
	 *            The key of the file to be uploaded
	 * @param file
	 *            File to be uploaded
	 */
	public void uploadFile(String bucketName, String key, File file) {
		AmazonS3 conn = ConnectionUtility.getConnection();
		if (!conn.doesBucketExistV2(bucketName)) {
			conn.createBucket(bucketName);
		}
		conn.putObject(new PutObjectRequest(bucketName, key, file));
	}

	/**
	 * This method downloads a file with a specified bucket to a valid location
	 * 
	 * @param bucketName
	 *            The bucket Name in which file has to be uploaded
	 * @param key
	 *            The key of the file to be uploaded
	 * @param destinationPath
	 *            The destination path to download the file
	 */
	public void downloadFile(String bucketName, String key, File destinationPath) {
		AmazonS3 conn = ConnectionUtility.getConnection();
		conn.getObject(new GetObjectRequest(bucketName, key), destinationPath);
	}
}
