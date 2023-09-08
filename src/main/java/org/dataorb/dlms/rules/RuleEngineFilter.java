package co.dataorb.java.rules;

import org.apache.commons.lang3.StringUtils;
import co.dataorb.java.rules.models.AttributeType;
import co.dataorb.java.rules.models.Rule;
import co.dataorb.java.rules.models.RuleAction;
import co.dataorb.java.rules.models.RuleActionAttribute;
import co.dataorb.java.rules.models.RuleEnrollment;
import co.dataorb.java.rules.models.RuleEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class RuleEngineFilter
{
    static List<Rule> filterRules( List<Rule> rules, RuleEnrollment ruleEnrollment )
    {
        List<Rule> filteredRules = new ArrayList<>();

        for ( Rule rule : rules )
        {
            if ( StringUtils.isEmpty( rule.programStage() ) )
            {
                List<RuleAction> ruleActions = filterActionRules( rule.actions(),
                    AttributeType.TRACKED_ENTITY_ATTRIBUTE );
                filteredRules.add( Rule.copy( rule, ruleActions ) );
            }
        }

        return filteredRules;
    }

    static List<Rule> filterRules( List<Rule> rules, RuleEvent ruleEvent )
    {
        List<Rule> filteredRules = new ArrayList<>();

        for ( Rule rule : rules )
        {
            if ( StringUtils.isEmpty( rule.programStage() ) ||
                Objects.equals( rule.programStage(), ruleEvent.programStage() ) )
            {
                List<RuleAction> ruleActions = filterActionRules( rule.actions(),
                    AttributeType.DATA_ELEMENT );
                filteredRules.add( Rule.copy( rule, ruleActions ) );
            }
        }

        return filteredRules;
    }

    private static List<RuleAction> filterActionRules( List<RuleAction> ruleActions, AttributeType attributeType )
    {
        List<RuleAction> filteredRuleActions = new ArrayList<>();

        for ( RuleAction ruleAction : ruleActions )
        {
            if ( !(ruleAction instanceof RuleActionAttribute) ||
                (((RuleActionAttribute) ruleAction).attributeType() == attributeType ||
                    ((RuleActionAttribute) ruleAction).attributeType() == AttributeType.UNKNOWN) )
            {
                filteredRuleActions.add( ruleAction );
            }
        }

        return filteredRuleActions;
    }
}
