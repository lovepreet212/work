package ca.jrvs.apps.trading.DAO;

import ca.jrvs.apps.trading.MarketDataConfig;
import ca.jrvs.apps.trading.Models.IexQuote;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MarketDataDaoTest {
    private MarketDataDao dao;
    @Before
    public void init() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
        dao = new MarketDataDao(cm, marketDataConfig);
    }

    @Test
    public void findIexQuotesByTickers() throws IOException {
        List<IexQuote> quoteList = (List<IexQuote>) dao.findAllById(Arrays.asList("AAPL", "FB"));
        assertEquals(2, quoteList.size());
        assertEquals("AAPL", quoteList.get(0).getSymbol());

        try {
            dao.findAllById(Arrays.asList("AAPL", "FB2"));
            fail();
        } catch(IllegalArgumentException e) {
            assertTrue(true);
        } catch(Exception e) {
            fail();
        }
    }
    @Test
    public void findByTickers() throws IOException {
        String ticker = "AAPL";
        IexQuote iexQuote = dao.findById(ticker).get();
        assertEquals(ticker, iexQuote.getSymbol());
    }
}