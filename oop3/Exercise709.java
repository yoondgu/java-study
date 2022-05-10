package oop3;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/* 
 * exersice 7_9 아래의 EventHandler를 익명 클래스로 변경하시오.
 */
class Exercise709 {
	
	public static void main(String[] args) {
		Frame f = new Frame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);
				e.getWindow().dispose();
				System.exit(0);
			}
		});
	}
}

//class EventHandler extends WindowAdapter {
//	public void windowClosing(WindowEvent e) {
//		e.getWindow().setVisible(false);
//		e.getWindow().dispose();
//		System.exit(0);
//	}
//}
