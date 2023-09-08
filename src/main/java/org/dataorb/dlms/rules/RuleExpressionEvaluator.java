package co.dataorb.java.rules;

import javax.annotation.Nonnull;

public interface RuleExpressionEvaluator
{
    @Nonnull
    String evaluate( @Nonnull String expression );
}
