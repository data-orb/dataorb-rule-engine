package co.dataorb.java.rules.util;

import co.dataorb.java.rules.models.RuleDataValue;
import co.dataorb.java.rules.models.RuleEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

public class MockRuleEvent extends RuleEvent {
    @Nonnull
    @Override
    public String event() {
        return null;
    }

    @Nonnull
    @Override
    public String programStage() {
        return null;
    }

    @Nonnull
    @Override
    public String programStageName() {
        return null;
    }

    @Nonnull
    @Override
    public Status status() {
        return null;
    }

    @Nonnull
    @Override
    public Date eventDate() {
        return null;
    }

    @Nullable
    @Override
    public Date dueDate() {
        return null;
    }

    @Nullable
    @Override
    public Date completedDate() {
        return null;
    }

    @Nonnull
    @Override
    public String organisationUnit() {
        return null;
    }

    @Nullable
    @Override
    public String organisationUnitCode() {
        return null;
    }

    @Nonnull
    @Override
    public List<RuleDataValue> dataValues() {
        return null;
    }
}
