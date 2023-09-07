package org.dataorb.dlms.rules.util;

import org.dataorb.dlms.rules.models.RuleAction;

import javax.annotation.Nonnull;

public class MockRuleAction extends RuleAction {
    @Nonnull
    @Override
    public String data() {
        return null;
    }
}
