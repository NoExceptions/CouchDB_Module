package com.dm.couchdb;


import com.dm.couchdb.web.records.LCSettingsRecord;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.Nullable;
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

    private @Nullable LightCouch getConnection(String connection) {

            LightCouch lc = null;
            SQuery<LCSettingsRecord> query = new SQuery<>(LCSettingsRecord.META).eq(LCSettingsRecord.LCConnectionName, connection);
            List<LCSettingsRecord> cons = gc.getPersistenceInterface().query(query);
            if (cons.size() > 0) {
                log.trace("getData: Justo found a connection named: " + connection);
                lc = new LightCouch(cons.get(0));
            } else {
                log.error("No connection found with name: " + connection);

            }
            return lc;

    }

    @Override
    public PyDictionary getData(String connection, String mangoQuery)  {
        PyDictionary fans = new PyDictionary();
        List<PyDictionary> ans;
        try {
            if(log.isTraceEnabled()){
                log.trace("getData | Executing mango query: "+mangoQuery);
            }
            LightCouch lc = getConnection(connection);
            ans = lc.cdb.findDocs(mangoQuery, PyDictionary.class);
            fans.put("response", ans);

        } catch (CouchDbException ex) {
            log.error(ex);

        }
        finally {
            return fans;
        }
    }

    @Override
    public PyDictionary getDataDict(String connection, PyDictionary mangoQuery)   {

        PyDictionary fans = new PyDictionary();


        List<PyDictionary> ans;
        try {
            LightCouch lc = getConnection(connection);
            if(log.isTraceEnabled()){
                log.trace("getDataDict | Executing mango query: "+mangoQuery.toString().replace("'", "\""));
            }
            ans = lc.cdb.findDocs(mangoQuery.toString().replace("'", "\""), PyDictionary.class);
            fans.put("response", ans);
            return fans;
        } catch (CouchDbException ex) {
            log.error(ex);
            throw ex;
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
    }

    @Override
    public PyDictionary saveData(String connection, PyDictionary data)  {

        try {
            LightCouch lc = getConnection(connection);
            Response resp = lc.cdb.save(data);

            return reponseHandler(resp);

        } catch (CouchDbException ex) {
            log.error(ex);
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }
    @Override
    public PyDictionary updateData(String connection, PyDictionary data)   {

        try {
            LightCouch lc = getConnection(connection);
            Response resp = lc.cdb.update(data);
            return reponseHandler(resp);
        } catch (CouchDbException ex) {
            log.error(ex);
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PyDictionary removeData(String connection, String _id, String rev)  {

        try {
            LightCouch lc = getConnection(connection);
            Response resp = lc.cdb.remove(_id, rev);
            return reponseHandler(resp);
        } catch (CouchDbException ex) {
            log.error(ex);
            throw ex;
        } catch (Exception e) {
            throw e;
        }
    }
}