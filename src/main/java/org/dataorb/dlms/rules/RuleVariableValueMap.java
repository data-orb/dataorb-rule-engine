package co.dataorb.java.rules;

import co.dataorb.java.rules.models.RuleEnrollment;
import co.dataorb.java.rules.models.RuleEvent;

import java.util.Map;

public final class RuleVariableValueMap
{
    private Map<RuleEnrollment, Map<String, RuleVariableValue>> enrollmentMap;

    private Map<RuleEvent, Map<String, RuleVariableValue>> eventMap;

    RuleVariableValueMap(
        Map<RuleEnrollment, Map<String, RuleVariableValue>> enrollmentMap,
        Map<RuleEvent, Map<String, RuleVariableValue>> eventMap )
    {
        this.enrollmentMap = enrollmentMap;
        this.eventMap = eventMap;
    }

    public Map<RuleEnrollment, Map<String, RuleVariableValue>> getEnrollmentMap()
    {
        return enrollmentMap;
    }

    public Map<RuleEvent, Map<String, RuleVariableValue>> getEventMap()
    {
        return eventMap;
    }
}
