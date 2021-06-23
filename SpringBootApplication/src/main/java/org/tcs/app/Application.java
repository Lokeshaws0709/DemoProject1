package org.tcs.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@SpringBootApplication
public class Application {

	@Autowired
	DataSource datasource;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean("mySqlEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getEntityManager() throws Exception {
		LocalContainerEntityManagerFactoryBean localEntityMgr = new LocalContainerEntityManagerFactoryBean();
		localEntityMgr.setPackagesToScan(new String[] { "org.tcs.app" });
		localEntityMgr.setDataSource(datasource);
		localEntityMgr.setPersistenceUnitName("mySqlDb");
		localEntityMgr.setJpaVendorAdapter(getJpaVendorAdapter());
		return localEntityMgr;
	}
/*
	public JpaTransactionManager getJpatransactionmanager() throws Exception {
		JpaTransactionManager jtxn = new JpaTransactionManager();
		jtxn.setEntityManagerFactory(getEntityManager().getObject());
		return jtxn;
	}
*/
	public JpaVendorAdapter getJpaVendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(true);
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setDatabase(Database.MYSQL);
		return vendorAdapter;
	}
}
