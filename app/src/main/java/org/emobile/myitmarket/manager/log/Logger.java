package org.emobile.myitmarket.manager.log;

/**
 * Created by Risto Muchev on 02-May-16.
 */
public class Logger {

    private static Logger instance = null;

    private ILogger log = null;

    private Logger(){}

    public void init(ILogger log){
        this.log = log;
    }

    public static Logger getInstance(){
        if(instance == null){
            instance = new Logger();
        }
        return instance;
    }

    private ILogger getLog() {
        return log;
    }

    public static void log(LogLevel logLevel, String message){
        getInstance().getLog().log(logLevel, "Oglasnik24", message);
    }

    public static void log(LogLevel logLevel, String tag, String message){
        getInstance().getLog().log(logLevel, tag, message);
    }
}
