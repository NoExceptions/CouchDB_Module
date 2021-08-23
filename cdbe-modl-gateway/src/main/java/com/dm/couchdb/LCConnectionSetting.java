package com.dm.couchdb;

import com.inductiveautomation.ignition.gateway.localdb.persistence.PersistentRecord;
import com.inductiveautomation.ignition.gateway.localdb.persistence.RecordMeta;

public class LCConnectionSetting extends PersistentRecord {

    public static final RecordMeta<LCConnectionSetting> META = new RecordMeta<>(LCConnectionSetting.class, "LCConnectionSetting");

    @Override
    public RecordMeta<?> getMeta() {
        return META;
    }
}