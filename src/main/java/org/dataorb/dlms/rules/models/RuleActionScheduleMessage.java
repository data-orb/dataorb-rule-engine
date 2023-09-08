package co.dataorb.java.rules.models;

import com.google.auto.value.AutoValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@AutoValue
public abstract class RuleActionScheduleMessage
    extends RuleAction
{
    @Nonnull
    public static RuleActionScheduleMessage create( @Nullable String notification, @Nullable String data )
    {
        return new AutoValue_RuleActionScheduleMessage( data == null ? "" : data,
            notification == null ? "" : notification );
    }

    @Nonnull
    public abstract String notification();
}
