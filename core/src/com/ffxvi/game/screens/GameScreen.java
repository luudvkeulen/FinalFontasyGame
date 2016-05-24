package com.ffxvi.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.ffxvi.game.MainClass;
import static com.ffxvi.game.MainClass.camera;
import com.ffxvi.game.entities.Direction;
import com.ffxvi.game.entities.Player;
import com.ffxvi.game.entities.PlayerCharacter;
import com.ffxvi.game.logics.ChatManager;
import com.ffxvi.game.logics.InputManager;
import com.ffxvi.game.models.Map;
import com.ffxvi.game.models.MapType;
import com.ffxvi.game.models.Projectile;
import com.ffxvi.game.support.Vector;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javafx.scene.input.MouseEvent;


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
	public static ArrayList<Projectile> projectiles;
	private final Stage stage;
	private final Skin skin;
	
	private Label playerLabel1;
	private Label playerLabel2;
	private Label healthLabel1;
	private Label healthLabel2;
	private Label scoreLabel;
	private final String[] levels = new String[]{"level1", "level2", "level3"};
	public String currentlevel = "";
	private InputManager inputManager;
	
	public GameScreen (MainClass game) {
		this.game = game;
		this.stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		
		// Store the default libgdx font under the name "default".
		BitmapFont bfont=new BitmapFont();
		//bfont.scale(1);
		skin.add("default",bfont);
		
		BitmapFont bfontred = new BitmapFont();
		bfontred.setColor(Color.RED);
		skin.add("red", bfontred);
		
		//mainPlayer = new Player(game, "Units/Skeleton_Dagger/Walk.png", "Units/Skeleton_Dagger/Slash.png");
		shape = new ShapeRenderer();
		batch = new SpriteBatch();
//		mainPlayer.setPos(camera.position.x, camera.position.y);
		//mainPlayer.setPos(64, 64);
		projectiles = new ArrayList();
		this.playerLabel1 = new Label("", skin);
		this.playerLabel2 = new Label("", skin);
		this.healthLabel1 = new Label("100", skin);
		this.healthLabel2  = new Label("100", skin);
		this.scoreLabel = new Label("5000 xp", skin);
		
		healthLabel2.setFontScale(2);
		playerLabel2.setFontScale(2);
		scoreLabel.setFontScale(2);
		
		healthLabel1.setColor(Color.RED);
		healthLabel2.setColor(Color.RED);
		scoreLabel.setColor(Color.YELLOW);
		
		playerLabel2.setHeight(20);
		scoreLabel.setHeight(20);
		healthLabel2.setHeight(20);
		
		stage.addActor(healthLabel1);
		stage.addActor(healthLabel2);
		stage.addActor(playerLabel1);
		stage.addActor(playerLabel2);
		stage.addActor(scoreLabel);
	}
	
	public void AddPlayer(String playerName, PlayerCharacter character) {
		mainPlayer = new Player(character, playerName, new Vector(64f,64f),this);
		mainPlayer.setPosition(64, 64);
		
		playerLabel1.setText(playerName);
		playerLabel2.setText(playerName);
		
		inputManager = new InputManager(game, mainPlayer);
	}
	
	@Override
	public void show() {
		int idx = new Random().nextInt(levels.length);
		currentlevel = levels[idx];
		String level = currentlevel + ".tmx";
		map = new TmxMapLoader().load(level);
		wallObjects = map.getLayers().get("WallObjects").getObjects();
		objects = map.getLayers().get("Objects").getObjects();
		doors = map.getLayers().get("Door").getObjects();
		renderer = new OrthogonalTiledMapRenderer(map, 1f);
		renderer.setView(camera);
	}
	
	public void setLevel(String level, Direction dir) {
		if(level.equals("null")) return;
		map = new TmxMapLoader().load(level + ".tmx");
		wallObjects = map.getLayers().get("WallObjects").getObjects();
		objects = map.getLayers().get("Objects").getObjects();
		doors = map.getLayers().get("Door").getObjects();
		for(RectangleMapObject rmo : doors.getByType(RectangleMapObject.class)) {
			if(rmo.getName().equals(currentlevel)) {
				switch(dir) {
					case UP:
						mainPlayer.setPosition(rmo.getRectangle().x, rmo.getRectangle().y + 64);
						break;
					case DOWN:
						mainPlayer.setPosition(rmo.getRectangle().x, rmo.getRectangle().y - 64);
						break;
					case LEFT:
						mainPlayer.setPosition(rmo.getRectangle().x - 64, rmo.getRectangle().y );
						break;
					case RIGHT:
						mainPlayer.setPosition(rmo.getRectangle().x + 64, rmo.getRectangle().y );
						break;
				}
				break;
			}
		}
		renderer = new OrthogonalTiledMapRenderer(map, 1f);
		currentlevel = level;
		renderer.setView(camera);
	}
	
	public static void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}
	
	public static void removeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
	}

	@Override
	public void render(float delta) {
		if (this.mainPlayer != null) {
			camera.position.set(mainPlayer.getX(), mainPlayer.getY(), 0);
			camera.update();

			renderer.setView(camera);
			renderer.render();
			
			// Set the playerLabel position to the position of the player
			Vector3 playerPos = new Vector3(mainPlayer.getX(), mainPlayer.getY(), 0);
			camera.project(playerPos);
			
			float playerLabelWidth = playerLabel1.getWidth();
			playerLabel1.setAlignment((int)playerLabelWidth/2);
			playerLabel1.setPosition(playerPos.x + 32, playerPos.y + mainPlayer.getCurrentAnim().getKeyFrame(0).getRegionHeight() + 12);
			
			float healthLabel1width = healthLabel1.getWidth();
			healthLabel1.setAlignment((int)(healthLabel1width /2));
			healthLabel1.setPosition(playerPos.x + 16, playerPos.y + mainPlayer.getCurrentAnim().getKeyFrame(0).getRegionHeight() - 18);
			
			healthLabel2.setPosition(0, healthLabel2.getHeight());
			
			playerLabel2.setAlignment((int)playerLabel2.getWidth() / 2);
			playerLabel2.setPosition(Gdx.graphics.getWidth() / 2,  playerLabel2.getHeight());
			
			scoreLabel.setPosition(Gdx.graphics.getWidth() - (scoreLabel.getWidth() * 2), scoreLabel.getHeight());
			
			batch.begin();
			mainPlayer.render(batch);
			mainPlayer.update();
			inputManager.checkInput();
			batch.end();
			
			stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
			stage.draw();

                        /*
			for (Projectile p : projectiles) {
				if (p.doRemove) {
					p = null;
				} else {
					p.update();
					p.render(shape, camera);
				}
			}
                        */
		}
	}

	@Override
	public void resize(int i, int i1) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}
=======
public class GameScreen implements Screen, Observer
{

    MainClass game;
    Player mainPlayer;

    //Map related
    OrthogonalTiledMapRenderer renderer;
    private static Map map;
    private final List<Map> maps;
    private final List<MapType> mapTypes;

    //Chat related
    private final List<Label> oldchatlabels;
    private final ChatManager chatManager;

    private ShapeRenderer shape;
    private SpriteBatch batch;
    public static ArrayList<Projectile> projectiles;
    private final Stage stage;
    private final Skin skin;

    //HUD Related
    private final Label playerLabel1;
    private final Label playerLabel2;
    private final Label healthLabel1;
    private final Label healthLabel2;
    private final Label scoreLabel;
    private final TextField textfield;

    private InputManager inputManager;

    public static Map getCurrentMap()
    {
        return map;
    }

    public GameScreen(MainClass game)
    {
        this.game = game;
        this.stage = new Stage();
        this.chatManager = new ChatManager();

        //Setup map stuff
        this.maps = new ArrayList();
        this.mapTypes = new ArrayList();
        loadMaps();

        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Store the default libgdx font under the name "default".
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        shape = new ShapeRenderer();
        batch = new SpriteBatch();

        projectiles = new ArrayList();

        textfield = new TextField("", skin);
        textfield.setPosition(10, Gdx.graphics.getHeight() - 200);
        textfield.setWidth(300);
        stage.addActor(textfield);

        //Setup labels
        ////Setup label variables
        this.playerLabel1 = new Label("", skin);
        this.playerLabel2 = new Label("", skin);
        this.healthLabel1 = new Label("100", skin);
        this.healthLabel2 = new Label("100", skin);
        this.scoreLabel = new Label("5000", skin);
        ////Setup label fontscales
        healthLabel2.setFontScale(2);
        playerLabel2.setFontScale(2);
        scoreLabel.setFontScale(2);
        ////Setup label colors
        healthLabel1.setColor(Color.RED);
        healthLabel2.setColor(Color.RED);
        scoreLabel.setColor(Color.YELLOW);
        ////Setup label height
        playerLabel2.setHeight(20);
        scoreLabel.setHeight(20);
        healthLabel2.setHeight(20);
        ////Add labels to stage
        stage.addActor(healthLabel1);
        stage.addActor(healthLabel2);
        stage.addActor(playerLabel1);
        stage.addActor(playerLabel2);
        stage.addActor(scoreLabel);

        oldchatlabels = new ArrayList();
    }

    private void loadMaps()
    {
        mapTypes.add(new MapType(1, "level1"));
        mapTypes.add(new MapType(2, "level2"));
        mapTypes.add(new MapType(3, "level3"));

        for (MapType mapType : mapTypes)
        {
            maps.add(new Map(mapType.getName() + ".tmx", mapType.getId()));
        }
    }

    public void AddPlayer(String playerName, PlayerCharacter character)
    {
        mainPlayer = new Player(character, playerName, new Vector(64f, 64f), this);
        mainPlayer.setPosition(64, 64);

        playerLabel1.setText(playerName);
        playerLabel2.setText(playerName);

        inputManager = new InputManager(game, mainPlayer);
        this.inputManager.addObserver(this);
    }

    @Override
    public void show()
    {
        int idx = new Random().nextInt(maps.size());
        map = maps.get(idx);

        renderer = new OrthogonalTiledMapRenderer(map.getMap(), 1f);
        renderer.setView(camera);

        stage.setKeyboardFocus(null);
    }

    public void setLevel(int mapId, Direction dir)
    {
        if (mapId == 0)
        {
            return;
        }
        Map oldMap = map;
        for (Map m : maps)
        {
            if (m.getId() == mapId)
            {
                map = m;
            }
        }

        if (oldMap == map)
        {
            return;
        }

        for (RectangleMapObject rmo : map.getDoors().getByType(RectangleMapObject.class))
        {
            if (Integer.parseInt(rmo.getName()) == oldMap.getId())
            {
                switch (dir)
                {
                    case UP:
                        mainPlayer.setPosition(rmo.getRectangle().x, rmo.getRectangle().y + 64);
                        break;
                    case DOWN:
                        mainPlayer.setPosition(rmo.getRectangle().x, rmo.getRectangle().y - 64);
                        break;
                    case LEFT:
                        mainPlayer.setPosition(rmo.getRectangle().x - 64, rmo.getRectangle().y);
                        break;
                    case RIGHT:
                        mainPlayer.setPosition(rmo.getRectangle().x + 64, rmo.getRectangle().y);
                        break;
                }
                break;
            }
        }
        renderer = new OrthogonalTiledMapRenderer(map.getMap(), 1f);
        renderer.setView(camera);
    }

    public static void addProjectile(Projectile projectile)
    {
        projectiles.add(projectile);
    }

    public static void removeProjectile(Projectile projectile)
    {
        projectiles.remove(projectile);
    }

    @Override
    public void render(float delta)
    {
        if (this.mainPlayer != null)
        {
            camera.position.set(mainPlayer.getX(), mainPlayer.getY(), 0);
            camera.update();

            renderer.setView(camera);
            renderer.render();

            // Set the playerLabel position to the position of the player
            Vector3 playerPos = new Vector3(mainPlayer.getX(), mainPlayer.getY(), 0);
            camera.project(playerPos);

            float playerLabelWidth = playerLabel1.getWidth();
            playerLabel1.setAlignment((int) playerLabelWidth / 2);
            playerLabel1.setPosition(playerPos.x + 32, playerPos.y + mainPlayer.getCurrentAnimation().getKeyFrame(0).getRegionHeight() + 12);

            float healthLabel1width = healthLabel1.getWidth();
            healthLabel1.setAlignment((int) (healthLabel1width / 2));
            healthLabel1.setPosition(playerPos.x + 16, playerPos.y + mainPlayer.getCurrentAnimation().getKeyFrame(0).getRegionHeight() - 18);

            healthLabel2.setPosition(0, healthLabel2.getHeight());

            playerLabel2.setAlignment((int) playerLabel2.getWidth() / 2);
            playerLabel2.setPosition(Gdx.graphics.getWidth() / 2, playerLabel2.getHeight());

            scoreLabel.setPosition(Gdx.graphics.getWidth() - (scoreLabel.getWidth() * 2), scoreLabel.getHeight());

            batch.begin();
            mainPlayer.render(batch);
            mainPlayer.update();
            inputManager.checkInput();
            batch.end();

            //Remove old chat labels
            for (Label l : oldchatlabels)
            {
                l.remove();
            }
            oldchatlabels.clear();

            //Draw new labels
            int counter = 0;
            int spacing = 15;
            for (Label label : chatManager.getMessages())
            {
                label.setPosition(10, Gdx.graphics.getHeight() - label.getHeight() - (counter * spacing));
                oldchatlabels.add(label);
                stage.addActor(label);

                counter++;
            }

            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();

            for (Projectile p : projectiles)
            {
                if (p.doRemove)
                {
                    p = null;
                }
                else
                {
                    p.update();
                    p.render(shape, camera);
                }
            }
        }
    }

    @Override
    public void resize(int i, int i1)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void update(Observable o, Object arg)
    {
        String chatMessage = this.textfield.getText();

        if (!chatMessage.isEmpty())
        {
            this.stage.setKeyboardFocus(this.scoreLabel);

            this.chatManager.addMessage(this.mainPlayer.getName(), chatMessage);
            this.textfield.setText("");
        }
        else
        {
            this.stage.setKeyboardFocus(this.textfield);
        }
    }
}
