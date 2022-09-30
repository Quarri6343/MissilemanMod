package quarri6343.missilemanmod.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import static quarri6343.missilemanmod.MissilemanMod.MODID;

public class ItemMissileLauncher extends Item {
    
    private static final String NAME = "itemmissilelauncher";
    private static final int COOLDOWN = 200;
    
    public ItemMissileLauncher(){
        super();
        this.setRegistryName(MODID, NAME);
        this.setTranslationKey(NAME);
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if (!worldIn.isRemote) {
            EntityTNTPrimed entityMissile = new MissileEntity(worldIn, (float)playerIn.posX + 0.5F, playerIn.posY, (float)playerIn.posZ + 0.5F, playerIn);
            worldIn.spawnEntity(entityMissile);
            worldIn.playSound(null, entityMissile.posX, entityMissile.posY, entityMissile.posZ, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.BLOCKS, 1.0F, 1.0F);
            playerIn.getCooldownTracker().setCooldown(this, COOLDOWN);
        }
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
}
