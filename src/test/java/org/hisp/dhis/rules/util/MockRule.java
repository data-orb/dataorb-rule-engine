package org.dataorb.dlms.rules.util;

import org.dataorb.dlms.rules.models.Rule;
import org.dataorb.dlms.rules.models.RuleAction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class MockRule extends Rule {
    @Nullable
    @Override
    public String name() {
        return null;
    }

    @Nullable
    @Override
    public String programStage() {
        return null;
    }

    @Nullable
    @Override
    public Integer priority() {
        return null;
    }

    @Nonnull
    @Override
    public String condition() {
        return "true";
    }

    @Nonnull
    @Override
    public List<RuleAction> actions() {
        return List.of();
    }

    @Nonnull
    @Override
    public String uid() {
        return null;
    }
}
