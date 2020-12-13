/*
 * Copyright © 2011 GlobalMentor, Inc. <http://www.globalmentor.com/>
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

package com.globalmentor.awt.geom;

import static java.util.Objects.*;

import java.awt.geom.Dimension2D;

/**
 * Immutable value class for storing a 2D dimension as a read-only value.
 * @implSpec This class performs {@link #equals(Object)} comparison based upon <code>==</code> equality of <code>double</code> values.
 * @author Garret Wilson
 */
public final class ImmutableDimension2D extends Dimension2D {

	/** An immutable, shared instance of coordinates for the origin <code>(0.0, 0.0)</code>. */
	public static ImmutableDimension2D ORIGIN = new ImmutableDimension2D(0.0, 0.0);

	/** The width of the dimension in double precision. */
	private final double width;

	/** The height of the dimension in double precision. */
	private final double height;

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	/**
	 * Returns an immutable dimension initialized with the specified width and specified height.
	 * @param width The specified width.
	 * @param height The specified height.
	 * @return An immutable dimension with the given coordinates.
	 */
	public static ImmutableDimension2D of(final double width, final double height) {
		if(width == 0 && height == 0) {
			return ORIGIN;
		}
		return new ImmutableDimension2D(width, height);
	}

	/**
	 * Returns an immutable dimension with the same coordinates of the given dimension.
	 * @implSpec If the given dimension is already immutable, it will be returned. Otherwise this implementation delegates to {@link #of(double, double)}.
	 * @param dimension The dimension the values of which will be copied.
	 * @return An immutable dimension with the given of the given dimension.
	 */
	public static ImmutableDimension2D of(final Dimension2D dimension) {
		if(dimension instanceof ImmutableDimension2D) {
			return (ImmutableDimension2D)dimension;
		}
		return of(dimension.getWidth(), dimension.getHeight());
	}

	/**
	 * Constructs a dimension initialized with the specified width and specified height.
	 * @param width The specified width.
	 * @param height The specified height.
	 */
	private ImmutableDimension2D(final double width, final double height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This version throws an exception, as this class is immutable.
	 * @throws UnsupportedOperationException to indicate that this value class is immutable.
	 */
	@Override
	public void setSize(double width, double height) {
		throw new UnsupportedOperationException("Class " + getClass() + " is immutable.");
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This version throws an exception, as this class is immutable.
	 * @throws UnsupportedOperationException to indicate that this value class is immutable.
	 */
	@Override
	public void setSize(final Dimension2D dimension2d) {
		throw new UnsupportedOperationException("Class " + getClass() + " is immutable.");
	}

	@Override
	public int hashCode() {
		return hash(width, height);
	}

	/**
	 * {@inheritDoc}
	 * @implSpec This implementation returns <code>true</code> if the other object is an instance of {@link Dimension2D} with the same 2D width and height
	 *           compared using <code>==</code> for equality. Thus negative and positive zero are considered the same.
	 * @implNote In general <code>double</code> values should be compared in a lenient manner to avoid rounding variations. For now this implementation uses
	 *           <code>==</code> for comparisons, but the possibility of using lenient comparisons may be considered in the future
	 * @see Dimension2D#getWidth()
	 * @see Dimension2D#getHeight()
	 */
	@Override
	public boolean equals(final Object object) {
		if(this == object) {
			return true;
		}
		if(!(object instanceof Dimension2D)) {
			return false;
		}
		final Dimension2D dimension2D = (Dimension2D)object;
		return getWidth() == dimension2D.getWidth() && getHeight() == dimension2D.getHeight();
	}

	/**
	 * Returns a the result of scaling this dimension so that no part lies outside the given constraining dimension.
	 * @implSpec This implementation delegates to {@link #constrainedBy(double, double)}
	 * @param maxDimension The constraining dimension.
	 * @return A dimension representing the constrained dimension.
	 */
	public ImmutableDimension2D constrainedBy(final Dimension2D maxDimension) {
		return constrainedBy(maxDimension.getWidth(), maxDimension.getHeight());
	}

	/**
	 * Returns a the result of scaling this dimension so that no part lies outside the constraining width and height.
	 * @implSpec This implementation delegates to {@link Geometry#constrainedBy(Dimension2D, double, double)}.
	 * @param maxWidth The outer constraining width.
	 * @param maxHeight The outer constraining height.
	 * @return A dimension representing the constrained dimension.
	 */
	public ImmutableDimension2D constrainedBy(final double maxWidth, final double maxHeight) {
		//Geometry.constrainedBy() should return an ImmutableDimension2D, but using ImmutableDimension2D.of()
		//obviates the need of putting additional requirements in the API of Geometry.constrainedBy().
		return ImmutableDimension2D.of(Geometry.constrainedBy(this, maxWidth, maxHeight));
	}

	@Override
	public String toString() {
		return "{width:" + getWidth() + ",height:" + getHeight() + "}";
	}

}
