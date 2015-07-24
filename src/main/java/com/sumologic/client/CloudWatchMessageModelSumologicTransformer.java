package com.sumologic.client;

import java.io.IOException;

import com.amazonaws.services.kinesis.model.Record;
import com.sumologic.client.SimpleKinesisMessageModel;
import com.sumologic.client.implementations.SumologicTransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.zip.GZIPInputStream;

/**
 * A custom transfomer for {@link SimpleKinesisMessageModel} records in JSON format. The output is in a format
 * usable for insertions to Sumologic.
 */
public class CloudWatchMessageModelSumologicTransformer implements
        SumologicTransformer<SimpleKinesisMessageModel> {

  private static final Log LOG = LogFactory.getLog(CloudWatchMessageModelSumologicTransformer.class);
  
    /**
     * Creates a new KinesisMessageModelSumologicTransformer.
     */
    public CloudWatchMessageModelSumologicTransformer() {
        super();
    }

    @Override
    public String fromClass(SimpleKinesisMessageModel message) {
        return message.toString();
    }

    @Override
    public SimpleKinesisMessageModel toClass(Record record) throws IOException {
      byte[] decodedRecord = record.getData().array();
      String stringifiedRecord = decompressGzip(decodedRecord);
      
      if (stringifiedRecord == null) {
        LOG.error("Unable to decompress the record: "+new String(record.getData().array()));
        LOG.error("Not attempting to transform into a Message Model");
        return null;
      }

      return new SimpleKinesisMessageModel(stringifiedRecord);
    }
    
    public static String decompressGzip(byte[] compressedData) {
      try {
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(compressedData));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        
        String outStr = "";
        String line;
        while ((line=bf.readLine())!=null) {
          outStr += line;
        }
        return outStr;
      } catch (IOException exc) {
        LOG.warn("Exception during decompression of data: " + exc.getMessage());
        return null;
      }
    }
    
    public static String byteBufferToString(ByteBuffer buffer){
      String data = "";
      CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
      try{
        int old_position = buffer.position();
        data = decoder.decode(buffer).toString();
        buffer.position(old_position);  
      }catch (Exception e){
        e.printStackTrace();
        return "";
      }
      return data;
    }

}
