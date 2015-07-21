package com.mcplusa.sumologic;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

public class SumologicSender {
  private static final Log LOG = LogFactory.getLog(SumologicSender.class);

  private String url = null;  
  private HttpClient httpClient = null;

  private int connectionTimeout = 1000;
  private int socketTimeout = 60000;
  private static final int RETRIES = 3;
  private static final int SLEEP_TIME = 1000;
  
	    
	public SumologicSender(String url) {
		  this.url = url;
		  
	    HttpParams params = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(params, connectionTimeout);
	    HttpConnectionParams.setSoTimeout(params, socketTimeout);
	    httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(), params);
	}

	public boolean sendToSumologic(String data) throws IOException{
	  int retries = RETRIES;
	  int sleep_time = SLEEP_TIME;
	  int statusCode;
	  
	  do {
  	  HttpPost post = null;
      post = new HttpPost(url);
      post.setEntity(new StringEntity(data, HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8));
      HttpResponse response = httpClient.execute(post);
      statusCode = response.getStatusLine().getStatusCode();
      
      //need to consume the body if you want to re-use the connection.
      EntityUtils.consume(response.getEntity());
      try { 
        post.abort(); 
      } catch (Exception ignore) {}
      if (statusCode == 429) {
        LOG.warn("Got TOO MANY REQUESTS from Sumologic");
        retries--;
        try {
          Thread.sleep(sleep_time);
        } catch (InterruptedException ignore) {}
      }
	  } while (statusCode == 429 && retries > 0);
    
    // Check if the request was successful;
    if (statusCode != 200) {
      LOG.warn(String.format("Received HTTP error from Sumo Service: %d", statusCode));
      return false;
    }
    else{ 
      return true;
    } 
	}
}