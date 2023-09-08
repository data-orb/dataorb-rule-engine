package co.dataorb.java.rules.models;

import com.google.auto.value.AutoValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static co.dataorb.java.rules.models.AttributeType.UNKNOWN;

@AutoValue
public abstract class RuleActionHideField
    extends RuleActionAttribute
{

    @Nonnull
    public static RuleActionHideField create(
        @Nullable String content, @Nonnull String field, @Nullable AttributeType attributeType )
    {
        return new AutoValue_RuleActionHideField( "", attributeType, content == null ? "" : content, field );
    }

    @Nonnull
    public static RuleActionHideField create( @Nullable String content, @Nonnull String field )
    {
        return create( content, field, UNKNOWN );
    }

    /**
     * @return a message to show to user
     * when a target field is hidden.
     */
    @Nonnull
    public abstract String content();

    /**
     * @return uid of the target field to hide.
     * It can be both dataElement and trackedEntityAttribute.
     */
    @Nonnull
    public abstract String field();
}
