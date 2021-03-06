/*
 * Copyright 2011-2012 the original author or authors.
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
package net.ml.vertx.mods.redis.commands.lists;


import java.util.concurrent.Future;

import net.ml.vertx.mods.redis.CommandContext;
import net.ml.vertx.mods.redis.commands.Command;
import net.ml.vertx.mods.redis.commands.CommandException;

import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.json.JsonObject;

/**
 * LInsertCommand
 * <p>
 * 
 * @author <a href="http://marx-labs.de">Thorsten Marx</a>
 */
public class LInsertCommand extends Command {

	public static final String COMMAND = "linsert";

	public LInsertCommand() {
		super(COMMAND);
	}

	@Override
	public void handle(final Message<JsonObject> message, CommandContext context) throws CommandException {
		String key = getMandatoryString("key", message);
		checkNull(key, "keys can not be null");
		
		String pivot = getMandatoryString("pivot", message);
		checkNull(pivot, "pivot can not be null");
		
		String value = getMandatoryString("value", message);
		checkNull(value, "value can not be null");
		
		Boolean before = message.body.getBoolean("before");
		checkNull(before, "before can not be null");
		
		
		try {
			
			final Future<Long> response = context.getConnection().linsert(key, before, pivot, value);
			
			response(message, response.get());
		} catch (Exception e) {
			sendError(message, e.getLocalizedMessage());
		}

	}
}
