package org.dataorb.dlms.rules.models;

import javax.annotation.Nonnull;

public abstract class RuleActionAttribute
    extends RuleAction
{

    @Nonnull
    public abstract AttributeType attributeType();
}
