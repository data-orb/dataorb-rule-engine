package co.dataorb.java.rules;

import co.dataorb.java.rules.models.Rule;
import co.dataorb.java.rules.models.RuleAction;
import co.dataorb.java.rules.models.RuleActionDisplayKeyValuePair;
import co.dataorb.java.rules.models.RuleDataValue;
import co.dataorb.java.rules.models.RuleEffect;
import co.dataorb.java.rules.models.RuleEvent;
import co.dataorb.java.rules.models.RuleValueType;
import co.dataorb.java.rules.models.RuleVariable;
import co.dataorb.java.rules.models.RuleVariableCurrentEvent;
import co.dataorb.java.rules.models.TriggerEnvironment;
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
public class VariableValueTypeTest
{
    @Test
    public void testNumericVariablesAreComparedCorrectly()
            throws Exception
    {
        RuleAction ruleAction = RuleActionDisplayKeyValuePair
                .createForFeedback( "test_action_content", "#{test_variable}" );
        Rule rule = Rule.create( null, null, "#{test_variable} > #{test_variable2}", Arrays.asList( ruleAction ), "", "" );
        RuleVariable ruleVariable = RuleVariableCurrentEvent
                .create( "test_variable", "test_data_element", RuleValueType.NUMERIC, true, new ArrayList<>());
        RuleVariable ruleVariable2 = RuleVariableCurrentEvent
                .create( "test_variable2", "test_data_element2", RuleValueType.NUMERIC, true, new ArrayList<>());

        RuleEngine ruleEngine = getRuleEngine( rule, Arrays.asList( ruleVariable, ruleVariable2 ) );

        RuleEvent ruleEvent = RuleEvent.create( "test_event", "test_program_stage",
                RuleEvent.Status.ACTIVE, new Date(), new Date(), "", null,
                Arrays.asList(RuleDataValue.create(new Date(), "", "test_data_element", "30"),
                        RuleDataValue.create(new Date(), "", "test_data_element2", "4")), "", null);
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( ruleEvent ).call();

        assertThat( ruleEffects.size() ).isEqualTo( 1 );
        assertThat( ruleEffects.get( 0 ).data() ).isEqualTo( "30" );
        assertThat( ruleEffects.get( 0 ).ruleAction() ).isEqualTo( ruleAction );
    }

    @Test
    public void testTextVariablesAreComparedCorrectly()
            throws Exception
    {
        RuleAction ruleAction = RuleActionDisplayKeyValuePair
                .createForFeedback( "test_action_content", "#{test_variable}" );
        Rule rule = Rule.create( null, null, "#{test_variable} > #{test_variable2}", Arrays.asList( ruleAction ), "", "" );
        RuleVariable ruleVariable = RuleVariableCurrentEvent
                .create( "test_variable", "test_data_element", RuleValueType.TEXT, true, new ArrayList<>());
        RuleVariable ruleVariable2 = RuleVariableCurrentEvent
                .create( "test_variable2", "test_data_element2", RuleValueType.TEXT, true, new ArrayList<>());

        RuleEngine ruleEngine = getRuleEngine( rule, Arrays.asList( ruleVariable, ruleVariable2 ) );

        RuleEvent ruleEvent = RuleEvent.create( "test_event", "test_program_stage",
                RuleEvent.Status.ACTIVE, new Date(), new Date(), "", null,
                Arrays.asList(RuleDataValue.create(new Date(), "", "test_data_element", "30"),
                        RuleDataValue.create(new Date(), "", "test_data_element2", "4")), "", null);
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( ruleEvent ).call();

        assertThat( ruleEffects.size() ).isEqualTo( 0 );
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
