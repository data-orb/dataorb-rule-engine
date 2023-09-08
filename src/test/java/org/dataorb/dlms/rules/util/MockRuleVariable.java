package co.dataorb.java.rules.util;

import co.dataorb.java.rules.Option;
import co.dataorb.java.rules.RuleVariableValue;
import co.dataorb.java.rules.RuleVariableValueMapBuilder;
import co.dataorb.java.rules.models.RuleAttributeValue;
import co.dataorb.java.rules.models.RuleDataValue;
import co.dataorb.java.rules.models.RuleVariable;

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
