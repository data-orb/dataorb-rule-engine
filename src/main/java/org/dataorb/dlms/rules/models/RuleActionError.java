package org.dataorb.dlms.rules.models;

import com.google.auto.value.AutoValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.dataorb.dlms.rules.models.AttributeType.UNKNOWN;

@AutoValue
public abstract class RuleActionError
    extends RuleAction
{

    @Nonnull
    public static RuleActionError create( @Nonnull String data )
    {
        return new AutoValue_RuleActionError( data );
    }
}
