package com.sml;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.sml.actors.PlayerActor;
import com.sml.control.ForceApplier;
import com.sml.control.IForceProvider;
import com.sml.control.PlayerCollisionListener;
import com.sml.control.PlayerLifeManager;
import com.sml.objects.Level;
import com.sml.objects.Menu;
import com.sml.objects.collections.Bugs;
import com.sml.utils.ICodeRepository;
import com.sml.utils.ILeaderScreen;

import java.util.ArrayList;

public class ProgerGame extends ApplicationAdapter {
    private final ICodeRepository codeRepository;
    private final IForceProvider forceProvider;
    private final float screenWidth = GameWorldConsts.SCREEN_WIDTH;
    private final float screenHeight = GameWorldConsts.SCREEN_HEIGHT;
    private Level level;
    private Menu menu;
    private boolean pause = false;
    private SpriteBatch batch;
    private PlayerActor playerActor;
    private OrthographicCamera camera;
    private float deltaTime;
    private float accumulator;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private ArrayList<Disposable> disposables = new ArrayList<Disposable>(10);
    private ForceApplier forceApplier;
    private PlayerCollisionListener playerCollisionListener;
    private PlayerLifeManager playerLifeManager;
    private Bugs bugs;

    public ProgerGame(ICodeRepository codeRepository, IForceProvider forceProvider, ILeaderScreen leaderScreen) {
        this.codeRepository = codeRepository;
        this.forceProvider = forceProvider;
    }

    @Override
    public void create() {
        playerLifeManager = new PlayerLifeManager();
        playerCollisionListener = new PlayerCollisionListener(playerLifeManager);

        menu = new Menu();
        menu.init();

        level = new Level();
        level.init(codeRepository);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        disposables.add(batch);

        Box2D.init();

        world = new World(new Vector2(0f, GameWorldConsts.GRAVITY), true);
        disposables.add(world);

        debugRenderer = new Box2DDebugRenderer();
        disposables.add(debugRenderer);

        playerActor = new PlayerActor(world, new Vector2(10, 240));
        disposables.add(playerActor);


        createVerticalMargins(world);

        bugs = new Bugs(world, camera);

        world.setContactListener(playerCollisionListener);

        forceApplier = new ForceApplier(playerActor, forceProvider);
    }

    @Override
    public void render() {
        deltaTime = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        level.update(deltaTime);
        batch.begin();

        level.draw(batch, deltaTime);

        batch.end();


        bugs.update(deltaTime);
        playerActor.update(deltaTime);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        bugs.draw(batch, deltaTime);
        playerActor.draw(batch, deltaTime);

        batch.end();

        doPhysicsStep(deltaTime);

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
    }

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= GameWorldConsts.TIME_STEP) {
            world.step(GameWorldConsts.TIME_STEP, GameWorldConsts.VELOCITY_ITERATIONS, GameWorldConsts.POSITION_ITERATIONS);
            accumulator -= GameWorldConsts.TIME_STEP;
        }

        forceApplier.applyForce();
    }

    private void createVerticalMargins(World world) {
        if (true) return;
        BodyDef floorDef = new BodyDef();
        floorDef.type = BodyDef.BodyType.StaticBody;
        floorDef.position.set(0, 10);

        Body floor = world.createBody(floorDef);

        floorDef.position.set(0, camera.viewportHeight);
        Body ceiling = world.createBody(floorDef);

        PolygonShape floorBox = new PolygonShape();
        floorBox.setAsBox(camera.viewportWidth, 10f);

        floor.createFixture(floorBox, 0f);
        ceiling.createFixture(floorBox, 0f);

        floorBox.dispose();
    }
}
