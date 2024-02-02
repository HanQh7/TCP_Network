package ss;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
public class TCP_server {
	
 public static void main(String[] args) {
	        try {
	            ServerSocket serverSocket = new ServerSocket(8080);
	            System.out.println("Server started and listening on port 8080...");

	            while (true) {
	                Socket clientSocket = serverSocket.accept();
	                System.out.println("Client connected: " + clientSocket);

	                // Create a new thread to handle the client request
	                ClientHandler clientHandler = new ClientHandler(clientSocket);
	                clientHandler.start();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    static class ClientHandler extends Thread {
	        private final Socket clientSocket;

	        public ClientHandler(Socket clientSocket) {
	            this.clientSocket = clientSocket;
	        }

	        @Override
	        public void run() {
	            try {
	                // Create input and output streams for client communication
	                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

	                String expression;
	                while ((expression = input.readLine()) != null) {
	                    if (expression.equalsIgnoreCase("exit")) {
	                        break;
	                    }

	                    try {
	                        // Evaluate the expression
	                        double result = evaluateExpression(expression);
	                        output.println(String.valueOf(result));
	                    } catch (IllegalArgumentException e) {
	                        output.println("Error: " + e.getMessage());
	                    }
	                }

	                System.out.println("Client disconnected: " + clientSocket);
	                clientSocket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	        private double evaluateExpression(String expression) {
	            // Evaluate the math expression and return the result
	            // You can use a library like ScriptEngine or implement your own expression evaluator
	            // For simplicity, let's assume the expression is in the format "operand1 operator operand2"

	            String[] parts = expression.split(" ");
	            if (parts.length != 3) {
	                throw new IllegalArgumentException("Invalid expression format");
	            }

	            double operand1 = Double.parseDouble(parts[0]);
	            double operand2 = Double.parseDouble(parts[2]);
	            double result;

	            switch (parts[1]) {
	                case "+":
	                    result = operand1 + operand2;
	                    break;
	                case "-":
	                    result = operand1 - operand2;
	                    break;
	                case "*":
	                    result = operand1 * operand2;
	                    break;
	                case "/":
	                    result = operand1 / operand2;
	                    break;
	                default:
	                    throw new IllegalArgumentException("Invalid operator: " + parts[1]);
	            }

	            return result;
	        }
	    }
	}
