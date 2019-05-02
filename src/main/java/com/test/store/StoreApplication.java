package com.test.store;

import com.test.store.conroller.web.FixerRequest;
import com.test.store.model.domain.FixerResponceEntity;
import com.test.store.model.domain.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
