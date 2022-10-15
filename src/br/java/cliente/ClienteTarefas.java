package br.java.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteTarefas {

	public static void main(String[] args) throws Exception {
		
		// REPRESENTA O SOCKET DO CLIENTE PARA SE CONECTAR NO PORT DO NOSSO SERVIDOR (O "SERVERSOCKET")
		// ACESSA O PORT DISPONIBILIZADO PELO SERVIDOR PARA CONEXÃO
		Socket socket = new Socket("localhost", 12345);
		System.out.println("-- Conexão estabelecida pelo cliente --");
		

		// Para receber dados do servidor    		
		Thread threadRecebeComandosServidor = new Thread(new Runnable() {
			@Override
			public void run() {
				
				try {
					System.out.println("Recebendo dados do servidor");
					Scanner respostaServidor = new Scanner(socket.getInputStream());
					while (respostaServidor.hasNextLine()) {
					    String linha = respostaServidor.nextLine();
					    System.out.println(linha);
					}
			        respostaServidor.close();        

				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
        // Para enviar dados do cliente 
		Thread threadEnviaComandosCliente = new Thread(new Runnable() {
			@Override
			public void run() {
				
				try {
					System.out.println("Você já pode enviar algum comando!");
					PrintStream saida = new PrintStream(socket.getOutputStream());		
					Scanner teclado = new Scanner(System.in);
					
					while (teclado.hasNextLine()) {
						String linha = teclado.nextLine();			
						if (linha.trim().equals(""))  // SAI QUANDO USUÁRIO PRESSIONA ENTER
							break;			
						saida.println(linha);
					}
					teclado.close();
					saida.close();
					
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		threadRecebeComandosServidor.start();
		threadEnviaComandosCliente.start();
		
		// PEDE À THREAD MAIN AGUARDAR, EVITANDO QUE FECHE O SOCKET NA LINHA ABAIXO
		// Apenas quando finalizarmos o envio dos comandos com um simples ENTER, terminaremos a thread de enviar comandos e assim a thread main poderá continuar.
		threadEnviaComandosCliente.join();

		System.out.println("Fechando o socket do cliente");
		socket.close();
	}

}
