package ss;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class TCP_client {
	    public static void main(String[] args) {
	        try {
	            Socket socket = new Socket("localhost", 8080);
	            System.out.println("Connected to server.");

	            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

	            String expression;
	            while (true) {
	                System.out.println("1 Enter a math expression operand1 operator operand2:");
	                System.out.println("2 Enter exit to End the program");
	                expression = input.readLine();
	                
	                if (expression.equalsIgnoreCase("exit")) {
	                    output.println("exit");
	                    break;
	                }

	                output.println(expression);
	                String result = serverInput.readLine();
	                System.out.println("Result: " + result);
	            }

	            System.out.println("Disconnected from server.");
	            socket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

