package entities;

import java.util.ArrayList;
import java.util.List;

public interface BankOperations {
	
	public List<BankAccount> accountsList = new ArrayList<>();
	public void setTotalBalance();
	public Double getTotalBalance();
	public void addAccount(BankAccount account);
	public Integer getNumbAccounts();
	public void startTransactions();
	
}
