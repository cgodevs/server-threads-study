package br.java.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteTarefas {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		// REPRESENTA O SOCKET DO CLIENTE PARA SE CONECTAR NO PORT DO NOSSO SERVIDOR (O "SERVERSOCKET")
		// ACESSA O PORT DISPONIBILIZADO PELO SERVIDOR PARA CONEXÃO
		Socket socket = new Socket("localhost", 12345);
		System.out.println("-- Conexão estabelecida pelo cliente --");
		
		// JÁ ENVIA O COMANDO "c1" PARA O SERVIDOR
		PrintStream saida = new PrintStream(socket.getOutputStream());
		saida.println("c1");
		
		// TRAVA O CLIENTE AGUARDANDO ENTER NO TECLADO
		Scanner teclado = new Scanner(System.in);
		teclado.nextLine();
		
		socket.close();
	}

}
