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

package de.schildbach.portal.service.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.schildbach.portal.message.user.MessageDao;
import de.schildbach.portal.persistence.user.User;
import de.schildbach.portal.persistence.user.UserDao;
import de.schildbach.portal.service.user.event.UserMessageEvent;

/**
 * @author Andreas Schildbach
 */
@Transactional
@Service
public class UserEventServiceImpl implements UserEventService
{
	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(UserEventServiceImpl.class);

	private UserDao userDao;
	private MessageDao messageDao;

	@Required
	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
	}

	@Required
	public void setMessageDao(MessageDao messageDao)
	{
		this.messageDao = messageDao;
	}

	public void onApplicationEvent(ApplicationEvent event)
	{
		if (event instanceof UserMessageEvent)
		{
			handleUserMessageEvent((UserMessageEvent) event);
		}
	}

	private void handleUserMessageEvent(UserMessageEvent event)
	{
		User user = userDao.read(User.class, event.username);

		messageDao.sendMessageAllChannels(user, event.subject, event.text);
	}
}
