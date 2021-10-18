import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
В банке (класс Bank) есть счета (класс Account) с двумя полями — money и accNumber.

Все счета хранятся внутри банка.
Множество клиентов банка могут одновременно переводить деньги между счетами и запрашивать баланс по своему счёту.
Всё происходит в highly concurrent (многопоточной) среде.

При этом транзакции на суммы > 50000 отправляются на проверку в службу безопасности.
Можно считать, что таких транзакций не более 5% от всех.
За проверку отвечает отдельный и уже реализованный метод Bank.isFraud().

Служба безопасности не может обрабатывать более одной транзакции одновременно.
Проверка занимает 1000 мс.

Если служба безопасности обнаружила мошенничество, необходимо заблокировать оба счёта,
 то есть запретить любые изменения остатков в дальнейшем.

Что нужно сделать

Создайте метод transfer() класса Bank, который переводит деньги с одного счёта на другой.
Если сумма транзакции > 50000 — транзакция отправляется на проверку службе безопасности: вызывается метод isFraud().
Если возвращается true, то счета блокируются (как – на ваше усмотрение).
Создайте метод getBalance класса Bank, который возвращает остаток на счёте по переданной строке номера аккаунта.

Рекомендации
Для решения задачи вы можете дорабатывать классы Account и Bank как угодно.
Дополнительно создайте тест (или набор тестов) для эмуляции реальной работы этих двух классов и проверки системы.
Проверяйте сумму на банковских счетах до запуска транзакций и после завершения — сумма в банке не должна измениться.
Удостоверьтесь, что ваша программа работает в многопоточном режиме. Для этого можете использовать утилиту visualVM.
 */

public class Main {
    private Account accountFrom;
    private Account accountTo;
    //private Bank bank = new Bank();

    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    public static void main(String [] args) throws InterruptedException {
       /* Bank bank = new Bank();

        System.out.println(bank.generateAccountList());
        System.out.println("Total balance at the start - " + bank.getSumAllAccounts());
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                bank.runThread();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                bank.runThread();
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        System.out.println("Total balance at the finish - " + bank.getSumAllAccounts());*/
        Runner runner = new Runner();
        Bank bank = new Bank();
        System.out.println(bank.getAccounts2());
        System.out.println("Total balance at the start - " + bank.getSumAllAccounts());
        long a = bank.getSumAllAccounts();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i<100; i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    //bank.runThread();
                    runner.runThread(bank);
                }
            });
        }
        executorService.shutdown();
        System.out.println("All work submitted");

        executorService.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("Total balance at the start - " + a);
        System.out.println("Total balance at the finish - " + bank.getSumAllAccounts());



    }

}
