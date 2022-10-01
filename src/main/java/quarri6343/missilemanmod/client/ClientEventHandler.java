package quarri6343.missilemanmod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import quarri6343.missilemanmod.MissilemanMod;

import javax.vecmath.Vector3f;
import java.util.List;

import static quarri6343.missilemanmod.common.ItemMissileLauncher.scanRange;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientEventHandler {
    
    @SubscribeEvent
    public static void onGameOverlayRender(RenderGameOverlayEvent.Post event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (Minecraft.getMinecraft(). player.getHeldItemMainhand().getItem().equals(MissilemanMod.itemMissileLauncher)) {
            AxisAlignedBB playerRange = new AxisAlignedBB(player.posX - scanRange, player.posY - scanRange, player.posZ - scanRange, player.posX + scanRange, player.posY + scanRange, player.posZ + scanRange);
            List<EntityLiving> nearbyEntities = Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityLiving.class, playerRange);
            
            if(nearbyEntities.size() > 0){
                Minecraft mc = Minecraft.getMinecraft();
                ProjectionUtil.setCameraLookAt(new Vector3f((float) player.posX, (float) player.posY + player.getEyeHeight(), (float) player.posZ),
                        new Vector3f((float) (player.posX + player.getLookVec().x), (float)(player.posY + player.getEyeHeight() + player.getLookVec().y), (float)(player.posZ + player.getLookVec().z)),
                        new Vector3f(0,1,0));
                
                for (int i = 0; i < nearbyEntities.size(); i ++){
                    Vector3f winpos = ProjectionUtil.EntityPos2ScreenPos(new Vector3f((float) nearbyEntities.get(i).posX, (float) nearbyEntities.get(i).posY + nearbyEntities.get(i).getEyeHeight(), (float) nearbyEntities.get(i).posZ), 0, 0, mc.displayWidth, mc.displayHeight);
                    ScaledResolution resolution = new ScaledResolution(mc);
                    //translate gui coordinates to window's ones (y is inverted)
                    int guiX = (int)(winpos.x / mc.displayWidth * resolution.getScaledWidth());
                    int guiY = (int)((mc.displayHeight - winpos.y) / mc.displayHeight * resolution.getScaledHeight());

                    ResourceHelper.bindTexture("textures/gui/crosshair.png");
                    GlStateManager.enableAlpha();
                    Gui.drawScaledCustomSizeModalRect(guiX - 8, guiY - 8, 0, 0, 16,16, 16,16, 16,16);
                    GlStateManager.disableAlpha();
                }
            }
            
            //fix cooldown bar not drawing
            GlStateManager.enableDepth();
        }
    }
}
