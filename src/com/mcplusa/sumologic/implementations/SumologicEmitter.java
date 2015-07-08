package com.mcplusa.sumologic.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mcplusa.sumologic.SumologicSender;
import com.mcplusa.sumologic.KinesisConnectorForSumologicConfiguration;

import com.amazonaws.services.kinesis.connectors.KinesisConnectorConfiguration;
import com.amazonaws.services.kinesis.connectors.UnmodifiableBuffer;
import com.amazonaws.services.kinesis.connectors.interfaces.IEmitter;

/**
 * This class is used to store records from a stream to Sumologic log files. It requires the use of a
 * SumologicTransformer, which is able to transform records into a format that can be sent to
 * Sumologic.
 */
public class SumologicEmitter implements IEmitter<String> {
    private static final Log LOG = LogFactory.getLog(SumologicEmitter.class);

    private SumologicSender sender;
    private KinesisConnectorForSumologicConfiguration config;

    public SumologicEmitter(KinesisConnectorConfiguration configuration) {
        this.config = (KinesisConnectorForSumologicConfiguration) configuration;
        sender = new SumologicSender(this.config.SUMOLOGIC_URL);
    }

    @Override
    public List<String> emit(final UnmodifiableBuffer<String> buffer)
        throws IOException {

        List<String> records = buffer.getRecords();

        return sendBatchConcatenating(records);
    }
    
    public List<String> sendBatchConcatenating(List<String> records) {
      String message = "";
      for(String record: records) {
        message += record;
        message += "\n";
      }
      try {
        LOG.info("Sending batch of: "+records.size()+" records");
        sender.sendToSumologic(message);
      } catch (IOException e) {
        LOG.warn("Couldn't send record to Sumologic: "+e.getMessage());
        return records;
      }
      return new ArrayList<String>();
    }

    @Override
    public void fail(List<String> records) {
        for (String record : records) {
            LOG.error("Could not emit record: " + record);
        }
    }

    /**
     * This helper method is used to dedupe a list of items. 
     * @param items a list of items
     * @return the subset of unique items
     */
    public Set<String> uniqueItems(List<String> items) {
        return new HashSet<String>(items);
    }

    @Override
    public void shutdown() {
    }
}