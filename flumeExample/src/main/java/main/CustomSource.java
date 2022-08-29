package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.flume.ChannelException;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jjmartinez jjmartinez@keedio.com - KEEDIO
 *
 */
public class CustomSource extends AbstractSource implements Configurable, PollableSource {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomSource.class);
    public static final String INPUT_FILE = "tettt.txt";

    private String inputFile;

    /**
     *
     * @param context
     */
    @Override
    public void configure(Context context) {
        inputFile = context.getString(INPUT_FILE);
    }

    /**
     * @return void
     */
    @Override
    public void start() {
        logger.warn("Start custom flume source");
        super.start();
    }

    /**
     * @return void
     */
    @Override
    public void stop () {
        // Disconnect from external client and do any additional cleanup
        // (e.g. releasing resources or nulling-out field values) ..
        super.stop();
    }

    /**
     * @return Status , process source configured from context
     * @throws org.apache.flume.EventDeliveryException
     */
    @Override
    public Status process() throws EventDeliveryException {
        Status status = null;

        try {
            // This try clause includes whatever Channel/Event operations you want to do
            // Receive new data
            Event event = new SimpleEvent();
            Map<String, String> headers = new HashMap<>();
            headers.put("type", "data");

            Path file = Paths.get(inputFile);
            try (InputStream in = Files.newInputStream(file);
                 BufferedReader reader =
                         new BufferedReader(new InputStreamReader(in))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    processLine(line.getBytes());
                }
            } catch (IOException e) {
                logger.error("ERROR: ", e);
            }

            // Store the Event into this Source's associated Channel(s)
            getChannelProcessor().processEvent(event);

            status = Status.READY;
        } catch (Throwable t) {
             // Log exception, handle individual exceptions as needed
            logger.error("ERROR: ",t);
            status = Status.BACKOFF;
        }
        return status;
    }

    /**
     * @void process file lines.
     * @param line byte[]
     */
    public void processLine(byte[] line) {
        byte[] message = line;
        Event event = new SimpleEvent();
        Map<String, String> headers = new HashMap<>();
        headers.put("timestamp", String.valueOf(System.currentTimeMillis()));
        event.setBody(message);
        event.setHeaders(headers);
        try {
            getChannelProcessor().processEvent(event);
        } catch (ChannelException e) {
            logger.error("ERROR: ",e);
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