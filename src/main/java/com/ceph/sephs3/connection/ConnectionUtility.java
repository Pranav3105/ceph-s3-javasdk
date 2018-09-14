package com.ceph.sephs3.connection;

import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class ConnectionUtility {
	
	private static AmazonS3 connection = null;
	
	@Value("{access.key}")
	private static String accessKey;
	
	@Value("{secret.key}")
	private static String secretKey;
	
	@Value("{endpoint}")
	private static String endpoint;
	
	private ConnectionUtility() {}
	
	public static AmazonS3 getConnection() {
		if(connection == null) {
			AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
			connection = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
					.withEndpointConfiguration(new EndpointConfiguration(endpoint, Regions.AP_SOUTH_1.toString())).build();
		}
		return connection;
	}
	

}
