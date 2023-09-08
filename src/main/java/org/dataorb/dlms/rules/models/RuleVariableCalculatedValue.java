package co.dataorb.java.rules.models;

import com.google.auto.value.AutoValue;
import co.dataorb.java.rules.Option;
import co.dataorb.java.rules.RuleVariableValue;
import co.dataorb.java.rules.RuleVariableValueMapBuilder;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
