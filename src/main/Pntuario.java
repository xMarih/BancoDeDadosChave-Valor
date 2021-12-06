package main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Pntuario {
	private int cpf;   
	private String nome;
	private String dataNasc;
	private String sexo;
	private String anotacao;
	private int m;
	private int quantidade;

	public static final int tamanho = 51;

	public Pntuario() {
		this.cpf = 0;
		this.nome = "";
		this.dataNasc = "";
		this.sexo = "";
		this.anotacao = "";

	}

	public void setm(int tamanho) {
		this.m = tamanho;
	}

	public void setcpf(int c) {
		this.cpf = c;
	}

	public void setnome(String n) {
		if (n.length() > 30)
			this.nome = n.substring(0, 29);
		else
			this.nome = n;
	}

	public void setdataNasc(String data) {
		if (data.length() > 11)
			this.dataNasc = data.substring(0, 10);
		else
			this.dataNasc = data;
	}

	public void setsexo(String sex) {
		if (sex.length() > 1)
			this.sexo = sex.substring(0, 1);
		else
			this.sexo = sex;
	}

	public void setanotacao(String ant) {
		if (ant.length() > getm())
			this.anotacao = ant.substring(0, getm() - 1);
		else
			this.anotacao = ant;
	}

	public void setquantidade(int q) {
		this.quantidade = this.quantidade + q;
	}

	public int getcpf() {
		return cpf;
	}

	public String getnome() {
		return nome;
	}

	public String getdataNasc() {
		return dataNasc;
	}

	public String getsexo() {
		return sexo;
	}

	public String getanotacao() {
		return anotacao;
	}

	public int getm() {
		return m;
	}

	public int getquantidade() {
		return quantidade;
	}

	public void imprimir() {

		System.out.println("CPF: " + this.cpf);
		System.out.println("Nome: " + this.nome);
		System.out.println("Data de Nascimento: " + this.dataNasc);
		System.out.println("Sexo: " + this.sexo);
		System.out.println("Anotação: " + this.anotacao);

	}

	public byte[] escrever() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeInt(cpf);
		dos.writeUTF(nome);
		dos.writeUTF(dataNasc);
		dos.writeUTF(sexo);
		dos.writeUTF(anotacao);
		dos.flush();
		return baos.toByteArray();

	}

	public void leitura(byte[] ba) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		DataInputStream dis = new DataInputStream(bais);
		cpf = dis.readInt();
		nome = dis.readUTF();
		dataNasc = dis.readUTF();
		sexo = dis.readUTF();
		anotacao = dis.readUTF();
	}

	public int inserir(RandomAccessFile file, int m) {
		int a = 0;
		try {

			if (file.length() == 0) {
				int id = 0;

				file.seek(id * tamanho + 10);
				file.writeChar(' ');
				file.seek(id * tamanho + 10 + 2);
				file.write(escrever());
				file.seek(0);
				a = id;
				id++;
				file.writeInt(id);
				file.seek(4);
				file.writeInt(m);
			} else {
				file.seek(0);
				int id = file.readInt();

				file.seek(id * tamanho + 10);
				file.writeChar(' ');
				file.seek(id * tamanho + 10 + 2);
				file.write(escrever());
				a = id;
				id++;
				file.seek(0);
				file.writeInt(id);

			}

		} catch (IOException e) {
		}
		return a;
	}

	public void read(RandomAccessFile file) {
		try {
			if (file.length() == 0) {
				System.out.println("Arquivo vazio");
			} else {
				file.seek(0);
				int id = file.readInt();
				file.seek(4);
				int m = file.readInt();

				int nbytes = 0;
				char lap;
				byte[] buffer = new byte[tamanho + m + 2];

				// System.out.println(m);

				for (int i = 0; i <= id; i++) {
					file.seek(i * tamanho + 10);
					lap = file.readChar();

					if (lap == ' ') {
						file.seek(i * tamanho + 10 + 2);
						nbytes = file.read(buffer);
						leitura(buffer);
						imprimir();
						System.out.println();
					}
				}
			}
		} catch (IOException e) {
		}
	}

	public void delete(RandomAccessFile file, long pos, int chave) {

		try {
			if (file.length() == 0) {
				System.out.println("Arquivo vazio");
			} else {
				boolean aux = false;

				file.seek(0);
				int id = file.readInt();
				file.seek(4);
				int m = file.readInt();

				int nbytes = 0;
				char lap;
				byte[] buffer = new byte[tamanho + m + 2];
                System.out.println(id);
                for (int i = 0; i < id; i++) {
				file.seek(i * tamanho + 10);
				lap = file.readChar();
				
				file.seek(i * tamanho + 10 + 2);
				nbytes = file.read(buffer);
				leitura(buffer);
				if (lap == ' ') {
					if (cpf == chave) {
						file.seek(i * tamanho + 10);
						file.writeChar('*');
						System.out.println("Registro: " + this.cpf + " apagado");
						aux = true;
					}
				}
				 }//
				if (aux == false)
					System.out.println("Erro ao apagar");
			}
		} catch (IOException e) {
		}
	}

	public void busca(RandomAccessFile file, int pos) {
		try {
			if (file.length() == 0) {
				System.out.println("Arquivo vazio");
			} else {
				file.seek(0);
				int id = file.readInt();
				file.seek(4);
				int m = file.readInt();

				int nbytes = 0;
				char lap;
				byte[] buffer = new byte[tamanho + m + 2];

				file.seek(pos * tamanho + 10);
				lap = file.readChar();
				file.seek(pos * tamanho + 10 + 2);
				nbytes = file.read(buffer);
				leitura(buffer);
				imprimir();
			}

		} catch (IOException e) {
		}

	}

	public void update(RandomAccessFile file, int cpf1, String nom, String nasc, String sex, String anot) {

		try {
			if (file.length() == 0) {
				System.out.println("Arquivo vazio");
			} else {
				boolean aux = false;

				file.seek(0);
				int id = file.readInt();
				file.seek(4);
				int m = file.readInt();

				int nbytes = 0;
				char lap;
				byte[] buffer = new byte[tamanho + m + 2];

				for (int i = 0; i <= id; i++) {
					file.seek(i * tamanho + 10);
					lap = file.readChar();

					file.seek(i * tamanho + 10 + 2);
					nbytes = file.read(buffer);
					leitura(buffer);

					if (lap == ' ') {
						if (this.cpf == cpf1) {
							file.seek(i * tamanho + 10);
							file.writeChar(' ');
							file.seek(i * tamanho + 10 + 2);
							file.writeInt(cpf1);
							file.writeUTF(nom);
							file.writeUTF(nasc);
							file.writeUTF(sex);
							file.writeUTF(anot);
							aux = true;
							System.out.println("Registro atualizado!");
						}
					}
				}
				if (aux == false)
					System.out.println("Registro não atualizado!");
			}
		} catch (IOException e) {
		}
	}
}
