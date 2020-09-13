package application;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import entities.Bank;
import entities.BankAccount;

public class Program {

	public static void main(String[] args) {
		
		Random random = new Random(); /// CLASS RANDOM PARA OS CALCULOS ALETORIOS SEJA DE DISTRIBUICAO DE SALDO COMO DE ALOCACAO DE CONTAS DESTINO
		ReentrantLock threadLock = new ReentrantLock(); /// O LOCK QUE CADA THREAD IRA RECORRER PARA BLOQUEAR A UTILIZACAO DE CADA TRANSFERENCIA
		Double maxAmount = 200.000; /// VALOR TOTAL REPARTIDO POR CADA THREAD E TOTAL DO BANCO
		Double currentAmount = maxAmount; /// VALOR UTILIZADO PARA A REPARTICAO ALEATORIA DE SALDO ENTRE CONTAS MANTENDO O LIMITE DE 200.000 PARA TODAS
		
		Bank bank = new Bank(); /// CLASS ONDE COLOCAREMOS A GESTAO DA LISTA DAS NOSSAS CONTAS
		
		while(currentAmount > 0.0) {
			/// VALOR ALEATORIO QUE SERA ATRIBUIDO COMO SALDO A CADA CONTA DENTRO DOS LIMITES MAXIMOS PRE-CONFIGURADOS
			Double randomValue = 5.000 + ((maxAmount/100) - 5.000) * random.nextDouble();
			//// ENQUANTO O VALOR QUE SERA ATRIBUIDO FOR INFERIOR AO VALOR QUE AINDA NOS RESTA DISTRIBUIR
			if(randomValue < currentAmount) {
				/// ADICIONAMOS UMA  NOVA CONTA A LISTA DE CONTAS NA NOSSA CLASS BANK
				bank.addAccount(new BankAccount(randomValue, 1.000 + (randomValue - 1.000) * random.nextDouble(), null, threadLock, bank));
				/// RETIRAMOS O VALOR ATRIBUIDO A CONTA GERADA DO NOSSO VALOR TOTAL
				currentAmount = currentAmount - randomValue;
			}else {
				/// ADICONAMOS UMA NOVA CONTA A LISTA DE CONTAS DA NOSSA CLASS BANK MAS AGORA COM O QUE NOS RESTOU DO TOTAL A SER DISTRIBUIDO
				bank.addAccount(new BankAccount(randomValue, currentAmount, null, threadLock, bank));
				/// IGUALAMOS O NOSSO VALOR ACTUAL A ZERO DE FORMA A QUEBRAR O CICLO
				currentAmount = 0.0;
				/// FAZEMOS ISTO AQUI POIS SABEMOS QUE AQUI JÁ FIZEMOS A DISTRUIBUICAO DE VALOR PELAS CONTAS TODAS CRIADAS ALEATORIAMENTE
				/// ASSOCIA CADA CONTA A UMA CONTA DESTINO DE FORMA ALEATORIA
				bank.accountsList.stream()
				.forEach((account)->{
					account.destinationAccount = bank.accountsList.get(random.nextInt(bank.accountsList.size()));
				});
				/// METODO RESPONSAVEL POR DAR INICIO A TODAS AS THREADS
				bank.startTransactions(); 
			}
		}
		
	}

}
