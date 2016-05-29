package com.sml;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.sml.actors.BugActor;
import com.sml.actors.PlayerActor;

import java.util.ArrayList;

public class ProgerGame extends ApplicationAdapter implements ContactListener {
    public static final int BUG_COUNT = 1;
    private SpriteBatch batch;
    private PlayerActor playerActor;
    private OrthographicCamera camera;
    private float deltaTime;
    private float accumulator;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private ShapeRenderer shapeRenderer;
    private ArrayList<Disposable> disposables = new ArrayList<Disposable>(BUG_COUNT);
    private BugActor bugActor[] = new BugActor[BUG_COUNT];
    private Vector2 forceApplied = new Vector2(0, 0);

    @Override
    public void create() {
        batch = new SpriteBatch();
        disposables.add(batch);

        Box2D.init();

        world = new World(new Vector2(0f, -10f), true);
        disposables.add(world);

        debugRenderer = new Box2DDebugRenderer();
        disposables.add(debugRenderer);

        playerActor = new PlayerActor(world, new Vector2(10, 240));
        disposables.add(playerActor);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 600);

        createFloor(world);

        shapeRenderer = new ShapeRenderer();
        disposables.add(shapeRenderer);

        for (int i = 0; i < BUG_COUNT; i++) {
            bugActor[i] = new BugActor
                    (world,
                            new Vector2(
                                    camera.viewportWidth + 50f * i,
                                    MathUtils.random(20f, camera.viewportHeight - 20))
                    );
            disposables.add(bugActor[i]);
        }

        world.setContactListener(this);
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

    @Override
    public void render() {
        Gdx.gl.glClearColor(5, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        deltaTime = Gdx.graphics.getDeltaTime();
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


        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(
                playerActor.getBody().getWorldCenter().x,
                playerActor.getBody().getWorldCenter().y,
                6f
        );

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.circle(
                playerActor.getPosition().x + playerActor.getCenterX(),
                playerActor.getPosition().y + playerActor.getCenterY(),
                6f
        );

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(forceApplied.x, forceApplied.y, 6f);

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(
                bugActor[0].getPosition().x + bugActor[0].getCenterX(),
                bugActor[0].getPosition().y + bugActor[0].getCenterY(),
                6f
        );

        shapeRenderer.setColor(Color.OLIVE);
        shapeRenderer.circle(
                bugActor[0].getBody().getWorldCenter().x,
                bugActor[0].getBody().getWorldCenter().y,
                6f
        );


        shapeRenderer.end();
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

    @Override
    public void dispose() {
        super.dispose();
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Body body = contact.getFixtureA().getBody();
        if (body.getType() != BodyDef.BodyType.DynamicBody) {
            body = contact.getFixtureB().getBody();
        }
        body.applyLinearImpulse(
                new Vector2(0, 60),
                forceApplied.set(body.getWorldCenter().x + 40f, body.getWorldCenter().y),
                true);
//        body.setAngularVelocity(0.5f);
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
