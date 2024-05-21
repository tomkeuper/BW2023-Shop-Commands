package com.tomkeuper.bedwars.addon.command;

import com.tomkeuper.bedwars.addon.ShopCommands;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.command.ParentCommand;
import com.tomkeuper.bedwars.api.command.SubCommand;
import com.tomkeuper.bedwars.api.shop.IPlayerQuickBuyCache;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShopMenu extends SubCommand {
    public ShopMenu(ParentCommand parent, String name) {
        super(parent, name);
        showInList(false);
    }

    @Override
    public boolean execute(String[] strings, CommandSender commandSender) {
        if (!(commandSender instanceof Player)) return false;

        IArena a = ShopCommands.getBedWars().getArenaUtil().getArenaByPlayer((Player) commandSender);
        if (a == null) return false;
        if (a.getStatus() != GameState.playing) return false;
        if (!a.isPlayer((Player) commandSender)) return false;

        AtomicBoolean found = new AtomicBoolean(false);
        if (ShopCommands.plugin.onlyOwnShop){
            ITeam team = a.getTeam((Player) commandSender);
            if (team == null) return false;
            if (team.getShop().distance(((Player)commandSender).getLocation()) < 4){
                IPlayerQuickBuyCache quickBuyCache = ShopCommands.getBedWars().getShopUtil().getPlayerQuickBuyCache().getQuickBuyCache(((Player) commandSender).getUniqueId());
                ShopCommands.getBedWars().getShopUtil().getShopManager().getShop().open((Player) commandSender, quickBuyCache,true);
                found.set(true);
            }
        } else {
            a.getTeams().forEach(team -> {
                if (team.getShop().distance(((Player)commandSender).getLocation()) < 4){
                    IPlayerQuickBuyCache quickBuyCache = ShopCommands.getBedWars().getShopUtil().getPlayerQuickBuyCache().getQuickBuyCache(((Player) commandSender).getUniqueId());
                    ShopCommands.getBedWars().getShopUtil().getShopManager().getShop().open((Player) commandSender, quickBuyCache,true);
                    found.set(true);
                }
            });
        }
        return false;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }
}
