**Multithreaded Web Server Project**

This project demonstrates the implementation of different types of web servers: a SingleThreaded, Multithreaded, and ThreadPool server, all built in Java. Each version handles client requests differently to showcase various concurrency models in a web server environment.

Project Structure
SingleThreaded Server

Handles one client at a time.
Accepts a connection, processes the request, and responds before moving on to the next client.
Multithreaded Server

Creates a new thread for each client request.
Enables multiple clients to connect and be served simultaneously.
ThreadPool Server

Uses a pool of threads to handle incoming requests.
Efficient for managing multiple client requests by reusing existing threads from the pool.
Technologies Used
Java: Core programming language for the server and client implementation.
Sockets: For communication between the client and server.
Multithreading: To handle concurrent client requests.
