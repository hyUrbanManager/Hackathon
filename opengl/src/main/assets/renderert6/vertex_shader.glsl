// 顶点坐标。
attribute vec4 a_Position;
// 纹理uv坐标。
attribute vec2 a_TextureCoordinates;

// varying纹理坐标。
varying vec2 v_TextureCoordinates;

void main() {
    v_TextureCoordinates = a_TextureCoordinates;
    gl_Position = a_Position;
}