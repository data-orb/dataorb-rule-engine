package co.dataorb.java.rules.models;

import com.google.auto.value.AutoValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static co.dataorb.java.rules.models.AttributeType.UNKNOWN;

@AutoValue
public abstract class RuleActionShowError
    extends RuleActionMessage
{

    @Nonnull
    public static RuleActionShowError create( @Nullable String content,
        @Nullable String data, @Nonnull String field, @Nullable AttributeType attributeType )
    {
        if ( content == null && data == null )
        {
            throw new IllegalArgumentException( "Both content and data must not be null" );
        }

        return new AutoValue_RuleActionShowError( data == null ? "" : data, attributeType,
            content == null ? "" : content, field );
    }

    @Nonnull
    public static RuleActionShowError create( @Nullable String content, @Nullable String data, @Nonnull String field )
    {
        return create( content, data, field, UNKNOWN );
    }
}
