package org.dataorb.dlms.rules;

import org.apache.commons.lang3.StringUtils;
import org.dataorb.dlms.rules.models.AttributeType;
import org.dataorb.dlms.rules.models.Rule;
import org.dataorb.dlms.rules.models.RuleAction;
import org.dataorb.dlms.rules.models.RuleActionAttribute;
import org.dataorb.dlms.rules.models.RuleEnrollment;
import org.dataorb.dlms.rules.models.RuleEvent;

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
