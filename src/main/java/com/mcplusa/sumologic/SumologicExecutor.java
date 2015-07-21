package com.mcplusa.sumologic;

import com.amazonaws.services.kinesis.connectors.KinesisConnectorRecordProcessorFactory;

import com.mcplusa.kinesis.KinesisConnectorExecutor;
import com.mcplusa.sumologic.SimpleKinesisMessageModel;
import com.mcplusa.sumologic.SumologicMessageModelPipeline;

public class SumologicExecutor extends KinesisConnectorExecutor<SimpleKinesisMessageModel, String> {

    private static String configFile = "SumologicConnector.properties";

    /**
    * SumologicExecutor constructor.
    * @param configFile Properties for the connector
    */
    public SumologicExecutor(String configFile) {
        super(configFile);
    }

    @Override
    public KinesisConnectorRecordProcessorFactory<SimpleKinesisMessageModel, String>
            getKinesisConnectorRecordProcessorFactory() {
        return new KinesisConnectorRecordProcessorFactory<SimpleKinesisMessageModel, String>
                    (new SumologicMessageModelPipeline(),config);
    }

    /**
     * Main method starts and runs the SumologicExecutor.
     * @param args
     */
    public static void main(String[] args) {  
        KinesisConnectorExecutor<SimpleKinesisMessageModel, String> sumologicExecutor =
                new SumologicExecutor(configFile);
        sumologicExecutor.run();
    }
}
