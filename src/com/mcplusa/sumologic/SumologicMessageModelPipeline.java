package com.mcplusa.sumologic;

import com.mcplusa.sumologic.KinesisMessageModel;
import com.mcplusa.sumologic.KinesisMessageModelSumologicTransformer;
import com.mcplusa.sumologic.implementations.SumologicEmitter;

import com.amazonaws.services.kinesis.connectors.interfaces.IKinesisConnectorPipeline;
import com.amazonaws.services.kinesis.connectors.KinesisConnectorConfiguration;
import com.amazonaws.services.kinesis.connectors.impl.BasicMemoryBuffer;
import com.amazonaws.services.kinesis.connectors.impl.AllPassFilter;
import com.amazonaws.services.kinesis.connectors.interfaces.IEmitter;
import com.amazonaws.services.kinesis.connectors.interfaces.IBuffer;
import com.amazonaws.services.kinesis.connectors.interfaces.ITransformer;
import com.amazonaws.services.kinesis.connectors.interfaces.IFilter;


/**
 * The Pipeline used by the Sumologic. Processes KinesisMessageModel records in JSON String
 * format. Uses:
 * <ul>
 * <li>{@link SumologicEmitter}</li>
 * <li>{@link BasicMemoryBuffer}</li>
 * <li>{@link KinesisMessageModelSumologicTransformer}</li>
 * <li>{@link AllPassFilter}</li>
 * </ul>
 */
public class SumologicMessageModelPipeline implements
        IKinesisConnectorPipeline<KinesisMessageModel, String> {

    @Override
    public IEmitter<String> getEmitter(KinesisConnectorConfiguration configuration) {
        return new SumologicEmitter(configuration);
    }

    @Override
    public IBuffer<KinesisMessageModel> getBuffer(KinesisConnectorConfiguration configuration) {
        return new BasicMemoryBuffer<KinesisMessageModel>(configuration);
    }

    @Override
    public ITransformer<KinesisMessageModel, String>
            getTransformer(KinesisConnectorConfiguration configuration) {
        return new KinesisMessageModelSumologicTransformer();
    }

    @Override
    public IFilter<KinesisMessageModel> getFilter(KinesisConnectorConfiguration configuration) {
        return new AllPassFilter<KinesisMessageModel>();
    }

}
