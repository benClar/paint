package drawing;

import drawing.context.Context;
import drawing.context.ContextImpl;

class App {


    public static void main(String[] args) {
        Context context = new ContextImpl();
        context.start();
    }
}
