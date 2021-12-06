package main;

import java.io.RandomAccessFile;

public class HashPrincipal {
	   
    String arqDiretorio;
    String arqBucket;
    RandomAccessFile arqDir;
    RandomAccessFile arqBuc;
    int quantidadeBucket;
    Diretorio diretorio;

public HashPrincipal(int n, String nd, String nc) throws Exception {
    quantidadeBucket = n;
    arqDiretorio = nd;
    arqBucket = nc;
    

    arqDir = new RandomAccessFile("C:\\Users\\mariana\\eclipse-workspace\\Banco de Dados Chave-Valor\\src\\dados" + arqDiretorio,"rw");
    arqBuc = new RandomAccessFile("C:\\Users\\mariana\\eclipse-workspace\\Banco de Dados Chave-Valor\\src\\dados" + arqBucket,"rw");

    // Se o diretorio ou os buckets estiverem vazios, cria um novo diretorio e lista de buckets
    if(arqDir.length()==0 || arqBuc.length()==0) {

        // Cria um novo diretorio, com profundidade  0 
        diretorio = new Diretorio();
        byte[] bd = diretorio.toByteArray();
        arqDir.write(bd);
        
        // Cria um  bucket, já apontado pelo único elemento do diretorio
        Bucket b = new Bucket(quantidadeBucket);
        bd = b.toByteArray();
        arqBuc.seek(0);
        arqBuc.write(bd);
    }
}

public boolean insere(int chave, long endereco) throws Exception {
    
    //Carrega o diretorio
    byte[] bd = new byte[(int)arqDir.length()];
    arqDir.seek(0);
    arqDir.read(bd);
    diretorio = new Diretorio();
    diretorio.fromByteArray(bd);        
   
    // Identifica a hash do diretorio,
    int i = diretorio.hash(chave);
    
    // Recupera o bucket
    Bucket b = new Bucket(quantidadeBucket);
    long enderecoBucket = diretorio.endereco(i);
    byte[] ba = new byte[b.tamanho()];
    arqBuc.seek(enderecoBucket);
    arqBuc.read(ba);
    b.fromByteArray(ba);
    
    // verifica se a chave já não existe no bucket
    if(b.busca(chave)!=-1)
        System.out.println("Chave já existe");     

    // Testa se o bucket já não está cheio
    // Se não estiver, insere o par de chave e endereco
    if(!b.bucketCheio()) {
        // Insere a chave no bucket e o atualiza 
        b.insere(chave, endereco);
        arqBuc.seek(enderecoBucket);
        arqBuc.write(b.toByteArray());
        return true;        
    }
    
    // Duplica o diretorio
    byte pl = b.profundidadeLocal;
    if(pl>=diretorio.profundidadeGlobal)
        diretorio.duplica();
    int pg = diretorio.profundidadeGlobal;

    // Cria os novos buckets, com os seus enderecos no arquivo bucket
    Bucket b1 = new Bucket(quantidadeBucket, pl+1);
    arqBuc.seek(enderecoBucket);
    arqBuc.write(b1.toByteArray());

    Bucket b2 = new Bucket(quantidadeBucket, pl+1);
    long novoEndereco = arqDir.length();
    arqBuc.seek(novoEndereco);
    arqBuc.write(b2.toByteArray());
    
    // Atualiza os enderecos no diretorio
    int inicio = diretorio.hash2(chave, b.profundidadeLocal);
    int deslocamento = (int)Math.pow(2,pl);
    int max = (int)Math.pow(2,pg);
    boolean troca = false;
    for(int j=inicio; j<max; j+=deslocamento) {
        if(troca)
            diretorio.atualizaEndereco(j,novoEndereco);
        troca=!troca;
    }
    
    // Atualiza o arquivo do diretorio
    bd = diretorio.toByteArray();
    arqDir.seek(0);
    arqDir.write(bd);
    
    // Reinsere as chaves
    for(int j=0; j<b.quantidade; j++) {
        insere(b.chaves[j], b.enderecos[j]);
    }
    insere(chave,endereco);
    return false;   

}

public long busca(int chave) throws Exception {
    
    //Carrega o diretorio
    byte[] bd = new byte[(int)arqDir.length()];
    arqDir.seek(0);
    arqDir.read(bd);
    diretorio = new Diretorio();
    diretorio.fromByteArray(bd);        
    
    // Identifica a hash do diretorio,
    int i = diretorio.hash(chave);
    
    // Recupera o bucket
    long enderecoBucket = diretorio.endereco(i);
    Bucket b = new Bucket(quantidadeBucket);
    byte[] ba = new byte[b.tamanho()];
    arqDir.seek(enderecoBucket);
    arqDir.read(ba);
    b.fromByteArray(ba);
    
    return b.busca(chave);
}

public boolean remove(int chave) throws Exception {
    
    //Carrega o diretorio
    byte[] bd = new byte[(int)arqDir.length()];
    arqDir.seek(0);
    arqDir.read(bd);
    diretorio = new Diretorio();
    diretorio.fromByteArray(bd);        
    
    // Identifica a hash do diretorio,
    int i = diretorio.hash(chave);
    
    // Recupera o bucket
    long enderecoBucket = diretorio.endereco(i);
    Bucket c = new Bucket(quantidadeBucket);
    byte[] ba = new byte[c.tamanho()];
    arqBuc.seek(enderecoBucket);
    arqBuc.read(ba);
    c.fromByteArray(ba);
    
    // remove a chave
    if(!c.remove(chave))
        return false;
    
    // Atualiza o bucket
    arqBuc.seek(enderecoBucket);
    arqBuc.write(c.toByteArray());
    return true;
}

public void imprime() {
    try {
        byte[] bd = new byte[(int)arqDir.length()];
        arqDir.seek(0);
        arqDir.read(bd);
        diretorio = new Diretorio();
        diretorio.fromByteArray(bd);   
        System.out.println("\tDiretorio");
        System.out.println(diretorio);

        System.out.println("\tBucket");
        arqBuc.seek(0);
      //  int 
        while(arqBuc.getFilePointer() != arqBuc.length()) {
        	Bucket b = new Bucket(quantidadeBucket);
            byte[] ba = new byte[b.tamanho()];
            arqBuc.read(ba);
            b.fromByteArray(ba);
            System.out.println(b);
        }
    } catch(Exception e) {
        e.printStackTrace();
    }
}
}