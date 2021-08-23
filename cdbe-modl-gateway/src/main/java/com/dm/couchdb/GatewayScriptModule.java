package com.dm.couchdb;


import com.dm.couchdb.web.records.LCSettingsRecord;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.lightcouch.CouchDbException;
import org.lightcouch.Response;
import org.python.core.PyDictionary;
import simpleorm.dataset.SQuery;

import java.util.List;


public class GatewayScriptModule implements CouchDBFunctions {
    GatewayContext gc;
    Logger log = LogManager.getLogger("CouchDB.Requests");

    public GatewayScriptModule(GatewayContext gc) {
        this.gc = gc;
    }

    private PyDictionary reponseHandler(Response resp) {
        PyDictionary ans = new PyDictionary();
        ans.put("_Id", resp.getId());
        ans.put("_Rev", resp.getRev());
        ans.put("Error", resp.getError());
        ans.put("Reason", resp.getReason());
        return ans;
    }

    @Override
    public PyDictionary getData(String connection, String mangoQuery) {

        SQuery<LCSettingsRecord> query = new SQuery<>(LCSettingsRecord.META).eq(LCSettingsRecord.LCConnectionName, connection);
        List<LCSettingsRecord> cons = gc.getPersistenceInterface().query(query);

        LightCouch lc = new LightCouch(cons.get(0));
        PyDictionary fans = new PyDictionary();
        List<PyDictionary> ans;
        try {
            ans = lc.cdb.findDocs(mangoQuery, PyDictionary.class);
            fans.put("response", ans);
            return fans;
        } catch (CouchDbException ex) {
            log.error(ex);
            throw ex;
        } catch (Exception ex) {
            log.error(ex);
            throw ex;
        }
    }

    @Override
    public PyDictionary getDataDict(String connection, PyDictionary mangoQuery) {
        SQuery<LCSettingsRecord> query = new SQuery<>(LCSettingsRecord.META).eq(LCSettingsRecord.LCConnectionName, connection);
        List<LCSettingsRecord> cons = gc.getPersistenceInterface().query(query);
        PyDictionary fans = new PyDictionary();
        LightCouch lc = new LightCouch(cons.get(0));

        List<PyDictionary> ans;
        try {
            ans = lc.cdb.findDocs(mangoQuery.asString(), PyDictionary.class);
            fans.put("response", ans);
            return fans;
        } catch (CouchDbException ex) {
            log.error(ex);
            throw ex;
        } catch (Exception ex) {
            log.error(ex);
            throw ex;
        }
    }

    @Override
    public PyDictionary saveData(String connection, PyDictionary data) {
        SQuery<LCSettingsRecord> query = new SQuery<>(LCSettingsRecord.META).eq(LCSettingsRecord.LCConnectionName, connection);
        List<LCSettingsRecord> cons = gc.getPersistenceInterface().query(query);
        PyDictionary fans = new PyDictionary();
        LightCouch lc = new LightCouch(cons.get(0));
        try {
            Response resp = lc.cdb.save(data);

            return reponseHandler(resp);

        } catch (CouchDbException ex) {
            log.error(ex);
            throw ex;
        } catch (Exception ex) {
            log.error(ex);
            throw ex;
        }
    }
    @Override
    public PyDictionary updateData(String connection, PyDictionary data) {
        SQuery<LCSettingsRecord> query = new SQuery<>(LCSettingsRecord.META).eq(LCSettingsRecord.LCConnectionName, connection);
        List<LCSettingsRecord> cons = gc.getPersistenceInterface().query(query);
        PyDictionary fans = new PyDictionary();
        LightCouch lc = new LightCouch(cons.get(0));
        try {
            Response resp = lc.cdb.update(data);
            return reponseHandler(resp);
        } catch (CouchDbException ex) {
            log.error(ex);
            throw ex;
        } catch (Exception ex) {
            log.error(ex);
            throw ex;
        }
    }

    @Override
    public PyDictionary removeData(String connection, String _id, String rev) {
        SQuery<LCSettingsRecord> query = new SQuery<>(LCSettingsRecord.META).eq(LCSettingsRecord.LCConnectionName, connection);
        List<LCSettingsRecord> cons = gc.getPersistenceInterface().query(query);
        PyDictionary fans = new PyDictionary();
        LightCouch lc = new LightCouch(cons.get(0));
        try {
            Response resp = lc.cdb.remove(_id, rev);
            return reponseHandler(resp);
        } catch (CouchDbException ex) {
            log.error(ex);
            throw ex;
        } catch (Exception ex) {
            log.error(ex);
            throw ex;
        }
    }
}