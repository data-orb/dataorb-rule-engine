package org.dataorb.dlms.rules;

public class DataItem
{
    private String displayName;

    private ItemValueType valueType;

    public DataItem( String value, ItemValueType valueType )
    {
        this.displayName = value;
        this.valueType = valueType;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public ItemValueType getValueType()
    {
        return valueType;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private String displayName;
        private ItemValueType itemValueType;

        public Builder value( String value )
        {
            this.displayName = value;
            return this;
        }

        public Builder valueType( ItemValueType valueType )
        {
            this.itemValueType = valueType;
            return this;
        }

        public DataItem build()
        {
            return new DataItem( displayName, itemValueType );
        }
    }
}
