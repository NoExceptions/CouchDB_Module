package br.com.fgltda.couchdb;

import org.python.core.PyDictionary;

public interface CouchDBFunctions {
    PyDictionary getData(String ConnectionName, String mangoQuery) ;

    PyDictionary getDataDict(String ConnectionName, PyDictionary mangoQuery) ;

    PyDictionary saveData(String ConnectionName, PyDictionary Data) ;

    PyDictionary updateData(String ConnectionName, PyDictionary Data) ;

    PyDictionary removeData(String ConnectionName, String Id, String Data) ;

}
