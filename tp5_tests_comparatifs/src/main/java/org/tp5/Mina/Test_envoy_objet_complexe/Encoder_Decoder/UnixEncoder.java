package org.tp5.Mina.Test_envoy_objet_complexe.Encoder_Decoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.tp5.Mina.Model.UnixTime;

public class UnixEncoder implements ProtocolEncoder {

    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        UnixTime encoder = (UnixTime)o;
        IoBuffer buffer = IoBuffer.allocate(4, false);
        buffer.putInt((int)encoder.value());
        buffer.flip();
        protocolEncoderOutput.write(buffer);
        //do not have to release the buffer, MINA will do it for you, see
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
