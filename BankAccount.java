package entities;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount extends Thread implements AccountOperations {

	public Double balance;
	public Double transfAmount;
	public BankAccount destinationAccount;
	public ReentrantLock threadLock;
	public Condition available;
	private Bank bank;
	
	@Override
	public void run() {
		while(true) {
			 try{
				 Thread.sleep(2000); /// INTERVALOS DE 2 SEGUNDOS DE FORMA A SER MAIS PRECEPTIVEL
				 transfer(); /// CHAMA O METODO TRANSFERENCIA
            }
            catch(Exception ex){
                System.out.println ( ex );
            }
		}
	}
	
	public BankAccount(Double balance, Double transfAmount, BankAccount destinationAccount, ReentrantLock threadLock, Bank bank) {
		this.balance = balance;
		this.transfAmount = transfAmount;
		this.destinationAccount = destinationAccount;
		this.threadLock = threadLock;
		this.available = threadLock.newCondition();
		this.bank = bank;
	}

	@Override
	public void transfer() throws InterruptedException {
		threadLock.lock(); /// A THREAD FICA COM O LOCK BLOQUEANDO O ACESSO A OUTRAS THREADS
		try {
			/// ITERAMOS ENQUANTO O SALDO FOR INFERIOR AO VALOR PEDIDO PARA TRANSFERENCIA
			while(balance<=transfAmount) {
				System.out.println(Thread.currentThread().getName()+" account balance is not enough for the transfer!");
				available.await(); /// A THREAD FICA EM ESPERA ATÉ QUE O SALDO AUMENTE
			}
			/// ASSIM QUE O SALDO SEJA FAVORAVEL A TRANSFERENCIA INICIAMOS O PROCESSO
			System.out.println(Thread.currentThread().getName()+" will transfer "+transfAmount+" to "+destinationAccount.getName()+" account!");
			balance = balance-transfAmount; /// RETIRA O VALOR DO SALDO DA CONTA
			destinationAccount.setAmount(transfAmount); /// TRANSFERE O VALOR DEFINIDO PARA A CONTA DESTINO
			System.out.println(Thread.currentThread().getName()+" account balance is now of: "+balance);
			available.signalAll(); /// A THREAD INFORMA OUTRAS THREADS QUE JÁ TERMINOU A OPERAÇÃO
		}
		finally {
			/// AO CONCLUIR A OPERAÇÃO A THREAD IMPRIME O SALDO TOTAL DO BANCO RECORRENDO METODO RESPECTIVO DA CLASS BANK
			System.out.println("This bank balance is: "+bank.getTotalBalance().toString());
			threadLock.unlock(); /// POR FIM LIBERTA O LOCK PARA A THREAD SEGUINTE
		}
	}

	@Override
	public void setAmount(Double amountToAdd) {
		this.balance = balance+amountToAdd; /// ADICIONA DADO VALOR AO SALDO DA CONTA
	}

}
