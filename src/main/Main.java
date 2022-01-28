package main;

import engine.io.Window;

public class Main implements Runnable{
    public Thread game;
    public static Window window;
    public static final int WIDTH = 1920, HEIGHT = 1080;;

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }
    public static void init(){
        System.out.println("Initializing Game!");
        window = new Window(WIDTH, HEIGHT, "RPG Game");
        window.create();
    }
    public void run(){
        init();
        while(true){
            update();
            render();
        }
    }
    private void update(){
        System.out.println("Updating Game!");
    }
    private void render(){
        System.out.println("Rendering Game!");
    }
    public static void main(String[] arg){
        new Main().start();
    }
}
