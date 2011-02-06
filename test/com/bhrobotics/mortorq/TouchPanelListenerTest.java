package com.bhrobotics.mortorq;

import junit.framework.*;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import java.util.Hashtable;

public class TouchPanelListenerTest extends TestCase {
	
    public void setUp() {
        Reactor.getInstance().startTicking();
    }
    
    public void tearDown() {
		
        Reactor.getInstance().stopTicking();
    }
    
    public void testCtor() {
        TouchPanelListener panel = new TouchPanelListener();
        assertNotNull(panel);
    }
    
    public void testScreenChoose() {
        TouchPanelListener panel = new TouchPanelListener();
        StubScreen screen1 = new StubScreen();
        StubScreen2 screen2 = new StubScreen2();
        Hashtable fullData = new Hashtable();
        fullData.put("oldDigitals", new Short((short) 0));
        fullData.put("newDigitals", new Short((short) 0x1544));
		Hashtable data = new Hashtable();
		data.put("oldValue",new Boolean(false));
		data.put("newValue", new Boolean(true)); 
        Event event = new Event("updateDigitals",fullData);
        Event screenEvent = new Event("updateDigital5",data);
        panel.addScreen(1, screen1);
        panel.addScreen(2, screen2);
        
		panel.handle(event);
		panel.handle(screenEvent);
        Reactor.getInstance().tick();
		//
		System.out.println("-----Finished Choose p1-----");
        
        assertTrue(screen1.received);
        assertFalse(screen2.received);
        assertSame(screen1.emitter, panel.getEmitter());
        assertSame(screen1, panel.getCurrentScreen());
        
		Hashtable fullData2 = new Hashtable();
        fullData2.put("oldDigitals", new Short((short) 0x1544));
        fullData2.put("newDigitals", new Short((short) 0x4236));
		Event event2 = new Event("updateDigitals",fullData2);
        screen1.received = false;
		
        panel.handle(event2);
        panel.handle(screenEvent);
        Reactor.getInstance().tick();
		//
		System.out.println("------Finished Choose p2-----");
        
        assertTrue(screen2.received);
        assertFalse(screen1.received);
        assertSame(screen2.emitter, panel.getEmitter());
        assertSame(screen2, panel.getCurrentScreen());
    }
	
	public void testTouchScreenBind() {
		TouchPanelListener panel = new TouchPanelListener();
		StubListener stub = new StubListener();
		
		panel.bind("test1",stub);
		
		assertTrue(stub.bound); 
	}
	
	public void testScreen() {
		TouchPanelListener panel = new TouchPanelListener();
		StubScreen3 screen3 = new StubScreen3();
		StubScreen screen1 = new StubScreen();
		StubScreen2 screen2 = new StubScreen2();
		StubListener listener = new StubListener();
		StubListener listener2 = new StubListener();
		StubListener listener3 = new StubListener();
		Hashtable data = new Hashtable();
		data.put("oldValue" ,new Boolean(false));
		data.put("newValue",new Boolean(true));
		Hashtable fullData = new Hashtable();
		fullData.put("oldDigitals" ,new Short((short)0x0000));
		fullData.put("newDigitals" ,new Short((short)0x4F9A));
		Hashtable fullData2 = new Hashtable();
		fullData2.put("oldDigitals" ,new Short((short)0x4F9A));
		fullData2.put("newDigitals" ,new Short((short)0x2F34));
		Hashtable fullData3 = new Hashtable();
		fullData3.put("oldDigitals" ,new Short((short)0x4F34));
		fullData3.put("newDigitals" ,new Short((short)0x8454));
		panel.addScreen(3, screen3); 
		panel.addScreen(1, screen1); 
		panel.addScreen(2, screen2);
		Event screenChangeEvent = new Event("updateDigitals",fullData);
		Event screenChangeEvent2 = new Event("updateDigitals",fullData2);
		Event screenChangeEvent3 = new Event("updateDigitals",fullData3); 
		Event screenEvent = new Event("updateDigital1" , data);
		panel.bind("test", listener);
		panel.bind("MeToo", listener2);
		panel.bind("Sucess!", listener3);

		panel.handle(screenEvent);
		panel.handle(screenChangeEvent);
		panel.handle(screenEvent);
		panel.handle(screenChangeEvent2);
		panel.handle(screenEvent);
		panel.handle(screenChangeEvent3);
		panel.handle(screenEvent);
		Reactor.getInstance().tick();
		System.out.println("-------Finished Screen p1------");
		
		assertTrue(screen1.received);
		assertTrue(screen2.received);
		assertTrue(listener.received);
		assertTrue(listener2.received);	
		assertTrue(listener3.received);
	}
	
	public void testStop(){
		TouchPanelListener panel = new TouchPanelListener();
		StubScreen screen = new StubScreen();
		StopListener kill = new StopListener();
		Hashtable fullData2 = new Hashtable();
		fullData2.put("oldDigitals" ,new Short((short)0x0000));
		fullData2.put("newDigitals" ,new Short((short)0x4F9A));
		Hashtable fullData = new Hashtable();
		fullData.put("oldDigitals" ,new Short((short)0x0000));
		fullData.put("newDigitals" ,new Short((short)0x2000));
		Hashtable stopData = new Hashtable();
		stopData.put("oldDigitals", new Short((short)0x2000));
		stopData.put("newDigitals",new Short((short)0x3009));	
		Event stop = new Event("updateDigitals",stopData);
		Reactor.getInstance().getProcess().bind("stop",kill);		
		
		panel.handle(new Event("updateDigitals",fullData));
		panel.handle(new Event ("updateDigitals",fullData2));
		panel.handle(stop);
		Reactor.getInstance().tick();
		//
		System.out.println("-----Finished Stop 1------");
		
		assertTrue(kill.received);
	}
		
    public void testBadScreenNumber() {
        TouchPanelListener panel = new TouchPanelListener();
   
        try {
            panel.addScreen(10, null);
            fail("Exepected Exception not thrown.");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Ignore.
        }
    }
    
	public void testScreenAdd () {
		TouchPanelListener panel = new TouchPanelListener();
		int i1 = 1;
		int i2 = 2;
		StubScreen screen1 = new StubScreen();
		StubScreen2 screen2 = new StubScreen2();
		
		panel.addScreen(i1,screen1);
		panel.addScreen(i2,screen2);
		
		assertSame(screen1,panel.getScreens()[i1]);
		assertSame(screen2,panel.getScreens()[i2]);			
	}
	
    private class StubListener implements Listener {
        public boolean received;
		private boolean bound;
        private EventEmitter emit;
		
        public void handle(Event e) {
            received = true;
        }
		
		public void bound(String event, EventEmitter emit){
			bound = true;
		}
		
		public void unbound(String event,EventEmitter emit) {}
    }
	
	private class StopListener implements Listener {
		public boolean received;
		private boolean bound;
        private EventEmitter emit;
		
        public void handle(Event e) {
            received = true;
        }
		
		public void bound(String event, EventEmitter emit){
			bound = true;
		}
        public void unbound(String event, EventEmitter emitter) {}
    }
		
    
    private class StubScreen implements TouchPanelListener.Screen {
        private EventEmitter emitter;
        public boolean received;

        public void handle(EventEmitter em, Event e) {
            emitter = em;
			received = true;
			System.out.println("StubScreen received: " + e.getName ()); 
			String name = e.getName();
			if (name.equals("updateDigital1")) {
				em.trigger(new Event("test",e.getData()));
			}
        }
    }
	
	private class StubScreen2 implements TouchPanelListener.Screen {
		private EventEmitter emitter;
		public boolean received;
		
		public void handle(EventEmitter em, Event e) {
			emitter = em;
			received = true;
			String name = e.getName();
			System.out.println("StubScreen2 received: " + e.getName ());
			if (name.equals("updateDigital1")) {
				em.trigger (new Event("MeToo", e.getData()));
			}
		}
	}
	
	private class StubScreen3 implements TouchPanelListener.Screen {
		private EventEmitter emitter;
		public boolean received;
		
		public void handle(EventEmitter em, Event e) {
			emitter = em ;
			received = true;
			String name = e.getName();
			System.out.println("StubScreen3 received: " + e.getName ());
			if (name.equals("updateDigital1")) {
				emitter.trigger (new Event("Sucess!", e.getData()));
			}
		}
	}
}