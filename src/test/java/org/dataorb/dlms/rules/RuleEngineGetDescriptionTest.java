package co.dataorb.java.rules;

import org.hisp.dhis.lib.expression.spi.ParseException;
import co.dataorb.java.rules.models.Rule;
import co.dataorb.java.rules.models.RuleAction;
import co.dataorb.java.rules.models.RuleActionDisplayKeyValuePair;
import co.dataorb.java.rules.models.RuleValidationResult;
import co.dataorb.java.rules.models.TriggerEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith( JUnit4.class )

public class RuleEngineGetDescriptionTest
{
    private String test_var_one = "Variable_ONE";
    private String test_var_two = "Variable_TWO";
    private String test_var_three = "Variable_THREE";
    private String test_var_date_one = "2020-01-01";
    private String test_var_date_two = "2020-02-02";
    private String completionDate = "Completion date";
    private String currentDate = "Current date";
    private String constant = "PI";
    private String test_var_number = "9";

    private Map<String, DataItem> itemStore = new HashMap<>();

    private RuleAction ruleAction = RuleActionDisplayKeyValuePair.createForFeedback("", "" );

    @org.junit.Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp()
    {
        itemStore = new HashMap<>();

        DataItem var_1 = DataItem.builder().value( test_var_one ).valueType( ItemValueType.TEXT ).build();
        DataItem var_2 = DataItem.builder().value( test_var_two ).valueType( ItemValueType.TEXT ).build();
        DataItem var_8 = DataItem.builder().value( test_var_three ).valueType( ItemValueType.TEXT ).build();
        DataItem var_3 = DataItem.builder().value( test_var_date_one ).valueType( ItemValueType.DATE ).build();
        DataItem var_4 = DataItem.builder().value( test_var_date_two ).valueType( ItemValueType.DATE ).build();
        DataItem var_5 = DataItem.builder().value( completionDate ).valueType( ItemValueType.DATE ).build();
        DataItem var_6 = DataItem.builder().value( constant ).valueType( ItemValueType.TEXT ).build();
        DataItem var_7 = DataItem.builder().value( currentDate ).valueType( ItemValueType.DATE ).build();
        DataItem var_9 = DataItem.builder().value( test_var_number ).valueType( ItemValueType.NUMBER ).build();

        itemStore.put( "test_var_one", var_1 );
        itemStore.put( "test_var_two", var_2 );
        itemStore.put( "test_var_date_one", var_3 );
        itemStore.put( "test_var_date_two", var_4 );
        itemStore.put( "completed_date", var_5 );
        itemStore.put( "NAgjOfWMXg6", var_6 );
        itemStore.put( "current_date", var_7 );
        itemStore.put( "test_var_three", var_8 );
        itemStore.put( "test_var_number", var_9 );
    }

    @Test
    public void evaluateGetDescriptionWithIncorrectRules()
    {
        Rule incorrectRuleHasValue = Rule
            .create( null, null, "d2:hasValue(#{test_var_one} + 1)", Arrays.asList( ruleAction ), "", "" );
        Rule incorrectSyntaxRule = Rule
            .create( null, null, "d2:daysBetween((#{test_var_date_one},#{test_var_date_two})",
                Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( incorrectRuleHasValue.condition() );

        assertNotNull( result );
        assertFalse( result.isValid() );

        ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        result = ruleEngine.evaluate( incorrectSyntaxRule.condition() );

        assertNotNull( result );
        assertFalse( result.isValid() );
    }

    @Test
    public void evaluateGetDescriptionWithInvalidProgramRuleVariable()
    {
        Rule rule = Rule.create( null, null, "d2:hasValue(#{test_var_one1})", Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( rule.condition() );

        assertNotNull( result );
        assertFalse( result.isValid() );
    }

    @Test
    public void getDescriptionForLengthFunction()
    {
        Rule rule = Rule.create( null, null, "d2:length(#{test_var_one}) > 0", Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( rule.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );

        rule = Rule.create( null, null, "d2:length(#{test_var_date_one}) > 0 ", Arrays.asList( ruleAction ), "", "" );

        result = ruleEngine.evaluate( rule.condition() );

        assertNotNull( result );
        assertFalse( result.isValid() );

        rule = Rule.create( null, null, "d2:length(#{test_var_number}) > 0 ", Arrays.asList( ruleAction ), "", "" );

        result = ruleEngine.evaluate( rule.condition() );

        assertNotNull( result );
        assertFalse( result.isValid() );
    }

    @Test
    public void testGetDescriptionWithD2FunctionsAndLogicalAnd()
    {
        Rule correctMultipleD2FunctionRule = Rule
            .create( null, null, "d2:count(#{test_var_one}) > 0 && d2:hasValue(#{test_var_two})",
                Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( correctMultipleD2FunctionRule.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionWithD2FunctionsTEA()
    {
        Rule conditionWithD2FunctionsTEA = Rule
            .create( null, null, "d2:hasValue('test_var_three')", Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( conditionWithD2FunctionsTEA.condition() );

        assertNotNull( result );
        assertEquals( "d2:hasValue(Variable_THREE)", result.getDescription() );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionWithPlainAttributeComparisonWithName()
    {
        Rule conditionWithD2FunctionsTEA = Rule
            .create( null, null, "'test_var_three' == 'email'", Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( conditionWithD2FunctionsTEA.condition() );

        assertNotNull( result );
        assertEquals( "'test_var_three' == 'email'", result.getDescription() );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionWithPlainAttributeComparison()
    {
        Rule conditionWithD2FunctionsTEA = Rule
            .create( null, null, "A{test_var_three} == 'email'", Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( conditionWithD2FunctionsTEA.condition() );

        assertNotNull( result );
        assertEquals( "Variable_THREE == 'email'", result.getDescription() );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionStringLiterals()
    {
        String condition = " true && false || 1 > 3";
        Rule literalStringRule = Rule.create( null, null, condition, Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( literalStringRule.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );

    }

    @Test
    public void testGetDescriptionD2BetweenFunction()
    {
        String condition = "d2:daysBetween(#{test_var_date_one},#{test_var_date_two}) > 0";
        Rule correctD2betweenFunctionRule = Rule.create( null, null, condition, Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( correctD2betweenFunctionRule.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionD2BetweenFunctionWithEnvironmentVariables()
    {
        String condition = "d2:daysBetween(V{completed_date},V{current_date}) > 0";
        Rule correctD2betweenFunctionRule = Rule.create( null, null, condition, Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( correctD2betweenFunctionRule.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionD2FunctionAttribute()
    {
        String condition = "A{test_var_number} > 0";
        Rule withoutD2AttFunctionRule = Rule.create( null, null, condition, Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( withoutD2AttFunctionRule.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionWithD2FunctionDataElement()
    {
        String condition = "#{test_var_number} > 0";
        Rule withoutD2DEFunctionRule = Rule.create( null, null, condition, Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( withoutD2DEFunctionRule.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionWithConstant()
    {
        String condition = "C{NAgjOfWMXg6} == 0";
        Rule constantRule = Rule.create( null, null, condition, Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( constantRule.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionWithProgramEnvironmentVariable()
    {
        String condition = "d2:hasValue(V{completed_date})";
        Rule programEnvVariableRule = Rule.create( null, null, condition, Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( programEnvVariableRule.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionWithSingleD2Function()
    {
        String condition = "d2:hasValue(#{test_var_one})";
        Rule correctRuleHasValue = Rule.create( null, null, condition, Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( correctRuleHasValue.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionWithMultipleD2FunctionsAndLogicalOROperator()
    {
        String condition = "d2:hasValue(#{test_var_two}) || d2:count(#{test_var_one}) > 0 ";

        Rule correctMultipleD2FunctionRuleWithOr = Rule
            .create( null, null, condition, Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( correctMultipleD2FunctionRuleWithOr.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionWithMultipleD2FunctionsAndLogicalANDOperator()
    {
        String condition = "d2:hasValue(#{test_var_two}) && d2:count(#{test_var_one}) > 0 ";

        Rule correctMultipleD2FunctionRuleWithAnd = Rule
            .create( null, null, condition, Arrays.asList( ruleAction ), "", "" );

        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();
        RuleValidationResult result = ruleEngine.evaluate( correctMultipleD2FunctionRuleWithAnd.condition() );

        assertNotNull( result );
        assertTrue( result.isValid() );
    }

    @Test
    public void testGetDescriptionForDataFieldExpression()
    {
        RuleEngine ruleEngine = getRuleEngineBuilderForDescription( itemStore ).build();

        RuleValidationResult result = ruleEngine.evaluateDataFieldExpression( "1 + 1" );
        assertNotNull( result );
        assertTrue( result.isValid() );

        result = ruleEngine.evaluateDataFieldExpression( "d2:hasValue(#{test_var_two}) && d2:count(#{test_var_one}) > 0 " );
        assertNotNull( result );
        assertTrue( result.isValid() );

        result = ruleEngine.evaluateDataFieldExpression( "1 + 1 +" );
        assertNotNull( result );
        assertFalse( result.isValid() );
        assertThat( result.getException(), instanceOf( ParseException.class ) );

        result = ruleEngine.evaluateDataFieldExpression( "d2:hasValue(#{test_var_two}) && d2:count(#{test_var_one}) > 0 (" );
        assertNotNull( result );
        assertFalse( result.isValid() );
        assertThat( result.getException(), instanceOf( ParseException.class ) );
    }

    private RuleEngine.Builder getRuleEngineBuilderForDescription( Map<String, DataItem> itemStore )
    {
        return RuleEngineContext
            .builder()
            .supplementaryData( new HashMap<String, List<String>>() )
            .constantsValue( new HashMap<String, String>() ).itemStore( itemStore ).ruleEngineItent( RuleEngineIntent.DESCRIPTION )
            .build().toEngineBuilder().triggerEnvironment( TriggerEnvironment.SERVER );
    }
}
