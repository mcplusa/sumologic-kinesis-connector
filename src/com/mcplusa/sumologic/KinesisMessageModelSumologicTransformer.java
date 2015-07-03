package com.mcplusa.sumologic;

import com.amazonaws.services.kinesis.connectors.BasicJsonTransformer;
import com.mcplusa.kinesis.KinesisMessageModel;
import com.mcplusa.kinesis.implementations.SumologicTransformer;


/**
 * A custom transfomer for {@link KinesisMessageModel} records in JSON format. The output is in a format
 * usable for insertions to Sumologic.
 */
public class KinesisMessageModelSumologicTransformer extends
        BasicJsonTransformer<KinesisMessageModel, String> implements
        SumologicTransformer<KinesisMessageModel> {

    /**
     * Creates a new KinesisMessageModelDynamoDBTransformer.
     */
    public KinesisMessageModelSumologicTransformer() {
        super(KinesisMessageModel.class);
    }

    @Override
    public String fromClass(KinesisMessageModel message) {
        return message.toString();
    }

}
