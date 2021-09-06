package br.com.fgltda.couchdb.web;

import br.com.fgltda.couchdb.GatewayHook;
import br.com.fgltda.couchdb.web.records.LCSettingsRecord;
import com.inductiveautomation.ignition.gateway.localdb.persistence.RecordMeta;
import com.inductiveautomation.ignition.gateway.web.components.RecordActionTable;
import com.inductiveautomation.ignition.gateway.web.pages.IConfigPage;
import org.apache.commons.lang3.tuple.Pair;


public class LCSettingsPage extends RecordActionTable<LCSettingsRecord> {


    public LCSettingsPage(IConfigPage configPage) {
        super(configPage);
    }

    @Override
    protected RecordMeta<LCSettingsRecord> getRecordMeta() {

        return LCSettingsRecord.META;
    }


    @Override
    public Pair<String, String> getMenuLocation() {
        return Pair.of(GatewayHook.CONFIG_CATEGORY.getName(),
                "couchdb");
    }

    @Override
    protected String getTitleKey() {

        return "CouchDB Settings";
    }


}