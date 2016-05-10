package com.ffxvi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ffxvi.game.MainClass;
import static com.ffxvi.game.MainClass.camera;
import com.ffxvi.game.entities.Bullet;
import com.ffxvi.game.entities.Player;
import com.ffxvi.game.entities.PlayerCharacter;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	MainClass game;
	Player mainPlayer;
        
	private ShapeRenderer shape;
	private SpriteBatch batch;
	public static MapObjects wallObjects;
	public static MapObjects objects;
	public static MapObjects doors;
	public static ArrayList<Bullet> bullets;
	private final Stage stage;
	private final Skin skin;
	
	private Label playerLabel;
	private GlyphLayout layout;
	private final String[] levels = new String[]{"level1", "level2"};
	
	public GameScreen (MainClass game) {
		this.game = game;
		this.stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		// Store the default libgdx font under the name "default".
		BitmapFont bfont=new BitmapFont();
		//bfont.scale(1);
		skin.add("default",bfont);
		
		//mainPlayer = new Player(game, "Units/Skeleton_Dagger/Walk.png", "Units/Skeleton_Dagger/Slash.png");
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
//		mainPlayer.setPos(camera.position.x, camera.position.y);
		//mainPlayer.setPos(64, 64);
		bullets = new ArrayList();
		this.playerLabel = new Label("", skin);
		stage.addActor(playerLabel);
		
		this.layout = new GlyphLayout();
	}
	
	public void AddPlayer(String playerName, PlayerCharacter character) {
		mainPlayer = new Player(game, character, playerName, this);
		mainPlayer.setPos(64, 64);
		
		playerLabel.setText(playerName);
	}
	
	@Override
	public void show() {
		int idx = new Random().nextInt(levels.length);
		String level = levels[idx] + ".tmx";
		
		map = new TmxMapLoader().load(level);
		wallObjects = map.getLayers().get("WallObjects").getObjects();
		objects = map.getLayers().get("Objects").getObjects();
		doors = map.getLayers().get("Door").getObjects();
		renderer = new OrthogonalTiledMapRenderer(map, 1f);
		renderer.setView(camera);
	}
	
	public void setLevel(String level) {
		map = new TmxMapLoader().load(level);
		wallObjects = map.getLayers().get("WallObjects").getObjects();
		objects = map.getLayers().get("Objects").getObjects();
		doors = map.getLayers().get("Door").getObjects();
		renderer = new OrthogonalTiledMapRenderer(map, 1f);
		renderer.setView(camera);
	}
	
	public static void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}
	
	public static void removeBullet(Bullet bullet) {
		bullets.remove(bullet);
	}

	@Override
	public void render(float delta) {
		if (this.mainPlayer != null) {
			
			//game.render();
			camera.position.set(mainPlayer.getX(), mainPlayer.getY(), 0);
			camera.update();

			renderer.setView(camera);
			renderer.render();
			
			// Set the playerLabel position to the position of the player
			Vector3 playerPos = new Vector3(mainPlayer.getX(), mainPlayer.getY(), 0);
			camera.project(playerPos);
			layout.setText(skin.getFont("default"), mainPlayer.getName());
			float playerLabelWidth = layout.width;
			playerLabel.setPosition(playerPos.x + 32 - (playerLabelWidth/2), playerPos.y + mainPlayer.getCurrentAnim().getKeyFrame(0).getRegionHeight());
			
	//		mainPlayer.render(shape, camera);
			batch.begin();
			mainPlayer.render(batch);
			mainPlayer.update();
			batch.end();
			
			stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
			stage.draw();

			for (Bullet b : bullets) {
				if (b.doRemove) {
					b = null;
				} else {
					b.update();
					b.render(shape, camera);
				}
			}
		}
	}

	@Override
	public void resize(int i, int i1) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		
	}
}
