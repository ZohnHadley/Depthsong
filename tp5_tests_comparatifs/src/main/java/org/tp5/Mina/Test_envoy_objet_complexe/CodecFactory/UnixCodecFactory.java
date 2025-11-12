package org.tp5.Mina.Test_envoy_objet_complexe.CodecFactory;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.tp5.Mina.Test_envoy_objet_complexe.Encoder_Decoder.UnixDecoder;
import org.tp5.Mina.Test_envoy_objet_complexe.Encoder_Decoder.UnixEncoder;

public class UnixCodecFactory implements ProtocolCodecFactory {

    private ProtocolEncoder encoder;
    private ProtocolDecoder decoder;

    public UnixCodecFactory(boolean client){
        if(client){
            encoder = new UnixEncoder();
            decoder = new UnixDecoder();
        }else{
            encoder = new UnixEncoder();
            decoder = new UnixDecoder();
        }
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
}
