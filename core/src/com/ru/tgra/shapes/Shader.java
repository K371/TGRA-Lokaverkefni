package com.ru.tgra.shapes;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Shader {
		
		private int renderingProgramID;
		private int vertexShaderID;
		private int fragmentShaderID;

		private int positionLoc;
		private int normalLoc;
		private int uvLoc;

		private int modelMatrixLoc;
		private int viewMatrixLoc;
		private int projectionMatrixLoc;
		
		private int eyePosLoc;

		private int globalAmbLoc;
		//private int colorLoc;
		
		private int lightPosLoc;
		private int lightPosLoc2;
		private int lightPosLoc3;
		private int lightColorLoc;
		private int lightColorLoc2;
		private int lightColorLoc3;
		private int lightColorLoc4;
		private int matDifLoc;
		private int matSpecLoc;
		private int matShineLoc;
		private int matEmissionLoc;

		
	public Shader() {
		
		String vertexShaderString;
		String fragmentShaderString;

		vertexShaderString = Gdx.files.internal("shaders/simple3D.vert").readString();
		fragmentShaderString =  Gdx.files.internal("shaders/simple3D.frag").readString();

		vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);
	
		Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
		Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);
	
		Gdx.gl.glCompileShader(vertexShaderID);
		Gdx.gl.glCompileShader(fragmentShaderID);
		
		System.out.println(Gdx.gl.glGetShaderInfoLog(vertexShaderID));
		System.out.println(Gdx.gl.glGetShaderInfoLog(fragmentShaderID));

		renderingProgramID = Gdx.gl.glCreateProgram();
	
		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);
	
		Gdx.gl.glLinkProgram(renderingProgramID);

		positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(positionLoc);

		normalLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_normal");
		Gdx.gl.glEnableVertexAttribArray(normalLoc);
		
		uvLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_uv");
		Gdx.gl.glEnableVertexAttribArray(uvLoc);

		modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		viewMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
		projectionMatrixLoc		= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

		/*colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");*/
		
		eyePosLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_eyePosition");

		globalAmbLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_globalAmbient");
		lightPosLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightPosition");
		lightPosLoc2			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightPosition2");
		lightPosLoc3			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightPosition3");
		lightColorLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightColor");
		lightColorLoc2			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightColor2");
		lightColorLoc3			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightColor3");
		lightColorLoc4			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightColor4");
		matDifLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialDiffuse");
		matSpecLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialSpecular");
		matShineLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialShininess");
		matEmissionLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialEmission");

		
		Gdx.gl.glUseProgram(renderingProgramID);
		
		
	}
/*
	public void setColor(float r, float g, float b, float a){
		
		Gdx.gl.glUniform4f(colorLoc, r, g, b, a);
	}
*/
	public void setEyePosition(float x, float y, float z, float w){
		
		Gdx.gl.glUniform4f(eyePosLoc, x, y, z, w);
	}
	
	public void setGlobalAmbient(float r, float g, float b, float a){
		
		Gdx.gl.glUniform4f(globalAmbLoc, r, g, b, a);
	}
	
	public void setLightPosition(float x, float y, float z, float w){
	
		Gdx.gl.glUniform4f(lightPosLoc, x, y, z, w);
	}
	
	public void setLightPosition2(float x, float y, float z, float w){
		
		Gdx.gl.glUniform4f(lightPosLoc2, x, y, z, w);
	}

	public void setLightPosition3(float x, float y, float z, float w){
		
		Gdx.gl.glUniform4f(lightPosLoc3, x, y, z, w);
	}

	public void setLightColor(float r, float g, float b, float a){
		
		Gdx.gl.glUniform4f(lightColorLoc, r, g, b, a);
	}

	public void setLightColor2(float r, float g, float b, float a){
		
		Gdx.gl.glUniform4f(lightColorLoc2, r, g, b, a);
	}

	public void setLightColor3(float r, float g, float b, float a){
		
		Gdx.gl.glUniform4f(lightColorLoc3, r, g, b, a);
	}

	public void setLightColor4(float r, float g, float b, float a){
		
		Gdx.gl.glUniform4f(lightColorLoc4, r, g, b, a);
	}

	public void setMaterialDiffuse(float r, float g, float b, float a){
		
		Gdx.gl.glUniform4f(matDifLoc, r, g, b, a);
	}
	
	public void setMaterialSpecular(float r, float g, float b, float a){
		
		Gdx.gl.glUniform4f(matSpecLoc, r, g, b, a);
	}

	public void setMaterialEmission(float r, float g, float b, float a){
		
		Gdx.gl.glUniform4f(matEmissionLoc, r, g, b, a);
	}
	
	public void setShininess(float shine){
		
		Gdx.gl.glUniform1f(matShineLoc, shine);
	}
	
	public int getVertexPointer() {
		
		return positionLoc;
	}
	
	public int getNormalPointer() {
		
		return normalLoc;
	}
	
	public int getUVPointer()
	{
		return uvLoc;
	}

	public void setModelMatrix(FloatBuffer matrix) {
		
		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, matrix);
	}
	
	public void setViewMatrix(FloatBuffer matrix) {
		
		Gdx.gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, matrix);
	}

	public void setProjectionMatrix(FloatBuffer matrix) {
	
		Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, matrix);
	}
}










