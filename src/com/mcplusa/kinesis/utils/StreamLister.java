package com.mcplusa.kinesis.utils;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.Record;
import com.amazonaws.services.kinesis.model.ShardIteratorType;

public class StreamLister {
  public static void main(String[] args) {
    AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider("SumologicConnector.properties");
    AWSCredentials credentials = credentialsProvider.getCredentials();

    AmazonKinesisClient kinesis = new AmazonKinesisClient(credentials);
    System.out.println("Streams available: "+Arrays.toString(KinesisUtils.listAllStreams(kinesis).toArray()));
    
    GetRecordsRequest recordRequest =new GetRecordsRequest();
    recordRequest.setShardIterator(kinesis.getShardIterator("sumologicStream", "0", ShardIteratorType.LATEST.toString()).getShardIterator());
    GetRecordsResult  results = kinesis.getRecords(recordRequest);
    
    for (Record record: results.getRecords()) {
      System.out.println("Record: "+new String(record.getData().asCharBuffer().array()));
    }
  }
}