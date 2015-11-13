package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.cameras.Cam;
import com.mygdx.game.cameras.OrthographicCam;
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

	// character
	ShaderProgram spot_light_shaderProgram;
	ShaderProgram point_light_shaderProgram;

	ShaderProgram character_shaderProgram;
	Vector3 characterPosition = new Vector3(new float[] { 0f, 0f, -1f });
	AnimationController animation_controller;
	ModelInstance character_model_instance;
	boolean isInitialized = false;
	AssetManager assets;
	//

	private Cam camera;
	private InputMultiplexer multiplexer;
	private Ship spaceShip1;
	private Ship spaceShip2;
	private List<Model> objects = new ArrayList<Model>();
	private List<Light> lights = new ArrayList<Light>();

	private ShaderProgram createShader(String dataFolder, String VSfilename,
			String FSfilename) {
		ShaderProgram shader;
		String vs = Gdx.files.internal(dataFolder + VSfilename).readString();
		String fs = Gdx.files.internal(dataFolder + FSfilename).readString();
		shader = new ShaderProgram(vs, fs);
		shader.pedantic = false;
		System.out.println(shader.getLog());
		return shader;
	}

	private void createCharacter() {
		character_shaderProgram = createShader(dataFolder, "characterVS.glsl",
				"defaultFS.glsl");
		assets = new AssetManager();
		// assets.load(dataFolder + "Dave.g3dj",
		// com.badlogic.gdx.graphics.g3d.Model
		// .class);

		assets.load("/home/fbejaran/TPVideojuegos/desktop/data/Dave.g3db",
				com.badlogic.gdx.graphics.g3d.Model.class);
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

		if (assets
				.isLoaded("/home/fbejaran/TPVideojuegos/desktop/data/Dave.g3db")) {

			if (!isInitialized) {
				System.out.println("se inicializo el personaje");
				com.badlogic.gdx.graphics.g3d.Model characterModel = null;

				characterModel = assets
						.get("/home/fbejaran/TPVideojuegos/desktop/data/Dave.g3db");

				character_model_instance = new ModelInstance(characterModel);
				animation_controller = new AnimationController(
						character_model_instance);
				animation_controller.animate(
						character_model_instance.animations.get(0).id, -1, 1f,
						null, 0.2f); // Starts the animaton

				isInitialized = true;
			}

			charShader.begin();
			// Bind whatever uniforms / textures you need
			Texture texture = new Texture(dataFolder + "uv_dave_mapeo.jpg");
			texture.bind();

			Matrix4 modelMatrix = new Matrix4().translate(characterPosition);
			Matrix4 viewProjection = camera.getVP();

			Matrix4 mvpMatrix = new Matrix4(modelMatrix);
			mvpMatrix.mul(viewProjection);

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
				charShader.setUniformMatrix("u_mvpMatrix", mvpMatrix);
				// StaticVariables.tempMatrix.idt();
				for (int i = 0; i < bones.length; i++) {
					final int idx = i / 16;
					bones[i] = (render.bones == null
							|| idx >= render.bones.length || render.bones[idx] == null) ? idtMatrix.val[i % 16]
							: render.bones[idx].val[i % 16];
				}
				charShader.setUniformMatrix4fv("u_bones", bones, 0,
						bones.length);
				render.mesh.render(charShader, render.primitiveType,
						render.meshPartOffset, render.meshPartSize);
			}
			charShader.end();

		}

	}

	@Override
	public void create() {

		spot_light_shaderProgram = createShader(dataFolder, "SpotLightVS.glsl",
				"SpotLightsFS.glsl");

		point_light_shaderProgram = createShader(dataFolder,
				"PointLightsVS.glsl", "PointLightsFS.glsl");

		shaders.add(spot_light_shaderProgram);
		shaders.add(point_light_shaderProgram);

		// objetos de la escena
		spaceShip1 = new Ship(dataFolder, new Vector3(
				new float[] { 0f, 0f, 0f }));
		spaceShip2 = new Ship(dataFolder, new Vector3(
				new float[] { 1f, 0f, 0f }));
		objects.add(spaceShip1);
		objects.add(spaceShip2);

		Cube cube = new Cube(dataFolder, new Vector3(new float[] { 0f, -0.5f,
				0f }));
		objects.add(cube);
		// camara

		camera = new OrthographicCam(this);
		multiplexer = new InputMultiplexer(camera);
		Gdx.input.setInputProcessor(multiplexer);
		Vector3 cam_pos = new Vector3(new float[] { 0f, 0f, 1f });
		camera.setPosition(cam_pos);

		// luces
		lights.add(new PointLight(point_light_shaderProgram, new Vector3(
				new float[] { 0f, 1f, 0.1f }), new Vector3(new float[] { 1f,
				0f, 0f })));

		lights.add(new SpotLight(spot_light_shaderProgram, new Vector3(
				new float[] { 6f, 10f, 0.2f }), new Vector3(new float[] { 0f,
				1f, 0f }), new Vector3(new float[] { 0.0f, -0.1f, 0.0f })));

		// personaje

		createCharacter();
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

		Gdx.gl.glDepthFunc(GL20.GL_LESS);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glEnable(GL20.GL_BLEND);

		// spot_light_shaderProgram.begin();
		// point_light_shaderProgram.begin();
		// original
		// shaderProgram.setUniformMatrix("u_worldView", new Matrix4()); // aca
		// trabajar
		// Intento de agregado de la camara aca abajo

		// camera.lookAt(new Vector3(new float[]{1f,-1f,0f}));

		// spaceshipMesh.render(shaderProgram, GL20.GL_TRIANGLES);
		// spaceShip.render(shaderProgram, GL20.GL_TRIANGLES);

		for (Model model : objects) {
			model.render(lights, camera, GL20.GL_TRIANGLES);
		}

		// spot_light_shaderProgram.end();
		// point_light_shaderProgram.end();
		String shader_log = spot_light_shaderProgram.getLog();
		if (shader_log.length() > 0)
			System.out.println(shader_log);

		String shader_log2 = point_light_shaderProgram.getLog();
		if (shader_log2.length() > 0)
			System.out.println(shader_log2);

		String shader_log3 = character_shaderProgram.getLog();
		if (shader_log3.length() > 0)
			System.out.println(shader_log3);

		// se anima al personaje
		// System.out.println("animation controller distinto null?"+(animation_controller!=null));

		renderCharacter(character_shaderProgram);
		if (animation_controller != null) {
			updateCharacter(animation_controller);

		}

	}

}
