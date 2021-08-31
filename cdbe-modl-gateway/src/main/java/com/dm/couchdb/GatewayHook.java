package com.dm.couchdb;

import com.dm.couchdb.web.LCSettingsPage;
import com.dm.couchdb.web.records.LCSettingsRecord;
import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.common.script.ScriptManager;
import com.inductiveautomation.ignition.common.script.hints.PropertiesFileDocProvider;
import com.inductiveautomation.ignition.gateway.clientcomm.ClientReqSession;
import com.inductiveautomation.ignition.gateway.localdb.persistence.IRecordListener;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.web.models.ConfigCategory;
import com.inductiveautomation.ignition.gateway.web.models.DefaultConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.IConfigTab;
import com.inductiveautomation.ignition.gateway.web.models.KeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleorm.dataset.SQuery;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GatewayHook extends AbstractGatewayModuleHook {

    public static final ConfigCategory CONFIG_CATEGORY =
            new ConfigCategory("couchdb", "couchdb.nav.header", 700);

    public static final IConfigTab HCE_CONFIG_ENTRY = DefaultConfigTab.builder()
            .category(CONFIG_CATEGORY)
            .name("couchdb")
            .i18n("couchdb.nav.settings.title")
            .page(LCSettingsPage.class)
            .terms("couchdb connect settings")
            .build();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private GatewayContext context;
    private GatewayScriptModule scriptModule = new GatewayScriptModule(this.context);

    @Override
    public void startup(LicenseState licenseState) {
        logger.info("startup()");
        BundleUtil.get().addBundle("couchdb", getClass(), "couchdb");
    }

    @Override
    public void shutdown() {
        BundleUtil.get().removeBundle("couchdb");
        logger.info("shutdown()");
    }

    @Override
    public void initializeScriptManager(ScriptManager manager) {
        super.initializeScriptManager(manager);

        manager.addScriptModule(
                "system.couchdb",
                scriptModule,
                new PropertiesFileDocProvider());
    }

    @Override
    public List<ConfigCategory> getConfigCategories() {
        return Collections.singletonList(CONFIG_CATEGORY);
    }

    @Override
    public List<? extends IConfigTab> getConfigPanels() {
        return Collections.singletonList(
                HCE_CONFIG_ENTRY
        );
    }


    @Override
    public void setup(GatewayContext gatewayContext) {
        this.context = gatewayContext;
        try {
            context.getSchemaUpdater().updatePersistentRecords(LCSettingsRecord.META);
        } catch (Exception e) {
            logger.error("Failed to setup persistence Record ", e);
        }
        logger.info("setup()");
        logger.debug("Beginning setup of couchdb Module");
        //maybeCreateHCSettings(this.context);
        // Register GatewayHook.properties by registering the GatewayHook.class with BundleUtils
        BundleUtil.get().addBundle("couchdb", getClass(), "couchdb");

        // listen for updates to the settings record...
        LCSettingsRecord.META.addRecordListener(new IRecordListener<LCSettingsRecord>() {
            @Override
            public void recordUpdated(LCSettingsRecord lcSettingsRecord) {
                logger.info("recordUpdated()");
            }

            @Override
            public void recordAdded(LCSettingsRecord lcSettingsRecord) {
                logger.info("recordAdded()");
            }

            @Override
            public void recordDeleted(KeyValue keyValue) {
                logger.info("recordDeleted()");
            }
        });

        scriptModule = new GatewayScriptModule(this.context);
        logger.debug("Setup Complete.");
    }

    @Override
    public Optional<String> getMountPathAlias() {
        return Optional.of("hce");
    }

    // Use this whenever you have mounted resources
    @Override
    public Optional<String> getMountedResourceFolder() {
        return Optional.of("mounted");
    }


    @Override
    public Object getRPCHandler(ClientReqSession session, String projectName) {
        return scriptModule;
    }

    public List<LCSettingsRecord> getConnData(String con_name) {
        SQuery<LCSettingsRecord> query = new SQuery<LCSettingsRecord>(LCSettingsRecord.META).eq(LCSettingsRecord.LCConnectionName, con_name);
        return this.context.getPersistenceInterface().query(query);
    }
}
