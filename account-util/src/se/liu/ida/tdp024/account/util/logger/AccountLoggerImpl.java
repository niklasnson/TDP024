package se.liu.ida.tdp024.account.util.logger;

public class AccountLoggerImpl implements AccountLogger {

    @Override
    public void log(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void log(LoggerLevel loggerLevel, String shortMessage, String longMessage) {
        if (loggerLevel == LoggerLevel.CRITICAL || loggerLevel == loggerLevel.ERROR) {
            System.err.println(shortMessage);
            System.err.println(longMessage);
        } else {
            System.out.println(shortMessage);
            System.out.println(longMessage);
        }
    }
}
