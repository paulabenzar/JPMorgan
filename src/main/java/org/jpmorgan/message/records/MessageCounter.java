package org.jpmorgan.message.records;

/**
 * Created by paulabenzar on 21.01.2018.
 */
public enum MessageCounter {

    INSTANCE;

    //TODO: RESET BEFORE OVERFLOW  WITHOUT LOOSING MESSAGES
    private Long nrOfMessages = 0L;

    public Long getNrOfMessages() {
        return nrOfMessages;
    }

    public void increment() {
        this.nrOfMessages++;
    }

    public void reset() {
        nrOfMessages = 0L;
    }
}
