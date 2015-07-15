package com.mcplusa.sumologic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

  private static final Log LOG = LogFactory.getLog(SumologicMessageModelPipeline.class);
  
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
      
      // Load specified class
      String argClass = ((KinesisConnectorForSumologicConfiguration)configuration).TRANSFORMER_CLASS;
      String className = "com.mcplusa.sumologic."+argClass;
      ClassLoader classLoader = SumologicMessageModelPipeline.class.getClassLoader();
      Class ModelClass = null;
      try {
        ModelClass = classLoader.loadClass(className);
        ITransformer<KinesisMessageModel, String> ITransformerObject = (ITransformer<KinesisMessageModel, String>)ModelClass.newInstance();
        LOG.info("Using transformer: "+ITransformerObject.getClass().getName());
        return ITransformerObject;
      } catch (ClassNotFoundException e) {
        LOG.error("Class not found: "+className+" error: "+e.getMessage());
      } catch (InstantiationException e) {
        LOG.error("Class not found: "+className+" error: "+e.getMessage());
      } catch (IllegalAccessException e) {
        LOG.error("Class not found: "+className+" error: "+e.getMessage());
      }
      
      return new DefaultKinesisMessageModelSumologicTransformer();
    }

    @Override
    public IFilter<KinesisMessageModel> getFilter(KinesisConnectorConfiguration configuration) {
        return new AllPassFilter<KinesisMessageModel>();
    }

}
