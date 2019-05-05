package com.test.store;

import com.test.store.web.FixerRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
//		FixerRequest<FixerResponceEntity> fr = new FixerRequest<>();
//		Map<String, String> ss = new HashMap<>();
//		ss.put("access_key", "ad2d0508d1b0963413b98ccf22225399");
//		FixerResponceEntity fe = fr.GET(ss, FixerResponceEntity.class);
//		String r = fe.rates.keySet().stream()
//				.collect(Collectors.joining(", "));
//		System.out.println(r);
		SpringApplication.run(StoreApplication.class, args);
	}

}
