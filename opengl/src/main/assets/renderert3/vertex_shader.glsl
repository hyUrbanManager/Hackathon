attribute vec4 my_Position;
attribute vec4 my_Color;

varying vec4 v_Color;

void main() {
    v_Color = my_Color;
    gl_Position = my_Position;
}