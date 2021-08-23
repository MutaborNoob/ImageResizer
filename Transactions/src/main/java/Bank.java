import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {

    private Map<String, Account> accounts;

    private ArrayList<Account> accounts2;


    private ArrayList<Account> blockedAccounts;
    private final Random random = new Random();

    public Bank(){
        accounts = new HashMap<>();
        generateAccountList();
        accounts2 = new ArrayList<Account>(accounts.values());
        blockedAccounts = new ArrayList<>();
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами. Если сумма транзакции > 50000,
     * то после совершения транзакции, она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка счетов (как – на ваше
     * усмотрение)
     */
    public synchronized void transfer(String fromAccountNum, String toAccountNum, long amount)
    {
        if (amount > 50_000)
        {
            try
            {
                if (isFraud(fromAccountNum, toAccountNum, amount))
                {
                    System.out.println("Cash transactions on accounts number "
                            + accounts.get(fromAccountNum).getAccNumber() +
                            " and " + accounts.get(toAccountNum).getAccNumber() +
                            " seem suspicious. Accounts blocked pending clarification");

                    accounts.get(fromAccountNum).setBlocked();
                    accounts.get(toAccountNum).setBlocked();

                }
                else
                    {
                        if (accounts.get(fromAccountNum).checkMoney(amount))
                        {
                            accounts.get(fromAccountNum).spending(amount);//забираем деньги с одного счета
                            accounts.get(toAccountNum).replenishment(amount);//пополняем второй счет
                        }
                        else
                            {
                            System.out.println( accounts.get(fromAccountNum).getAccNumber() + " not enough funds to write off");
                            }
                    }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }else {
            //System.out.println("до траты " + accounts.get(fromAccountNum).getMoney());
            accounts.get(fromAccountNum).spending(amount);//забираем деньги с одного счета
            //System.out.println("после траты " + accounts.get(fromAccountNum).getMoney());
            accounts.get(toAccountNum).replenishment(amount);//пополняем второй счет
        }

    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    public long getSumAllAccounts() {
        //accounts.values().stream().mapToLong(Account::getMoney).sum();
        return accounts2.stream().mapToLong(Account::getMoney).sum();
    }

    public ArrayList<Account> getBlockedAccounts() {
        return blockedAccounts;
    }

    public void transfer(Account fromAccountNum, Account toAccountNum, long amount){
        //fromAccountNum.spending(amount);
        //toAccountNum.replenishment(amount);
        fromAccountNum.spending(amount);
        toAccountNum.replenishment(amount);
    }

    public Map<String, Account> generateAccountList(){
        int countOfAccount = (int) (Math.random() * 100 + 10);
        long moneyOfAccount;
        for (int i = 0; i <= countOfAccount; i++){
            moneyOfAccount = (long)(Math.random() * 100_000 + 40_000);
            accounts.put(Integer.toString(i), new Account(moneyOfAccount, Integer.toString(i)));
        }
        return accounts;
    }

    public ArrayList<Account> getAccounts2() {
        return accounts2;
    }

    public void runThread(){

        //Lock lock1 = new ReentrantLock();
        //Lock lock2 = new ReentrantLock();

        //Lock lock1 = new ReentrantLock();
        //Lock lock2 = new ReentrantLock();
        //lock1.lock();
        //lock2.lock();
        //try {
            long money = (long) (Math.random() * 50_000 + 1000);

            int intId =(int) (Math.random() * accounts.size());
            int int2Id =(int) (Math.random() * accounts.size());
            while (intId == int2Id){
                intId =(int) (Math.random() * accounts.size());
            }
            synchronized (Integer.toString(intId)){
                synchronized (Integer.toString(int2Id)){
                    transfer(Integer.toString(intId), Integer.toString(int2Id), money);
                }
            }
            //transfer(Integer.toString(intId), Integer.toString(int2Id), money);
        //}finally {
            //lock1.unlock();
            //lock2.unlock();
        //}

    }

}
