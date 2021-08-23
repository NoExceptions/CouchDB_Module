package com.dm.couchdb;


import com.inductiveautomation.ignition.client.gateway_interface.ModuleRPCFactory;
import org.python.core.PyDictionary;

public class ClientScriptModule extends AbstractCouchScripts {

    private final CouchDBFunctions rpc;

    public ClientScriptModule() {
        rpc = ModuleRPCFactory.create(
                "com.dm.couchdb",
                CouchDBFunctions.class
        );
    }

    @Override
    protected  PyDictionary saveDataImpl(String conn, PyDictionary data) {
        try {

            return rpc.saveData(conn, data);
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    protected  PyDictionary removeDataImpl(String connection, String _id, String _rev) {
        try {

            return rpc.removeData(connection, _id, _rev);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    protected  PyDictionary getDataImpl(String conn, String mangoQuery) {
        try {
            return rpc.getData(conn, mangoQuery);
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    protected PyDictionary getDataDictImpl(String ConnectionName, PyDictionary MangoQuery) {
        try {

            return rpc.getDataDict(ConnectionName, MangoQuery);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    protected  PyDictionary updateDataImpl(String conn, PyDictionary data) {
        try {

            return rpc.updateData(conn, data);
        } catch (Exception e) {
            throw e;
        }

    }


}
