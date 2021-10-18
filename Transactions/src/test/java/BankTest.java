import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class BankTest extends TestCase {

    private Bank bank;
    private Map<String, Account> accounts;

    private Account a1;
    private Account a2;
    private Account a3;
    private Account a4;
    private Account a5;
    private Account a6;

    @Override
    @After
    public void setUp() throws Exception{

        bank = new Bank();
        accounts = new HashMap<>();

        a1 = new Account(10_000, "1");
        a2 = new Account(20_000, "2");
        a3 = new Account(30_000, "3");
        a4 = new Account(40_000, "4");
        a5 = new Account(50_000, "5");
        a6 = new Account(60_000, "6");

        //accounts.clear();

        accounts.put("1", a1);
        accounts.put("2", a2);
        accounts.put("3", a3);
        accounts.put("4", a4);
        accounts.put("5", a5);
        accounts.put("6", a6);

        bank.setAccounts(accounts);

    }
    @Before
    public void clearBank() {
        bank = null;
    }

    @Test
    public void testTransferOneThread() throws NullPointerException{
        bank.transfer("1", "2", 1000);
        long actualFrom = a1.getMoney();
        long expectedFrom = 9000;
        long actualTo = a2.getMoney();
        long expectedTo = 21000;
        assertEquals(expectedFrom, actualFrom);
        assertEquals(expectedTo, actualTo);
    }

    @Test
    public void testTransferTooMuch() throws InterruptedException {
        bank.transfer("1", "2", 100000);
        long actualFrom = a1.getMoney();
        long expectedFrom = 10000;
        long actualTo = a2.getMoney();
        long expectedTo = 20000;
        assertEquals(expectedFrom, actualFrom);
        assertEquals(expectedTo, actualTo);
    }
}
