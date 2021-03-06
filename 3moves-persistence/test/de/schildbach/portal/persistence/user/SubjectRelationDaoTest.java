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

package de.schildbach.portal.persistence.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.schildbach.portal.persistence.AbstractDaoTest;

/**
 * @author Andreas Schildbach
 */
public class SubjectRelationDaoTest extends AbstractDaoTest
{
	@Autowired
	private SubjectRelationDao subjectRelationDao;

	@Test
	public void shouldCreateReadAndDelete()
	{
		SubjectRelation relation = new SubjectRelation(new Date(), user1, user2, RelationType.FRIEND);

		int count = countRowsInTable(SubjectRelation.TABLE_NAME);

		int id = subjectRelationDao.create(relation);
		flushSession();

		assertEquals(++count, countRowsInTable(SubjectRelation.TABLE_NAME));
		assertFalse(id == 0);

		relation = subjectRelationDao.read(SubjectRelation.class, id);

		subjectRelationDao.delete(relation);
		flushSession();

		assertEquals(--count, countRowsInTable(SubjectRelation.TABLE_NAME));
	}
}
