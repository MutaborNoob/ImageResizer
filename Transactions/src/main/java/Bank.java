import java.util.*;

public class Bank {

    private Map<String, Account> accounts;

    private ArrayList<Account> accounts2;

    private ArrayList<Account> blockedAccounts;
    private final Random random = new Random();

    public Bank(){
        accounts = generateAccountList();
        //generateAccountList();
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
    private boolean checkAccount(final String fromAccountNum,final String toAccountNum){

        //boolean a = accounts2.stream().anyMatch(account -> account.getAccNumber().equals(fromAccountNum) && account.getAccNumber().equals(toAccountNum));
        return accounts2.stream().anyMatch(account -> account.getAccNumber().equals(fromAccountNum))
                && accounts2.stream().anyMatch(account -> account.getAccNumber().equals(toAccountNum));
    }
    public void transfer(final String fromAccountNum,final String toAccountNum, long amount)
    {

        final Account accountFrom = accounts.get(fromAccountNum);
        final Account accountTo = accounts.get(toAccountNum);

        final Object firstLock = accountFrom.compareTo(accountTo) > 0 ? accounts.get(fromAccountNum) : accounts.get(toAccountNum);
        final Object secondLock = accountFrom.compareTo(accountTo) <= 0 ? accounts.get(fromAccountNum) : accounts.get(toAccountNum);

        if (!checkAccount(fromAccountNum, toAccountNum)){
            return;
        }

        int fromId = Integer.parseInt(fromAccountNum);
        int toId = Integer.parseInt(toAccountNum);

        if (fromId < toId)
        {
            synchronized (firstLock)
            {
                synchronized (secondLock)
                {
                    if (amount > 50_000)
                    {
                        try {
                            if (isFraud(fromAccountNum, toAccountNum, amount))
                            {
                                System.out.println("Cash transactions on accounts number "
                                + accountFrom.getAccNumber() +
                                " and " + accountTo.getAccNumber() +
                                " seem suspicious. Accounts blocked pending clarification");

                                accountFrom.setBlocked();
                                accountTo.setBlocked();
                            }else {
                                doTransfer(accountFrom, accountTo, amount);
                            }

                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }else {
                        doTransfer(accountFrom, accountTo, amount);
                    }

                }
            }
        }
    }

    private void doTransfer(Account fromAccount, Account toAccount, long amount){//если нет блока, то перевод денег

        if (fromAccount.checkMoney(amount)){
            if (!fromAccount.isBlocked() && !toAccount.isBlocked()){
                fromAccount.spending(amount);
                toAccount.replenishment(amount);
            }else {
                System.out.println("One of the accounts is blocked!");
            }
        }else {
            System.out.println(fromAccount.getAccNumber() + " not enough funds to write off");
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

    public Map<String, Account> generateAccountList(){
        Map<String, Account> newAccounts = new HashMap<>();
        int countOfAccount = (int) (Math.random() * 100 + 10);
        long moneyOfAccount;
        for (int i = 0; i <= countOfAccount; i++){
            moneyOfAccount = (long)(Math.random() * 100_000 + 40_000);
            newAccounts.put(Integer.toString(i), new Account(moneyOfAccount, Integer.toString(i)));
        }
        return newAccounts;
    }

    public ArrayList<Account> getAccounts2() {
        return accounts2;
    }


    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

}
