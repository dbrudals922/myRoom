package example;

import java.util.Map;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.conf.ConfigurationException;
import org.apache.flume.sink.AbstractSink;
import org.eclipse.jetty.util.thread.ExecutionStrategy.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaEsSink extends AbstractSink implements Configurable {

	private static final Logger log = LoggerFactory.getLogger(KafkaEsSink.class);
	private ObjectMapper mapper = new ObjectMapper();
	private Producer<String, String> producer;    
	private String topics;
	
	@Override
	public void configure(Context context) {
		topics = context.getString(CXNlpKafkaSinkConstants.TOPIC);
		if (topics == null) {
			throw new ConfigurationException("Kafka topic must be specified.");
		}
		producer = new Producer<String, String>(new ProducerConfig(AgentUtil.getConfigProperties(context)));
	}

	@Override
	public Status process() throws EventDeliveryException {        
		// Start transaction
		Channel channel = getChannel();
		Transaction transaction = channel.getTransaction();
		transaction.begin();
		try {
			// Get event from channel
			Event event = channel.take();
			if (event == null) {
				transaction.commit();
				return Status.READY;

			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> map = mapper.readValue(event.getBody(), Map.class);
			
			//	key
			String cxId = event.getHeaders().get(CXConstants.CX_KEY_ES_TYPE_FIELD);
			
			producer.send(new KeyedMessage<String, String>(topics, cxId, mapper.writeValueAsString(map))); 
			transaction.commit();
			return Status.READY;
		} catch (Throwable e) { 
			try {
				transaction.rollback();
				return Status.BACKOFF;
			} catch (Exception e2) {
				log.error("Rollback Exception:{}", e2);
			}		
			log.error("KafkaSink Exception:{}", e);
			return Status.BACKOFF;
		} finally {
			transaction.close();
		}
	}

	@Override
	public synchronized void start() {
		super.start();
	}

	@Override
	public synchronized void stop() {
		producer.close();
		super.stop();
	}    

}