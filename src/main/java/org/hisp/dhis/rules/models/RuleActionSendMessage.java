package org.dataorb.dlms.rules.models;

import com.google.auto.value.AutoValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@AutoValue
public abstract class RuleActionSendMessage
    extends RuleAction
{
    @Nonnull
    public static RuleActionSendMessage create( @Nullable String notification, @Nullable String data )
    {
        return new AutoValue_RuleActionSendMessage( data == null ? "" : data,
            notification == null ? "" : notification );
    }

    @Nonnull
    public abstract String notification();
}
