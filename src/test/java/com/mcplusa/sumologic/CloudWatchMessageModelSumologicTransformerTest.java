package com.mcplusa.sumologic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Ignore;

import com.amazonaws.services.kinesis.model.Record;
import com.mcplusa.sumologic.CloudWatchMessageModelSumologicTransformer;
import com.mcplusa.sumologic.SimpleKinesisMessageModel;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.CharBuffer;
import java.nio.ByteBuffer;


public class CloudWatchMessageModelSumologicTransformerTest {
  public static Charset charset = Charset.forName("UTF-8");
  public static CharsetEncoder encoder = charset.newEncoder();
  
  @Test
  public void theTransformerShouldFailGracefullyWhenUnableToTransform () {
    CloudWatchMessageModelSumologicTransformer transfomer = new CloudWatchMessageModelSumologicTransformer();
    
    String randomData = "Some random string without GZIP compression";
    ByteBuffer bufferedData = null;
    try {
      bufferedData = encoder.encode(CharBuffer.wrap(randomData));
    } catch (Exception e) {
      Assert.fail("Getting error: "+e.getMessage());
    }
    
    Record mockedRecord = new Record();
    mockedRecord.setData(bufferedData);
    
    SimpleKinesisMessageModel messageModel = null;
    try {
      messageModel = transfomer.toClass(mockedRecord);
    } catch (IOException e) {
      Assert.fail("Getting error while transforming: "+e.getMessage());
    }
    
    Assert.assertNull(messageModel);
  }
}