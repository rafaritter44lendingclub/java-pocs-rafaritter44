package com.github.rafaritter44.java_pocs.spring.security.authorization_server.config;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.services.managers.ApplianceBootstrap;
import org.keycloak.services.managers.RealmManager;
import org.keycloak.services.resources.KeycloakApplication;
import org.keycloak.services.util.JsonConfigProviderFactory;
import org.keycloak.util.JsonSerialization;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.github.rafaritter44.java_pocs.spring.security.authorization_server.config.KeycloakServerProperties.AdminUser;

public class EmbeddedKeycloakApplication extends KeycloakApplication {
	
	private static KeycloakServerProperties keycloakServerProperties;
	
	public EmbeddedKeycloakApplication() {
		final KeycloakSession session = getSessionFactory().create();
		final ApplianceBootstrap applianceBootstrap = new ApplianceBootstrap(session);
		final AdminUser admin = keycloakServerProperties.getAdminUser();
		try {
			session.getTransactionManager().begin();
			applianceBootstrap.createMasterRealmUser(admin.getUsername(), admin.getPassword());
			final RealmManager manager = new RealmManager(session);
			final Resource realmImportFile = new ClassPathResource(keycloakServerProperties.getRealmImportFile());
			manager.importRealm(JsonSerialization.readValue(realmImportFile.getInputStream(), RealmRepresentation.class));
			session.getTransactionManager().commit();
		} catch (Exception e) {
			session.getTransactionManager().rollback();
		}
		session.close();
	}
	
	@Override
	protected void loadConfig() {
		final JsonConfigProviderFactory factory = new RegularJsonConfigProviderFactory();
		Config.init(factory.create().orElseThrow(() -> new RuntimeException("Error creating ConfigProvider")));
	}

	public static KeycloakServerProperties getKeycloakServerProperties() {
		return keycloakServerProperties;
	}

	public static void setKeycloakServerProperties(KeycloakServerProperties keycloakServerProperties) {
		EmbeddedKeycloakApplication.keycloakServerProperties = keycloakServerProperties;
	}
	
}
