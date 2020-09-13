package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bank {
	
	public List<BankAccount> accountsList = new ArrayList<>();
	private Double total;
	Random random = new Random();

	public Bank() {
		///this.accountsList = accountsList;
	}
		
	public Double getTotalBalance() {
		total = accountsList.stream()
				.mapToDouble(account -> account.balance).sum();
		return total;
	}
	
	public void addAccount(BankAccount account) {
		accountsList.add(account);
	}
	
	public void startTransactions() {
		accountsList.stream().forEach(account -> account.start());
	}

}
