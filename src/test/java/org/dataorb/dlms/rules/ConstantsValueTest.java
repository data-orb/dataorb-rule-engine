package org.dataorb.dlms.rules;

import org.dataorb.dlms.rules.models.Rule;
import org.dataorb.dlms.rules.models.RuleAction;
import org.dataorb.dlms.rules.models.RuleActionAssign;
import org.dataorb.dlms.rules.models.RuleActionShowError;
import org.dataorb.dlms.rules.models.RuleAttributeValue;
import org.dataorb.dlms.rules.models.RuleDataValue;
import org.dataorb.dlms.rules.models.RuleEffect;
import org.dataorb.dlms.rules.models.RuleEnrollment;
import org.dataorb.dlms.rules.models.RuleEvent;
import org.dataorb.dlms.rules.models.TriggerEnvironment;
import org.dataorb.dlms.rules.util.MockRule;
import org.dataorb.dlms.rules.util.MockRuleVariable;
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
        org.dataorb.dlms.rules.models.Rule rule = org.dataorb.dlms.rules.models.Rule
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
        org.dataorb.dlms.rules.models.Rule rule = org.dataorb.dlms.rules.models.Rule
            .create( null, 1, "true", Arrays.asList( assignAction ), "test_program_rule1", "" );
        org.dataorb.dlms.rules.models.Rule rule2 = org.dataorb.dlms.rules.models.Rule
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
        org.dataorb.dlms.rules.models.Rule rule = org.dataorb.dlms.rules.models.Rule
            .create( null, 1, "true", Arrays.asList( assignAction ), "test_program_rule1", "" );
        org.dataorb.dlms.rules.models.Rule rule2 = org.dataorb.dlms.rules.models.Rule
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
        org.dataorb.dlms.rules.models.Rule rule = org.dataorb.dlms.rules.models.Rule
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
