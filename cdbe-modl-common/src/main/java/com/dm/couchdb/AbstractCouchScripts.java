package com.dm.couchdb;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.script.hints.ScriptArg;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;
import org.python.core.PyDictionary;

public abstract class AbstractCouchScripts implements CouchDBFunctions {
    static {
        BundleUtil.get().addBundle(
                AbstractCouchScripts.class.getSimpleName(),
                AbstractCouchScripts.class.getClassLoader(),
                AbstractCouchScripts.class.getName().replace('.', '/')
        );
    }

    @Override
    @ScriptFunction(docBundlePrefix = "AbstractCouchScripts")
    public PyDictionary getData(
            @ScriptArg("ConnectionName") String ConnectionName,
            @ScriptArg("MangoQuery") String MangoQuery)  {
        return getDataImpl(ConnectionName, MangoQuery) ;

    }

    protected abstract PyDictionary getDataImpl( String ConnectionName, String MangoQuery) ;

    @Override
    @ScriptFunction(docBundlePrefix = "AbstractCouchScripts")
    public PyDictionary getDataDict(
            @ScriptArg("ConnectionName") String ConnectionName,
            @ScriptArg("MangoQuery") PyDictionary MangoQuery) {
        return getDataDictImpl(ConnectionName, MangoQuery);
    }

    protected abstract PyDictionary getDataDictImpl( String ConnectionName, PyDictionary MangoQuery) ;

    @Override
    @ScriptFunction(docBundlePrefix = "AbstractCouchScripts")
    public PyDictionary saveData(
            @ScriptArg("ConnectionName") String ConnectionName,
            @ScriptArg("Data") PyDictionary Data) {
        return saveDataImpl(ConnectionName, Data);
    }

    protected abstract PyDictionary saveDataImpl( String ConnectionName, PyDictionary Data);

    @Override
    @ScriptFunction(docBundlePrefix = "AbstractCouchScripts")
    public PyDictionary updateData(
            @ScriptArg("ConnectionName") String ConnectionName,
            @ScriptArg("Data") PyDictionary Data){
        return updateDataImpl(ConnectionName, Data);
    }

    protected abstract PyDictionary updateDataImpl( String ConnectionName, PyDictionary Data);

    @Override
    @ScriptFunction(docBundlePrefix = "AbstractCouchScripts")
    public PyDictionary removeData(
            @ScriptArg("ConnectionName") String ConnectionName,
            @ScriptArg("Id") String Id,
            @ScriptArg("Rev") String Rev){

        return removeDataImpl(ConnectionName, Id, Rev) ;
    }


    protected abstract PyDictionary removeDataImpl( String ConnectionName, String Id,String Rev);

}
