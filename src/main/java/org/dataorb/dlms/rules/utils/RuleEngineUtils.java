package co.dataorb.java.rules.utils;

import co.dataorb.java.rules.ItemValueType;

import java.util.Map;

import static java.util.Map.entry;

public class RuleEngineUtils
{
    public static final String ENV_VAR_CURRENT_DATE = "current_date";
    public static final String ENV_VAR_COMPLETED_DATE = "completed_date";
    public static final String ENV_VAR_EVENT_DATE = "event_date";
    public static final String ENV_VAR_EVENT_COUNT = "event_count";
    public static final String ENV_VAR_DUE_DATE = "due_date";
    public static final String ENV_VAR_EVENT_ID = "event_id";
    public static final String ENV_VAR_ENROLLMENT_DATE = "enrollment_date";
    public static final String ENV_VAR_ENROLLMENT_ID = "enrollment_id";
    public static final String ENV_VAR_ENROLLMENT_COUNT = "enrollment_count";
    public static final String ENV_VAR_INCIDENT_DATE = "incident_date";
    public static final String ENV_VAR_TEI_COUNT = "tei_count";
    public static final String ENV_VAR_EVENT_STATUS = "event_status";
    public static final String ENV_VAR_OU = "org_unit";
    public static final String ENV_VAR_ENROLLMENT_STATUS = "enrollment_status";
    public static final String ENV_VAR_PROGRAM_STAGE_ID = "program_stage_id";
    public static final String ENV_VAR_PROGRAM_STAGE_NAME = "program_stage_name";
    public static final String ENV_VAR_PROGRAM_NAME = "program_name";
    public static final String ENV_VAR_ENVIRONMENT = "environment";
    public static final String ENV_VAR_OU_CODE = "orgunit_code";

    // new environment variable must be added in this map
    public static final Map<String, ItemValueType> ENV_VARIABLES = Map.ofEntries(
            entry( ENV_VAR_COMPLETED_DATE, ItemValueType.DATE ),
            entry( ENV_VAR_CURRENT_DATE, ItemValueType.DATE ),
            entry( ENV_VAR_EVENT_DATE, ItemValueType.DATE ),
            entry( ENV_VAR_INCIDENT_DATE, ItemValueType.DATE ),
            entry( ENV_VAR_ENROLLMENT_DATE, ItemValueType.DATE ),
            entry( ENV_VAR_DUE_DATE, ItemValueType.DATE ),
            entry( ENV_VAR_EVENT_COUNT, ItemValueType.NUMBER ),
            entry( ENV_VAR_TEI_COUNT, ItemValueType.NUMBER ),
            entry( ENV_VAR_ENROLLMENT_COUNT, ItemValueType.NUMBER ),
            entry( ENV_VAR_EVENT_ID, ItemValueType.NUMBER ),
            entry( ENV_VAR_PROGRAM_STAGE_ID, ItemValueType.NUMBER ),
            entry( ENV_VAR_ENROLLMENT_ID, ItemValueType.NUMBER ),
            entry( ENV_VAR_ENROLLMENT_STATUS, ItemValueType.TEXT ),
            entry( ENV_VAR_EVENT_STATUS, ItemValueType.TEXT ),
            entry( ENV_VAR_OU, ItemValueType.TEXT ),
            entry( ENV_VAR_OU_CODE, ItemValueType.TEXT ),
            entry( ENV_VAR_ENVIRONMENT, ItemValueType.TEXT ),
            entry( ENV_VAR_PROGRAM_NAME, ItemValueType.TEXT ),
            entry( ENV_VAR_PROGRAM_STAGE_NAME, ItemValueType.TEXT )
            );
}
