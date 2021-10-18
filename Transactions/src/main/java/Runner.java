import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {
    private Account fromAcc;
    private Account toAcc;

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    //private Bank bank;

    public void runThread(Bank bank){
        Map<String, Account> accounts = bank.getAccounts();

        long money = (long) (Math.random() * 70_000 + 1000);

        //lock1.lock();
        //lock2.lock();
        //try {
            //long money = (long) (Math.random() * 70_000 + 1000);

            Integer intId =(int) (Math.random() * accounts.size());
            Integer int2Id =(int) (Math.random() * accounts.size());
            while (intId.equals(int2Id)){
                intId =(int) (Math.random() * accounts.size());
            }

            bank.transfer(Integer.toString(intId), Integer.toString(int2Id), money);

        //}finally {
          //  lock1.unlock();
            //lock2.unlock();
        //}
    }
}
