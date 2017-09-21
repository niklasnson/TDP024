package se.liu.ida.tdp024.account.util.logger;

public interface AccountLogger {
    
    enum LoggerLevel {
        DEBUG,
        INFO,
        NOTIFY,
        WARNING,
        ERROR,
        CRITICAL,
        ALERT,
        EMERGENCY
    }
    
    public void log(Throwable throwable);
    
    public void log(LoggerLevel loggerLevel, String shortMessage, String longMessage);
    
}
