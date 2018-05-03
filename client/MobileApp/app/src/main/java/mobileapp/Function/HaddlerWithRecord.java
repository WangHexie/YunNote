package mobileapp.Function;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class HaddlerWithRecord extends Handler {
    private int id ;

    public Handler HaddlerWithRecord(int id){
        this.id = id;
        return new Handler() {
            @Override
            public void publish(LogRecord logRecord) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };
    }

    @Override
    public void publish(LogRecord logRecord) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
