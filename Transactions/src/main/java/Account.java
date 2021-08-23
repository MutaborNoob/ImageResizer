public class Account implements Comparable<Account> {

    private long money;
    private String accNumber;
    private boolean isBlocked;

    public Account(long money, String accNumber) {
        this.money = money;
        this.accNumber = accNumber;
        isBlocked = false;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        if (isBlocked = false)
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

    public void replenishment(long amount){//прибыль
        if (!isBlocked)
        {
            money = money + amount;
        }else
            {
            System.out.println(accNumber +". This account has been blocked. Wait for the decision of the safety commission");
            }

    }

    public void spending(long amount){//расход
        if (!isBlocked)
        {
            if (money >= amount)
            {
                money = money - amount;
            }
            else if (money < amount)
            {
                System.out.println(accNumber + " not enough funds to write off");
            }

        }
        else{
            System.out.println(accNumber +". This account has been blocked. Wait for the decision of the safety commission");
        }
    }

    public void setBlocked(){
        isBlocked = true;
    }

    public boolean isBlocked() {
        return isBlocked;
    }
    public boolean checkMoney(long amount){
        return money >= amount;
    }

    @Override
    public int compareTo(Account o) {
        return this.getAccNumber().compareTo(o.getAccNumber());
    }
}
