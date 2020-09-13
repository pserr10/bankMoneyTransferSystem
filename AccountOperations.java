package entities;

import java.util.concurrent.locks.ReentrantLock;

public interface AccountOperations {
	
	public static final Double amount = null; 
	public static final Double transfAmount = null;
	public static final BankAccount destinationAccount = null;
	public static final ReentrantLock threadLock = null;
	public void transfer() throws InterruptedException;
	public void setAmount(Double amount);
	
}
