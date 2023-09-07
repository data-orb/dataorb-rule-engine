package org.dataorb.dlms.rules.models;

import org.dataorb.dlms.lib.expression.spi.ParseException;

public class RuleEngineValidationException extends ParseException {
    public RuleEngineValidationException(String s) {
        super(s);
    }
}