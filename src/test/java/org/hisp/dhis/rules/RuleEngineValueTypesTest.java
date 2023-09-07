package org.dataorb.dlms.rules;

import org.dataorb.dlms.rules.models.Rule;
import org.dataorb.dlms.rules.models.RuleAction;
import org.dataorb.dlms.rules.models.RuleActionDisplayKeyValuePair;
import org.dataorb.dlms.rules.models.RuleDataValue;
import org.dataorb.dlms.rules.models.RuleEffect;
import org.dataorb.dlms.rules.models.RuleEvent;
import org.dataorb.dlms.rules.models.RuleValueType;
import org.dataorb.dlms.rules.models.RuleVariable;
import org.dataorb.dlms.rules.models.RuleVariableCurrentEvent;
import org.dataorb.dlms.rules.models.TriggerEnvironment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith( JUnit4.class )
public class RuleEngineValueTypesTest
{
    @Test
    public void booleanVariableWithoutValueMustFallbackToDefaultBooleanValue()
        throws Exception
    {
        RuleAction ruleAction = RuleActionDisplayKeyValuePair
            .createForFeedback( "test_action_content", "#{test_variable}" );
        Rule rule = Rule.create( null, null, "true", Arrays.asList( ruleAction ), "", "" );
        RuleVariable ruleVariable = RuleVariableCurrentEvent
            .create( "test_variable", "test_data_element", RuleValueType.BOOLEAN, true, new ArrayList<>());

        RuleEngine ruleEngine = getRuleEngine( rule, Arrays.asList( ruleVariable ) );

        RuleEvent ruleEvent = RuleEvent.create( "test_event", "test_program_stage",
            RuleEvent.Status.ACTIVE, new Date(), new Date(), "", null, new ArrayList<RuleDataValue>(), "", null);
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( ruleEvent ).call();

        assertThat( ruleEffects.size() ).isEqualTo( 1 );
        assertThat( ruleEffects.get( 0 ).data() ).isEqualTo( "false" );
        assertThat( ruleEffects.get( 0 ).ruleAction() ).isEqualTo( ruleAction );
    }

    @Test
    public void numericVariableWithoutValueMustFallbackToDefaultNumericValue()
        throws Exception
    {
        RuleAction ruleAction = RuleActionDisplayKeyValuePair
            .createForFeedback( "test_action_content", "#{test_variable}" );
        Rule rule = Rule.create( null, null, "true", Arrays.asList( ruleAction ), "", "" );
        RuleVariable ruleVariable = RuleVariableCurrentEvent
            .create( "test_variable", "test_data_element", RuleValueType.NUMERIC, true, new ArrayList<>());

        RuleEngine ruleEngine = getRuleEngine( rule, Arrays.asList( ruleVariable ) );

        RuleEvent ruleEvent = RuleEvent.create( "test_event", "test_program_stage",
            RuleEvent.Status.ACTIVE, new Date(), new Date(), "", null, new ArrayList<RuleDataValue>(), "", null);
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( ruleEvent ).call();

        assertThat( ruleEffects.size() ).isEqualTo( 1 );
        assertThat( ruleEffects.get( 0 ).data() ).isEqualTo( "0" );
        assertThat( ruleEffects.get( 0 ).ruleAction() ).isEqualTo( ruleAction );
    }

    @Test
    public void textVariableWithoutValueMustFallbackToDefaultTextValue()
        throws Exception
    {
        RuleAction ruleAction = RuleActionDisplayKeyValuePair
            .createForFeedback( "test_action_content", "#{test_variable}" );
        Rule rule = Rule.create( null, null, "true", Arrays.asList( ruleAction ), "", "" );
        RuleVariable ruleVariable = RuleVariableCurrentEvent
            .create( "test_variable", "test_data_element", RuleValueType.TEXT, true, new ArrayList<>());

        RuleEngine ruleEngine = getRuleEngine( rule, Arrays.asList( ruleVariable ) );

        RuleEvent ruleEvent = RuleEvent.create( "test_event", "test_program_stage",
            RuleEvent.Status.ACTIVE, new Date(), new Date(), "", null, new ArrayList<RuleDataValue>(), "", null);
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( ruleEvent ).call();

        assertThat( ruleEffects.size() ).isEqualTo( 1 );
        assertThat( ruleEffects.get( 0 ).data() ).isEqualTo( "" );
        assertThat( ruleEffects.get( 0 ).ruleAction() ).isEqualTo( ruleAction );
    }

    private RuleEngine getRuleEngine( Rule rule, List<RuleVariable> ruleVariables )
    {
        return RuleEngineContext
            .builder()
            .rules( Arrays.asList( rule ) )
            .ruleVariables( ruleVariables )
            .supplementaryData( new HashMap<String, List<String>>() )
            .constantsValue( new HashMap<String, String>() )
            .build().toEngineBuilder().triggerEnvironment( TriggerEnvironment.SERVER )
            .build();
    }
}
