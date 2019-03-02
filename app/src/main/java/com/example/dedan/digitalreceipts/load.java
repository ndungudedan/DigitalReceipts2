package com.example.dedan.digitalreceipts;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class load  {
String filename;

    public load(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
