package com.mcplusa.sumologic;

import com.amazonaws.services.kinesis.connectors.KinesisConnectorRecordProcessorFactory;

import com.mcplusa.kinesis.KinesisConnectorExecutor;
import com.mcplusa.kinesis.KinesisMessageModel;
import com.mcplusa.sumologic.SumologicMessageModelPipeline;

public class SumologicExecutor extends KinesisConnectorExecutor<KinesisMessageModel, String> {

    private static String configFile = "SumologicConnector.properties";

    /**
    * SumologicExecutor constructor.
    * @param configFile Properties for the connector
    */
    public SumologicExecutor(String configFile) {
        super(configFile);
    }

    @Override
    public KinesisConnectorRecordProcessorFactory<KinesisMessageModel, String>
            getKinesisConnectorRecordProcessorFactory() {
        return new KinesisConnectorRecordProcessorFactory<KinesisMessageModel, String>
                    (new SumologicMessageModelPipeline(),config);
    }

    /**
     * Main method starts and runs the SumologicExecutor.
     * 
     * @param args
     */
    public static void main(String[] args) {  
        KinesisConnectorExecutor<KinesisMessageModel, String> sumologicExecutor =
                new SumologicExecutor(configFile);
        sumologicExecutor.run();
    }
}
