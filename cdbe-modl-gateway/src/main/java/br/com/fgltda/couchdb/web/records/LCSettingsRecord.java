package br.com.fgltda.couchdb.web.records;

import com.inductiveautomation.ignition.gateway.localdb.persistence.*;

import com.inductiveautomation.ignition.gateway.web.components.editors.PasswordEditorSource;
import simpleorm.dataset.SFieldFlags;

public class LCSettingsRecord extends PersistentRecord {


    public static final RecordMeta<LCSettingsRecord> META = new RecordMeta<LCSettingsRecord>(
            LCSettingsRecord.class, "LCSettingsRecord").setNounKey("LCSettingsRecord.Noun").setNounPluralKey(
            "LCSettingsRecord.Noun.Plural");
    public static final IdentityField Id = new IdentityField(META, "Id");


    //Setting per lightcouch connection
    public static final StringField LCConnectionName = new StringField(META, "ConnectionName", SFieldFlags.SMANDATORY, SFieldFlags.SDESCRIPTIVE);
    public static final StringField LCNodeName = new StringField(META, "NodeName", SFieldFlags.SMANDATORY);
    public static final StringField LCDataBase = new StringField(META, "DBName", SFieldFlags.SMANDATORY);
    public static final IntField LCPortNum = new IntField(META, "PortNumber", SFieldFlags.SMANDATORY);
    public static final StringField LCUserName = new StringField(META, "User", SFieldFlags.SMANDATORY);
    public static final EncodedStringField LCPassword = new EncodedStringField(META, "Password", SFieldFlags.SMANDATORY);
    public static final BooleanField LCisSecure = new BooleanField(META, "isSecure", SFieldFlags.SMANDATORY);

    private static final PasswordEditorSource PASSWORD_EDITOR_SOURCE = null;

    static { LCPassword.getFormMeta().setEditorSource(PASSWORD_EDITOR_SOURCE.getSharedInstance()); }
    public Long getId() {
        return getLong(Id);
    }

    public void setId(Long id) {
        setLong(Id, id);
    }

    public String getLCConnectionName() {
        return getString(LCConnectionName);
    }

    public void setLCConnectionName(String name) {
        setString(LCConnectionName, name);
    }

    public String getLCNodeName() {
        return getString(LCNodeName);
    }

    public void setLCNodeName(String name) {
        setString(LCNodeName, name);
    }

    public String getLCDataBase() {
        return getString(LCDataBase);
    }

    public void setLCDataBase(String name) {
        setString(LCDataBase, name);
    }

    public Integer getLCPortNum() {
        return getInt(LCPortNum);
    }

    public void setLCPortNum(Integer port) {
        setInt(LCPortNum, port);
    }

    public String getLCUserName() {
        return getString(LCUserName);
    }

    public void setLCUserName(String name) {
        setString(LCUserName, name);
    }

    public String getLCPassword() {
        return getString(LCPassword);
    }

    public void setLCPassword(String pass) {
        setString(LCPassword, pass);
    }

    public Boolean getisSec() {
        return getBoolean(LCisSecure);
    }

    public void setisSec(Boolean sec) {
        setBoolean(LCisSecure, sec);
    }


    @Override
    public RecordMeta<?> getMeta() {
        return META;
    }
}
