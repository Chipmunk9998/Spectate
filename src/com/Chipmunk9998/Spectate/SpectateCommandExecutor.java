package com.Chipmunk9998.Spectate;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Chipmunk9998.Spectate.api.SpectateManager;

public class SpectateCommandExecutor implements CommandExecutor {

	Spectate plugin;

	public SpectateCommandExecutor(Spectate plugin) {

		this.plugin = plugin;

	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (!(sender instanceof Player)) {

			sender.sendMessage(ChatColor.RED + "You can't execute this command from the console.");
			return true;

		}

		Player cmdsender = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("spectate")) {

			if (args.length == 0) {
				
				if (!cmdsender.hasPermission("spectate.help") && !cmdsender.hasPermission("spectate.use")) {
					
					cmdsender.sendMessage(ChatColor.RED + "You do not have permission.");
					return true;
					
				}

				showHelp(cmdsender);
				return true;

			}

			if (args[0].equalsIgnoreCase("off")) {
				
				if (!cmdsender.hasPermission("spectate.use")) {
					
					cmdsender.sendMessage(ChatColor.RED + "You do not have permission.");
					return true;
					
				}

				if (!SpectateManager.isSpectating(cmdsender)) {

					cmdsender.sendMessage(ChatColor.GRAY + "You are not currently spectating anyone.");
					return true;

				}

				cmdsender.sendMessage(ChatColor.GRAY + "You have stopped spectating " + SpectateManager.getTarget(cmdsender).getName() + ".");
				SpectateManager.stopSpectating(cmdsender, true);
				return true;

			}else if (args[0].equalsIgnoreCase("mode")) {
				
				if (!cmdsender.hasPermission("spectate.mode")) {
					
					cmdsender.sendMessage(ChatColor.RED + "You do not have permission.");
					return true;
					
				}

				if (args.length < 2) {

					cmdsender.sendMessage(ChatColor.RED + "Error: You must enter in a mode.");
					return true;

				}

				int newMode = 0;

				if (args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("default")) {

					if (SpectateManager.getSpectateMode(cmdsender) == 1) {

						cmdsender.sendMessage(ChatColor.RED + "Error: You are already in this mode.");

					}else {

						newMode = 1;
						cmdsender.sendMessage(ChatColor.GRAY + "You are now using the default spectate mode.");

					}

				}else if (args[1].equalsIgnoreCase("2") || args[1].equalsIgnoreCase("scroll")) {

					if (SpectateManager.getSpectateMode(cmdsender) == 2) {

						cmdsender.sendMessage(ChatColor.RED + "Error: You are already in this mode.");

					}else {

						newMode = 2;
						cmdsender.sendMessage(ChatColor.GRAY + "You are now using the scroll spectate mode.");

					}

				}else {

					cmdsender.sendMessage(ChatColor.RED + "Error: Unknown mode \"" + args[1] + "\"");
					return true;

				}

				SpectateManager.setSpectateMode(cmdsender, newMode);
				return true;

			}else if (args[0].equalsIgnoreCase("angle")) {
				
				if (!cmdsender.hasPermission("spectate.angle")) {
					
					cmdsender.sendMessage(ChatColor.RED + "You do not have permission.");
					return true;
					
				}

				if (args.length < 2) {

					cmdsender.sendMessage(ChatColor.RED + "Error: You must enter in an angle.");
					return true;

				}
				
				int newAngle = 0;

				if (args[1].equalsIgnoreCase("1") || args[1].equalsIgnoreCase("firstperson")) {

					if (SpectateManager.getSpectateAngle(cmdsender) == 1) {

						cmdsender.sendMessage(ChatColor.RED + "Error: You are already in first person mode.");

					}else {

						newAngle = 1;
						
						cmdsender.sendMessage(ChatColor.GRAY + "You are now in first person mode.");

					}

				}else if (args[1].equalsIgnoreCase("2") || args[1].equalsIgnoreCase("thirdperson")) {

					if (SpectateManager.getSpectateAngle(cmdsender) == 2) {

						cmdsender.sendMessage(ChatColor.RED + "Error: You are already in third person mode.");

					}else {

						newAngle = 2;
						cmdsender.sendMessage(ChatColor.GRAY + "You are now in third person mode.");

					}

				}else if (args[1].equalsIgnoreCase("3") || args[1].equalsIgnoreCase("thirdpersonfront")) {

					if (SpectateManager.getSpectateAngle(cmdsender) == 3) {

						cmdsender.sendMessage(ChatColor.RED + "Error: You are already in third person front mode.");

					}else {

						newAngle = 3;
						cmdsender.sendMessage(ChatColor.GRAY + "You are now in third person front mode.");

					}

				}else if (args[1].equalsIgnoreCase("4") || args[1].equalsIgnoreCase("freeroam")) {

					if (SpectateManager.getSpectateAngle(cmdsender) == 4) {

						cmdsender.sendMessage(ChatColor.RED + "Error: You are already in free roam mode.");

					}else {

						newAngle = 4;
						cmdsender.sendMessage(ChatColor.GRAY + "You are now in free roam mode.");

					}

				}else {

					cmdsender.sendMessage(ChatColor.RED + "Error: Unknown angle \"" + args[1] + "\"");
					return true;

				}

				SpectateManager.setSpectateAngle(cmdsender, newAngle);
				return true;

			}else if (args[0].equalsIgnoreCase("scan")) {
				
				if (!cmdsender.hasPermission("spectate.scan")) {
					
					cmdsender.sendMessage(ChatColor.RED + "You do not have permission.");
					return true;
					
				}

				if (args.length < 2) {

					cmdsender.sendMessage(ChatColor.RED + "Error: You must enter in an interval.");
					return true;

				}
				
				int interval = 0;
				
				try {

					interval = Integer.parseInt(args[1]);

				}catch (NumberFormatException e) {

					cmdsender.sendMessage(ChatColor.RED + "Error: " + args[1] + " is not a number.");
					return true;

				}

				SpectateManager.startScanning(cmdsender, interval);
				return true;

			}else if (args[0].equalsIgnoreCase("help")) {
				
				if (!cmdsender.hasPermission("spectate.help") && !cmdsender.hasPermission("spectate.use")) {
					
					cmdsender.sendMessage(ChatColor.RED + "You do not have permission.");
					return true;
					
				}

				showHelp(cmdsender);
				return true;

			}
			
			if (!cmdsender.hasPermission("spectate.use")) {
				
				cmdsender.sendMessage(ChatColor.RED + "You do not have permission.");
				return true;
				
			}

			Player targetPlayer = plugin.getServer().getPlayer(args[0]);

			if (targetPlayer == null) {

				cmdsender.sendMessage(ChatColor.RED + "Error: Player is not online.");
				return true;

			}

			if (cmdsender.getName() == targetPlayer.getName()) {

				cmdsender.sendMessage(ChatColor.GRAY + "Did you really just try to spectate yourself?");
				return true;

			}

			if (SpectateManager.isSpectating(cmdsender)) {

				if (targetPlayer.getName().equals(SpectateManager.getTarget(cmdsender).getName())) {

					cmdsender.sendMessage(ChatColor.GRAY + "You are already spectating them.");
					return true;

				}

			}

			if (SpectateManager.isSpectating(targetPlayer)) {

				cmdsender.sendMessage(ChatColor.GRAY + "They are currently spectating someone.");
				return true;

			}

			if (targetPlayer.isDead()) {

				cmdsender.sendMessage(ChatColor.GRAY + "They are currently dead.");
				return true;

			}

			SpectateManager.savePlayerState(cmdsender);
			SpectateManager.startSpectating(cmdsender, plugin.getServer().getPlayer(args[0]));

			return true;

		}else if (cmd.getName().equalsIgnoreCase("control")) {

			//TODO: Finish controlling

		}

		return true;

	}
	
	public void showHelp(Player cmdsender) {
		
		cmdsender.sendMessage(ChatColor.RED + "Commands for Spectate:");
		cmdsender.sendMessage(ChatColor.RED + "/spectate [PlayerName]: " + ChatColor.GRAY + "Puts you into spectate mode and lets you see what the target sees.");
		cmdsender.sendMessage(ChatColor.RED + "/spectate off : " + ChatColor.GRAY + "Takes you out of spectate mode.");
		cmdsender.sendMessage(ChatColor.RED + "/spectate scan [interval] : " + ChatColor.GRAY + "Starts the scanning mode with the specified interval.");
		cmdsender.sendMessage(ChatColor.RED + "/spectate mode [1 | default]: " + ChatColor.GRAY + "Puts you into the default spectate mode.");
		cmdsender.sendMessage(ChatColor.RED + "/spectate mode [2 | scroll]: " + ChatColor.GRAY + "Puts you into scroll style mode with left click and right click controls.");
		cmdsender.sendMessage(ChatColor.RED + "/spectate help : " + ChatColor.GRAY + "Shows this help page.");
		
	}

}
