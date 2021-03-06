/*
 * Copyright (C) 2005-2014 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.bm.user;

import org.alfresco.bm.event.RaiseEventsEventProcessor;

/**
 * Triggers random user sessions.
 * <p/>
 * Each generated event is tagged with a unique ID that should be carried throughout
 * the lifecycle of the user browser session.
 * 
 * <h1>Input</h1>
 * 
 * None.
 * 
 * <h1>Actions</h1>
 * 
 * Each new user 'session' is created by choosing a random user
 * 
 * <h1>Output</h1>
 * 
 * Events with data having the 'username' string as data.
 *
 * @author Derek Hulley
 * @since 1.0
 */
public class GenerateUserSessionsEventProcessor extends RaiseEventsEventProcessor
{
    private final UserDataService userDataService;
    
    /**
     * @param userDataService           provides access to random users
     * @param outputEventName           the name of the output events to generate
     * @param timeBetweenEvents         delay between user sessions
     * @param outputEventCount          number of user sessions to trigger
     */
    public GenerateUserSessionsEventProcessor(
            UserDataService userDataService,
            String outputEventName,
            long timeBetweenEvents,
            int outputEventCount)
    {
        super(outputEventName, timeBetweenEvents, outputEventCount);
        this.userDataService = userDataService;
    }

    /**
     * Chooses a random user.
     */
    @Override
    protected Object getNextEventData()
    {
        UserData user = userDataService.getRandomUser();
        if (user == null)
        {
            throw new IllegalStateException("No users available to generate user sessions.");
        }
        String username = user.getUsername();
        // Done
        return username;
    }
}
