package com.mcplusa.sumologic;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

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

  private static Logger logger = Logger.getLogger(KinesisMessageModelSumologicTransformer.class);
  
  /**
   * Creates a new KinesisMessageModelSumologicTransformer.
   */
  public KinesisMessageModelSumologicTransformer() {
      super(KinesisMessageModel.class);
  }
  
  @Override
  public String fromClass(KinesisMessageModel message) {
      String item = null;
      /*putIntegerIfNonempty(item, "user_id", message.userid);
      putStringIfNonempty(item, "username", message.username);
      putStringIfNonempty(item, "firstname", message.firstname);
      putStringIfNonempty(item, "lastname", message.lastname);
      putStringIfNonempty(item, "city", message.city);
      putStringIfNonempty(item, "state", message.state);
      putStringIfNonempty(item, "email", message.email);
      putStringIfNonempty(item, "phone", message.phone);
      putBoolIfNonempty(item, "likesports", message.likesports);
      putBoolIfNonempty(item, "liketheatre", message.liketheatre);
      putBoolIfNonempty(item, "likeconcerts", message.likeconcerts);
      putBoolIfNonempty(item, "likejazz", message.likejazz);
      putBoolIfNonempty(item, "likeclassical", message.likeclassical);
      putBoolIfNonempty(item, "likeopera", message.likeopera);
      putBoolIfNonempty(item, "likerock", message.likerock);
      putBoolIfNonempty(item, "likevegas", message.likevegas);
      putBoolIfNonempty(item, "likebroadway", message.likebroadway);
      putBoolIfNonempty(item, "likemusicals", message.likemusicals);*/
      
      
      return item;
  }
}