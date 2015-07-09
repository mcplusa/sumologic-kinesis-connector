package com.mcplusa.sumologic;

import java.io.IOException;

import com.amazonaws.services.kinesis.connectors.BasicJsonTransformer;
import com.amazonaws.services.kinesis.model.Record;
import com.mcplusa.sumologic.KinesisMessageModel;
import com.mcplusa.sumologic.implementations.SumologicEmitter;
import com.mcplusa.sumologic.implementations.SumologicTransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;


/**
 * A custom transfomer for {@link KinesisMessageModel} records in JSON format. The output is in a format
 * usable for insertions to Sumologic.
 */
public class KinesisMessageModelSumologicTransformer implements
        SumologicTransformer<KinesisMessageModel> {

  private static final Log LOG = LogFactory.getLog(KinesisMessageModelSumologicTransformer.class);
  
    /**
     * Creates a new KinesisMessageModelSumologicTransformer.
     */
    public KinesisMessageModelSumologicTransformer() {
        super();
    }

    @Override
    public String fromClass(KinesisMessageModel message) {
        return message.toString();
    }

    @Override
    public KinesisMessageModel toClass(Record record) throws IOException {
      byte[] decodedRecord = record.getData().array();
      String stringifiedRecord = decompressGzip(decodedRecord);

      return new KinesisMessageModel(stringifiedRecord);
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

}
