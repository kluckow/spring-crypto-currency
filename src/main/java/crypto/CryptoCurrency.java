package crypto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class CryptoCurrency {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	
	private String name;
	
	private int rank;
	
	private float price_usd;
	
	private float price_btc;
	
	// TODO: add timestamp for time interval filtering

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public float getPrice_usd() {
		return price_usd;
	}

	public void setPrice_usd(float price_usd) {
		this.price_usd = price_usd;
	}

	public float getPrice_btc() {
		return price_btc;
	}

	public void setPrice_btc(float price_btc) {
		this.price_btc = price_btc;
	}
	
}
