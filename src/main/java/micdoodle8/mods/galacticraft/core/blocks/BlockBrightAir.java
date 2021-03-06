package micdoodle8.mods.galacticraft.core.blocks;

import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBrightAir extends BlockAir
{
    public BlockBrightAir(String assetName)
    {
        this.setResistance(1000.0F);
        this.setHardness(0.0F);
        this.setUnlocalizedName(assetName);
        this.setStepSound(new SoundType("sand", 0.0F, 1.0F));
        this.setLightLevel(1.0F);
    }

    @Override
    public boolean canReplace(World worldIn, BlockPos pos, EnumFacing side, ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return true;
    }

    @Override
    public int getMobilityFlag()
    {
        return 1;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.air);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return false;
    }

    @Override
    public int getLightValue(IBlockAccess world, BlockPos pos)
    {
        return 15 - getMetaFromState(world.getBlockState(pos));
    }
}
