package main;

import engine.io.Input;
import engine.io.Window;
import org.lwjgl.glfw.GLFW;

public class Main implements Runnable{
    public Thread game;
    public Window window;
    public final int WIDTH = 1920, HEIGHT = 1080;

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }
    public void init(){
        window = new Window(WIDTH, HEIGHT, "RPG Game");
        window.setBackgroundColor(0.0f,0.0f, 0.3f);

        window.create();
    }
    public void run(){
        init();
        while(!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)){
            update();
            render();
        }
        window.destroy();
    }
    private void update(){
        window.update();
        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)){

        }
    }
    private void render(){
        window.swapBuffers();
    }
    public static void main(String[] arg){
        new Main().start();
    }
}
