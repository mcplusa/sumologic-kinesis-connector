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
    public static final String PROP_SUMOLOGIC_USE_LOG4J = "useLog4j";
    public final String SUMOLOGIC_URL;
    public final boolean SUMOLOGIC_USE_LOG4J;
    
    public final boolean DEFAULT_SUMOLOGIC_USE_LOG4J = false;

    /**
     * Configure the connector application with any set of properties that are unique to the application. Any
     * unspecified property will be set to a default value.
     */
    public KinesisConnectorForSumologicConfiguration(Properties properties, AWSCredentialsProvider credentialsProvider) {
        super(properties, credentialsProvider);
        SUMOLOGIC_URL = properties.getProperty(PROP_SUMOLOGIC_URL, null);
        SUMOLOGIC_USE_LOG4J = getBooleanProperty(PROP_SUMOLOGIC_USE_LOG4J, 
            DEFAULT_SUMOLOGIC_USE_LOG4J, properties);
    }
    
    private boolean getBooleanProperty(String property, boolean defaultValue, Properties properties) {
      String propertyValue = properties.getProperty(property, Boolean.toString(defaultValue));
      return Boolean.parseBoolean(propertyValue);
  }
}
