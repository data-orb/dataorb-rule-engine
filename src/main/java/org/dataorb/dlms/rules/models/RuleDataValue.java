package co.dataorb.java.rules.models;

import com.google.auto.value.AutoValue;

import javax.annotation.Nonnull;
import java.util.Date;

@AutoValue
public abstract class RuleDataValue
{

    public static RuleDataValue create( @Nonnull Date eventDate, @Nonnull String programStage,
        @Nonnull String dataelement, @Nonnull String value )
    {
        return new AutoValue_RuleDataValue( eventDate, programStage, dataelement, value );
    }

    @Nonnull
    public abstract Date eventDate();

    @Nonnull
    public abstract String programStage();

    @Nonnull
    public abstract String dataElement();

    @Nonnull
    public abstract String value();
}
