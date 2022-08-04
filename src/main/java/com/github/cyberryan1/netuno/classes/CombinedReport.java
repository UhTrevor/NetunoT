package com.github.cyberryan1.netuno.classes;

import com.github.cyberryan1.cybercore.utils.CoreGUIUtils;
import com.github.cyberryan1.cybercore.utils.CoreUtils;
import com.github.cyberryan1.netuno.api.ApiNetuno;
import com.github.cyberryan1.netuno.guis.utils.GUIUtils;
import com.github.cyberryan1.netunoapi.models.reports.NReport;
import com.github.cyberryan1.netunoapi.models.time.NDate;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CombinedReport {

    private OfflinePlayer target;
    private List<NReport> reports;
    private long mostRecentDate;

    public CombinedReport( OfflinePlayer target ) {
        this.target = target;

        reports = ApiNetuno.getData().getNetunoReports().getReports( target );
        if ( reports.size() > 0 ) {
            mostRecentDate = reports.get( 0 ).getTimestamp();
            for ( NReport nr : reports ) {
                if ( nr.getTimestamp() > mostRecentDate ) { mostRecentDate = nr.getTimestamp(); }
            }
        }
    }

    public OfflinePlayer getTarget() { return target; }

    public List<NReport> getAllReports() { return reports; }

    public long getMostRecentDate() { return mostRecentDate; }

    public ItemStack getAsItem() {
        ItemStack skull = GUIUtils.getPlayerSkull( target );

        skull = CoreGUIUtils.setItemName( skull, "&p" + target.getName() );

        //      Reason, Amount
        HashMap<String, Integer> reasonAmount = new HashMap<>();
        for ( NReport nr : reports ) {
            if ( reasonAmount.containsKey( nr.getReason() ) ) {
                int amount = reasonAmount.get( nr.getReason() ) + 1;
                reasonAmount.replace( nr.getReason(), amount );
            }
            else {
                reasonAmount.put( nr.getReason(), 1 );
            }
        }

        List<String> reasons = new ArrayList<>();
        for ( String rea : reasonAmount.keySet() ) {
            int amount = reasonAmount.get( rea );
            reasons.add( CoreUtils.getColored( " &8- &p" + amount + "x &s" + rea ) );
        }

        ArrayList<String> lore = new ArrayList<>();
        lore.add( CoreUtils.getColored( "&pDate: &s" + new NDate( mostRecentDate ).getDateString() ) );
        lore.add( CoreUtils.getColored( "&pReason(s):" ) );
        lore.addAll( reasons );

        return CoreGUIUtils.setItemLore( skull, lore );
    }

}
