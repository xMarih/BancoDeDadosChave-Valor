package main;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class main {
    //atualiza registro
	public static void atualizar(RandomAccessFile arq, int cpf, int m) throws IOException {

		Scanner sc = new Scanner(System.in);

		long soma = 0, tempoInicial, tempoFinal, total = 0;
		//float tempoTotal;
		String nome, data, sexo, ant;
		Pntuario obj = new Pntuario();

		System.out.print("Nome: \n");
		nome = sc.nextLine();
		sc.nextLine();

		System.out.print("Data de nascimento: \n");
		data = sc.nextLine();

		sc.nextLine();
		System.out.print("Sexo: \n");
		sexo = sc.nextLine();

		sc.nextLine();
		System.out.print("Anotação: \n");
		ant = sc.nextLine();
		
		tempoInicial = System.currentTimeMillis();
		obj.setm(m);
		obj.update(arq, cpf, nome, data, sexo, ant);
		
		tempoFinal = System.currentTimeMillis();

		total = tempoFinal - tempoInicial;
		soma = soma + total;
		//tempoTotal = soma / 1000;
		System.out.println(soma + " milessegundos");

	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int cpf, m, a = 0, b = 0, op = 0;
		long soma = 0, tempoInicial, tempoFinal, total = 0;
		float tempoTotal;
		String nome, data, sexo, ant;

		Pntuario pt = new Pntuario();

		RandomAccessFile arqBucket;
		System.out.println("\tSISTEMA DE PONTUÁRIO");
		System.out.println("Informe o valor de 'm': ");
		m = sc.nextInt();
		pt.setm(m);
		sc.nextLine();
		System.out.println("Nome do arquivo mestre: ");
		String mestre = sc.nextLine();
		sc.nextLine();
		System.out.println("Nome do Diretorio: ");
		String dir = sc.nextLine();
		sc.nextLine();
		System.out.println("Nome do Bucket: ");
		String buck = sc.nextLine();
		sc.nextLine();
		System.out.println("Informe o valor de n: ");
		int n = sc.nextInt();
		sc.nextLine();
		//System.out.println("Informe a P.G.: ");
		//byte pg = sc.nextByte();
		sc.nextLine();
		RandomAccessFile arq;

		try {
			arq = new RandomAccessFile("C:\\Users\\mariana\\eclipse-workspace\\Banco de Dados Chave-Valor\\src\\dados" + mestre,
					"rw");
			HashPrincipal ab = new HashPrincipal(n, dir, buck);

			do {
				System.out.println("Menu");
				System.out.println(
						" " + "[1] Criar\n " + "[2] Apagar\n " + "[3] Mostrar registros\n " + "[4] Atualizar\n");
				op = sc.nextInt();

				pt.setm(m);

				if (op == 1) {
					do {
						System.out.print("CPF: ");
						cpf = sc.nextInt();

						sc.nextLine();
						System.out.print("Nome: ");
						nome = sc.nextLine();
						sc.nextLine();

						System.out.print("Data de nascimento: ");
						data = sc.nextLine();

						sc.nextLine();
						System.out.print("Sexo: ");
						sexo = sc.nextLine();

						sc.nextLine();
						System.out.print("Anotação: \n");
						ant = sc.nextLine();

						tempoInicial = System.currentTimeMillis();

						pt.setcpf(cpf);
						pt.setnome(nome);
						pt.setdataNasc(data);
						pt.setsexo(sexo);
						pt.setanotacao(ant);

						int a1 = pt.inserir(arq, m);

						ab.insere(cpf, a1);

						tempoFinal = System.currentTimeMillis();
						total = tempoFinal - tempoInicial;
						soma = soma + total;

						System.out.println("deseja inserir mais registros?\n [1] Sim\t [2] Não");
						b = sc.nextInt();
					} while (b == 1);

					System.out.printf(" Tempo em milessegundos: %d\n", soma);

				} else if (op == 2) {
					System.out.println("CPF que deseja apagar: ");
					cpf = sc.nextInt();

					tempoInicial = System.currentTimeMillis();

					long a1 = ab.busca(cpf);
					pt.delete(arq, a1, cpf);
					ab.remove(cpf);

					tempoFinal = System.currentTimeMillis();
					total = tempoFinal - tempoInicial;
					soma = soma + total;
					//tempoTotal = soma / 1000;
					System.out.println(soma + " milessegundos");

				} else if (op == 3) {
					System.out.println("\t[1] Imprimir arquivos: \n\t[2] Buscar registro: ");
					int op1 = sc.nextInt();
					if (op1 == 1) {
						tempoInicial = System.currentTimeMillis();
						pt.read(arq);
						ab.imprime();
						tempoFinal = System.currentTimeMillis();
						soma = soma + total;
						//tempoTotal = soma / 1000;
						System.out.println(soma + " milessegundos");
					} else if (op1 == 2) {
						System.out.println("CPF que deseja buscar: ");
						cpf = sc.nextInt();
						tempoInicial = System.currentTimeMillis();
						int bus = (int) ab.busca(cpf);
						pt.busca(arq, bus);
						tempoFinal = System.currentTimeMillis();
						total = tempoFinal - tempoInicial;
						soma = soma + total;
						//tempoTotal = soma / 1000;
						System.out.println(soma + " milessegundos");
					}

				} else if (op == 4) {
					System.out.println("Informe CPF que deseja atualizar: ");
					cpf = sc.nextInt();
					//int buscaCPF = (int) ab.busca(cpf);
					//System.out.println(buscaCPF);
					atualizar(arq, cpf, m);
				}

				System.out.println("Deseja continuar?:\n [1] Sim\t [2] Não ");
				a = sc.nextInt();

			} while (a == 1);
			System.out.println("\n-------------------------------------------");

			arq.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}