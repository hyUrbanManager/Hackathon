precision mediump float;

// 2d纹理。
uniform sampler2D u_TextureUnit;

// 纹理st坐标。
varying vec2 v_TextureCoordinates;

void main() {
    vec2 texturePosition = vec2(v_TextureCoordinates.x, 0.5);
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
}