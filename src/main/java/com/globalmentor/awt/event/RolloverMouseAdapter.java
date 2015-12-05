/*
 * Copyright © 1996-2009 GlobalMentor, Inc. <http://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.globalmentor.awt.event;

import java.awt.*;
import java.awt.event.*;

import com.globalmentor.java.Objects;

/**
 * Adapter that modifies a component when the mouse rolls over the component. Usually the action is simply highlighting the component in a different color.
 * <p>
 * Install the rollover mouse adapter by adding it as a listener to the component, as in <code>label.addMouseListener(rolloverMouseAdapter);</code>.
 * </p>
 * @author Garret Wilson
 */
public class RolloverMouseAdapter extends MouseAdapter {

	/** The rollover color; defaults to <code>Color.red</code>. */
	private Color rolloverColor = Color.red;

	/** @return The rollover color; defaults to <code>Color.red</code>. */
	public Color getRolloverColor() {
		return rolloverColor;
	}

	/**
	 * Sets the rollover color property.
	 * @param newRolloverColor The new rollover color, or <code>null</code> if the component color should not be changed for mouse rollovers.
	 */
	public void setRolloverColor(final Color newRolloverColor) {
		rolloverColor = newRolloverColor;
	}

	/** Notes the old foreground color in use before we change to the rollover color. */
	protected Color oldForegroundColor = null;

	/** Default constructor. */
	public RolloverMouseAdapter() {
	}

	/**
	 * Invoked when the mouse enters a component. Sets the color to the foreground color.
	 * @param mouseEvent The mouse event.
	 */
	public void mouseEntered(final MouseEvent mouseEvent) {
		final Component component = mouseEvent.getComponent(); //get the component the event is from
		if(component != null) { //if we have a valid component
			final Color rolloverColor = getRolloverColor(); //get the rollover color
			if(rolloverColor != null) { //if we have a rollover color
				oldForegroundColor = component.getForeground(); //save the old foreground color
				component.setForeground(rolloverColor); //update the color to the rollover color
			}
		}
	}

	/**
	 * Invoked when the mouse exits a component. Sets the foreground color back to the original foreground color if the foreground color has not been modified.
	 * @param mouseEvent The mouse event.
	 */
	public void mouseExited(final MouseEvent mouseEvent) {
		final Component component = mouseEvent.getComponent(); //get the component the event is from
		if(component != null) { //if we have a valid component
			final Color foregroundColor = component.getForeground(); //get the current foreground color
			//if we still have the old foreground color, and the color is still the rollover color (don't do anything if they've changed the color during the rollover)
			if(oldForegroundColor != null && Objects.equals(component.getForeground(), getRolloverColor())) {
				component.setForeground(oldForegroundColor); //set the foreground color back to the way it was
			}
		}
	}

}