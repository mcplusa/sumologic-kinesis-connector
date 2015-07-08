package com.mcplusa.sumologic;

import java.util.Properties;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.kinesis.connectors.KinesisConnectorConfiguration;


/**
 * This class contains constants used to configure AWS Services in Amazon Kinesis Connectors. The user
 * should use System properties to set their proper configuration. An instance of
 * KinesisConnectorConfiguration is created with System properties and an AWSCredentialsProvider.
 */
public class KinesisConnectorForSumologicConfiguration extends KinesisConnectorConfiguration {
    // Properties added for Sumologic
    public static final String PROP_SUMOLOGIC_URL = "sumologicUrl";
    public final String SUMOLOGIC_URL;

    /**
     * Configure the connector application with any set of properties that are unique to the application. Any
     * unspecified property will be set to a default value.
     */
    public KinesisConnectorForSumologicConfiguration(Properties properties, AWSCredentialsProvider credentialsProvider) {
        super(properties, credentialsProvider);
        
        SUMOLOGIC_URL = properties.getProperty(PROP_SUMOLOGIC_URL, null);
    }
}
