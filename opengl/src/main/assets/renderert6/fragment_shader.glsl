precision mediump float;

// 2d纹理。
uniform sampler2D u_TextureUnit;

// 纹理st坐标。
varying vec2 v_TextureCoordinates;

// 模糊半径。
uniform int u_Radius;

uniform float u_WidthOffset;
uniform float u_HeightOffset;

uniform int u_IsDrawOrigin;

void main() {
    if (u_IsDrawOrigin == 1) {
        gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
        return;
    }

    int diameter = 2 * u_Radius + 1;
    vec4 sampleTex;
    vec3 color = vec3(0, 0, 0);
    float weightSum = 0.0;
    for (int i = 0; i < diameter; i++) {
        vec2 offset = vec2(float(i - u_Radius) * u_WidthOffset, float(i - u_Radius) * u_HeightOffset);
        sampleTex = vec4(texture2D(u_TextureUnit, v_TextureCoordinates.st + offset));

        // stack模糊
        float boxWeight = float(u_Radius) + 1.0 - abs(float(i) - float(u_Radius));
        color += sampleTex.rgb * boxWeight;
        weightSum += boxWeight;
    }
    gl_FragColor = vec4(color / weightSum, sampleTex.a);
}
// 均值模糊
//        float boxWeight = float(1.0) / float(diameter);
//        color += sampleTex.rgb * boxWeight;
//        weightSum += boxWeight;
