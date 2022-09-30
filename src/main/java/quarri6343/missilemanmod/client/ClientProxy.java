package quarri6343.missilemanmod.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import quarri6343.missilemanmod.MissilemanMod;
import quarri6343.missilemanmod.common.CommonProxy;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import quarri6343.missilemanmod.common.MissileEntity;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        RenderingRegistry.registerEntityRenderingHandler(MissileEntity.class, manager -> new RenderTNTPrimed(manager));
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(MissilemanMod.itemMissileLauncher, 0, new ModelResourceLocation(new ResourceLocation(MissilemanMod.MODID, "itemmissilelauncher"), "inventory"));
    }
}
