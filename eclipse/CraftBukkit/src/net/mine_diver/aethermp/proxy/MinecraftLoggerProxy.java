package net.mine_diver.aethermp.proxy;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import net.minecraft.server.mod_AetherMp;

public class MinecraftLoggerProxy extends Handler {

	@Override
	public void close() throws SecurityException {
		
	}

	@Override
	public void flush() {
		
	}

	@Override
	public void publish(LogRecord record) {
		if (record.getMessage().equals("Starting minecraft server version Beta 1.7.3")) {
			Logger.getLogger(record.getLoggerName()).removeHandler(this);
			mod_AetherMp.CORE.init();
		}
	}
	
}
