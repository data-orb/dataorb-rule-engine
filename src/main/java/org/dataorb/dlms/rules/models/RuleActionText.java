package co.dataorb.java.rules.models;

import javax.annotation.Nonnull;

abstract class RuleActionText
    extends RuleAction
{
    public static final String LOCATION_FEEDBACK_WIDGET = "feedback";

    public static final String LOCATION_INDICATOR_WIDGET = "indicators";

    /**
     * @return a static content which will
     * be shown to the user.
     */
    @Nonnull
    public abstract String content();

    /**
     * @return location of where the result of this
     * action must be rendered: can be either 'feedback'
     * or 'program indicator' widget.
     */
    @Nonnull
    public abstract String location();
}
