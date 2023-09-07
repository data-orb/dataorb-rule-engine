package org.dataorb.dlms.rules.models;

import com.google.auto.value.AutoValue;
import org.dataorb.dlms.rules.Option;
import org.dataorb.dlms.rules.RuleVariableValue;
import org.dataorb.dlms.rules.RuleVariableValueMapBuilder;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zubair Asghar.
 */

@AutoValue
public abstract class RuleVariableCalculatedValue
    extends RuleVariable
{
    @Nonnull
    public static RuleVariableCalculatedValue create(@Nonnull String name,
                                                     @Nonnull String variable, @Nonnull RuleValueType variableType, boolean useCodeForOptionSet, List<Option> options)
    {
        return new AutoValue_RuleVariableCalculatedValue( name, useCodeForOptionSet, options, variable, variableType );
    }

    @Nonnull
    public abstract String calculatedValueVariable();

    @Nonnull
    public abstract RuleValueType calculatedValueType();

    @Override
    public Map<String, RuleVariableValue> createValues( RuleVariableValueMapBuilder builder,
        Map<String, List<RuleDataValue>> allEventValues,
        Map<String, RuleAttributeValue> currentEnrollmentValues,
        Map<String, RuleDataValue> currentEventValues )
    {
        Map<String, RuleVariableValue> valueMap = new HashMap();

        valueMap.put( this.name(), RuleVariableValue.create( this.calculatedValueType() ) );
        return valueMap;
    }
}
