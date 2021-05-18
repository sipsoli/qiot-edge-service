package be.cronos.edge.service;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeGreetingResourceIT extends GreetingResourceFeedbackController {

    // Execute the same tests but in native mode.
}
