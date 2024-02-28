package com.github.cyberryan1.netuno.models.commands;

import com.github.cyberryan1.cybercore.spigot.command.CyberSuperCommand;

public abstract class HelpableSuperCommand extends CyberSuperCommand implements GenericHelpableCommand {

    private int helpOrder = -1; // -1 means it will be skipped

    public HelpableSuperCommand( String name, String permission, String permissionMsg, String usage ) {
        super( name, permission, permissionMsg, usage );
    }

    public HelpableSuperCommand( String name, String permission, String usage ) {
        super( name, permission, usage );
    }

    public HelpableSuperCommand( String name, String usage ) {
        super( name, usage );
    }

    protected void setHelpOrder( int helpOrder ) { this.helpOrder = helpOrder; }

    public int getHelpOrder() { return this.helpOrder; }

    public String getHelpMsg() { return super.getUsage(); }
}