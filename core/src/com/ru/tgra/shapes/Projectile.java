package com.ru.tgra.shapes;

import com.badlogic.gdx.math.Vector3;

public class Projectile {
	private float pitch;
	private float rotation;
	private float x, y, z, move;
	private boolean pigeon;
	private Vector3 v;
	private boolean hit;
	
	public void setHit(boolean hit){
		this.hit = hit;
	}
	
	public Projectile(){
		v = new Vector3();
		hit = false;
		pigeon = false;
	}
	public void setPigeon(boolean isIt){
		pigeon = isIt;
	}
	
	public float getPitch() {
		return pitch;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
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
	public float getAbsoluteY(){
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(x, y, z);
		ModelMatrix.main.addRotationY(rotation);
		ModelMatrix.main.addRotationX(pitch);
		ModelMatrix.main.addTranslation(0, 0, move);
		float X = ModelMatrix.main.matrix.get(12);
		float Y = ModelMatrix.main.matrix.get(13);
		float Z = ModelMatrix.main.matrix.get(14);
		
		ModelMatrix.main.popMatrix();
		
		return Y;
	}
	
	public float getAbsoluteX(){
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(x, y, z);
		ModelMatrix.main.addRotationY(rotation);
		ModelMatrix.main.addRotationX(pitch);
		ModelMatrix.main.addTranslation(0, 0, move);
		float X = ModelMatrix.main.matrix.get(12);
		float Y = ModelMatrix.main.matrix.get(13);
		float Z = ModelMatrix.main.matrix.get(14);
		ModelMatrix.main.popMatrix();
		
		return X;
	}
	
	public boolean getPigeon(){
		return pigeon;
	}
	
	public float getAbsoluteZ(){
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(x, y, z);
		ModelMatrix.main.addRotationY(rotation);
		ModelMatrix.main.addRotationX(pitch);
		ModelMatrix.main.addTranslation(0, 0, move);
		float X = ModelMatrix.main.matrix.get(12);
		float Y = ModelMatrix.main.matrix.get(13);
		float Z = ModelMatrix.main.matrix.get(14);
		ModelMatrix.main.popMatrix();
		
		return Z;
	}
	
	public float getMove(){
		return move;
	}
	
	public Vector3 getV(){
		return v;
	}
	
	public double distance(Vector3 v2){
		return Math.sqrt(v.dst2(v2));
	}
	
	public void drawProjectile(Shader shader){
		ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(x, y, z);
			ModelMatrix.main.addRotationY(rotation);
			ModelMatrix.main.addRotationX(pitch);
			ModelMatrix.main.addTranslation(0, 0, move);
			float X = ModelMatrix.main.matrix.get(12);
			float Y = ModelMatrix.main.matrix.get(13);
			float Z = ModelMatrix.main.matrix.get(14);
			
			v.x = X;
			v.y = Y;
			v.z = Z;
				
			ModelMatrix.main.addScale(0.1f, 0.1f, 0.1f);
			
			move -=0.1f;
			
			if(pigeon && !hit){
				
				pitch -= 0.18f;
				ModelMatrix.main.addScale(1, 0.15f, 1);
				shader.setModelMatrix(ModelMatrix.main.getMatrix());
				SphereGraphic.drawSolidSphere();
			}
			else if(!pigeon){
				move -=0.25f;
				shader.setModelMatrix(ModelMatrix.main.getMatrix());
				SphereGraphic.drawSolidSphere();
			}
			
			
		
		ModelMatrix.main.popMatrix();
	}
	
	
	
	
}
