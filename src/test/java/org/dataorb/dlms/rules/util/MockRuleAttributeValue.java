package co.dataorb.java.rules.util;

import co.dataorb.java.rules.models.RuleAttributeValue;

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
