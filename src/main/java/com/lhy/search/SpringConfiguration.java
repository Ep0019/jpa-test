package com.lhy.search;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SpringConfiguration {
	/*public @Bean
		JestClient jestClient() {
		String connectionUrl = "http://localhost:9200";
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig
		       .Builder(connectionUrl)
		       .multiThreaded(true)
		       .build());
		JestClient client = factory.getObject();
		return client;
	}*/
	public @Bean
	Client client() {
		Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.sniff", false).put("cluster.name", "elasticsearch").build();  
		TransportClient transportClient= new TransportClient(settings);
		Client client=transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300)); 
		return client;
	}
}
