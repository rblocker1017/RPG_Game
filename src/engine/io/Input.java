package engine.io;

import org.lwjgl.glfw.*;

public class Input {
    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX, mouseY;
    private static double scrollX, scrollY;

    private GLFWCursorPosCallback mouseMove;
    private GLFWMouseButtonCallback mouseButtons;
    private GLFWScrollCallback mouseScroll;

    public Input(){
        mouseMove = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };
        mouseButtons = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE); // Check if key is pressed or not
            }
        };

        mouseScroll = new GLFWScrollCallback(){

            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }
    public static boolean isKeyDown(int key){
        return keys[key];
    }

    public static boolean isButtonDown(int button){
        return buttons[button];
    }

    public void destroy(){
        mouseMove.free();
        mouseButtons.free();
        mouseScroll.free();
    }
    public static double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public GLFWCursorPosCallback getMouseMove() {
        return mouseMove;
    }

    public void setMouseMove(GLFWCursorPosCallback mouseMove) {
        this.mouseMove = mouseMove;
    }

    public GLFWMouseButtonCallback getMouseButtons() {
        return mouseButtons;
    }

    public void setMouseButtons(GLFWMouseButtonCallback mouseButtons) {
        this.mouseButtons = mouseButtons;
    }

    public GLFWScrollCallback getMouseScroll() {
        return mouseScroll;
    }

    public static double getScrollX() {
        return scrollX;
    }

    public static void setScrollX(double scrollX) {
        Input.scrollX = scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }

    public static void setScrollY(double scrollY) {
        Input.scrollY = scrollY;
    }

    public void setMouseScroll(GLFWScrollCallback mouseScroll) {
        this.mouseScroll = mouseScroll;
    }
    public void setButtons(int key, boolean action){
        keys[key] = action;
    }

}
