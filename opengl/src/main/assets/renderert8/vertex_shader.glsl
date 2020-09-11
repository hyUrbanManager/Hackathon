attribute vec4 my_Position;

varying vec4 v_Color;

// 自定义颜色
void main() {
    // 等边三角
//    if (my_Position.x > 0.0) {
//        v_Color = vec4(0.0, 0.0, 1.0, 1.0);
//    } else if (my_Position.x <0.0) {
//        v_Color = vec4(0.0, 1.0, 0.0, 1.0);
//    } else if (my_Position.x == 0.0) {
//        v_Color = vec4(1.0, 0.0, 0.0, 1.0);
//    }

    if (my_Position.x > 0.0) {
        v_Color = vec4(0.0, 1.0, 1.0, 1.0);
    } else if (my_Position.x <0.0) {
        v_Color = vec4(1.0, 1.0, 0.0, 1.0);
    } else if (my_Position.x == 0.0) {
        v_Color = vec4(1.0, 0.0, 1.0, 1.0);
    }

    gl_Position = my_Position;
}