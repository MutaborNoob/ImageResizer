public class Account implements Comparable<Account> {

    private long money;
    private String accNumber;
    private boolean isBlocked;

    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
        isBlocked = false;
    }

    public synchronized long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        if (!isBlocked)
        {
            this.money = money;
        }
        else
        {
            System.out.println(accNumber +". This account has been blocked. Wait for the decision of the safety commission");
        }
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public synchronized void replenishment(long amount){//прибыль
        if (!isBlocked)
        {
            money = money + amount;
        }else
            {
            return;
            }

    }

    public synchronized void spending(long amount){//расход
        if (!isBlocked)
        {
            if (money >= amount)
            {
                money = money - amount;
            }
            else
            {
                return;
            }

        }
        else{
            return;
        }
    }

    public synchronized void setBlocked(){
        isBlocked = true;
    }

    public boolean isBlocked() {
        return isBlocked;
    }
    public synchronized boolean checkMoney(long amount){
        return money >= amount;
    }

    @Override
    public int compareTo(Account o) {
        return this.getAccNumber().compareTo(o.getAccNumber());
    }
}
