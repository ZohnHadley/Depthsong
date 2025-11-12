package org.tp5.Mina.Test_taille_de_paquets.Handlers;

import io.netty.buffer.ByteBuf;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.tp5.Netty.Test_taille_de_paquets.Netty_TestServerTailleDePaquets;
import org.tp5.PrintColors;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class ClientHandler extends IoHandlerAdapter {

    private IoSession current_session;

    @Override
    public void sessionOpened(IoSession session) {
        current_session = session;
        ByteBuffer text_message = ByteBuffer.allocate(1024)  ;
        text_message.put(Netty_TestServerTailleDePaquets.message.getBytes());
        session.write(Netty_TestServerTailleDePaquets.message);
        System.out.println("(client) message envoyé");
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println("(client) message reçue du server : " + message);
        session.closeNow();
    }

    @Override
    public void exceptionCaught( IoSession session, Throwable cause ) throws Exception {
        cause.printStackTrace();
    }


    public InetSocketAddress getLocalChannel() {
        return (InetSocketAddress) current_session.getLocalAddress();
    }
}
