Features/ patterns:
-Generic Publisher/Subscriber events framework for communication between components.
-Single threaded
-Highly modularised design imploying single responsibility principle.  Separates logic, state and I/O:
    -Graphics output (GraphicsEventListener)
    -parsing of input tokens (InterpreterListener)
    -current state (StateEventListener)
    -interpretation of input tokens (DrawEventListener)
    -input by user (KeyboardInputPlugin)
-Listeners communicate with each other using central message hub.
-Builder Pattern used to control event creation, ensure immutability of events and validate.
-Error handling and error messages.
-TDD used


Potential Improvements/ Other comments:
-Make publishers all singletons.
-use dependency injection.
-Replace graphics listener with proper graphics thread.
-State could keep a stack of canvas' to allow for undo operations.
-Productionise: Given time constraints, some of the following could be worked on:
    -Improve test coverage.  For a production application each public method would have a unit test.
    -Document all classes, see DrawEventListener for example of proper documentation which would be applied to all.
- Proper logging, everything just goes to stdout currently.