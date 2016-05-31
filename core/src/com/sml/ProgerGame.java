package com.sml;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.sml.actors.BugActor;
import com.sml.actors.PlayerActor;
import com.sml.control.ForceApplier;
import com.sml.control.IForceProvider;
import com.sml.control.PlayerCollisionListener;
import com.sml.control.PlayerLifeManager;
import com.sml.objects.Level;
import com.sml.objects.Menu;
import com.sml.utils.ICodeRepository;

import java.util.ArrayList;

public class ProgerGame extends ApplicationAdapter {
    public static final int BUG_COUNT = 1;
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
    private ArrayList<Disposable> disposables = new ArrayList<Disposable>(BUG_COUNT);
    private BugActor bugActor[] = new BugActor[BUG_COUNT];
    private Vector2 forceApplied = new Vector2(0, 0);
    private ForceApplier forceApplier;
    private PlayerCollisionListener playerCollisionListener;
    private PlayerLifeManager playerLifeManager;

    public ProgerGame(ICodeRepository codeRepository, IForceProvider forceProvider) {
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
        level.init();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenWidth, screenHeight);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);


        batch = new SpriteBatch();
        disposables.add(batch);

        Box2D.init();

        world = new World(new Vector2(0f, -10f), true);
        disposables.add(world);

        debugRenderer = new Box2DDebugRenderer();
        disposables.add(debugRenderer);

        playerActor = new PlayerActor(world, new Vector2(10, 240));
        disposables.add(playerActor);


        createFloor(world);

        for (int i = 0; i < BUG_COUNT; i++) {
            bugActor[i] = new BugActor
                    (world,
                            new Vector2(
                                    camera.viewportWidth + 50f * i,
                                    MathUtils.random(20f, camera.viewportHeight - 20))
                    );
            disposables.add(bugActor[i]);
        }

        world.setContactListener(playerCollisionListener);

        forceApplier = new ForceApplier(playerActor, forceProvider);
    }

    @Override
    public void render() {
        deltaTime = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//        if(!pause) {
//            level.update(Gdx.graphics.getDeltaTime());
//            level.draw(batch);
//        } else {
//            menu.update(Gdx.graphics.getDeltaTime());
//            menu.draw(batch);
//        }

        level.draw(batch, deltaTime);
        level.update(deltaTime);

        batch.end();

//        if (Gdx.input.isTouched()) {
//            pause = true;
//        }

        camera.update();

        playerActor.computeNext(deltaTime);
        for (int i = 0; i < BUG_COUNT; i++) {
            bugActor[i].computeNext(deltaTime);
        }

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        playerActor.draw(batch);
        for (int i = 0; i < BUG_COUNT; i++) {
            bugActor[i].draw(batch);
        }
        batch.end();


        doPhysicsStep(deltaTime);
        debugRenderer.render(world, camera.combined);

        forceApplier.applyForce();
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
    }

    private void createFloor(World world) {
        BodyDef floorDef = new BodyDef();
        floorDef.type = BodyDef.BodyType.StaticBody;
        floorDef.position.set(0, 10);

        Body floor = world.createBody(floorDef);

        floorDef.position.set(0, camera.viewportHeight - 10);
        Body ceiling = world.createBody(floorDef);

        PolygonShape floorBox = new PolygonShape();
        floorBox.setAsBox(camera.viewportWidth, 10f);

        floor.createFixture(floorBox, 0f);
        ceiling.createFixture(floorBox, 0f);

        floorBox.dispose();
    }
}
