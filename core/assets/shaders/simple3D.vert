
#ifdef GL_ES
precision mediump float;
#endif

attribute vec3 a_position;
attribute vec3 a_normal;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;

uniform vec4 u_eyePosition;

uniform vec4 u_lightPosition;
uniform vec4 u_lightPosition2;
uniform vec4 u_lightPosition3;
uniform vec4 u_lightColor;
uniform vec4 u_lightColor2;
uniform vec4 u_lightColor3;
uniform vec4 u_lightColor4;

uniform vec4 u_globalAmbient;

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;

uniform float u_materialShininess;

uniform vec4 u_materialEmission;

varying vec4 v_normal;
varying vec4 v_color;

void main()
{
	vec4 position = vec4(a_position.x, a_position.y, a_position.z, 1.0);
	position = u_modelMatrix * position;

	vec4 normal = vec4(a_normal.x, a_normal.y, a_normal.z, 0.0);
	normal = u_modelMatrix * normal;
	
	//global coordinates
	
	
	//lighting
	
	v_normal = normal;
	
	vec4 v = u_eyePosition - position; //direction to the camera
	
	
	// light 1 (Position light)
	vec4 v_s = u_lightPosition - position; //direction to the light
	vec4 v_h = v_s + v;
	
	float lambert = max(0.0, dot(v_normal, v_s) / (length(v_normal) * length(v_s))); //The intensity of how the light hits the surfice.
	float phong = max(0.0, dot(v_normal, v_h) / (length(v_normal) * length(v_h)));
	
	vec4 diffuseColor = lambert * u_lightColor * u_materialDiffuse;
	vec4 specularColor = pow(phong, u_materialShininess) * u_lightColor * u_materialSpecular;
	
	vec4 lightCalcColor1 = diffuseColor + specularColor; 
	
	//end light 1
	
	
	
	// light 2 (Position light)
	v_s = u_lightPosition2 - position; //direction to the light
	v_h = v_s + v;
	
	lambert = max(0.0, dot(v_normal, v_s) / (length(v_normal) * length(v_s))); //The intensity of how the light hits the surfice.
	phong = max(0.0, dot(v_normal, v_h) / (length(v_normal) * length(v_h)));
	
	diffuseColor = lambert * u_lightColor2 * u_materialDiffuse;
	specularColor = pow(phong, u_materialShininess) * u_lightColor2 * u_materialSpecular;
	
	vec4 lightCalcColor2 = diffuseColor + specularColor; 
	
	//end light 2
	
	
	
	// light 3 (Position light)
	v_s = u_lightPosition2 - position; //direction to the light
	v_h = v_s + v;
	
	lambert = max(0.0, dot(v_normal, v_s) / (length(v_normal) * length(v_s))); //The intensity of how the light hits the surfice.
	phong = max(0.0, dot(v_normal, v_h) / (length(v_normal) * length(v_h)));
	
	diffuseColor = lambert * u_lightColor3 * u_materialDiffuse;
	specularColor = pow(phong, u_materialShininess) * u_lightColor3 * u_materialSpecular;
	
	vec4 lightCalcColor3 = diffuseColor + specularColor; 
	
	//end light 3
	
	
	
	
	// light 4 (Directional)

	vec4 lightCalcColor4 = (dot(normal, vec4(0,0,1,0)) / length(normal)) * u_lightColor4;

	//end light 4
	
	
	
	v_color = u_globalAmbient * u_materialDiffuse + u_materialEmission + lightCalcColor1 + lightCalcColor2 + lightCalcColor3 + lightCalcColor4;
	
	
	position = u_viewMatrix * position;



	gl_Position = u_projectionMatrix * position;
}