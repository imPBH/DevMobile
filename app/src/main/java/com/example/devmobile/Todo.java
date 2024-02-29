package com.example.devmobile;

public class Todo {
    private String _title;
    private boolean _done;

    public Todo(String title, boolean done) {
        _title = title;
        _done = done;
    }

    public String getTitle() {
        return _title;
    }

    public boolean getDone() {
        return _done;
    }

    public void setDone(boolean newValue) {
        _done = newValue;
    }
}
