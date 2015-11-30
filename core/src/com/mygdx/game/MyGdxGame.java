package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.cameras.Cam;
import com.mygdx.game.cameras.OrthographicCam;
import com.mygdx.game.lights.DirectionalLight;
import com.mygdx.game.lights.Light;
import com.mygdx.game.lights.PointLight;
import com.mygdx.game.lights.SpotLight;
import com.mygdx.game.models.Cube;
import com.mygdx.game.models.Model;
import com.mygdx.game.models.Ship;

public class MyGdxGame extends ApplicationAdapter {
	public static String dataFolder = "./data/";
	SpriteBatch batch;
	private List<ShaderProgram> shaders = new ArrayList<ShaderProgram>();

	ShaderProgram spot_light_shaderProgram;
	ShaderProgram point_light_shaderProgram;
	ShaderProgram directional_light_shaderProgram;
	// character
	ShaderProgram character_shaderProgram;
	Vector3 characterPosition = new Vector3(new float[] { 0f, 0f, -1f });
	AnimationController animation_controller;
	ModelInstance character_model_instance;
	boolean isInitialized = false;
	AssetManager assets;
	boolean character_animation_on = true;
	//

	private Cam camera;
	private InputMultiplexer multiplexer;
	private Ship spaceShip1;
	private Ship spaceShip2;
	private List<Model> objects = new ArrayList<Model>();
	
	//lights
	private List<Light> lights = new ArrayList<Light>();
	private boolean lights_on =true;
	private Vector3 directional_original_position;
	private Vector3 directional_current_position;
	private Vector3 spotlight_original_direction;
	private Vector3 spotlight_current_direction;
	private float delta=0.05f;
	private float limit=3.5f;
	private SpotLight spotlight;
	private PointLight pointlight;
	private Vector3 pointlight_original_position;
	private Vector3 pointlight_current_position;
	private Vector3 ship1_original_position;
	private Vector3 ship1_current_position;
	private Ship ship1;
	private float delta_ship=0.01f;
	private float limit_ship=0.2f;
	// shadows
	private static final int PRIMITIVE_TYPE= GL20.GL_TRIANGLES;
	ShaderProgram depthMapper;
	ShaderProgram directional_shadow_shader;
	DirectionalLight directionalLight;
	FrameBuffer shadowBuffer;
	public static final int DEPTHMAPIZE = 1024;
	private boolean shadows_on=true;
	private static boolean DEBUGGING_DEPTHMAP=false;
	
	
	private ShaderProgram createShader(String dataFolder, String VSfilename, String FSfilename) {
		ShaderProgram shader;
		String vs = Gdx.files.internal(dataFolder + VSfilename).readString();
		String fs = Gdx.files.internal(dataFolder + FSfilename).readString();
		shader = new ShaderProgram(vs, fs);
		shader.pedantic = false;
		System.out.println(shader.getLog());
		return shader;
	}

	private void createCharacter() {
		character_shaderProgram = createShader(dataFolder, "characterVS.glsl", "characterFS.glsl");
		shaders.add(character_shaderProgram);
		assets = new AssetManager();
		// assets.load(dataFolder + "Dave.g3dj",
		// com.badlogic.gdx.graphics.g3d.Model
		// .class);

		assets.load(dataFolder + "Dave.g3db", com.badlogic.gdx.graphics.g3d.Model.class);
		assets.finishLoading();
		// com.badlogic.gdx.graphics.g3d.Model characterModel = assets
		// .get(dataFolder + "Dave.g3db");

		// com.badlogic.gdx.graphics.g3d.Model characterModel = null;
		//
		// characterModel = assets
		// .get("/home/fbejaran/TPVideojuegos/desktop/data/Dave.g3db");
		//
		// character_model_instance = new ModelInstance(characterModel);
		// animation_controller = new
		// AnimationController(character_model_instance);
		// animation_controller.animate(
		// character_model_instance.animations.get(0).id, -1, 1f, null,
		// 0.2f); // Starts the animaton

	}

	public void updateCharacter(AnimationController animationController) {
		animationController.update(Gdx.graphics.getDeltaTime());

	}

	public void renderCharacter(ShaderProgram charShader) {

		if (assets.isLoaded(dataFolder + "Dave.g3db")) {

			if (!isInitialized) {

				com.badlogic.gdx.graphics.g3d.Model characterModel = null;

				characterModel = assets.get(dataFolder + "Dave.g3db");

				character_model_instance = new ModelInstance(characterModel);
				animation_controller = new AnimationController(character_model_instance);
				animation_controller.animate(character_model_instance.animations.get(0).id, -1, 1f, null, 0.2f); // Starts
																													// the
																													// animaton

				isInitialized = true;
			}

			charShader.begin();
			// Bind whatever uniforms / textures you need
			Texture texture = new Texture(dataFolder + "uv_dave_mapeo.jpg");
			// Texture texture = new Texture(dataFolder + "tile.png");

			texture.bind();
			charShader.setUniformi("u_texture", 0);
			Matrix4 modelMatrix = new Matrix4().translate(characterPosition);
			Matrix4 view = camera.getV();
			Matrix4 viewProjection = camera.getVP();

			Matrix4 mvpMatrix = new Matrix4(modelMatrix);
			mvpMatrix.mul(viewProjection);
			Matrix4 u_modelViewMatrix = new Matrix4(modelMatrix).mul(view);
			Array<Renderable> renderables = new Array<Renderable>();
			final Pool<Renderable> pool = new Pool<Renderable>() {
				@Override
				protected Renderable newObject() {
					return new Renderable();
				}

				@Override
				public Renderable obtain() {
					Renderable renderable = super.obtain();
					renderable.material = null;
					renderable.mesh = null;
					renderable.shader = null;
					return renderable;
				}
			};
			character_model_instance.getRenderables(renderables, pool);
			Matrix4 idtMatrix = new Matrix4();
			float[] bones = new float[12 * 16];
			for (int i = 0; i < bones.length; i++)
				bones[i] = idtMatrix.val[i % 16];
			for (Renderable render : renderables) {

				// mvpMatrix.set(g.cam.combined);
				// mvpMatrix.mul(render.worldTransform);
				charShader.setUniformMatrix("u_mvp", mvpMatrix);
				charShader.setUniformMatrix("u_modelViewMatrix", u_modelViewMatrix);
				// charShader.setUniformMatrix("pepe", mvpMatrix);
				// StaticVariables.tempMatrix.idt();
				Matrix4 nMatrix = new Matrix4(u_modelViewMatrix);
				nMatrix.inv();
				nMatrix.tra();
				charShader.setUniformMatrix("u_normalMatrix", nMatrix);
				for (int i = 0; i < bones.length; i++) {
					final int idx = i / 16;
					bones[i] = (render.bones == null || idx >= render.bones.length || render.bones[idx] == null) ? idtMatrix.val[i % 16]
							: render.bones[idx].val[i % 16];
				}
				charShader.setUniformMatrix4fv("u_bones", bones, 0, bones.length);
				render.mesh.render(charShader, render.primitiveType, render.meshPartOffset, render.meshPartSize);
			}
			charShader.end();
			// System.out.println("se rendereo el personaje");

		}

	}

	@Override
	public void create() {
		directional_shadow_shader=createShader(dataFolder, 
				"DirectionalShadowVS.glsl", 
				"DirectionalShadowFS.glsl");
		depthMapper = createShader(dataFolder, "DepthMapperVS.glsl", "DepthMapperFS.glsl");
		directional_light_shaderProgram = createShader(dataFolder, "DirectionalLightsVS.glsl",
				"DirectionalLightsFS.glsl");

		spot_light_shaderProgram = createShader(dataFolder, "SpotLightVS.glsl", "SpotLightsFS.glsl");

		point_light_shaderProgram = createShader(dataFolder, "PointLightsVS.glsl", "PointLightsFS.glsl");

		
		
		shaders.add(spot_light_shaderProgram);
		shaders.add(point_light_shaderProgram);
		//shaders.add(directional_light_shaderProgram);
		shaders.add(depthMapper);
		shaders.add(directional_shadow_shader);

		// objetos de la escena central [SPOTLIGHT]
		spaceShip1 = new Ship(dataFolder, new Vector3(new float[] { 0f, 0f, 0f }));
		spaceShip2 = new Ship(dataFolder, new Vector3(new float[] { 1f, 0f, 0f }));
		objects.add(spaceShip1);
		objects.add(spaceShip2);
		Cube cube = new Cube(dataFolder, new Vector3(new float[] { 0f, -0.8f, 0.5f }));
		objects.add(cube);

		
		
		//objetos de la escena derecha [POINTLIGHT]
		objects.add(new Ship(dataFolder, new Vector3(new float[] { 2f, 0f, 0f })));
		objects.add(new Ship(dataFolder, new Vector3(new float[] { 3f, 0f, 0f })));
		objects.add(new Cube(dataFolder, new Vector3(new float[] { 2f, -0.8f, 0.5f })));
		
		
		//objetos de la escena izquierda [DIRECTIONAL LIGHT]
		objects.add(new Ship(dataFolder, new Vector3(new float[] { -1f, 0f, 0f })));
		ship1_original_position=new Vector3(new float[] { -2f, 0f, 0f });
		ship1=new Ship(dataFolder, new Vector3(ship1_original_position));
		objects.add(ship1);
		objects.add(new Cube(dataFolder, new Vector3(new float[] { -2f, -0.8f, 0.5f })));
//		
		
		// camara

		camera = new OrthographicCam(this);
		multiplexer = new InputMultiplexer(camera);
		Gdx.input.setInputProcessor(multiplexer);
		Vector3 cam_pos = new Vector3(new float[] { 1.75f, 0f, 1f });
		camera.setPosition(cam_pos);

		/*
		 *  luces
		 */
		
		spotlight_original_direction=new Vector3(new float[] { 0f, -0.1f,
				 0.0f });
		spotlight=new SpotLight(spot_light_shaderProgram, 
				new Vector3(new
						 float[] { 1.5f, 2f, 0.2f }), 
						 new Vector3( new float[] { 0f, 1f, 0f }),
						 new Vector3(spotlight_original_direction));
		lights.add(spotlight);
		
		
		
		pointlight_original_position=new Vector3(new
				 float[] { 6f, 1f, 0.1f });
		pointlight=new PointLight(point_light_shaderProgram,
				new Vector3(pointlight_original_position) ,
					new Vector3( new float[] { 1f, 0f, 0f }));
		 lights.add(pointlight);
//	
		 
		
		
		float x_directional=-3.5f;
		float z_directional=-0.1f;
		directional_original_position=new Vector3(new float[] { x_directional, 5f, z_directional });
		directionalLight = new DirectionalLight(
				directional_shadow_shader,
				new Vector3(directional_original_position), 
				new Vector3(new float[] { x_directional, 0f, z_directional }), 
				new Vector3(new float[] {1f, 1f, 1f }));
		
		lights.add(directionalLight);
//		// personaje

		createCharacter();
	}

	private void updateLights(){
		
		if(directional_current_position==null)
			directional_current_position=directionalLight.getPosition();
		
		directional_current_position.x+=delta;
		float displacement=directional_original_position.x-directional_current_position.x;
		
		if(spotlight_current_direction==null)
			spotlight_current_direction=spotlight.getPosition();
		
		spotlight_current_direction.x+=delta;
				
		
		if(pointlight_current_position==null)
			pointlight_current_position=pointlight.getPosition();
		
		pointlight_current_position.x+=delta;
		

		
		if(Math.abs(displacement)>=limit)
			delta=-delta;
		
		
	}
	public void updateShip(){
		if(ship1_current_position==null)
			ship1_current_position=ship1.getPosition();
		
		ship1_current_position.x+=delta_ship;
		float displacement=ship1_original_position.x-ship1_current_position.x;
		
		if(Math.abs(displacement)>=limit_ship)
			delta_ship=-delta_ship;
	
	}
	
	public void changeCamera(Cam cam) {
		if (cam != null) {
			multiplexer.removeProcessor(cam);// se quita el listener para camara
												// vieja
			this.camera = cam;
			multiplexer.addProcessor(cam);// agrega listener camara nueva

		}
	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		
		
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
		
		
		if(shadows_on){
		//SE CREA EL SHADOW MAP
		createShadowBuffer();
		}
		
//		//
//		Gdx.gl.glClearColor(0, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//		//
//		// s.getColorBufferTexture().bind();
//		//
//		for (Model model : objects) {
//			model.renderShadow(GL20.GL_TRIANGLES, depthMapper, directionalLight,camera);
//		}
//		//
		
		//RENDER DE LUCES
		if (lights_on) {
			for (Model model : objects) {
				Gdx.gl20.glEnable(GL20.GL_BLEND);
				Gdx.gl20.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);
				model.render(lights, camera, PRIMITIVE_TYPE,shadows_on,shadowBuffer);
			}
			
			//Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);

		}
		
		//RENDER DE PERSONAJE
		if (character_animation_on) {
			// System.out.println("animation controller distinto null?"+(animation_controller!=null));

			renderCharacter(character_shaderProgram);
			if (animation_controller != null) {
				Gdx.gl20.glEnable(GL20.GL_BLEND);
				Gdx.gl20.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);
				updateCharacter(animation_controller);

			}
		}
		
		
//		if(shadows_on &&! DEBUGGING_DEPTHMAP){
//			//SE PINTAN LAS SOMBRAS
//			for(Model model:objects){
//				shadowBuffer.getColorBufferTexture().bind(1);
//				model.renderShadow(directional_shadow_shader
//						,directionalLight
//						,camera
//						,PRIMITIVE_TYPE
//										);
//			}
//		}	


		//updateLights(); 
		updateShip();
		//LOG DE ERRORES DE SHADERS
		for (ShaderProgram shader : shaders) {
			printShaderLog(shader);
		}

	}
	
	private void createShadowBuffer(){
		// SHADOW MAP
		if (shadowBuffer == null)
			shadowBuffer = new FrameBuffer(Format.RGBA8888, DEPTHMAPIZE, DEPTHMAPIZE, true);

		if(!DEBUGGING_DEPTHMAP)
			shadowBuffer.begin();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl20.glDisable(GL20.GL_BLEND);
		for (Model model : objects) {
			if(directionalLight!=null)
			model.renderShadowMapping(GL20.GL_TRIANGLES, depthMapper, directionalLight);//se guardan las profundidades a la luz
		}
		
		if(!DEBUGGING_DEPTHMAP){
			//ScreenshotFactory.saveScreenshot(shadowBuffer.getWidth(),shadowBuffer.getHeight(),"testing_depthmap");
	
		shadowBuffer.end();
		//shadowBuffer.getColorBufferTexture().bind(1);
		
		
//
	//	directional_shadow_shader.setUniformf("u_shadowmap",1);
		}
		Gdx.gl20.glEnable(GL20.GL_BLEND);
		Gdx.gl20.glBlendFunc(GL20.GL_ONE, GL20.GL_ONE);
	}

	private void printShaderLog(ShaderProgram shader) {
		if (shader != null) {
			//System.out.println("vertex shader: "+name);
			String log = shader.getLog();
			if (log.length() > 0) {
				System.out.println(log);
			}
		}
	}

}
