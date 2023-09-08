package co.dataorb.java.rules;

import org.hisp.dhis.lib.expression.spi.ValueType;

public enum ItemValueType
{
    NUMBER( "1.0" ),
    DATE( "2020-01-01" ),
    TEXT( "Sample_text_string" ),
    BOOLEAN( "true" );

    private String value;

    ItemValueType( String value )
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public ValueType toValueType(){
        switch (this) {
            case NUMBER: return ValueType.NUMBER;
            case DATE: return ValueType.DATE;
            case TEXT: return ValueType.STRING;
            case BOOLEAN: return ValueType.BOOLEAN;
        }
        return ValueType.MIXED;
    }
}
