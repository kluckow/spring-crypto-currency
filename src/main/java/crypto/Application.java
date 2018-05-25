package crypto;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	private CryptoCurrencyRepository cryptoCurrencyRepository;
	
	public static void main(String args[]) {
		SpringApplication.run(Application.class);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {

			Timer timer = new Timer ();
			TimerTask hourlyTask = new TimerTask () {
			    @Override
			    public void run () {
					
					ResponseEntity<CryptoCurrency[]> responseEntity = restTemplate.getForEntity("https://api.coinmarketcap.com/v1/ticker/?limit=10", CryptoCurrency[].class);
					CryptoCurrency[] cryptos = responseEntity.getBody();
					HttpStatus statusCode = responseEntity.getStatusCode();
					for (CryptoCurrency crypto : cryptos) {
						log.info("######################### STATUS CODE: " + statusCode);
						log.info("######################### CRYPTO NAME: " + crypto.getName());
						log.info("######################### CRYPTO RANK: " + crypto.getRank());
						log.info("######################### CRYPTO PRICE USD: " + crypto.getPrice_usd());
						log.info("######################### CRYPTO PRICE BTC: " + crypto.getPrice_btc());
						log.info("######################### LOG END");
						
						// http://localhost:8080/crypto/add?name=euro&rank=1&price_usd=0.7&price_btc=20000
						
						CryptoCurrency newCrypto = new CryptoCurrency();
						newCrypto.setName(crypto.getName());
						newCrypto.setRank(crypto.getRank());
						newCrypto.setPrice_usd(crypto.getPrice_usd());
						newCrypto.setPrice_btc(crypto.getPrice_btc());
						cryptoCurrencyRepository.save(newCrypto);
						
					}
			        
			    }
			};

			// TODO: make configurable
			timer.schedule (hourlyTask, 0l, 1000*60*60); // 1h
//			timer.schedule (hourlyTask, 0l, 1000*5); // 5sec 
		};
	}
}