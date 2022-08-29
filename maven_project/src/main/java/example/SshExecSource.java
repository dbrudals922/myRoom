package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SshExecSource extends AbstractSource implements Configurable, PollableSource 
{
	private static final Logger logger = LoggerFactory.getLogger(SshExecSource.class);
	private transient ObjectMapper mapper;
	
	private String domain;
	private String hostName, userName, userPass, timeOut;
	private String command;

	private SshClientExec sshClient ;
	public static LinkedList<String> execTailQueue = new LinkedList<String>();
	
	@Override
	public void configure(Context context) {
		Properties prop = AgentUtil.getConfigProperties(context);
		domain = prop.getProperty(CXAgentConstants.DOMAIN);
		hostName = prop.getProperty(CXSshSourceConstants.HOST_NAME);
		userName = prop.getProperty(CXSshSourceConstants.USER_NAME);
		userPass = prop.getProperty(CXSshSourceConstants.USER_PASS);
		timeOut = prop.getProperty(CXSshSourceConstants.TIMEOUT);
		command = prop.getProperty(CXSshSourceConstants.COMMAND);
		sshClient = new SshClientExec(hostName, userName, userPass, timeOut);
	}

	@Override
	public void start() { 
		
	}

	@Override
	public void stop () {
	}

	@Override
	public Status process() throws EventDeliveryException {

		System.out.println("incomming");
		sshClient.putStreamInTail(command);
		System.out.println("outcomming");
		return Status.READY;
	}
	
	class SshClientExec extends A_SshClient{
		private final static int SSH_PORT = 22;

		public SshClientExec(String hostName, String userName, String userPass, String timeOut)
		{
			super(hostName, userName, userPass, SSH_PORT, timeOut);
		}

		public ArrayList<String> putStreamInTail(String command) 
		{
			ArrayList<String> ret = new ArrayList<String>();
			ChannelExec channel = null;
			try {
				bindSession();
				channel = (ChannelExec) session.openChannel("exec");
				channel.setCommand(command);
				channel.setInputStream(null);
				channel.setErrStream(System.err);
				
				channel.connect();
				
				int line_counter = 1;
				
				Map<String, String> headers = new HashMap<String, String>();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(channel.getInputStream()));
				String record;
				while ((record = reader.readLine()) != null)
				{
//					System.err.println(line);
					if(record == null || record.equals("")) continue;
					logger.debug("r: " + record);
					
					//	headers setting
					headers.put("line_number", Integer.toString(line_counter++));
					headers.put(CXConstants.CX_KEY_ES_TYPE_FIELD, domain);
					
					//////////////////////////////////////////////////////
					//	cx elasticsearch collect formatting
					try {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put(CXConstants.CX_KEY_ES_MAPPINGID, line_counter++);
						map.put(CXConstants.CX_KEY_ES_INDEX_FIELD, domain + CXConstants.CX_CMD_DOT + CXBasicUtil.currentTimeDataFormat(DateConstants.CX_KEY_DATE_FORMAT_yyyyMMdd));
						map.put(CXConstants.CX_KEY_ES_TYPE_FIELD, domain);
						map.put(CXConstants.CX_KEY_ES_REG_DATE, CXBasicUtil.currentTimeDataFormat(DateConstants.CX_KEY_DATE_FORMAT_yyyyMMdd));
						map.put(CXConstants.CX_KEY_ES_CRAWL_DATE, CXBasicUtil.currentTimeDataFormat(DateConstants.CX_KEY_DATE_FORMAT_yyyyMMddHHmmss));
						map.put(CXConstants.CX_KEY_UNDEFINE_DATA, record);
						record = mapper.writeValueAsString(map);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						logger.debug(e1.getMessage());
					}
					//////////////////////////////////////////////////////
					
					Event e = EventBuilder.withBody(record, CXConstants.CX_ENCODING_UTF8, headers);

					// Store the Event into this Source's associated Channel(s)
					getChannelProcessor().processEvent(e);
				}
			} catch( Exception e ) {
				logger.error( e.toString() );
			} finally{
				closeQuietly(session, channel);
			}
			return ret;
		}
	}

	@Override
	public long getBackOffSleepIncrement() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getMaxBackOffSleepInterval() {
		// TODO Auto-generated method stub
		return 0;
	}
}
