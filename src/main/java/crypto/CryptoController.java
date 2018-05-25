package crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/crypto")
public class CryptoController {

	@Autowired
	private CryptoCurrencyRepository cryptoCurrencyRepository;
	
	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewCryptoCurrency (@RequestParam String name
			, @RequestParam int rank, @RequestParam String price_usd, @RequestParam String price_btc) {
		
		// TODO: only add if not already existing
		CryptoCurrency crypto = new CryptoCurrency();
		crypto.setName(name);
		crypto.setRank(rank);
		crypto.setPrice_usd(Float.parseFloat(price_usd));
		crypto.setPrice_btc(Float.parseFloat(price_btc));
		cryptoCurrencyRepository.save(crypto);
		return "Saved";
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<CryptoCurrency> getAllUsers() {
		// This returns a JSON or XML with the users
		return cryptoCurrencyRepository.findAll();
	}
}
