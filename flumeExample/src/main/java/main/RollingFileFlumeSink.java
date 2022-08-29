package main;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class RollingFileFlumeSink extends AbstractSink implements Configurable {
    private static final Logger logger = LoggerFactory.getLogger(RollingFileFlumeSink.class);
     
    //flume 설정값
    //Agent.sources.source_name.설정값
    //예시) Agent.sources.source_name.sink.id=1
    private static final String SINK_ID = "sink.id";
    private static final String SINK_FILENAME = "sink.filename";
    private static final String SINK_FILEPATTERN = "sink.filepattern";
 
    private RollingFileLogger rollingFileLogger;
 
    //설정값을 configure 객체를 통해 가져와서 적용
    //context.getString("설정값 이름", "미 설정 시 디폴트값");
    @Override
    public void configure(Context context) {
        String sinkId = context.getString(SINK_ID, "1");
        String sinkFileName = context.getString(SINK_FILENAME, "/tmp/flume_rollingfile_sink/rolling_file.log");
        String sinkFilePattern = context.getString(SINK_FILEPATTERN, "/tmp/flume_rollingfile_sink/${date:yyyy-MM}/rolling_file-%d{yyyy-MM-dd}.log.gz");
 
        logger.info("{} : {} ", SINK_ID, sinkId);
        logger.info("{} : {} ", SINK_FILENAME, sinkFileName);
        logger.info("{} : {} ", SINK_FILEPATTERN, sinkFilePattern);
 
        rollingFileLogger = new RollingFileLogger(sinkId, sinkFileName, sinkFilePattern);
    }

    //실제 sink가 작업을 처리하는 부분
    //본 소스 코드에서는 process() -> handleEvent()에서 작업을 진행
    //이벤트 데이터가 필요할 경우, event 객체의 getHeaders(), getBody() 메소드 사용
    @Override
    public Status process() throws EventDeliveryException {
        Status status = null;
        // Start transaction
        Channel ch = getChannel();
        Transaction txn = ch.getTransaction();
        txn.begin();
        try {
            // This try clause includes whatever Channel operations you want to
            // do
            Event event = ch.take();
            // Send the Event to the external repository.
            // storeSomeData(e);
             
            handleEvent(event.getBody());
 
            txn.commit();
            status = Status.READY;
        } catch (Throwable t) {
            txn.rollback();
            // Log exception, handle individual exceptions as needed
            status = Status.BACKOFF;
 
            // re-throw all Errors
            if (t instanceof Error) {
                throw (Error) t;
            }
        }
        // you must add this line of code in order to close the Transaction.
        txn.close();
        return status;
    }
 
    public void handleEvent(byte[] msg)  {
        try {
            //body부분을 utf-8로 변환, byte[] 그대로 필요할 경우(예- BlobDeserializer를 거쳐온 byte[] 바이너리 파일)에는 아래 라인 삭제
            String msgStr = new String(msg, "utf-8");
            rollingFileLogger.write(msgStr);
        } catch (Exception e) {
            logger.error("Cookie inject error : ", e.getMessage(), e);
        }
    }
}