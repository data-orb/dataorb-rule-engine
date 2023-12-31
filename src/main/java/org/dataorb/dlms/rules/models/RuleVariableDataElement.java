package co.dataorb.java.rules.models;


import javax.annotation.Nonnull;

abstract class RuleVariableDataElement
    extends RuleVariable
{

    @Nonnull
    public abstract String dataElement();

    @Nonnull
    public abstract RuleValueType dataElementType();
}
