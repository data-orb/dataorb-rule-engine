package org.dataorb.dlms.rules.util;

import org.dataorb.dlms.rules.models.RuleAttributeValue;

import javax.annotation.Nonnull;

public class MockRuleAttributeValue extends RuleAttributeValue {
    @Nonnull
    @Override
    public String trackedEntityAttribute() {
        return null;
    }

    @Nonnull
    @Override
    public String value() {
        return null;
    }
}
