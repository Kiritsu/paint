package paint;

import paint.enums.LogLevel;
import paint.packets.PacketBase;
import paint.services.ClientAcceptorService;
import paint.services.ClientTimeoutCheckService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class represents a server that will accept clients and broadcast packets all over the different clients.
 * @author Allan Mercou, Mathieu Lagnel, Gabriel Cousin
 * @version 1.0
 */
public class Server {
    /**
     * Represents the list of every client.
     */
    private ArrayList<Socket> clients;

    /**
     * ServerSocket that will handle client's connections and sending/receiving packets.
     */
    private ServerSocket serverSocket;

    /**
     * Service that will handle client's connection.
     */
    private ClientAcceptorService clientAcceptorService;

    /**
     * Service that will handle client's timeout.
     */
    private ClientTimeoutCheckService clientTimeoutService;

    /**
     * Indicates whether the server must be stopped.
     */
    private boolean quitRequested;

    /**
     * IPAddress of the server. (doesn't really matter because we're running under localhost)
     */
    private String address;

    /**
     * Port of the server.
     */
    private short port;

    /**
     * Creates a new server.
     * @param address Address of the server.
     * @param port Port of the server.
     */
    public Server(String address, short port) throws IOException {
        this.clients = new ArrayList<>();
        this.clientAcceptorService = new ClientAcceptorService(this);
        this.clientTimeoutService = new ClientTimeoutCheckService(this);

        this.quitRequested = false;

        this.address = address;
        this.port = port;

        this.serverSocket = new ServerSocket(this.port);

        Logger.println(LogLevel.Debug, "Server", "Server initialized.");
    }

    /**
     * Starts the server and starts the different services.
     */
    public void startServer() throws IOException {
        this.clientAcceptorService.start();
        this.clientTimeoutService.start();

        Logger.println(LogLevel.Info, "Server", "Server started.");
    }

    /**
     * Adds a client to our list of clients.
     * @param client Instance of a client.
     */
    public void addClient(Socket client) {
        if (!this.clients.contains(client)) {
            this.clients.add(client);

            Logger.println(LogLevel.Debug, "Server",
                    "A client has been added.");
        } else {
            Logger.println(LogLevel.Error, "Server",
                    "Unable to add a client to the ArrayList because it already contains it. (shouldn't happen.)");
        }
    }

    /**
     * Deletes a client from the ArrayList.
     * @param client Client to remove.
     */
    public void deleteClient(Socket client) {
        if (this.clients.contains(client)) {
            this.clients.remove(client);

            Logger.println(LogLevel.Debug, "Server", "A client has disconnected.");
        }
    }

    /**
     * Sends a packet to every other clients if a source client is specified.
     * @param packet Packet to send.
     * @param sourceClient Client that sent that packet.
     */
    public void broadcastPacket(PacketBase packet, Socket sourceClient) throws IOException {
        Logger.println(LogLevel.Debug, "Packet Broadcaster", "Broadcasting packet: " + packet);

        for (Socket client : clients) {
            PrintWriter output = new PrintWriter(client.getOutputStream());
            output.println(packet.toString());
            output.flush();
        }
    }

    /**
     * Sends the current user count to every connected client.
     */
    public void sendUserCount() throws IOException {
        Logger.println(LogLevel.Debug, "Server Packet Broadcaster", "Sending usercount [Packet Id: 3] ");

        for (Socket client : clients) {
            PrintWriter output = new PrintWriter(client.getOutputStream());
            output.println("2 " + clients.size());
            output.flush();
        }
    }

    /**
     * Returns the different clients.
     */
    public ArrayList<Socket> getClients() {
        return this.clients;
    }

    /**
     * Updates our list of clients to only keep connected and active sockets.
     */
    public void updateClients(ArrayList<Socket> clients) {
        this.clients = new ArrayList<>(clients);
    }

    /**
     * Returns the server socket.
     */
    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    /**
     * Indicates if cancellation is requested.
     */
    public boolean isQuitRequested() {
        return this.quitRequested;
    }

    /**
     * Indicates that we're going to stop the server and the clients.
     */
    public void shutdown() {
        this.quitRequested = true;
    }

    /**
     * Gets a String representation of the current server.
     */
    @Override
    public String toString() {
        return "Server bound to " + address + ":" + port + " | " + clients.size() + " clients connected.";
    }

    public static void main(String[] args) throws IOException {
        new Server("localhost", (short) 8000).startServer();
    }
}
