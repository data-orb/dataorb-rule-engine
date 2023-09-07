package org.dataorb.dlms.rules.models;

import com.google.auto.value.AutoValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.dataorb.dlms.rules.models.AttributeType.UNKNOWN;

@AutoValue
public abstract class RuleActionSetMandatoryField
    extends RuleActionAttribute
{

    @Nonnull
    public static RuleActionSetMandatoryField create( @Nonnull String field, @Nullable AttributeType attributeType )
    {
        return new AutoValue_RuleActionSetMandatoryField( "", attributeType, field );
    }

    @Nonnull
    public static RuleActionSetMandatoryField create( @Nonnull String field )
    {
        return create( field, UNKNOWN );
    }

    @Nonnull
    public abstract String field();
}
