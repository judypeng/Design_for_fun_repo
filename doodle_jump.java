import java.awt.Robot;
import com.leapmotion.leap.*;
import java.awt.event.*;
import com.leapmotion.leap.Gesture.State;
import java.awt.Dimension;
import java.awt.AWTException;

class CustomListener extends Listener {
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
		InteractionBox box = frame.interactionBox();
		
			/*if(f.type() == Finger.Type.TYPE_INDEX) {
				Vector fingerPos = f.tipPosition();
				Vector boxFingerPos = box.normalizePoint(fingerPos);
				Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				//Java Robot class, leap motion vector
				robot.mouseMove((int) (screen.width * boxFingerPos.getX()),(int) (screen.height - boxFingerPos.getY() * screen.height));
				
			}*/
		int extendedFingers = 0;
		HandList hands = frame.hands();
		Hand firstHand = hands.get(0);
		for (Finger finger : firstHand.fingers())
		{
		    if(finger.isExtended()) extendedFingers++;
		}
	
		if (extendedFingers == 0 && firstHand.isValid()) {
			 robot.keyPress(KeyEvent.VK_SPACE);
			 try {
			 Thread.sleep(50);}catch (InterruptedException e) {
				e.printStackTrace();
		     }
			 robot.keyRelease(KeyEvent.VK_SPACE);	 
		}
		
		Finger fingerWithSmallestX = frame.fingers().leftmost();
		Finger fingerWithLargestX = frame.fingers().rightmost();
		Vector leftPos = fingerWithSmallestX.tipPosition();
		Vector rightPos = fingerWithLargestX.tipPosition();
		
		//Vector boxleftPos = box.normalizePoint(leftPos);
		//Vector boxrightPos = box.normalizePoint(rightPos);
		
		int diff = (int) (leftPos.getY() - rightPos.getY());
		//System.out.println(diff);

			if (diff < -7) {
				robot.keyPress(KeyEvent.VK_LEFT);
				try {
					Thread.sleep((int)Math.abs(diff*0.25));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//System.out.println("leftkey pressed" + diff);
				robot.keyRelease(KeyEvent.VK_LEFT);
			}else if (diff > 10) {
				robot.keyPress(KeyEvent.VK_RIGHT);
				try {
					Thread.sleep((int)Math.abs(diff*0.25));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//System.out.println("right pressed" + diff);
				robot.keyRelease(KeyEvent.VK_RIGHT);
			}
			
			//Thread.sleep(40);
	}
}// end of listener class



// The "main()" function
// NAME THIS CLASS WHATEVER YOUR FILE IS NAMED
class doodle_jump {
	
  public static void main(String[] args){
    // initializes our detector
	  CustomListener listener = new CustomListener();
    Controller controller = new Controller();
    controller.addListener(listener);
    System.out.println("Press Enter to quit...");

    try {
      System.in.read();
    } catch(Exception e) {}
    controller.removeListener(listener);
  }
}
