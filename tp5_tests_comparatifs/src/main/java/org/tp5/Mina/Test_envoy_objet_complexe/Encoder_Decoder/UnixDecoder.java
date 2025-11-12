package org.tp5.Mina.Test_envoy_objet_complexe.Encoder_Decoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.tp5.Mina.Model.UnixTime;

public class UnixDecoder extends CumulativeProtocolDecoder {
    //Now let’s have a look at the decoder.
    // The CumulativeProtocolDecoder is a great help for writing your own decoder:
    // it will buffer all incoming data until your decoder decides it can do something with it.
    // In this case the message has a fixed size, so it’s easiest to wait until all data is available:
    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer in_ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

        if (in_ioBuffer.remaining() >= 4) {
            long currentTime = in_ioBuffer.getInt();
            UnixTime unixTime = new UnixTime(currentTime);
            protocolDecoderOutput.write(unixTime);
            return true;
        }
        //when there is not enough data available to decode a message, just return false
        return false;
    }
}
