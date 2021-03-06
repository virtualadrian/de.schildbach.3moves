/*
 * Copyright 2001-2011 the original author or authors.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.schildbach.game.presentation.list;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import de.schildbach.user.presentation.Environment;

/**
 * @author Andreas Schildbach
 */
@Controller
public class GamesTeaserController extends ParameterizableViewController
{
	private Map<Integer, Integer> resolutionThresholds;
	private Environment environment;

	@Required
	public void setResolutionThresholds(Map<Integer, Integer> resolutionThresholds)
	{
		this.resolutionThresholds = resolutionThresholds;
	}

	@Required
	public void setEnvironment(Environment environment)
	{
		this.environment = environment;
	}

	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
	{
		// determine number of columns
		int columns = 0;
		int resolution = environment.getScreenResolution();
		for (Map.Entry<Integer, Integer> threshold : resolutionThresholds.entrySet())
		{
			if (threshold.getKey() <= resolution && threshold.getValue() > columns)
				columns = threshold.getValue();
		}

		// return model
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("columns", columns);
		return new ModelAndView(getViewName(), model);
	}
}
