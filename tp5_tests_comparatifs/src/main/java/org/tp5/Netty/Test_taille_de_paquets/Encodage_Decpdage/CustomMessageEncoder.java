package org.tp5.Netty.Test_taille_de_paquets.Encodage_Decpdage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import org.tp5.Netty.Test_taille_de_paquets.Netty_TestServerTailleDePaquets;
import org.tp5.PrintColors;

public class CustomMessageEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, io.netty.channel.ChannelPromise promise) {
        ByteBuf text_message = ctx.alloc().buffer(Netty_TestServerTailleDePaquets.numberOfBytes);
        byte[] bytes = new byte[Netty_TestServerTailleDePaquets.numberOfBytes];

        for (int i = 0; i < Netty_TestServerTailleDePaquets.numberOfBytes; i++) {
            if (i >= Netty_TestServerTailleDePaquets.message.length()) {
                System.out.println(PrintColors.ANSI_YELLOW + "!!!warning!!! : le taille du \"paquets\" est plus grande que le message" + PrintColors.ANSI_RESET);
                break;
            }
            bytes[i] = Netty_TestServerTailleDePaquets.message.getBytes()[i];
        }

        text_message.writeBytes(bytes);
        ctx.writeAndFlush(text_message);
    }

}
