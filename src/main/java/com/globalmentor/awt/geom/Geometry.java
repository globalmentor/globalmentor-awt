/*
 * Copyright © 1996-2011 GlobalMentor, Inc. <https://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.globalmentor.awt.geom;

import static com.globalmentor.java.Conditions.*;

import java.awt.*;
import java.awt.geom.Dimension2D;

import javax.annotation.*;

/**
 * Utilities that work with geometrical coordinates.
 * @author Garret Wilson
 */
public class Geometry {

	/** This class cannot be publicly instantiated. */
	private Geometry() {
	}

	/**
	 * Returns a the result of scaling the inner dimension so that no part lies outside the outer dimension. This method does not modify the original dimension.
	 * @implSpec This implementation may return an immutable dimension.
	 * @implSpec This implementation delegates to {@link #constrainedBy(Dimension2D, double, double)}.
	 * @param dimension The inner dimension to be constrained.
	 * @param maxDimension The outer constraining dimension.
	 * @return A dimension representing the constrained dimension.
	 * @throws IllegalArgumentException if the constraining dimension has a negative width or height.
	 */
	public static Dimension2D constrainedBy(final Dimension2D dimension, final Dimension2D maxDimension) {
		return constrainedBy(dimension, maxDimension.getWidth(), maxDimension.getHeight());
	}

	/**
	 * Returns a the result of scaling the inner dimension so that no part lies outside the constraining width and height. This method does not modify the
	 * original dimension.
	 * @implSpec This implementation may return an immutable dimension.
	 * @param dimension The inner dimension to be constrained.
	 * @param maxWidth The outer constraining width.
	 * @param maxHeight The outer constraining height.
	 * @return A dimension representing the constrained dimension.
	 * @throws IllegalArgumentException if the maximum width or height is negative.
	 */
	public static Dimension2D constrainedBy(final Dimension2D dimension, @Nonnegative final double maxWidth, @Nonnegative final double maxHeight) {
		final double width = dimension.getWidth();
		checkArgument(width > 0.0, "Cannot constrain dimension by a negative width.");
		final double height = dimension.getHeight();
		checkArgument(height > 0.0, "Cannot constrain dimension by a negative height.");
		if(maxWidth == 0.0 || maxHeight == 0.0) { //constraining to zero in either dimension results in a zero dimension
			return ImmutableDimension2D.ZERO;
		}
		if(width <= maxWidth && height <= maxHeight) { //if nothing needs to be constrained
			return dimension; //return the dimension unchanged
		}
		final double ratio = width / height; //determine the relationship of the sides
		final double constrainingRatio = maxWidth / maxHeight;
		final double constrainedWidth;
		final double constrainedHeight;
		if(constrainingRatio < ratio) { //width constrained
			constrainedWidth = maxWidth;
			constrainedHeight = constrainedWidth / ratio;
		} else if(constrainingRatio > ratio) { //height constrained
			constrainedHeight = maxHeight;
			constrainedWidth = constrainedHeight * ratio;
		} else { //same ratio
			assert constrainingRatio == ratio;
			constrainedWidth = maxWidth; //no need to do calculations --- use the constraining dimensions themselves
			constrainedHeight = maxHeight;
		}
		return ImmutableDimension2D.of(constrainedWidth, constrainedHeight);
	}

	/**
	 * Calculates the center point of the shape.
	 * @param shape A shape the center of which to return.
	 * @return The point representing the center of the rectangular bounding area of the shape.
	 * @see Shape#getBounds
	 */
	public static Point getCenter(final Shape shape) {
		final Rectangle bounds = shape.getBounds(); //get the bounds of the shape
		return getCenter(bounds.x, bounds.y, bounds.width, bounds.height); //return the center of the bounding rectangle
	}

	/**
	 * Calculates the center point of the bounding coordinates.
	 * @param x The horizontal location of the bounding area.
	 * @param y The vertical location of the bounding area.
	 * @param width The width of the bounding area.
	 * @param height The height of the bounding area.
	 * @return The point representing the center of the bounding area.
	 */
	public static Point getCenter(final int x, final int y, final int width, final int height) {
		return new Point(x + (width / 2), y + (height / 2)); //create a point in the center of the bounding area
	}

}
