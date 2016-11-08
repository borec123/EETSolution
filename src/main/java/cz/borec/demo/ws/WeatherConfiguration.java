/*package cz.borec.demo.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class WeatherConfiguration {

	@Bean
	public static Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("xsd/EETXMLSchema.xsd");
		return marshaller;
	}

	@Bean
	public FIClient weatherClient(Jaxb2Marshaller marshaller) {
		FIClient client = new FIClient();
		client.setDefaultUri("http://ws.cdyne.com/WeatherWS");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}
*/