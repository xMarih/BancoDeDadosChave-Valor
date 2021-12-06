package main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class Bucket {

    byte   profundidadeLocal;   
    short  quantidade;                // quantidade presentes no bucket
    short  quantidadeMaxima;   // quantidade máxima de pares que o bucket pode conter
    int[]  chaves;                          
    long[] enderecos;                  
    short  bytesPorPar;                 // tamanho fixo de cada par de chave/endereco em bytes
    short  tamanhoFixo;             // tamanho fixo do bucket em bytes

    public Bucket(int qtdmax) throws Exception {
        this(qtdmax, 0);
    }

    public Bucket(int qtdmax, int pl) throws Exception {

        profundidadeLocal = (byte)pl;
        quantidade = 0;
        quantidadeMaxima = (short)qtdmax;
        chaves = new int[quantidadeMaxima];
        enderecos = new long[quantidadeMaxima];
        bytesPorPar = 12;  // int + long
        tamanhoFixo = (short)(bytesPorPar * quantidadeMaxima + 3);
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeByte(profundidadeLocal);
        dos.writeShort(quantidade);
        int i=0;
        while(i<quantidade) {
            dos.writeInt(chaves[i]);
            dos.writeLong(enderecos[i]);
            i++;
        }
        while(i<quantidadeMaxima) {
            dos.writeInt(0);
            dos.writeLong(0);
            i++;
        }
        return baos.toByteArray();            
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        profundidadeLocal = dis.readByte();
        quantidade = dis.readShort();
        int i=0;
        while(i<quantidadeMaxima) {
            chaves[i] = dis.readInt();
            enderecos[i] = dis.readLong();
            i++;
        }
    }

    public boolean insere(int c, long e) {
        if(bucketCheio())
            return false;
        int i=quantidade-1;
       while(i>=0 && c<chaves[i]) {
            chaves[i+1] = chaves[i];
            enderecos[i+1] = enderecos[i];
            i--;
        }
        i++;
        chaves[i] = c;
        enderecos[i] = e;
        quantidade++;
        return true;
 
    }

    public long busca(int c) {
        if(bucketVazio())
            return -1;
        int i=0;
        while(i<quantidade && c>chaves[i])
            i++;
        if(i<quantidade && c==chaves[i])
            return enderecos[i];
        else
            return -1;        
    }

    public boolean remove(int c) {
        if(bucketVazio())
            return false;
        int i=0;
        while(i<quantidade && c>chaves[i])
            i++;
        if(c==chaves[i]) {
            while(i<quantidade-1) {
                chaves[i] = chaves[i+1];
                enderecos[i] = enderecos[i+1];
                i++;
            }
            quantidade--;
            return true;
        }
        else
            return false;        
    }

    public boolean bucketVazio() {
        return quantidade == 0;
    }

    public boolean bucketCheio() {
        return quantidade == quantidadeMaxima;
    }

    public String toString() {
        String s = "\nProfundidade Local: "+profundidadeLocal+
                   "\nQuantidade: "+quantidade+
                   "\n| ";
        int i=0;
        while(i<quantidade) {
            s += chaves[i] + " - " + enderecos[i] + " | ";
            i++;
        }
        while(i<quantidadeMaxima) {
            s += " - | ";
            i++;
        }
        return s;
    }

    public int tamanho() {
        return tamanhoFixo;
    }

}