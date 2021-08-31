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
    protected  PyDictionary saveDataImpl(String conn, PyDictionary data)  {

        return rpc.saveData(conn, data);

    }

    @Override
    protected  PyDictionary removeDataImpl(String connection, String _id, String _rev)  {
        return rpc.removeData(connection, _id, _rev);
    }

    @Override
    protected  PyDictionary getDataImpl(String conn, String mangoQuery) {
        return rpc.getData(conn, mangoQuery);

    }

    @Override
    protected PyDictionary getDataDictImpl(String ConnectionName, PyDictionary MangoQuery) {

        return rpc.getDataDict(ConnectionName, MangoQuery);
    }

    @Override
    protected  PyDictionary updateDataImpl(String conn, PyDictionary data)  {

        return rpc.updateData(conn, data);

    }


}
