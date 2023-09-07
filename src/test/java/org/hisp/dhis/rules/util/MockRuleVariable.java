package org.dataorb.dlms.rules.util;

import org.dataorb.dlms.rules.Option;
import org.dataorb.dlms.rules.RuleVariableValue;
import org.dataorb.dlms.rules.RuleVariableValueMapBuilder;
import org.dataorb.dlms.rules.models.RuleAttributeValue;
import org.dataorb.dlms.rules.models.RuleDataValue;
import org.dataorb.dlms.rules.models.RuleVariable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class MockRuleVariable extends RuleVariable {
    @Nonnull
    @Override
    public String name() {
        return null;
    }

    @Nonnull
    @Override
    public boolean useCodeForOptionSet() {
        return false;
    }

    @Nonnull
    @Override
    public List<Option> options() {
        return null;
    }

    @Override
    public Map<String, RuleVariableValue> createValues(RuleVariableValueMapBuilder builder, Map<String, List<RuleDataValue>> allEventValues, Map<String, RuleAttributeValue> currentEnrollmentValues, Map<String, RuleDataValue> currentEventValues) {
        return Map.of();
    }
}
