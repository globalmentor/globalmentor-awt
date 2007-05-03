package com.garretwilson.awt.geom;

import java.awt.*;

/**Utilities that work with geometrical coordinates.
@author Garret Wilson
*/

public class GeometryUtilities
{

	/**This class cannot be publicly instantiated.*/
	private GeometryUtilities()
	{
	}

	/**Constrains the given inner dimension within the given outer dimension by scaling the inner dimension so that no part lies outside the outer dimension.
	@param dimension The inner dimension to be constrained.
	@param constrainingDimension The outer constraining dimension.
	@return A dimension representing the constrained dimension.
	 */
	public static Dimension constrain(final Dimension dimension, final Dimension constrainingDimension)
	{
		final int width=dimension.width;
		final int height=dimension.height;
		final int constrainingWidth=constrainingDimension.width;
		final int constrainingHeight=constrainingDimension.height;
		if(width<=constrainingWidth && height<=constrainingHeight)	//if nothing needs to be constrained
		{
			return dimension;	//return the dimension unchanged
		}
		final double relation=(double)width/height;	//determine the relationship of the sides
//TODO del Debug.trace("relation of sides is:", relation);
		int newWidth, newHeight;
		newHeight=(int)(constrainingWidth/relation);	//get the matching height for a constrained width
//TODO del Debug.trace("trying to constrain width to", constrainingWidth, "height to ", newHeight);
		if(newHeight<=constrainingHeight)	//if the height has been constrained
		{
			newWidth=constrainingWidth;	//constrain the width to the edges
		}
		else	//if the height needs to be constrained
		{
			newWidth=(int)(constrainingHeight*relation);	//get the matching width for a constrained height
			newHeight=constrainingHeight;	//constrain the height to the edges
//TODO del Debug.trace("that didn't work; trying to constrain width to", newWidth, "height to ", newHeight);
		}
		return new Dimension(newWidth, newHeight);	//return the new constrained dimensions
	}
	
	/**Calculates the center point of the shape.
	@param shape A shape the center of which to return.
	@return The point representing the center of the rectangular bounding area of
		the shape.
	@see Shape#getBounds
	*/
	public static Point getCenter(final Shape shape)
	{
		final Rectangle bounds=shape.getBounds();  //get the bounds of the shape
		return getCenter(bounds.x, bounds.y, bounds.width, bounds.height);  //return the center of the bounding rectangle
	}

	/**Calculates the center point of the bounding coordinates.
	@param x The horizontal location of the bounding area.
	@param y The vertical location of the bounding area.
	@param width The width of the bounding area.
	@param height The height of the bounding area.
	@return The point representing the center of the bounding area.
	*/
	public static Point getCenter(final int x, final int y, final int width, final int height)
	{
		return new Point(x+(width/2), y+(height/2));  //create a point in the center of the bounding area
	}

}
