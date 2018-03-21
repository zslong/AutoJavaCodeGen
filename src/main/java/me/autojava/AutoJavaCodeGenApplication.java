package me.autojava;

import me.autojava.generator.DealGen;
import me.autojava.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class AutoJavaCodeGenApplication implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger("DealCodeGenApplication");

	@Resource
	private DealGen dealGen;

	public static void main(String[] args) {
		SpringApplication.run(AutoJavaCodeGenApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		dealGen.generateBaseDeal();
		logger.info("generated base deal...");
		for (Product product : Product.values()) {
			dealGen.generateProductDeal(product);
			logger.info("generated deal of product {}...", product.name());
		}
		dealGen.writeToFile();
	}
}
