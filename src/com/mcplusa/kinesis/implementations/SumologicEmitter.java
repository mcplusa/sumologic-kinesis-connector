/**
 * Copyright 2013-2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.mcplusa.kinesis.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.kinesis.connectors.KinesisConnectorConfiguration;
import com.amazonaws.services.kinesis.connectors.UnmodifiableBuffer;
import com.amazonaws.services.kinesis.connectors.interfaces.IEmitter;

/**
 * This class is used to store records from a stream to Sumologic log files. It requires the use of a
 * SumologicTransformer, which is able to transform records into a format that can be sent to
 * Sumologic. Log4j is used to perform batch requests on the contents of a buffer when
 * emitting. This class requires the configuration of log4j using Sumologic's Log4j client.
 */
public class SumologicEmitter implements IEmitter<String> {
    private static final Log LOG = LogFactory.getLog(SumologicEmitter.class);

    public SumologicEmitter(KinesisConnectorConfiguration configuration) {
    }

    @Override
    public List<String> emit(final UnmodifiableBuffer<String> buffer)
        throws IOException {

        List<String> records = buffer.getRecords();
        for (String record : records) {
            System.out.println("Got record \n"+record);
        }

        // TODO Send them to Sumologic
        
        return new ArrayList<String>(); // TODO return unprocessed records
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
        //sumologicClient.shutdown();
    }
}