package quarri6343.missilemanmod.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import static quarri6343.missilemanmod.MissilemanMod.MODID;

public class ItemMissileLauncher extends Item {
    
    private static final String name = "itemmissilelauncher";
    
    public ItemMissileLauncher(){
        super();
        this.setRegistryName(MODID, name);
        this.setTranslationKey(name);
        this.setCreativeTab(CreativeTabs.MATERIALS);
    }
}
