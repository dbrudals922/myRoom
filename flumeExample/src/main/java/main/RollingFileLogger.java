package main;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
 
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
 
public class RollingFileLogger {
 
    private static final Logger logger = LogManager.getLogger(RollingFileLogger.class);
 
    private Logger fileWriter;
 
    private String fileName;
    private String filePattern;
    private String appenderName;
    private String loggerName;
 
    //생성자
    public RollingFileLogger(String loggerId, String fileName, String filePattern) {
        this.fileName = fileName;
        this.filePattern = filePattern;
 
        appenderName = loggerId + "_appender";
        loggerName = loggerId + "_logger";
 
        logger.info("fileName : " + fileName);
        logger.info("filePattern : " + filePattern);
        logger.info("appenderName : " + appenderName);
        logger.info("loggerName : " + loggerName);
 
        updateLoggerConfig();
        fileWriter = LogManager.getLogger(loggerName);
    }
 
    //로그 설정값 적용
    private void updateLoggerConfig() {
        final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        final Configuration config = ctx.getConfiguration();
 
        // add RollingFileAppender
        TriggeringPolicy policy = TimeBasedTriggeringPolicy.createPolicy("1", "true");
        Layout<?> layout = PatternLayout.createLayout("%m%n", null, config, null, Charset.forName("utf-8"), true, false, null, null);
        Appender appender = RollingFileAppender.createAppender(fileName, filePattern, "true", appenderName, "true", "", "true", policy, null, layout, null, "true", "false", null, config);
        appender.start();
        config.addAppender(appender);
 
        // add AsyncLogger
        AppenderRef ref = AppenderRef.createAppenderRef(appenderName, null, null);
        AppenderRef[] refs = new AppenderRef[] { ref };
 
        LoggerConfig loggerConfig = LoggerConfig.createLogger(false, Level.INFO, loggerName, "true", refs, null, config, null);
 
        loggerConfig.addAppender(appender, null, null);
        config.addLogger(loggerName, loggerConfig);
        ctx.updateLoggers();
    }
 
    //실제 로그 쓰는 부분(RollingFileFlumeSink에서 호출)
    public void write(String msg) {
        fileWriter.info("{}", msg);
    }
 
}
