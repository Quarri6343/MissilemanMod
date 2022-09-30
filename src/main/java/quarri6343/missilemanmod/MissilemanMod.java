package quarri6343.missilemanmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import quarri6343.missilemanmod.common.CommonProxy;
import quarri6343.missilemanmod.common.ItemMissileLauncher;

import static quarri6343.missilemanmod.MissilemanMod.MODID;

@Mod(modid = MODID,
        name = "MissilemanMod",
        acceptedMinecraftVersions = "[1.12,1.13)")
public class MissilemanMod {

    public static final String MODID = "missilemanmod";
    
    @SidedProxy(modId = MODID, clientSide = "quarri6343.missilemanmod.client.ClientProxy", serverSide = "quarri6343.missilemanmod.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static MissilemanMod instance;

    public static Logger logger;
    
    public static final ItemMissileLauncher itemMissileLauncher = new ItemMissileLauncher();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
