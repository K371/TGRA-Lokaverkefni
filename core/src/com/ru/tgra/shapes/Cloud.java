package com.ru.tgra.shapes;

public class Cloud {
	private float x, y, z;
	
	public Cloud(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public void drawCloud(Shader shader){
		ModelMatrix.main.pushMatrix();
		
		ModelMatrix.main.addTranslation(x, y, z);
		ModelMatrix.main.addScale(4, 1, 2);
		
		shader.setMaterialDiffuse(1.0f, 1.0f, 1.0f, 1.0f);
		
		
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SphereGraphic.drawSolidSphere();
		
		ModelMatrix.main.addTranslation(1, 0, 1);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SphereGraphic.drawSolidSphere();
		
		ModelMatrix.main.addTranslation(-0.5f, 1, 0);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SphereGraphic.drawSolidSphere();
		
		
		
		shader.setMaterialDiffuse(0.2f, 0.2f, 0.2f, 1.0f);
		
		ModelMatrix.main.popMatrix();
	}

}
