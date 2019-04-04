attribute vec2 vertexPos;
attribute vec2 texturePos;
uniform mat4 view;
uniform mat4 model;
uniform mat4 projection;
varying vec2 textureCoord;
void main()
{
   textureCoord=texturePos;
   gl_Position=projection*view*model*vec4(vertexPos,1.0,1.0);
}