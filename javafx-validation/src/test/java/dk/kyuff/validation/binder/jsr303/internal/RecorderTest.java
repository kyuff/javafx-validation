package dk.kyuff.validation.binder.jsr303.internal;

import dk.kyuff.validation.binder.jsr303.model.Friend;
import dk.kyuff.validation.binder.jsr303.model.Parent;
import junit.framework.TestCase;

import java.util.List;

public class RecorderTest extends TestCase {

    public void testRecord_method_reference() throws Exception {
        // setup
        Recorder<Parent> recorder = new Recorder<>(Parent.class);

        // execute
        List<String> fields = recorder.record(Parent::getName);

        // assert
        assertEquals(1, fields.size());
        assertTrue(fields.contains("name"));
    }

    public void testRecord_date() throws Exception {
        // setup
        Recorder<Friend> recorder = new Recorder<>(Friend.class);

        // execute
        List<String> fields = recorder.record(Friend::getBirthday);

        // assert
        assertEquals(1, fields.size());
        assertTrue(fields.contains("birthday"));
    }

    public void testRecord_string() throws Exception {
        // setup
        Recorder<Friend> recorder = new Recorder<>(Friend.class);

        // execute
        List<String> fields = recorder.record(Friend::getName);

        // assert
        assertEquals(1, fields.size());
        assertTrue(fields.contains("name"));
    }

    public void testRecord_lambda() throws Exception {
        // setup
        Recorder<Parent> recorder = new Recorder<>(Parent.class);

        // execute
        List<String> fields = recorder.record(proxy -> {
            proxy.getBirthday();
            proxy.getChild();
        });

        // assert
        assertEquals(2, fields.size());
        assertTrue(fields.contains("birthday"));
        assertTrue(fields.contains("child"));
    }


    public void testRecord_nested_objects() throws Exception {
        // setup
        Recorder<Parent> recorder = new Recorder<>(Parent.class);

        // execute
        List<String> fields = recorder.record(proxy -> {
            proxy.getBirthday();
            proxy.getChild().getName();
            proxy.getChild().getFriend();
            proxy.getChild().getFriend().getAge();
        });

        // assert
        assertEquals(4, fields.size());
        assertTrue(fields.contains("birthday"));
        assertTrue(fields.contains("child.name"));
        assertTrue(fields.contains("child.friend"));
        assertTrue(fields.contains("child.friend.age"));
    }
}