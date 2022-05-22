package hanu.gdsc.coreProblem.services.testCasePing;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanu.gdsc.coreProblem.config.TestCasePingConfig;
import hanu.gdsc.share.domains.Id;
import hanu.gdsc.share.error.InvalidInputError;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;

public class SocketThread {
    private Id coderId;
    private final Socket socket;
    private final DataInputStream socketIn;
    private final DataOutputStream socketOut;
    private final boolean closed[];

    // Utils
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public SocketThread(ServerSocket serverSocket) throws Exception {
        socket = serverSocket.accept();
        socketIn = new DataInputStream(socket.getInputStream());
        socketOut = new DataOutputStream(socket.getOutputStream());
        closed = new boolean[]{false};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ZonedDateTime endTime = ZonedDateTime.now().plusSeconds(TestCasePingConfig.WAIT_FOR_CODER_ID_SECOND);
                while (ZonedDateTime.now().isBefore(endTime)) {
                    try {
                        String coderIdFromSocket = socketIn.readUTF();
                        coderId = new Id(coderIdFromSocket);
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidInputError e) {
                    }
                }
                try {
                    close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // TODO: start this thread
    }

    private void close() throws IOException {
        socket.close();
        socketIn.close();
        socketOut.close();
        closed[0] = true;
    }

    public Id getCoderId() {
        return coderId;
    }

    public void send(Object payload) throws IOException {
        try {
            socketOut.writeUTF(objectMapper.writeValueAsString(payload));
            socketOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public boolean closed() {
        return closed[0];
    }
}
