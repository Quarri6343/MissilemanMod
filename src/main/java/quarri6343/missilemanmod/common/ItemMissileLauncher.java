package quarri6343.missilemanmod.common;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static quarri6343.missilemanmod.MissilemanMod.MODID;

public class ItemMissileLauncher extends Item {

    public static final int scanRange = 50;
    private static final String NAME = "itemmissilelauncher";
    private static final int COOLDOWN = 200;
    
    private int missileCount = 0;
    private List<EntityLiving> missileQueue = new ArrayList<>();
    private int tickcount = 0;

    public ItemMissileLauncher() {
        super();
        this.setRegistryName(MODID, NAME);
        this.setTranslationKey(NAME);
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        AxisAlignedBB playerRange = new AxisAlignedBB(playerIn.posX - scanRange, playerIn.posY - scanRange, playerIn.posZ - scanRange, playerIn.posX + scanRange, playerIn.posY + scanRange, playerIn.posZ + scanRange);
        List<EntityLiving> nearbyEntities = Minecraft.getMinecraft().world.getEntitiesWithinAABB(EntityLiving.class, playerRange);
        if (nearbyEntities.size() == 0)
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));

        for (int i = 0; i < nearbyEntities.size(); i++) {
            if (!worldIn.isRemote) {
                missileQueue.add(nearbyEntities.get(i));
            }
        }

        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        if(!worldIn.isRemote && ++tickcount % 5 == 0 && missileQueue.size() > 0){
            MissileEntity entityMissile = new MissileEntity(worldIn, (float) entityIn.posX + (++missileCount % 2 == 0 ? 1F : 0), entityIn.posY, (float) entityIn.posZ, (EntityLivingBase) entityIn, missileQueue.get(0));
            missileQueue.remove(0);
            worldIn.spawnEntity(entityMissile);
            worldIn.playSound(null, entityMissile.posX, entityMissile.posY, entityMissile.posZ, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.BLOCKS, 1.0F, 1.0F);
            ((EntityPlayer)entityIn).getCooldownTracker().setCooldown(this, COOLDOWN);
        }
    }
}
