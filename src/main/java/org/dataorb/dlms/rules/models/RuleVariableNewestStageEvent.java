package org.dataorb.dlms.rules.models;

import com.google.auto.value.AutoValue;
import org.dataorb.dlms.rules.Option;
import org.dataorb.dlms.rules.RuleVariableValue;
import org.dataorb.dlms.rules.RuleVariableValueMapBuilder;
import org.dataorb.dlms.rules.Utils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dataorb.dlms.rules.Utils.getLastUpdateDate;

@AutoValue
public abstract class RuleVariableNewestStageEvent
    extends RuleVariableDataElement
{

    @Nonnull
    public static RuleVariableNewestStageEvent create(@Nonnull String name, @Nonnull String dataElement,
                                                      @Nonnull String programStage, @Nonnull RuleValueType dataElementType, boolean useCodeForOptionSet, List<Option> options)
    {
        return new AutoValue_RuleVariableNewestStageEvent( name, useCodeForOptionSet, options, dataElement,
            dataElementType, programStage );
    }

    @Nonnull
    public abstract String programStage();

    @Override
    public Map<String, RuleVariableValue> createValues( RuleVariableValueMapBuilder builder,
        Map<String, List<RuleDataValue>> allEventValues,
        Map<String, RuleAttributeValue> currentEnrollmentValues,
        Map<String, RuleDataValue> currentEventValues )
    {
        Map<String, RuleVariableValue> valueMap = new HashMap();
        List<RuleDataValue> stageRuleDataValues = new ArrayList<>();
        List<RuleDataValue> sourceRuleDataValues = allEventValues.get( this.dataElement() );
        if ( sourceRuleDataValues != null && !sourceRuleDataValues.isEmpty() )
        {

            // filter data values based on program stage
            for ( int i = 0; i < sourceRuleDataValues.size(); i++ )
            {
                RuleDataValue ruleDataValue = sourceRuleDataValues.get( i );
                if ( this.programStage().equals( ruleDataValue.programStage() ) )
                {
                    stageRuleDataValues.add( ruleDataValue );
                }
            }
        }

        if ( stageRuleDataValues.isEmpty() )
        {
            valueMap.put( this.name(), RuleVariableValue.create( this.dataElementType() ) );
        }
        else
        {
            RuleVariableValue variableValue;

            RuleDataValue value = stageRuleDataValues.get( 0 );

            String optionValue = this.useCodeForOptionSet() ? value.value() : getOptionName( value.value() );

            variableValue = RuleVariableValue.create( optionValue,
                        this.dataElementType(), Utils.values( stageRuleDataValues ),
                        getLastUpdateDate( stageRuleDataValues ) );

            valueMap.put( this.name(), variableValue );
        }

        return valueMap;
    }
}
