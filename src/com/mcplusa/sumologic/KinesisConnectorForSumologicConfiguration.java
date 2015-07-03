/*
 * Copyright 2013-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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
