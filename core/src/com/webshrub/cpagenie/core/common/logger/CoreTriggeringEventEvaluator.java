package com.webshrub.cpagenie.core.common.logger;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.TriggeringEventEvaluator;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Nov 8, 2010
 * Time: 3:24:36 PM
 */
public class CoreTriggeringEventEvaluator implements TriggeringEventEvaluator {

    public boolean isTriggeringEvent(LoggingEvent event) {
        return event.getLevel().isGreaterOrEqual(Level.ERROR);
    }
}
