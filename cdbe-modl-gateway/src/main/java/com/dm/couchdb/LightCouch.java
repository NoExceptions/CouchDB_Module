package com.dm.couchdb;

import com.dm.couchdb.web.records.LCSettingsRecord;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbException;
import org.lightcouch.DocumentConflictException;
import org.lightcouch.Response;
import org.python.core.*;

import java.util.Map;

public class LightCouch {
    public CouchDbClient cdb;
    private String Node, Database, User, Password;
    private Integer Port;

    public LightCouch(String node, String database, String user, String password, Integer port, Boolean isSecure) {
        Node = node;
        Database = database;
        User = user;
        Password = password;
        Port = port;
        String Protocol = isSecure ? "Https" : "Http";
        cdb = new CouchDbClient(Database, true, Protocol, Node, Port, User, Password);

    }

    public LightCouch(LCSettingsRecord lcr) {
        Node = lcr.getLCNodeName();
        Database = lcr.getLCDataBase();
        User = lcr.getLCUserName();
        Password = lcr.getLCPassword();
        Port = lcr.getLCPortNum();
        String Protocol = lcr.getisSec() ? "Https" : "Http";
        cdb = new CouchDbClient(Database, true, Protocol, Node, Port, User, Password);

    }

    public LightCouch(PyDictionary dict) {
        try {
            Node = (String) dict.get("node");
            Database = (String) dict.get("database");
            User = (String) dict.get("user");
            ;
            Password = (String) dict.get("password");
            Port = (Integer) dict.get("port");
            Boolean isSecure = (Boolean) dict.get("is_secure");
            String Protocol = isSecure ? "Https" : "Http";
            cdb = new CouchDbClient(Database, true, Protocol, Node, Port, User, Password);
        } catch (Exception e) {
            throw e;
        }

    }

    public static PyDictionary MapToPyDict(Map<String, Object> map) throws Exception {
        PyDictionary myDict = new PyDictionary();
        for (Map.Entry<String, Object> kvp : map.entrySet()) {
            String key = kvp.getKey();
            Object value = kvp.getValue();

            if (value == null) {
                //skip value
            } else if (value instanceof String) {
                myDict.put(key, new PyUnicode((String) value.toString()));
            } else if (value instanceof Long) {
                myDict.put(key, new PyLong((Long) value));
            } else if (value instanceof Float) {
                myDict.put(key, new PyFloat((Float) value));
            } else if (value instanceof Double) {
                myDict.put(key, new PyFloat((Double) value));
            } else if (value instanceof Integer) {
                myDict.put(key, new PyInteger((Integer) value));
            } else {
                throw new IllegalArgumentException("Unexpected value type: " + value.toString());
            }
        }
        return myDict;
    }

    public Response save(PyDictionary doc) {

        try {
            return cdb.save(doc);
        } catch (DocumentConflictException dex) {
            throw dex;

        } catch (CouchDbException cdbe) {
            throw cdbe;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
