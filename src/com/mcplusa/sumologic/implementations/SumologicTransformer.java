package com.mcplusa.sumologic.implementations;

import com.amazonaws.services.kinesis.connectors.interfaces.ITransformer;

/**
 * This interface defines an ITransformer that has an output type of Map of attribute name (String)
 * to AttributeValue so that the item can be put into Sumologic.
 * 
 * @param <T>
 */
public interface SumologicTransformer<T> extends ITransformer<T, String> {

}
