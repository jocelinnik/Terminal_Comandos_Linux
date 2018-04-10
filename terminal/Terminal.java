// TERMINAL DE COMANDOS LINUX
// DEVELOPTERS: ANA LIDIA E LINNIK

//COLABORADORES DE LEVE: ELENKARLA SILVA, LEIDE MARINA E MOUHAMADOU.

/*LISTA DE COMANDOS DISPONIVEIS:
1) ls
2) touch
3) cd
4) pwd
5) mv
6) cp
7) rm
8) mkdir
9) rmdir
10) exit
*/
import java.io.IOException;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.lang.StringBuilder;
import java.util.Scanner;

public class Terminal{
	
	public static void executaTerminal(StringBuilder caminho){ 
		Scanner leitor = new Scanner(System.in);

		System.out.printf("\nterminal: %s> ",caminho.toString());
		String entrada = leitor.nextLine();

		String comandos[] = entrada.split(" "); //Quebrar a string de comando e parametro em espacos

		int contador = 0;
		for(String cont : comandos){
			contador++;
		}
		
		switch(comandos[0]){ //O primeiro parametro eh o comando
			case "cd": 
				String novo = funcaoCD(caminho,comandos).toString(); //Recebe o novo endereco
				caminho.delete(0,caminho.length()); //Apaga o antigo
				caminho.append(novo); //Adiciona o novo
				break;	
			case "pwd":
				funcaoPWD(caminho);
				break; 
			case "ls":
				funcaoLS(caminho);
				break;
			case "touch":
				funcaoTOUCH(caminho,comandos);
				break;
			case "rm":
				funcaoRM(caminho,comandos);
				break;
			case "cp":
				funcaoCP(contador,comandos);
				 break;
			case "mv":
				funcaoMV(contador,comandos);
				break;
			case "mkdir":
				funcaoMKDIR(caminho,comandos);
				break;	
			case "rmdir":
				funcaoRMDIR(caminho,comandos);
				break;
			case "exit":
				System.out.println("FIM DA EXECUCAO DO TERMINAL\nOBRIGADO :)\n");
				System.exit(0);
				break;	
			default:
				System.out.println("COMANDO INVALIDO, POR FAVOR TENTE NOVAMENTE!");
				break;
		}			
	}

	public static StringBuilder funcaoCD(StringBuilder caminho,String comandos[]){
		StringBuilder diretorio =  new StringBuilder(caminho.toString()); //uma funcao CD para teste
		if(comandos[1].equals(":")){// comando cd para mudar para o diretorio raiz
			String removido[] = (caminho.toString()).split("/"); //dividir a string em parametros 
			int qtd_parametros = 0;
			for(String cont : comandos){
				qtd_parametros++;
			}
			if(qtd_parametros > 0){
				String nova[] = (caminho.toString().split(removido[qtd_parametros-1])); //Separo a string removido
				//int total = parametro(nova);
				int total_caminho = 0;
				for(String cont : comandos){
					total_caminho++;
				}
				if(total_caminho > 0){
					StringBuilder novo = new StringBuilder(nova[0]); //Faco o caminho andar para o solicitado	
					return novo; //Retorno  o novo caminho do diretorio
				}
			}else{
				System.out.println("O CAMINHO PASSADO NAO EXISTE!");
			}
		}else{// comando cd para mudar para o diretorio passado
			File teste = new File(comandos[1]);
			if(teste.exists()){ //Verifico se eh um diretorio completo
				if(teste.isFile()){ //Se for um arquivo, retorna mensagem de erro
					System.out.println("NAO EH POSSIVEL MUDAR POIS NAO EH UM DIRETORIO");
				}else{
					StringBuilder novo = new StringBuilder(comandos[1]);
					return novo;
				}
			}else{
				diretorio.append("/").append(comandos[1]);
				File dir = new File(diretorio.toString());
				if(dir.exists()){ //Verifico se o diretorio existe
					return diretorio; //Retorno o novo caminho do diretorio
				}else{
					System.out.println("O CAMINHO PASSADO NAO EXISTE!");
				}
			}
		}
		return caminho;
	}

	public static void funcaoPWD(StringBuilder caminho){
		File arquivo = new File(caminho.toString());
		System.out.println(arquivo.getAbsolutePath());
	}

	public static void funcaoLS(StringBuilder caminho){
		File arquivo = new File(caminho.toString());  
        if(arquivo.exists()){ //Verifico se o arquivo existe
            if(arquivo.isFile()){ //se for um arquivo, retorno a quantidade de bytes do arquivo
                System.out.printf("É um arquivo de tamanho %s bytes\n",arquivo.length());
            }else{ //Se eh um diretorio entao listo todo o conteudo dele
            	String[] arquivos = arquivo.list(); //Pego todo o conteudo do diretorio
				for( String file : arquivos){
					System.out.println(file);			
				}
            }         
        }else{
            System.out.println("O DIRETORIO NAO EXISTE");
        }
    }

    public static void funcaoTOUCH(StringBuilder caminho,String comandos[]){
     	File arquivo = new File(comandos[1]);
     	String removido[] = (comandos[1].toString()).split("/"); //Dividir a string em parametros 
     	if(removido[0] == comandos[1]){ //somente o comando touch e o nome do arquivoa ser criado no diretorio atual
     		StringBuilder nova = new StringBuilder(caminho.toString());
     		nova.append("/").append(comandos[1]); 
     		Path novoArquivo = FileSystems.getDefault().getPath(nova.toString());
     		try{
				Files.createFile(novoArquivo); 
			}catch(IOException error) { 
				System.err.println(error);
			} 
     	}else{ //comando touch passando o caminho para o novo arquivo
			Path novoArquivo = FileSystems.getDefault().getPath(comandos[1]);
			try{
				Files.createFile(novoArquivo); 
			}catch(IOException error) { 
				System.err.println(error); 
			}
		}
    }

    public static void funcaoRM(StringBuilder caminho,String comandos[]){ 
		String removido[] = (comandos[1].toString()).split("/"); //Dividir a string em parametros 
		if(removido[0] == comandos[1]){ // somente o comando rm e o nome do arquivo a ser removido do diretorio atual 
     		StringBuilder nova = new StringBuilder(caminho.toString());
     		nova.append("/").append(comandos[1]); 
     		Path arquivoRemovido = FileSystems.getDefault().getPath(nova.toString());
     		try{
				Files.delete(arquivoRemovido); 
			}catch(IOException error) { 
				System.err.println(error);
			} 
     	}else{ // comando rm passando o caminho do arquivo a ser removido
     		Path arquivoRemovido = FileSystems.getDefault().getPath(comandos[1]);
	    	String aux = arquivoRemovido.toString();
			File arquivo = new File(aux);

     		if(arquivo.exists()){
	         	if(arquivo.isFile()){ //Se for um arquivo, deleta
	         		try { 
						Files.delete(arquivoRemovido); 
					} catch (IOException error) { 
						System.err.println(error); 
					}
	         	}else{ //Se for um diretorio, retorna mensagem de erro
	         		System.out.println("PARA DELETAR UM DIRETORIO , UTILIZE O RMDIR");
	         	}
        	}
     	}
    }

	public static void funcaoMV(int contador,String comandos[]){
		if(contador == 2){	
			System.out.println("OPERACAO INVALIDA");
			
		}else if(contador == 3){
			StringBuilder destino = new StringBuilder(comandos[2]);
			destino.append("/").append(ultimoParametro(comandos)); 
			
			Path moveArquivo = FileSystems.getDefault().getPath(comandos[1]);
			Path destinoFinal = FileSystems.getDefault().getPath(destino.toString());
			
			try { 
				Files.move(moveArquivo, destinoFinal, StandardCopyOption.REPLACE_EXISTING);
			}catch (IOException error){ 
				System.err.println(error); 
			}
		}else{
			System.out.println("OPERACAO INVALIDA");
		}
	}

	public static void funcaoCP(int contador,String comandos[]){
		StringBuilder destino = new StringBuilder(comandos[2]);
		destino.append("/").append(ultimoParametro(comandos)); 
		
		Path copiaArquivo = FileSystems.getDefault().getPath(comandos[1]);
		Path destinoFinal = FileSystems.getDefault().getPath(destino.toString()); 

		String caminho = copiaArquivo.toString();
		File arquivo = new File(caminho);
		
		if(arquivo.exists()){
         	if(arquivo.isFile()){ //Se for um arquivo, copia o arquivo
         		try { 
					Files.copy(copiaArquivo, destinoFinal,StandardCopyOption.REPLACE_EXISTING); 
				} catch (IOException error) { 
					System.err.println(error); 
				}
				
         	}else{ //Se for um diretorio, copia o diretorio e todos os arquivos dentro dele
                String[] todosArquivos = arquivo.list();
                String destinoDiretorio = destinoFinal.toString(); 
                try { 
					Files.copy(copiaArquivo, destinoFinal,StandardCopyOption.REPLACE_EXISTING); 
				} catch (IOException error) { 
					System.err.println(error); 
				}

				// copiando todos os arquivos existentes dentro do diretorio copiado
                for(String file : todosArquivos){
                  	StringBuilder caminho_arquivo = new StringBuilder();
                  	StringBuilder destino_arquivo = new StringBuilder();
                  	String caminhoArq,destinoArq;

                  	caminho_arquivo.append(caminho).append("/").append(file);
                	destino_arquivo.append(destino).append("/").append(file);
                	caminhoArq = caminho_arquivo.toString();
                	destinoArq = destino_arquivo.toString();
                	Path partida = FileSystems.getDefault().getPath(caminhoArq);
                	Path chegada = FileSystems.getDefault().getPath(destinoArq); 
                	
                	try { 
						Files.copy(partida,chegada,StandardCopyOption.REPLACE_EXISTING);  
					} catch (IOException error) { 
						System.err.println(error); 
					}
                }
            }

        }else{
        	System.out.println("O ENDERECO PASSADO NAO EXISTE!");
        } 
	}

    public static void funcaoMKDIR(StringBuilder caminho,String comandos[]){
     	String removido[] = (comandos[1].toString()).split("/"); //Dividir a string em parametros 

     	if(removido[0] == comandos[1]){ // somente o comando mkdir e o diretorio a ser criado dentro do diretorio atual 
     		StringBuilder nova = new StringBuilder(caminho.toString());
     		nova.append("/").append(comandos[1]); 
     		Path novoDiretorio = FileSystems.getDefault().getPath(nova.toString());
     		try { 
				Files.createDirectories(novoDiretorio);
			} catch (IOException error) { 
				System.err.println(error); 
			} 
     	}else{// comando mkdir passando o caminho para onde serah criado o diretorio
			Path novoDiretorio = FileSystems.getDefault().getPath(comandos[1]);
			try { 
				Files.createDirectories(novoDiretorio);
			} catch (IOException error) { 
				System.err.println(error); 
			} 
		}
    }

	public static void funcaoRMDIR(StringBuilder caminho,String comandos[]){
		String removido[] = (comandos[1].toString()).split("/"); //Dividir a string em parametros 
     	
     	if(removido[0] == comandos[1]){ // somente o comando rmdir e o diretorio a ser removido do diretorio atual
     		StringBuilder nova = new StringBuilder(caminho.toString());
     		nova.append("/").append(comandos[1]); 
     		Path diretorioRemovido = FileSystems.getDefault().getPath(nova.toString());
			File arquivo = new File(diretorioRemovido.toString());
     	
     		if(arquivo.exists()){
     			if(arquivo.isFile()){ //Se for um arquivo, retorna mensagem de erro
					System.out.println("PARA REMOVER ARQUIVOS , USE O COMANDO RM");			
	         	}else{ //Se for um diretorio , remove
	                String[] todosArquivos = arquivo.list(); // todos os arquivos do diretorio a ser removido

	                //deletando todos os arquivos do diretorio a ser removido
	                for(String file : todosArquivos){
	                  	StringBuilder caminho_arquivo = new StringBuilder();
	                  	caminho_arquivo.append(diretorioRemovido).append("/").append(file);
	                	Path saida = FileSystems.getDefault().getPath(caminho_arquivo.toString());
	                	try { 
							Files.delete(saida); 
						} catch (IOException error) { 
							System.err.println(error); 
						}
	                }
	                // removendo o diretorio
	                try {
						Files.delete(diretorioRemovido);  
					} catch (IOException e) { 
						System.err.println(e); 
					}
	                System.out.println("DIRETORIO REMOVIDO!");
            	}
        	}else{
        	 	System.out.println("O DIRETORIO NAO EXISTE!");
    		}
     	}else{// comando rmdir passando o caminho do diretorio de onde vai ser removido
     		Path diretorioRemovido = FileSystems.getDefault().getPath(comandos[1]);
			File arquivo = new File(diretorioRemovido.toString());
     	
     		if(arquivo.exists()){
     			if(arquivo.isFile()){ //Se for um arquivo, retorna mensagem de erro
					System.out.println("PARA REMOVER ARQUIVOS ,USE O COMANDO RM");			
	         	}else{ //Se for um diretorio, remove
	                String[] todosArquivos = arquivo.list(); // todos os arquivos do diretorio a ser removido

	                // deletando todos os arquivos do diretorio a ser removido
	                for(String file : todosArquivos){
	                  	StringBuilder caminho_arquivo = new StringBuilder();
	                  	caminho_arquivo.append(diretorioRemovido).append("/").append(file);
	                	Path saida = FileSystems.getDefault().getPath(caminho_arquivo.toString());
	                	try { 
							Files.delete(saida);
						} catch (IOException error) { 
							System.err.println(error); 
						}
	                }
	                //removendo o diretorio
	                try {
						Files.delete(diretorioRemovido);  
					} catch (IOException error) { 
						System.err.println(error); 
					}
            	}
        	}else{
        	 	System.out.println("O DIRETORIO NAO EXISTE!");
    		}
     	}
	}

	public  static String ultimoParametro(String comandos[]){ //Pega o ultimo parametro do caminho
		String retorno[] = comandos[1].split("/");

		int contador = 0;
		for(String cont : retorno){
			contador++;
		}
		return retorno[contador-1];
	}
	
	public static void main(String[] args) {
		Scanner leia = new Scanner(System.in);
		StringBuilder caminho = new StringBuilder(); 
		boolean valido = false;
		String usu = "";

		while(valido == false){ //Enquanto usuario  nao digitar um diretorio raiz valido 
			System.out.println("ENTRE COM O DIRETORIO RAIZ DO SEU COMPUTADOR: ");
			usu = leia.nextLine(); 
			File arquivo = new File(usu); 
			if(arquivo.exists()){ //Verifico se o diretorio raiz existe
				valido = true;
			}else{
				System.out.println("ENTRADA INVALIDA , TENTE NOVAMENTE!");
			}
		}

		caminho.append(usu); //Crio o caminho inicial

		while(true){ 
			executaTerminal(caminho);	
		}	
		
	}
}
