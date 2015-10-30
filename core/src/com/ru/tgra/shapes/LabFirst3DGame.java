package com.ru.tgra.shapes;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.List;

public class LabFirst3DGame extends ApplicationAdapter {

	Shader shader;
	
	private float angle;
	private float leftAngle;
	private float upAngle;
	private float objectRotationAngle;
	private float objectPitchAngle;

	public static int colorLoc;
	
	private Camera cam;
	private ArrayList<Projectile> projectiles;
	//private Camera orthoCam;
	
	private float fov = 100.0f;
	
	private Maze maze;
	
	private Sound sound;
	private Sound clayBreak;
	private Sound thrower;
	//private Sound winSong;
	//private boolean win;
	//private long winTrack;
	//private long track;
	//private float volume;

	@Override
	public void create () 
	{
		projectiles = new ArrayList<Projectile>();
		leftAngle = 315;
		upAngle = 0;
		//win = true;
		//volume = 1;
		
		shader = new Shader();
		maze = new Maze(15, 15);
		

		BoxGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SphereGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SincGraphic.create(shader.getVertexPointer());
		CoordFrameGraphic.create(shader.getVertexPointer());

		Gdx.gl.glClearColor(0.4f, 0.4f, 1.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		cam = new Camera();
		cam.look(new Point3D(1.5f, 1f, -0.5f), new Point3D(2.5f,1,-1.5f), new Vector3D(0,1,0));
		//orthoCam = new Camera();
		//orthoCam.orthographicProjection(-10, 10, -10, 10, 3.0f, 100);
		
		Gdx.input.setCursorCatched(true);
		sound = Gdx.audio.newSound(Gdx.files.internal("ShotgunBoom.mp3"));
		clayBreak = Gdx.audio.newSound(Gdx.files.internal("ClayBreaking.mp3"));
		thrower = Gdx.audio.newSound(Gdx.files.internal("Thrower.mp3"));
		clayBreak.setVolume(1, 0.2f);
		//winSong = Gdx.audio.newSound(Gdx.files.internal("celebrate.mp3"));
		//track = sound.play(1);
		
		
		
	}
	
	private void update()
	{
		leftAngle %= 360;
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		/* If we have reached the area where the z coord is less than -13 then we have reached 
		 * our end goal, quit playing our stressful music and start playing celebratory music */
		/*
		if(cam.eye.z < -13 && win){
			win = false;
			sound.dispose();
			winTrack = winSong.play(1);
		}
		*/
		float soundAngle = leftAngle;
		soundAngle %= 360;
		float pan;
		if(soundAngle < 180 && soundAngle > 0){
			pan = soundAngle / 180;
			pan = 1-pan;
			
			//System.out.println(pan);
		}
		else{
			
		}
		
		
		angle += 180.0f * deltaTime;
		
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.slide(-3.0f * deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			cam.slide(3.0f * deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.walkForward(3.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.walkForward(-3.0f * deltaTime);
		}
		
		/* Mute functionality */
		/*if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			if(volume == 1){
				volume = 0;
			}
			else{
				volume = 1;
			}
			if(win){
				sound.setVolume(track, volume);
			}
			else{
				winSong.setVolume(winTrack, volume);
			}
		}
		*/
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			cam.rotateY(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cam.rotateY(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			cam.pitch(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			cam.pitch(-90.0f * deltaTime);
		}
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.graphics.setDisplayMode(500, 500, false);
			/*if(!win){
				winSong.dispose();
			}else{
				sound.dispose();
			}*/
			sound.dispose();
			clayBreak.dispose();
			thrower.dispose();
			Gdx.app.exit();
		}
		float changeX = -0.2f * Gdx.input.getDeltaX();
		float changeY = -0.2f * Gdx.input.getDeltaY();
		leftAngle += changeX;
		
		if(upAngle + changeY <= 70 && upAngle + changeY >= -85){
			upAngle += changeY;
		}
		
		cam.rotateY(changeX);
		cam.pitch(changeY);	
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			Projectile projectile = new Projectile();
			projectile.setX(cam.eye.x);
			projectile.setY(cam.eye.y);
			projectile.setZ(cam.eye.z);
			projectile.setPitch(upAngle);
			projectile.setRotation(leftAngle);
			projectiles.add(projectile);
			sound.play(1);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
			Projectile projectile = new Projectile();
			projectile.setX(0);
			projectile.setY(0);
			projectile.setZ(0);
			projectile.setPitch(45);
			projectile.setRotation(-45);
			projectile.setPigeon(true);
			projectiles.add(projectile);
			Projectile projectile2 = new Projectile();
			projectile2.setX(10);
			projectile2.setY(0);
			projectile2.setZ(0);
			projectile2.setPitch(55);
			projectile2.setRotation(45);
			projectile2.setPigeon(true);
			projectiles.add(projectile2);
			
		}
	}
	
	private void display()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glUniform4f(LabFirst3DGame.colorLoc, 1.0f, 1.0f, 1.0f, 1.0f);
		for(int viewNum = 0; viewNum < 1; viewNum++)
		{
			if(viewNum == 0)
			{
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				cam.perspectiveProjection(fov, 1.0f, 0.1f, 100.0f);
				shader.setViewMatrix(cam.getViewMatrix());
				shader.setProjectionMatrix(cam.getProjectionMatrix());
				shader.setEyePosition(cam.eye.x, cam.eye.y, cam.eye.z, 1.0f);
			}
			else
			{
				
				/*Gdx.gl.glViewport(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
				orthoCam.look(new Point3D(7.0f, 40.0f, -7.0f), new Point3D(7.0f, 0.0f, -7.0f), new Vector3D(0,0,-1));
				shader.setViewMatrix(orthoCam.getViewMatrix());
				shader.setProjectionMatrix(orthoCam.getProjectionMatrix());
				shader.setEyePosition(orthoCam.eye.x, orthoCam.eye.y, orthoCam.eye.z, 1.0f);
				*/
			}
		
			ModelMatrix.main.loadIdentityMatrix();
			
			/* Reticle */
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);
			ModelMatrix.main.addRotationY(leftAngle);
			ModelMatrix.main.addRotationX(upAngle);
			ModelMatrix.main.addTranslation(0, 0, -0.3f);
			ModelMatrix.main.addScale(0.005f, 0.005f, 0.1f);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			SphereGraphic.drawOutlineSphere();
			ModelMatrix.main.popMatrix();
			
			/* Barrel */
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);
			ModelMatrix.main.addRotationY(leftAngle);
			ModelMatrix.main.addRotationX(upAngle);
			ModelMatrix.main.addTranslation(0.05f, -0.2f, 0);
			ModelMatrix.main.addScale(0.05f, 0.05f, 3);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			SphereGraphic.drawOutlineSphere();
			ModelMatrix.main.popMatrix();
			
			
			for(Projectile p : projectiles){
				p.drawProjectile(shader);
			}
			
			for(int i = 0; i < projectiles.size(); i++){
				for(int j = 0; j < projectiles.size(); j++){
					if(i!=j && !projectiles.get(i).getPigeon()){
						if(projectiles.get(i).distance(projectiles.get(j).getV()) < 0.3f){
							projectiles.get(i).setHit(true);
							projectiles.get(j).setHit(true);
							System.out.println("HIT!");
							clayBreak.play(1);
							break;
						}
					}
				}
			}
		
			
			for(Projectile p : projectiles){
				if(p.getAbsoluteY() <= 0 || p.getAbsoluteY() > 30 || p.getAbsoluteZ() < -30 || p.getAbsoluteZ() > 30 || p.getAbsoluteX() < -30 || p.getAbsoluteX() > 30){
					projectiles.remove(p);
					break;
				}
			}
			float s = (float)Math.sin(angle * Math.PI / 180.0);
			float c = (float)Math.cos(angle * Math.PI / 180.0);
			
			
			//Light 1
			shader.setLightPosition(4 + 10.0f, 7.0f,4 -10.0f, 1.0f);
			shader.setLightColor(1.0f, 1.0f, 1.0f, 1.0f);

			//Light 2
			shader.setLightPosition2(4 + 4.0f, 7.0f, 4 - 6.0f, 1.0f);
			shader.setLightColor2(1.0f, 1.0f, 1.0f, 1.0f);


			//Light 3
			shader.setLightPosition3(4 + 8.0f, 7.0f, 4 -1.0f, 1.0f);
			shader.setLightColor3(1.0f, 1.0f, 1.0f, 1.0f);

			//Directional light
			shader.setLightColor4(0.9f, 0.9f, 0.9f, 1.0f);
			

			
			shader.setGlobalAmbient(0.0f, 1.0f, 0.0f, 1);
			shader.setMaterialEmission(1.0f, 1.0f, 1.0f, 1.0f);
			shader.setMaterialDiffuse(0.3f, 0.4f, 0, 1);
			shader.setMaterialSpecular(0, 0.9f, 0, 1);
			
			/*
			//Lightbulp 1
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(4 * s + 10.0f, 7.0f,4 * c -10.0f);
			ModelMatrix.main.addScale(0.1f, 0.1f, 0.1f);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			SphereGraphic.drawSolidSphere();
			ModelMatrix.main.popMatrix();
			
			//Lightbulp 2
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(4 * s + 4.0f, 7.0f, 4 * c - 6.0f);
			ModelMatrix.main.addScale(0.1f, 0.1f, 0.1f);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			SphereGraphic.drawSolidSphere();
			ModelMatrix.main.popMatrix();
			
			//Lightbulp 3
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addTranslation(4 * s + 8.0f, 7.0f, 4 * c - 1.0f);
			ModelMatrix.main.addScale(0.1f, 0.1f, 0.1f);
			shader.setModelMatrix(ModelMatrix.main.getMatrix());
			SphereGraphic.drawSolidSphere();
			ModelMatrix.main.popMatrix();
			*/
			shader.setMaterialDiffuse(0.3f, 0.3f, 0.7f, 1.0f);
			shader.setMaterialSpecular(1.0f, 1.0f, 1.0f, 1.0f);
			shader.setMaterialEmission(0, 0, 0, 1);
			shader.setShininess(10.0f);
			
			
			
			

			//drawExtraObjects();
			
			maze.drawMaze();
			
			if(viewNum == 1)
			{
				//shader.setMaterialDiffuse(1.0f, 0.3f, 0.1f, 1.0f);
				
				ModelMatrix.main.pushMatrix();
				ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);

				ModelMatrix.main.addScale(0.25f, 0.25f, 0.25f);
				shader.setModelMatrix(ModelMatrix.main.getMatrix());
				SphereGraphic.drawSolidSphere();

				ModelMatrix.main.popMatrix();				
			}
		}
	}

	@Override
	public void render () {
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();

	}
	/* A method used to draw the extra objects in our project. They are best seen
	 * when the end of the maze has been reached. The objects are a large cross
	 * that the user can't pass through, a flying box with spheres orbiting it,
	 * and two other spheres.
	 */
	public void drawExtraObjects() {
		//draw collidable object
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(7.5f, 1, -16.0f);

		ModelMatrix.main.addScale(0.5f, 0.5f, 0.5f);
		objectRotationAngle += 45 * Gdx.graphics.getDeltaTime();
		ModelMatrix.main.addRotationY(objectRotationAngle);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube();
		ModelMatrix.main.popMatrix();
		
		//draw floating objects
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(7.5f, 10.0f, -14.0f);
		ModelMatrix.main.addScale(1, 2, 1);
		objectRotationAngle += 45 * Gdx.graphics.getDeltaTime();
		ModelMatrix.main.addRotationY(objectRotationAngle);
		ModelMatrix.main.addTranslation(0, 0, 1);
		ModelMatrix.main.addRotationX(-objectRotationAngle);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		BoxGraphic.drawSolidCube();
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addScale(0.25f, 0.25f, 0.25f);
		ModelMatrix.main.addRotationY(objectRotationAngle);
		ModelMatrix.main.addTranslation(6, 0, 0);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SphereGraphic.drawSolidSphere();
		ModelMatrix.main.popMatrix();
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addScale(0.25f, 0.25f, 0.25f);
		ModelMatrix.main.addRotationY(-objectRotationAngle);
		ModelMatrix.main.addTranslation(0, 0, 8);
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		SphereGraphic.drawSolidSphere();
		ModelMatrix.main.popMatrix();
		ModelMatrix.main.popMatrix();
	}
}