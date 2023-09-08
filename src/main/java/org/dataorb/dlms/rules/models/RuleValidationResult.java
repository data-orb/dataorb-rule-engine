package co.dataorb.java.rules.models;

public class RuleValidationResult
{
    private String description;
    private boolean isValid;
    private String errorMessage;
    private Throwable exception;

    public RuleValidationResult( String description, boolean isValid, String errorMessage, Throwable exception )
    {
        this.description = description;
        this.isValid = isValid;
        this.errorMessage = errorMessage;
        this.exception = exception;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isValid()
    {
        return isValid;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public Throwable getException()
    {
        return exception;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private String description;
        private boolean isValid;
        private String errorMessage;
        private Throwable exception;

        public Builder description( String description )
        {
            this.description = description;
            return this;
        }

        public Builder isValid( boolean isValid )
        {
            this.isValid = isValid;
            return this;
        }

        public Builder errorMessage( String errorMessage )
        {
            this.errorMessage = errorMessage;
            return this;
        }

        public Builder exception( Throwable exception )
        {
            this.exception = exception;
            return this;
        }

        public RuleValidationResult build()
        {
            return new RuleValidationResult( this.description, this.isValid, this.errorMessage, exception );
        }
    }
}
