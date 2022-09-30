package quarri6343.missilemanmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import quarri6343.missilemanmod.common.CommonProxy;

@Mod(modid = "missilemanmod",
        name = "MissilemanMod",
        acceptedMinecraftVersions = "[1.12,1.13)")
public class MissilemanMod {

    @SidedProxy(modId = "missilemanmod", clientSide = "quarri6343.missilemanmod.client.ClientProxy", serverSide = "quarri6343.missilemanmod.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static MissilemanMod instance;

    public static Logger logger;

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
