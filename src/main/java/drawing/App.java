package drawing;

import drawing.Context.Context;
import drawing.Context.ContextImpl;

class App {


    public static void main(String[] args) {
        Context context = new ContextImpl();
        context.start();
    }
}
