package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F11;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class Window {
    private int width, height;
    private String title;
    private long window;
    public int frames;
    public static long time;
    public Input input;
    private float backgroundR, backgroundG, backgroundB;
    private GLFWWindowSizeCallback sizeCallback;
    private GLFWKeyCallback keyboard;
    private boolean isResized;
    private boolean isFullScreen;
    private int[] windowPosX = new int[1], windowPosY = new int[1];


    public Window(int width, int height, String title){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create(){
        if(!GLFW.glfwInit()){
            System.err.println("ERROR: GLFW wasn't initialized.");
            return;
        }
        input = new Input();
        window = GLFW.glfwCreateWindow(width, height, title, isFullScreen ? GLFW.glfwGetPrimaryMonitor() : 0 , 0);

        if(window == 0){
            System.err.println("ERROR: Window wasn't created.");
            return;
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        windowPosX[0] = (videoMode.width() - width)/2;
        windowPosY[0] = (videoMode.height() - height)/2;
        GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);


        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        createCallBacks();
        GLFW.glfwShowWindow(window);
        GLFW.glfwSwapInterval(1);

        time = System.currentTimeMillis();
    }
    private void createCallBacks(){
        sizeCallback = new GLFWWindowSizeCallback(){

            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };

        keyboard = new GLFWKeyCallback(){

            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if(key == GLFW.GLFW_KEY_F11 && action == GLFW.GLFW_PRESS){
                    setFullScreen(!isFullScreen());
                }
                input.setButtons(key, (action != GLFW.GLFW_RELEASE));
            }
        };

        GLFW.glfwSetKeyCallback(window, keyboard);
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMove());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtons());
        GLFW.glfwSetWindowSizeCallback(window,sizeCallback);
        GLFW.glfwSetScrollCallback(window, input.getMouseScroll());

    }
    public void update(){
        if(isResized){
            GL11.glViewport(0, 0, width, height);
            isResized = false;
        }
        GL11.glClearColor(backgroundR, backgroundG, backgroundB, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GLFW.glfwPollEvents();
        frames++;
        if(System.currentTimeMillis() > time + 1000){
            GLFW.glfwSetWindowTitle(window, title + " | FPS:" + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers(){
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public void destroy(){
        input.destroy();
        GLFW.glfwTerminate();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyCursor(window);
    }

    public void setBackgroundColor(float r, float g, float b){
        backgroundR = r;
        backgroundG = g;
        backgroundB = b;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
        isResized = true;

        if (isFullScreen) {
            GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);

            int tempWidth = width;
            int tempHeight = height;

            GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, videoMode.width(), videoMode.height(), 10);

            width = tempWidth;
            height = tempHeight;
        } else {
            GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
        }

    }
}
