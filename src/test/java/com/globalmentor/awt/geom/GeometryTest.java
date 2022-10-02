/*
 * Copyright © 2020 GlobalMentor, Inc. <https://www.globalmentor.com/>
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

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

/**
 * Tests of {@link Geometry}.
 * @author Garret Wilson
 */
public class GeometryTest {

	/** @see Geometry#constrainedBy(java.awt.geom.Dimension2D, double, double) */
	@Test
	public void testConstrainedBy() {
		final double landscapeRatio = 3000.0 / 2000.0; //3000 x 2000 is 1.5 ratio
		//landscape
		assertThat("Landscape unconstrained.", Geometry.constrainedBy(ImmutableDimension2D.of(3000, 2000), 9000, 9000), is(ImmutableDimension2D.of(3000, 2000)));
		assertThat("Landscape exactly constrained.", Geometry.constrainedBy(ImmutableDimension2D.of(3000, 2000), 3000, 2000),
				is(ImmutableDimension2D.of(3000, 2000)));
		assertThat("Landscape constrained by portrait.", Geometry.constrainedBy(ImmutableDimension2D.of(3000, 2000), 400, 500),
				is(ImmutableDimension2D.of(400, 400 / landscapeRatio))); //constrained width
		assertThat("Landscape constrained by square.", Geometry.constrainedBy(ImmutableDimension2D.of(3000, 2000), 400, 400),
				is(ImmutableDimension2D.of(400, 400 / landscapeRatio))); //constrained width
		assertThat("Landscape constrained by narrower landscape.", Geometry.constrainedBy(ImmutableDimension2D.of(3000, 2000), 500, 400),
				is(ImmutableDimension2D.of(500, 500 / landscapeRatio))); //constrained width
		assertThat("Landscape constrained by same aspect ratio.", Geometry.constrainedBy(ImmutableDimension2D.of(3000, 2000), 600, 400),
				is(ImmutableDimension2D.of(600, 400)));
		assertThat("Landscape constrained by wider landscape.", Geometry.constrainedBy(ImmutableDimension2D.of(3000, 2000), 700, 400),
				is(ImmutableDimension2D.of(400 * landscapeRatio, 400))); //constrained height
		assertThat("Landscape constrained by zero width.", Geometry.constrainedBy(ImmutableDimension2D.of(3000, 2000), 0, 1000), is(ImmutableDimension2D.ZERO));
		assertThat("Landscape constrained by zero height.", Geometry.constrainedBy(ImmutableDimension2D.of(3000, 2000), 1000, 0), is(ImmutableDimension2D.ZERO));
		assertThat("Landscape constrained by zero.", Geometry.constrainedBy(ImmutableDimension2D.of(3000, 2000), 0, 0), is(ImmutableDimension2D.ZERO));
		//portrait
		final double portraitRatio = 2000.0 / 3000.0; //2000 x 3000 is 0.6... ratio
		assertThat("Portrait unconstrained.", Geometry.constrainedBy(ImmutableDimension2D.of(2000, 3000), 9000, 9000), is(ImmutableDimension2D.of(2000, 3000)));
		assertThat("Portrait exactly constrained.", Geometry.constrainedBy(ImmutableDimension2D.of(2000, 3000), 2000, 3000),
				is(ImmutableDimension2D.of(2000, 3000)));
		assertThat("Portrait constrained by narrower portrait.", Geometry.constrainedBy(ImmutableDimension2D.of(2000, 3000), 400, 700),
				is(ImmutableDimension2D.of(400, 400 / portraitRatio))); //constrained width
		assertThat("Portrait constrained by same aspect ratio.", Geometry.constrainedBy(ImmutableDimension2D.of(2000, 3000), 400, 600),
				is(ImmutableDimension2D.of(400, 600)));
		assertThat("Portrait constrained by wider portrait.", Geometry.constrainedBy(ImmutableDimension2D.of(2000, 3000), 400, 500),
				is(ImmutableDimension2D.of(500 * portraitRatio, 500))); //constrained height
		assertThat("Portrait constrained by square.", Geometry.constrainedBy(ImmutableDimension2D.of(2000, 3000), 400, 400),
				is(ImmutableDimension2D.of(400 * portraitRatio, 400))); //constrained height
		assertThat("Portrait constrained by landscape.", Geometry.constrainedBy(ImmutableDimension2D.of(2000, 3000), 500, 400),
				is(ImmutableDimension2D.of(400 * portraitRatio, 400))); //constrained height
		assertThat("Portrait constrained by zero width.", Geometry.constrainedBy(ImmutableDimension2D.of(2000, 3000), 0, 1000), is(ImmutableDimension2D.ZERO));
		assertThat("Portrait constrained by zero height.", Geometry.constrainedBy(ImmutableDimension2D.of(2000, 3000), 1000, 0), is(ImmutableDimension2D.ZERO));
		assertThat("Portrait constrained by zero width.", Geometry.constrainedBy(ImmutableDimension2D.of(2000, 3000), 0, 0), is(ImmutableDimension2D.ZERO));
	}

}
