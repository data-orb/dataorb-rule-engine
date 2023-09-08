package co.dataorb.java.rules;

import co.dataorb.java.rules.models.Rule;
import co.dataorb.java.rules.models.RuleAction;
import co.dataorb.java.rules.models.RuleActionAssign;
import co.dataorb.java.rules.models.RuleActionShowError;
import co.dataorb.java.rules.models.RuleAttributeValue;
import co.dataorb.java.rules.models.RuleDataValue;
import co.dataorb.java.rules.models.RuleEffect;
import co.dataorb.java.rules.models.RuleEnrollment;
import co.dataorb.java.rules.models.RuleEvent;
import co.dataorb.java.rules.models.TriggerEnvironment;
import co.dataorb.java.rules.util.MockRule;
import co.dataorb.java.rules.util.MockRuleVariable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith( JUnit4.class )
public class ConstantsValueTest
{

    @Test( expected = IllegalArgumentException.class )
    public void shouldThrowExceptionIfConstantsValueMapIsNull()
    {
        RuleEngineContext.builder()
            .rules( Arrays.asList( new MockRule()) )
            .ruleVariables( Arrays.asList( new MockRuleVariable()) )
            .supplementaryData( new HashMap<String, List<String>>() )
            .constantsValue( null )
            .build();

    }

    @Test
    public void assignConstantValueFromAssignActionInEnrollment()
        throws Exception
    {
        RuleAction assignAction = RuleActionAssign.create( null, "C{A1234567890}", "#{test_attribute}" );
        co.dataorb.java.rules.models.Rule rule = co.dataorb.java.rules.models.Rule
            .create( null, 1, "true", Arrays.asList( assignAction ), "test_program_rule1", "" );

        Map<String, String> constantsValueMap = new HashMap<>();
        constantsValueMap.put( "A1234567890", "3.14" );

        RuleEngine.Builder ruleEngineBuilder = getRuleEngine( Arrays.asList( rule ), constantsValueMap );

        RuleEnrollment enrollment = RuleEnrollment.builder()
            .enrollment( "test_enrollment" )
            .programName( "test_program" )
            .incidentDate( new Date() )
            .enrollmentDate( new Date() )
            .status( RuleEnrollment.Status.ACTIVE )
            .organisationUnit( "test_ou" )
            .organisationUnitCode( "test_ou_code" )
            .attributeValues( Arrays.asList( RuleAttributeValue.create( "test_attribute", "test_value" ) ) )
            .build();

        RuleEngine ruleEngine = ruleEngineBuilder.build();
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( enrollment ).call();

        assertThat( ruleEffects.size() ).isEqualTo( 1 );
        assertThat( ruleEffects.get( 0 ).data() ).isEqualTo( "3.14" );
        assertThat( ruleEffects.get( 0 ).ruleAction() ).isEqualTo( assignAction );
    }

    @Test
    public void assignValue()
        throws Exception
    {
        RuleAction assignAction = RuleActionAssign.create( null, "4", "test_attribute" );
        RuleAction action = RuleActionShowError.create( null, "#{test_attribute}", "" );
        co.dataorb.java.rules.models.Rule rule = co.dataorb.java.rules.models.Rule
            .create( null, 1, "true", Arrays.asList( assignAction ), "test_program_rule1", "" );
        co.dataorb.java.rules.models.Rule rule2 = co.dataorb.java.rules.models.Rule
            .create( null, 1, "#{test_attribute} > 3", Arrays.asList( action ), "test_program_rule2", "" );

        RuleEngine.Builder ruleEngineBuilder = getRuleEngine( Arrays.asList( rule, rule2 ),
            new HashMap() );

        RuleEnrollment enrollment = RuleEnrollment.builder()
            .enrollment( "test_enrollment" )
            .programName( "test_program" )
            .incidentDate( new Date() )
            .enrollmentDate( new Date() )
            .status( RuleEnrollment.Status.ACTIVE )
            .organisationUnit( "test_ou" )
            .organisationUnitCode( "test_ou_code" )
            .attributeValues( Arrays.asList( RuleAttributeValue.create( "test_attribute", "test_value" ) ) )
            .build();

        RuleEngine ruleEngine = ruleEngineBuilder.build();
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( enrollment ).call();

        assertThat( ruleEffects.size() ).isEqualTo( 2 );
        assertThat( ruleEffects.get( 0 ).data() ).isEqualTo( "4" );
        assertThat( ruleEffects.get( 0 ).ruleAction() ).isEqualTo( assignAction );
        assertThat( ruleEffects.get( 1 ).data() ).isEqualTo( "4" );
        assertThat( ruleEffects.get( 1 ).ruleAction() ).isEqualTo( action );
    }

    @Test
    public void assignValueThroughVariable()
        throws Exception
    {
        RuleAction assignAction = RuleActionAssign.create( "#{test_attribute}", "4", null );
        RuleAction action = RuleActionShowError.create( null, "#{test_attribute}", "" );
        co.dataorb.java.rules.models.Rule rule = co.dataorb.java.rules.models.Rule
            .create( null, 1, "true", Arrays.asList( assignAction ), "test_program_rule1", "" );
        co.dataorb.java.rules.models.Rule rule2 = co.dataorb.java.rules.models.Rule
            .create( null, 1, "#{test_attribute} > 3", Arrays.asList( action ), "test_program_rule2", "" );

        RuleEngine.Builder ruleEngineBuilder = getRuleEngine( Arrays.asList( rule, rule2 ),
            new HashMap() );

        RuleEnrollment enrollment = RuleEnrollment.builder()
            .enrollment( "test_enrollment" )
            .programName( "test_program" )
            .incidentDate( new Date() )
            .enrollmentDate( new Date() )
            .status( RuleEnrollment.Status.ACTIVE )
            .organisationUnit( "test_ou" )
            .organisationUnitCode( "test_ou_code" )
            .attributeValues( Arrays.asList( RuleAttributeValue.create( "test_attribute", "test_value" ) ) )
            .build();

        RuleEngine ruleEngine = ruleEngineBuilder.build();
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( enrollment ).call();

        assertThat( ruleEffects.size() ).isEqualTo( 1 );
        assertThat( ruleEffects.get( 0 ).data() ).isEqualTo( "4" );
        assertThat( ruleEffects.get( 0 ).ruleAction() ).isEqualTo( action );
    }

    @Test
    public void assignConstantValueFromAssignActionInEvent()
        throws Exception
    {
        RuleAction assignAction = RuleActionAssign.create( null, "C{A1234567890}", "#{test_data_element}" );
        co.dataorb.java.rules.models.Rule rule = co.dataorb.java.rules.models.Rule
            .create( null, 1, "true", Arrays.asList( assignAction ), "test_program_rule1", "" );

        Map<String, String> constantsValueMap = new HashMap<>();
        constantsValueMap.put( "A1234567890", "3.14" );

        RuleEngine.Builder ruleEngineBuilder = getRuleEngine( Arrays.asList( rule ), constantsValueMap );

        RuleEvent ruleEvent = RuleEvent.builder()
            .event( "test_event" )
            .programStage( "test_program_stage" )
            .programStageName( "" )
            .status( RuleEvent.Status.ACTIVE )
            .eventDate( new Date() )
            .dueDate( new Date() )
            .organisationUnit( "" )
            .organisationUnitCode( "" )
            .completedDate( new Date() )
            .dataValues( Arrays.asList( RuleDataValue.create(
                new Date(), "test_program_stage", "test_data_element", "test_value" ) ) )
            .build();

        RuleEngine ruleEngine = ruleEngineBuilder.build();
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( ruleEvent ).call();

        assertThat( ruleEffects.size() ).isEqualTo( 1 );
        assertThat( ruleEffects.get( 0 ).data() ).isEqualTo( "3.14" );
        assertThat( ruleEffects.get( 0 ).ruleAction() ).isEqualTo( assignAction );

    }

    private RuleEngine.Builder getRuleEngine( List<Rule> rules,
        Map<String, String> constantsValueMap )
    {
        return RuleEngineContext
            .builder()
            .rules( rules )
            .ruleVariables( new ArrayList<>())
            .supplementaryData( new HashMap<String, List<String>>() )
            .constantsValue( constantsValueMap )
            .build().toEngineBuilder().triggerEnvironment( TriggerEnvironment.SERVER );
    }

}
