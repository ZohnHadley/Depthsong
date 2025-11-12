#ifdef GL_ES
precision mediump float;
#endif

varying vec3 v_texCoord0;

void main() {
	gl_FragColor = vec4(v_texCoord0, 1.0);
}
