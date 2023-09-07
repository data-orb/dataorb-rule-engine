package org.dataorb.dlms.rules;


import org.dataorb.dlms.rules.models.Rule;
import org.dataorb.dlms.rules.models.RuleEnrollment;
import org.dataorb.dlms.rules.models.RuleEvent;
import org.dataorb.dlms.rules.models.RuleVariable;
import org.dataorb.dlms.rules.models.TriggerEnvironment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RuleEngineTestUtils
{
    public static RuleEngine getRuleEngine( Rule rule, List<RuleVariable> ruleVariables )
    {
        return getRuleEngineBuilder( Arrays.asList( rule ), ruleVariables )
            .build();
    }

    public static RuleEngine getRuleEngine( List<Rule> rules, RuleEnrollment ruleEnrollment,
        List<RuleEvent> ruleEvents )
    {
        return getRuleEngineBuilder( rules, ruleEnrollment, ruleEvents )
            .build();
    }

    public static RuleEngine getRuleEngine( List<Rule> rules )
    {
        return getRuleEngineBuilder( rules, List.of() )
            .build();
    }

    public static RuleEngine.Builder getRuleEngineBuilder( Rule rule, List<RuleVariable> ruleVariables )
    {
        return getRuleEngineBuilder( Arrays.asList( rule ), ruleVariables );
    }

    private static RuleEngine.Builder getRuleEngineBuilder( List<Rule> rule, List<RuleVariable> ruleVariables )
    {
        return RuleEngineContext
            .builder()
            .rules( rule )
            .ruleVariables( ruleVariables )
            .supplementaryData( new HashMap<String, List<String>>() )
            .constantsValue( new HashMap<String, String>() )
            .build().toEngineBuilder().triggerEnvironment( TriggerEnvironment.SERVER );
    }

    private static RuleEngine.Builder getRuleEngineBuilder( List<Rule> rule, RuleEnrollment ruleEnrollment,
        List<RuleEvent> ruleEvents )
    {
        return getRuleEngineBuilder( rule, List.of() )
            .enrollment( ruleEnrollment )
            .events( ruleEvents );
    }
}
