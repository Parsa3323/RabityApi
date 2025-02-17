package ir.rabity.api.menu;


import ir.rabity.api.exceptions.MenuManagerException;
import ir.rabity.api.exceptions.MenuManagerNotSetupException;
import ir.rabity.api.menu.Menu;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {

        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof Menu) {
            Menu menu = (Menu) holder;
            if (e.getCurrentItem() == null) {
                return;
            }

            if (menu.cancelAllClicks()) {
                e.setCancelled(true);
            }


            try {
                menu.handleMenu(e);
            } catch (MenuManagerNotSetupException menuManagerNotSetupException) {
                System.out.println(ChatColor.RED + "THE MENU MANAGER HAS NOT BEEN CONFIGURED. CALL MENUMANAGER.SETUP()");
            } catch (MenuManagerException menuManagerException) {
                menuManagerException.printStackTrace();
            }
        }

    }

    @EventHandler
    public void onMenuClose(InventoryCloseEvent e) {
        InventoryHolder holder = e.getInventory().getHolder();

        if (holder instanceof Menu) {
            Menu menu = (Menu) holder;
            menu.handleMenuClose();
        }
    }

}