import java.awt.Robot;
import com.leapmotion.leap.*;
import java.awt.event.*;
import com.leapmotion.leap.Gesture.State;
import java.awt.Dimension;
import java.awt.AWTException;

class CustomListener_P extends Listener {
  // The automator. More in the information document under "Leap Motion 101"
  public Robot robot;

  // Executes once your program and detector are connected, are there any specific gestures you're going
  // to look for later on? Uncomment the ones you want to enable below
  public void onConnect(Controller controller) {
    System.out.println("Connected");
    controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
    controller.enableGesture(Gesture.Type.TYPE_SWIPE);
    controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
  //  controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    controller.setPolicy(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES); // allows the program to run in places other than the terminal
  }

  // Executes when detector stops working/is no longer connected to computer
  public void onDisconnect(Controller controller) {
    System.out.println("Disconnected");
  }

  // Executes when you manually stop running the detector/program
  public void onExit(Controller controller) {
    System.out.println("Exited");
  }

  // More info in the Leap Motion 101 section
  public void onFrame(Controller c) {
    /*try {
    // Creating our robot friend. Feel free to name it anything you'd like
      robot = new Robot();
    } catch(Exception e) {}
    Frame frame = controller.frame();*/
	  try { robot = new Robot(); } catch (Exception e) {}
		Frame frame = c.frame();
		//Leep Motion Interaction Box
		/*InteractionBox box = frame.interactionBox();*/
		//for (Finger f : frame.fingers()) {
			/*if(f.type() == Finger.Type.TYPE_INDEX) {
				Vector fingerPos = f.tipPosition();
				Vector boxFingerPos = box.normalizePoint(fingerPos);
				Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				//Java Robot class, leap motion vector
				robot.mouseMove((int) (screen.width * boxFingerPos.getX()),(int) (screen.height - boxFingerPos.getY() * screen.height));
				
			}*/
			try {
			boolean click = true;
			HandList hands = frame.hands();
			Hand firstHand = hands.get(0);
			int extendedFingers = 0;
			for (Finger finger : firstHand.fingers())
			{
			    if(finger.isExtended()) extendedFingers++;
			}
		
			if (extendedFingers >= 2 && click) {
				 robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				 Thread.sleep(50);
				 robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				 click = false;
			}else {click = true;}
			Thread.sleep(50);
		    }catch(Exception e) {}
		//}
	}
}// end of listener class



// The "main()" function
// NAME THIS CLASS WHATEVER YOUR FILE IS NAMED
class paint_hit {
	
  public static void main(String[] args){
    // initializes our detector
	  CustomListener_P listener = new CustomListener_P();
    Controller controller = new Controller();
    controller.addListener(listener);
    System.out.println("Press Enter to quit...");

    try {
      System.in.read();
    } catch(Exception e) {}
    controller.removeListener(listener);
  }
}
