package com.tomkeuper.bedwars.addon.command;

import com.tomkeuper.bedwars.addon.ShopCommands;
import com.tomkeuper.bedwars.api.arena.GameState;
import com.tomkeuper.bedwars.api.arena.IArena;
import com.tomkeuper.bedwars.api.arena.team.ITeam;
import com.tomkeuper.bedwars.api.command.ParentCommand;
import com.tomkeuper.bedwars.api.command.SubCommand;
import com.tomkeuper.bedwars.api.shop.IPlayerQuickBuyCache;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ShopMenu extends SubCommand {
    public ShopMenu(ParentCommand parent, String name) {
        super(parent, name);
    }

    @Override
    public boolean execute(String[] strings, CommandSender commandSender) {
        if (!(commandSender instanceof Player)) return false;

        IArena a = ShopCommands.getBedWars().getArenaUtil().getArenaByPlayer((Player) commandSender);
        if (a == null) return false;
        if (a.getStatus() != GameState.playing) return false;
        if (!a.isPlayer((Player) commandSender)) return false;
        ITeam t = a.getTeam((Player) commandSender);
        if (t.getShop().distance(((Player)commandSender).getLocation()) < 4){
            IPlayerQuickBuyCache quickBuyCache = ShopCommands.getBedWars().getShopUtil().getPlayerQuickBuyCache().getQuickBuyCache(((Player) commandSender).getUniqueId());
            ShopCommands.getBedWars().getShopUtil().getShopManager().getShop().open((Player) commandSender, quickBuyCache,true);

            return true;
        }
        return false;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }
}
