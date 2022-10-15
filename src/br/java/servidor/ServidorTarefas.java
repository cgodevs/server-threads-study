package br.java.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTarefas {

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("-- Iniciando Servidor --");
		ServerSocket servidor = new ServerSocket(12345);
		
		// CRIA POOL DE THREADS PARA GERENCIAMENTO DINÂMICO
		ExecutorService threadPool = Executors.newCachedThreadPool();
		
		while(true) {
			// ESCUTA A CHEGADA DE UMA CONEXÃO, TRAVA ENQUANTO UMA NÃO CHEGA
			Socket socket = servidor.accept(); 
			System.out.println("Aceitando novo cliente na porta " + socket.getPort());
			
			DistribuirTarefas distribuidor = new DistribuirTarefas(socket);
			threadPool.execute(distribuidor);
			
//			Set<Thread> todasAsThreads = Thread.getAllStackTraces().keySet();
//			for (Thread thread : todasAsThreads) {
//			    System.out.println(thread.getName());
//			}
		}
	}

}
