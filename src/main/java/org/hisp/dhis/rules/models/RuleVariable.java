package org.dataorb.dlms.rules.models;

import org.dataorb.dlms.rules.Option;
import org.dataorb.dlms.rules.RuleVariableValue;
import org.dataorb.dlms.rules.RuleVariableValueMapBuilder;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public abstract class RuleVariable
{
    /**
     * @return Name of the variable. Something what users refer to
     * when building program rules.
     */
    @Nonnull
    public abstract String name();

    public abstract boolean useCodeForOptionSet();

    @Nonnull
    public abstract List<Option> options();

    public abstract Map<String, RuleVariableValue> createValues( RuleVariableValueMapBuilder builder,
        Map<String, List<RuleDataValue>> allEventValues,
        Map<String, RuleAttributeValue> currentEnrollmentValues,
        Map<String, RuleDataValue> currentEventValues );

    public String getOptionName( String value )
    {
        // if no option found then existing value in the context will be used
        if ( options() == null || options().isEmpty() )
        {
            return value;
        }
        return options().stream()
                .filter( op -> Objects.equals( value, op.getCode() ) )
                .map( Option::getName )
                .findAny()
                .orElse( value );
    }
}