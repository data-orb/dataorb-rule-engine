package co.dataorb.java.rules.models;

public enum TriggerEnvironment
{
    ANDROIDCLIENT( "AndroidClient" ),
    SERVER( "Server" );

    private String clientName;

    TriggerEnvironment( String clientName )
    {
        this.clientName = clientName;
    }

    public String getClientName()
    {
        return clientName;
    }
}
