package com.github.cyberryan1.netuno.commands;

import com.github.cyberryan1.netuno.utils.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NetunoCmd implements CommandExecutor {

    private final String COMMAND_ORDER[] = { "help", "warn", "clearchat", "kick", "mute", "unmute", "history", "ban", "unban",
            "ipinfo", "ipmute", "unipmute", "ipban", "unipban", "report", "reports", "togglesigns", "reload" };

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String args[] ) {

        if ( VaultUtils.hasPerms( sender, ConfigUtils.getStr( "general.staff-perm" ) ) == false ) {
            CommandErrors.sendInvalidPerms( sender );
            return true;
        }

        if ( Utils.isOutOfBounds( args, 0 ) == false ) {
            if ( args[0].equalsIgnoreCase( "reload" ) ) {
                if ( VaultUtils.hasPerms( sender, ConfigUtils.getStr( "general.reload-perm" ) ) ) {
                    sender.sendMessage( Utils.getColored( "&7Attempting to reload &6Netuno&7..." ) );
                    Utils.logInfo( "Attempting to reload Netuno..." );

                    if ( ConfigUtils.getConfigManager().getConfigFile().exists() == false || ConfigUtils.getConfigManager().getConfigFile() == null ) {
                        Utils.logInfo( "Found no config file, recreating it..." );
                        ConfigUtils.getConfigManager().saveDefaultConfig();
                    }

                    if ( PunishGUIUtils.getPunishGUIManager().getConfigFile().exists() == false || PunishGUIUtils.getPunishGUIManager().getConfigFile() == null ) {
                        Utils.logInfo( "Found no punishgui file, recreating it..." );
                        PunishGUIUtils.getPunishGUIManager().saveDefaultConfig();
                    }

                    ConfigUtils.getConfigManager().reloadConfig();
                    PunishGUIUtils.getPunishGUIManager().reloadConfig();

                    sender.sendMessage( Utils.getColored( "&7Successfully reloaded &6Netuno" ) );
                    Utils.logInfo( "Successfully reloaded Netuno and its files" );
                }

                else {
                    CommandErrors.sendInvalidPerms( sender );
                }
            }

            else if ( args[0].equalsIgnoreCase( "help" ) ) {
                if ( Utils.isOutOfBounds( args, 1 ) == false ) {
                    int page = 0;
                    try { page = Integer.parseInt( args[1] ) - 1; }
                    catch ( NumberFormatException e ) { sender.sendMessage( Utils.getColored( "&7Invalid page number!" ) ); }

                    if ( page >= 0 && page < Math.ceil( COMMAND_ORDER.length / 6.0 ) ) {
                        sendHelpMessage( sender, page * 6, page * 6 + 6 );
                    }

                    else {
                        sender.sendMessage( Utils.getColored( "&7Invalid page number!" ) );
                    }
                }

                else {
                    sendHelpMessage( sender, 0, 6 );
                }
            }

            else {
                sendHelpMessage( sender, 0, 6 );
            }
        }

        else {
            sendHelpMessage( sender, 0, 6 );
        }

        return true;
    }

    private void sendHelpMessage( CommandSender sender, int start, int end ) {
        String toSend = "\n";
        for ( int index = start; index < end; index++ ) {
            toSend += CommandErrors.getCommandUsage( COMMAND_ORDER[index] ) + "\n";
        }
        toSend += "\n";
        Utils.sendAnyMsg( sender, toSend );
    }
}
