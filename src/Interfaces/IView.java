package Interfaces;
import java.awt.event.ActionListener;
	public interface IView extends IObserver {
	    void setMoveListener(ActionListener listener);
	    void setResetListener(ActionListener listener);
	}
