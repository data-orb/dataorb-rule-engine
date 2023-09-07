package org.dataorb.dlms.rules.models;

import org.dataorb.dlms.rules.RuleEngine;
import org.dataorb.dlms.rules.RuleEngineContext;
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
public class CalculatedValueTest
{
    @Test
    public void evaluateTenThousandRulesTest()
        throws Exception
    {
        int i = 10000;
        RuleEngine.Builder ruleEngineBuilder = getRuleEngine( createRules( i ) );

        RuleEnrollment enrollment = RuleEnrollment.builder()
            .enrollment( "test_enrollment" )
            .programName( "test_program" )
            .incidentDate( new Date() )
            .enrollmentDate( new Date() )
            .status( RuleEnrollment.Status.ACTIVE )
            .organisationUnit( "test_ou" )
            .organisationUnitCode( "test_ou_code" )
            .attributeValues( Arrays.<RuleAttributeValue>asList() )
            .build();

        RuleEvent ruleEvent = RuleEvent.builder()
            .event( "test_event" )
            .programStage( "test_program_stage" )
            .programStageName( "" )
            .status( RuleEvent.Status.ACTIVE )
            .eventDate( new Date() )
            .dueDate( new Date() )
            .organisationUnit( "" )
            .organisationUnitCode( "" )
            .dataValues( Arrays.asList( RuleDataValue.create(
                new Date(), "test_program_stage", "test_data_element", "test_value" ) ) )
            .build();

        RuleEngine ruleEngine = ruleEngineBuilder.enrollment( enrollment ).build();
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( ruleEvent ).call();

        assertThat( ruleEffects.size() ).isEqualTo( i );
    }

    @Test
    public void sendMessageMustGetValueFromAssignAction()
        throws Exception
    {
        RuleAction assignAction = RuleActionAssign.create( "#{test_calculated_value}", "2+2", null );
        org.dataorb.dlms.rules.models.Rule rule = org.dataorb.dlms.rules.models.Rule
            .create( null, 1, "true", Arrays.asList( assignAction ), "test_program_rule1", "" );

        RuleAction sendMessageAction = RuleActionSendMessage.create( "test_notification", "4" );
        org.dataorb.dlms.rules.models.Rule rule2 = org.dataorb.dlms.rules.models.Rule
            .create( null, 4, "#{test_calculated_value}==4", Arrays.asList( sendMessageAction ), "test_program_rule2",
                "" );

        RuleEnrollment enrollment = RuleEnrollment.builder()
            .enrollment( "test_enrollment" )
            .programName( "test_program" )
            .incidentDate( new Date() )
            .enrollmentDate( new Date() )
            .status( RuleEnrollment.Status.ACTIVE )
            .organisationUnit( "test_ou" )
            .organisationUnitCode( "test_ou_code" )
            .attributeValues( Arrays.<RuleAttributeValue>asList() )
            .build();

        RuleEvent ruleEvent = RuleEvent.builder()
            .event( "test_event" )
            .programStage( "test_program_stage" )
            .programStageName( "" )
            .status( RuleEvent.Status.ACTIVE )
            .eventDate( new Date() )
            .dueDate( new Date() )
            .organisationUnit( "" )
            .organisationUnitCode( "" )
            .dataValues( Arrays.asList( RuleDataValue.create(
                new Date(), "test_program_stage", "test_data_element", "test_value" ) ) )
            .build();

        RuleEngine ruleEngine = getRuleEngine( Arrays.asList( rule, rule2 ) ).enrollment( enrollment ).build();
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( ruleEvent ).call();

        assertThat( ruleEffects.get( 0 ).data() ).isEqualTo( "4" );
        assertThat( ruleEffects.get( 0 ).ruleAction() ).isEqualTo( sendMessageAction );
    }

    private List<org.dataorb.dlms.rules.models.Rule> createRules( int i )
    {
        List<org.dataorb.dlms.rules.models.Rule> rules = new ArrayList<>();
        RuleAction assignAction = RuleActionAssign.create( "#{test_calculated_value}", "2+2", null );
        org.dataorb.dlms.rules.models.Rule rule = org.dataorb.dlms.rules.models.Rule
            .create( null, 1, "true", Arrays.asList( assignAction ), "test_program_rule1", "" );

        RuleAction sendMessageAction = RuleActionSendMessage.create( "test_notification", "4" );
        org.dataorb.dlms.rules.models.Rule rule2 = org.dataorb.dlms.rules.models.Rule
            .create( null, 4, "#{test_calculated_value}==4", Arrays.asList( sendMessageAction ), "test_program_rule2",
                "" );
        for ( int j = 0; j < i; j++ )
        {
            rules.add( rule );
            rules.add( rule2 );
        }
        return rules;
    }

    @Test
    public void sendMessageMustGetValueFromAssignActionInSingleExecution()
        throws Exception
    {
        RuleAction assignAction = RuleActionAssign.create( "#{test_calculated_value}", "2+2", null );
        org.dataorb.dlms.rules.models.Rule rule = org.dataorb.dlms.rules.models.Rule
            .create( null, 1, "true", Arrays.asList( assignAction ), "test_program_rule1", "" );

        RuleAction sendMessageAction = RuleActionSendMessage.create( "test_notification", "4.0" );
        org.dataorb.dlms.rules.models.Rule rule2 = org.dataorb.dlms.rules.models.Rule
            .create( null, 4, "#{test_calculated_value}==4.0", Arrays.asList( sendMessageAction ),
                "test_program_rule2", "" );

        RuleEngine.Builder ruleEngineBuilder = getRuleEngine( Arrays.asList( rule, rule2 ) );

        RuleEnrollment enrollment = RuleEnrollment.builder()
            .enrollment( "test_enrollment" )
            .programName( "test_program" )
            .incidentDate( new Date() )
            .enrollmentDate( new Date() )
            .status( RuleEnrollment.Status.ACTIVE )
            .organisationUnit( "test_ou" )
            .organisationUnitCode( "test_ou_code" )
            .attributeValues( Arrays.<RuleAttributeValue>asList() )
            .build();

        RuleEvent ruleEvent = RuleEvent.builder()
            .event( "test_event" )
            .programStage( "test_program_stage" )
            .programStageName( "" )
            .status( RuleEvent.Status.ACTIVE )
            .eventDate( new Date() )
            .dueDate( new Date() )
            .organisationUnit( "" )
            .organisationUnitCode( "" )
            .dataValues( Arrays.asList( RuleDataValue.create(
                new Date(), "test_program_stage", "test_data_element", "test_value" ) ) )
            .build();

        RuleEngine ruleEngine = ruleEngineBuilder.enrollment( enrollment ).build();
        List<RuleEffect> ruleEffects = ruleEngine.evaluate( ruleEvent ).call();

        assertThat( ruleEffects.size() ).isEqualTo( 1 );
        assertThat( ruleEffects.get( 0 ).data() ).isEqualTo( "4" );
        assertThat( ruleEffects.get( 0 ).ruleAction() ).isEqualTo( sendMessageAction );

    }

    private RuleEngine.Builder getRuleEngine( List<org.dataorb.dlms.rules.models.Rule> rules )
    {
        RuleVariable ruleVariable = RuleVariableCalculatedValue
            .create( "test_calculated_value", "", RuleValueType.TEXT, true, new ArrayList<>());

        return RuleEngineContext
            .builder()
            .rules( rules )
            .ruleVariables( Arrays.asList( ruleVariable ) )
            .supplementaryData( new HashMap<String, List<String>>() )
            .constantsValue( new HashMap<String, String>() )
            .build().toEngineBuilder().triggerEnvironment( TriggerEnvironment.SERVER );
    }
}
