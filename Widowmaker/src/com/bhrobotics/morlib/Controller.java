package com.bhrobotics.morlib;

public abstract class Controller {

    //**************************************************************************
    // Interface
    //**************************************************************************

    public void start() {}

    public void newData() {}
    public void run() {}
    public void stop() {}

    //**************************************************************************
    /// Automation
    //**************************************************************************

    public void refresh() {

    }
}