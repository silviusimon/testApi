package com.testapi.testapi;

import com.testapi.configuration.AccountJpaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The Account service application.
 */
@Import(AccountJpaConfig.class)
@SpringBootApplication(scanBasePackages = {"com.testapi.testapi", "com.testapi.configuration"})
@EnableJpaRepositories("com.testapi.testapi.repository")
@EnableCaching
public class AccountServiceApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

}
