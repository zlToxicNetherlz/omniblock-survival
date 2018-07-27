package net.omniblock.survival.systems.commands;

import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.survival.SurvivalPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Esta clase gestiona el sistema
 * de back, tanto comandos
 * como listeners
 *
 * @author SoZyk
 *
 */
public class Back implements CommandExecutor, Listener {

    private static Map<Player, Location> backLocations = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        //BACK Command
        if(cmd.getName().equalsIgnoreCase("back")){

            if(!backLocations.containsKey(player)){

                player.sendMessage(TextUtil.format("&cAhora mismo no tienes ninguna lugar al cual volver."));
                addPlayerLocation(player);
                return true;
            }

            player.sendMessage(TextUtil.format("&eSerás teletransportado en 3 segundos. ¡No te muevas!"));

            new BukkitRunnable() {
                int seconds = 3;
                Location loc = player.getLocation().clone();

                @Override
                public void run() {

                    if(!player.isOnline()){
                        cancel();
                        return;
                    }

                    if(seconds < 3 &&
                            (loc.getX() != player.getLocation().getX() ||
                            loc.getY() != player.getLocation().getY() ||
                            loc.getZ() != player.getLocation().getZ())){

                        player.sendMessage(TextUtil.format("&c¡Te has movido! Teletransporte cancelado."));
                        cancel();
                        return;
                    }

                    if(seconds==1)player.sendMessage(TextUtil.format("&eTeletransportando..."));

                    if(seconds <= 0){
                        player.teleport(backLocations.get(player));
                        backLocations.put(player, loc);

                        cancel();
                        return;
                    }

                    seconds--;
                }
            }.runTaskTimer(SurvivalPlugin.getInstance(), 0, 20);

            return true;
        }
        return false;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!backLocations.containsKey(e.getPlayer())) addPlayerLocation(e.getPlayer());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        addPlayerLocation(e.getPlayer(), e.getFrom());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        addPlayerLocation(e.getEntity());
    }

    /**
     *
     * Metodo que sirve para guardar la
     * la locacion de un jugador
     * para la función de back.
     *
     * @param player
     *          Jugador del que se registra la locación.
     */
    public static void addPlayerLocation(Player player){
        backLocations.put(player, player.getLocation().clone());
    }

    /**
     *
     * Metodo que sirve para guardar la
     * locacipon de un jugador para
     * la funcion de back.
     *
     * @param player
     *          Jugador del que se registra la locación.
     * @param loc
     *          La locación del jugador.
     */
    public static void addPlayerLocation(Player player, Location loc){
        backLocations.put(player, loc);
    }

    /**
     *
     * Metodo para guardar las locaciones
     * de todos los jugadores en caso
     * de que se haga reload del
     * plugin y se eliminen las locaciones
     * previamente guardadas
     *
     */
    public static void saveLocations(){
        for(Player player : Bukkit.getServer().getOnlinePlayers())
            addPlayerLocation(player);
    }
}
